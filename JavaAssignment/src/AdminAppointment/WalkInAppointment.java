package AdminAppointment;

import Appointment.Appointment;
import Role.Doctor;
import Role.Patient;
import Role.UserFileReader;
import Schedule.DoctorSchedule;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class WalkInAppointment extends JFrame {

    private JButton backButton;
    private JComboBox<LocalDate> appoinmentDateField;
    private JComboBox<LocalTime> startTimeComboBox;
    private JComboBox<LocalTime> endTimeComboBox;
    private JComboBox<String> doctorNameComboBox;
    private String patientID;
    private String doctorID;
    private List<Appointment> appointments;
    private List<DoctorSchedule> doctorSchedules;
    private DefaultTableModel model;

    public WalkInAppointment() throws IOException {
        setTitle("Walk In Appointment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

//************************* Walk In Appointment Title ****************************************
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 15));
        titleBar.setPreferredSize(new Dimension(800, 80));
        titleBar.setBorder(BorderFactory.createEtchedBorder(WIDTH, Color.lightGray, Color.BLACK));
        titleBar.setBackground(new Color(215, 203, 255, 80));

        ImageIcon back = new ImageIcon("src/images/backButton.png");
        Image backImg = back.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        backButton = new JButton(new ImageIcon(backImg));
        backButton.setPreferredSize(new Dimension(30, 30));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener((ActionEvent e) -> {
            dispose();
            try {
                AdminViewAppointment adminViewAppointment = new AdminViewAppointment();
            } catch (IOException ex) {
                Logger.getLogger(WalkInAppointment.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        titleBar.add(backButton);

        JLabel title = new JLabel("Walk-in Appointment");
        title.setFont(new Font("Segeo UI", Font.BOLD, 30));
        titleBar.add(title);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH));
        contentPanel.setBackground(new Color(157, 226, 191, 95));

        JPanel namePanel = new JPanel();
        JLabel patientName = new JLabel("Patient Name:");
        patientName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        namePanel.setBackground(new Color(157, 226, 191, 60));
        namePanel.add(patientName);

        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");
        List<Patient> patients = UserFileReader.readPatientsFromFile("src/TextFiles/PatientData.txt");
        String[] patientNames = new String[patients.size()];

        // Extract patient names
        for (int i = 0; i < patients.size(); i++) {
            patientNames[i] = patients.get(i).getName();
        }
        JComboBox<String> patientNameComboBox = new JComboBox<>(patientNames);
        patientNameComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        patientNameComboBox.setBackground(new Color(217, 241, 253));
        namePanel.add(patientNameComboBox);

        JTable appointmentTable = createAppointmentTable();
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentTable);
        appointmentScrollPane.setPreferredSize(new Dimension(1480, 540));
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

                String patientName = (String) patientNameComboBox.getSelectedItem();
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
                    schedules.remove(selectedSchedule);
                    // Write the updated schedules back to the file
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("src/TextFiles/Schedules.txt"));
                        for (DoctorSchedule schedule : schedules) {
                            writer.write(schedule.toString() + "\n");
                        }
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                for (Patient patient : patients) {
                    if (patient.getName().equals(patientName)) {
                        patientID = patient.getId();
                    }
                }

                for (Doctor doctor : doctors) {
                    if (doctor.getName().equals(doctorName)) {
                        doctorID = doctor.getId();
                    }
                }
//
//               Increment rowCount
                int rowCount = getRowCount("src/TextFiles/Appointments.txt");
                int currentAppointmentID = rowCount + 1;
                String appointmentID = "AP" + String.format("%03d", currentAppointmentID);
//
                String appointmentStatus = "CONFIRMED";
                String visitType = "Walk-in";
                // Write data to a text file
                try {
                    Appointment.writeAppointmentToFileAppend("src/TextFiles/Appointments.txt", Arrays.asList(new Appointment(appointmentID, patientID, doctorID, appointmentDate, startTime, endTime, appointmentStatus, visitType)));
                    dispose();
                    WalkInAppointment walkInAppointment = new WalkInAppointment();
                    JOptionPane.showMessageDialog(null, "Appointment made successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while making appointment.");
                }
            }

        });

        contentPanel.add(namePanel);
        contentPanel.add(appointmentScrollPane);
        contentPanel.add(addAppointmentBtn);

        add(contentPanel, BorderLayout.CENTER);

        add(titleBar, BorderLayout.NORTH);

        setVisible(true);
    }

    private JTable createAppointmentTable() throws IOException {
        String[] columnNames = {"Doctor Name", "Available dates", "Start time", "End time"};
        Object[][] filteredData = getFilteredScheduleData();

        model = new DefaultTableModel(filteredData, columnNames);
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

        JTableHeader header = appointmentTable.getTableHeader();
        header.setDefaultRenderer(new WalkInAppointment.WIHeaderRenderer(appointmentTable));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        appointmentTable.setRowHeight(appointmentTable.getRowHeight() + 20);
        for (int i = 0; i < appointmentTable.getColumnCount(); i++) {
            TableColumn column = appointmentTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
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

    private Object[][] getFilteredScheduleData() throws IOException {
        List<DoctorSchedule> schedules = DoctorSchedule.readSchedulesFromFile("src/TextFiles/Schedules.txt");
        List<Object[]> validSchedules = new ArrayList<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        String currentDateString = currentDate.format(dateFormatter);
        String currentTimeString = currentTime.format(timeFormatter);

        LocalDate currentDateFormatted = LocalDate.parse(currentDateString, dateFormatter);
        LocalTime currentTimeFormatted = LocalTime.parse(currentTimeString, timeFormatter);

        for (DoctorSchedule schedule : schedules) {
            LocalDate scheduleDate = schedule.getAvailableDate();
            LocalTime scheduleEndTime = schedule.getEndTime();

            if ((scheduleDate.isAfter(currentDateFormatted) || (scheduleDate.isEqual(currentDateFormatted) && scheduleEndTime.isAfter(currentTimeFormatted)))) {
                validSchedules.add(new Object[]{
                    schedule.getDoctorName(),
                    schedule.getAvailableDate().format(dateFormatter),
                    schedule.getStartTime().format(timeFormatter),
                    schedule.getEndTime().format(timeFormatter)
                });
            }
        }

//    Get the particular row
        Object[][] filteredData = new Object[validSchedules.size()][4];
        for (int i = 0; i < validSchedules.size(); i++) {
            filteredData[i] = validSchedules.get(i);
        }
        return filteredData;
    }

    //    Update the schedule table when timeslots are updated
    private void updateScheduleTable(String doctorID) throws IOException {
        Object[][] filteredData = getFilteredScheduleData();
        model.setDataVector(filteredData, new String[]{"Available dates", "Start time", "End time"});
        model.fireTableDataChanged();
    }
    
    class WIHeaderRenderer extends JLabel implements TableCellRenderer {

        public WIHeaderRenderer(JTable table) {
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
}