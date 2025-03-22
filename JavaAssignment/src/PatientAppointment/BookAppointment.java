package PatientAppointment;

import AdminAppointment.WalkInAppointment;
import Appointment.Appointment;
import Role.Doctor;
import Role.UserFileReader;
import Schedule.DoctorSchedule;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class BookAppointment extends JPanel {

    private JButton backButton;
    private JComboBox<LocalDate> appoinmentDateField;
    private JComboBox<LocalTime> startTimeComboBox;
    private JComboBox<LocalTime> endTimeComboBox;
    private JComboBox<String> doctorNameComboBox;
    private String patientID;
    private String doctorID;
    private List<Appointment> appointments;
    private List<DoctorSchedule> doctorSchedules;
    private JPanel bookAppointment;

//    public static void main(String[] args) throws IOException {
//        WalkInAppointment walkIn = new WalkInAppointment();
//    }
    public BookAppointment(String patientID) throws IOException {

        bookAppointment = new JPanel();
        bookAppointment.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH));
        contentPanel.setBackground(new Color(157, 226, 191, 95));

        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");

        JTable appointmentTable = createAppointmentTable();
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentTable);
        appointmentScrollPane.setPreferredSize(new Dimension(1450, 540));
        appointmentScrollPane.getViewport().setBackground(new Color(219, 208, 255, 90));
        appointmentScrollPane.setBorder(BorderFactory.createLineBorder(new Color(219, 208, 255, 90)));

        JButton addAppointmentBtn = new JButton("Add Appointment");
        addAppointmentBtn.setFont(new Font("Segeo UI", Font.BOLD, 16));
        addAppointmentBtn.setPreferredSize(new Dimension(180, 30));
        addAppointmentBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addAppointmentBtn.setBackground(Color.BLACK);
        addAppointmentBtn.setForeground(Color.WHITE);
        addAppointmentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appointmentTable.getSelectedRow();

                String doctorName = appointmentTable.getValueAt(selectedRow, 0).toString();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                String appointmentDateStr = (String) appointmentTable.getValueAt(selectedRow, 1);
                LocalDate appointmentDate = LocalDate.parse(appointmentDateStr, dateFormatter);

                String startTimeStr = (String) appointmentTable.getValueAt(selectedRow, 2);
                LocalTime startTime = LocalTime.parse(startTimeStr, timeFormatter);

                String endTimeStr = (String) appointmentTable.getValueAt(selectedRow, 3);
                LocalTime endTime = LocalTime.parse(endTimeStr, timeFormatter);

                List<DoctorSchedule> schedules = DoctorSchedule.readSchedulesFromFile("src/TextFiles/Schedules.txt");
                // Find the corresponding schedule
                DoctorSchedule selectedSchedule = null;
                for (DoctorSchedule schedule : schedules) {
                    try {
                        if (schedule.getDoctorName().equals(doctorName)
                                && schedule.getAvailableDate().equals(appointmentDate)
                                && schedule.getStartTime().equals(startTime)
                                && schedule.getEndTime().equals(endTime)) {
                            selectedSchedule = schedule;
                            break;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(WalkInAppointment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // Remove the selected schedule
                if (selectedSchedule != null) {
                    ((DefaultTableModel) appointmentTable.getModel()).removeRow(selectedRow);
                    schedules.remove(selectedSchedule);

                    // Write the updated schedules back to the file
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("src/TextFiles/Schedules.txt"));
                        for (DoctorSchedule schedule : schedules) {
                            int rowCount = getRowCount("src/TextFiles/Schedules.txt");
                            int currentScheduleID = rowCount + 1;
                            String scheduleID = "SC" + String.format("%03d", currentScheduleID);
                            writer.write(schedule.toString() + "\n");
                        }
                        writer.close();
                        revalidate();
                        repaint();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                for (Doctor doctor : doctors) {
                    if (doctor.getName().equals(doctorName)) {
                        doctorID = doctor.getId();
                    }
                }
//
//                //                    Increment rowCount
                int rowCount = getRowCount("src/TextFiles/Appointments.txt");
                int currentAppointmentID = rowCount + 1;
                String appointmentID = "AP" + String.format("%03d", currentAppointmentID);
//
                String appointmentStatus = "CONFIRMED";
                String visitType = "Scheduled";
                // Write data to a text file
                try {
                    Appointment.writeAppointmentToFileAppend("src/TextFiles/Appointments.txt", Arrays.asList(new Appointment(appointmentID, patientID, doctorID, appointmentDate, startTime, endTime, appointmentStatus, visitType)));
                    JOptionPane.showMessageDialog(null, "Appointment made successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while making appointment.");
                }
            }

        });
        contentPanel.add(appointmentScrollPane);
        bookAppointment.add(addAppointmentBtn, BorderLayout.SOUTH);

        bookAppointment.add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JTable createAppointmentTable() throws IOException {
        String[] columnNames = {"Doctor Name", "Available dates", "Start time", "End time"};
        List<DoctorSchedule> schedules = DoctorSchedule.readSchedulesFromFile("src/TextFiles/Schedules.txt");
        Object[][] data = new Object[schedules.size()][columnNames.length];

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        String currentDateString = currentDate.format(dateFormatter);
        String currentTimeString = currentTime.format(timeFormatter);

        LocalDate currentDateFormatted = LocalDate.parse(currentDateString, dateFormatter);
        LocalTime currentTimeFormatted = LocalTime.parse(currentTimeString, timeFormatter);

        int index = 0;
        for (DoctorSchedule schedule : schedules) {
            LocalDate scheduleDate = schedule.getAvailableDate();
            LocalTime scheduleEndTime = schedule.getEndTime();

            // Check if the date has passed or if the time has passed for the current date
            if (scheduleDate.isAfter(currentDateFormatted) || (scheduleDate.isEqual(currentDateFormatted) && scheduleEndTime.isAfter(currentTimeFormatted))) {
                data[index][0] = schedule.getDoctorName();
                data[index][1] = schedule.getAvailableDate().format(dateFormatter);
                data[index][2] = schedule.getStartTime().format(timeFormatter);
                data[index][3] = schedule.getEndTime().format(timeFormatter);
                index++;
            }
        }

        // Resize data array to fit the number of valid schedules
        Object[][] filteredData = new Object[index][4];
        System.arraycopy(data, 0, filteredData, 0, index);

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable appointmentTable = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(172, 247, 219, 70) : Color.WHITE);
                }
                return c;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        appointmentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        JTableHeader header = appointmentTable.getTableHeader();
        header.setDefaultRenderer(new PatientAppointment.BAHeaderRenderer(appointmentTable));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        appointmentTable.setRowHeight(appointmentTable.getRowHeight() + 20);
        for (int i = 0; i < appointmentTable.getColumnCount(); i++) {
            TableColumn column = appointmentTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }

        return appointmentTable;
    }

    private int getRowCount(String filePath) {
        int rowCount = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.readLine() != null) {
                rowCount++;
            }
        } catch (IOException e) {
        }
        return rowCount;
    }

    public JPanel getMainPanel() {
        return bookAppointment;
    }

    protected void afterAppointmentBooked() {
//        PatientPageGUI.updateTableModel();
    }
}

class BAHeaderRenderer extends JLabel implements TableCellRenderer {

    public BAHeaderRenderer(JTable table) {
        setOpaque(true);
        setFont(new Font("Segoe UI", Font.BOLD, 18));
        setBackground(new Color(169, 220, 247, 80));
        setForeground(Color.BLACK);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");
        return this;
    }
}
