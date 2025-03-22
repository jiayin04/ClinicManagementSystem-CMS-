/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Schedule;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 *
 * @author ASUS
 */
public class UploadSchedule extends JPanel {

    private JPanel appointmentSelectionPanel;
    private DefaultTableModel model;

    public UploadSchedule(String doctorID) throws IOException {
        appointmentSelectionPanel = new JPanel();
        appointmentSelectionPanel.setBackground(new Color(224, 251, 226));
        appointmentSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new UploadSchedule.DateLabelFormatter());
        appointmentSelectionPanel.add(new JLabel("Select Date: "));
        appointmentSelectionPanel.add(datePicker);

        List<String> timeSlots = loadTimeSlotsFromFile("src/TextFiles/Timeslots.txt");

        JComboBox<String> timeSlotComboBox = new JComboBox<>();
        for (String timeSlot : timeSlots) {
            timeSlotComboBox.addItem(timeSlot);
        }
        appointmentSelectionPanel.add(new JLabel("Select Time Slot: "));
        appointmentSelectionPanel.add(timeSlotComboBox);

        JButton selectButton = new JButton("Select");
        selectButton.setBackground(Color.WHITE);
        selectButton.addActionListener(e -> {
            String selectedDate = datePicker.getJFormattedTextField().getText();
            String selectedTimeSlot = (String) timeSlotComboBox.getSelectedItem();

            LocalDate date = LocalDate.parse(selectedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime startTime = LocalTime.parse(selectedTimeSlot.split(" - ")[0].trim(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime endTime = LocalTime.parse(selectedTimeSlot.split(" - ")[1].trim(), DateTimeFormatter.ofPattern("HH:mm"));

            try {
                if (DoctorSchedule.isTimeSlotAvailable(doctorID, date, startTime, endTime, "src/TextFiles/Schedules.txt", "src/TextFiles/Appointments.txt")) {
                    String scheduleID = "SC" + String.format("%04d", DoctorSchedule.readSchedulesFromFile("src/TextFiles/Schedules.txt").size() + 1);
                    DoctorSchedule schedule = new DoctorSchedule(scheduleID, doctorID, date, startTime, endTime);

                    List<DoctorSchedule> schedules = DoctorSchedule.readSchedulesFromFile("src/TextFiles/Schedules.txt");
                    schedules.add(schedule);

                    DoctorSchedule.writeSchedulesToFile(schedules, "src/TextFiles/Schedules.txt");
                    JOptionPane.showMessageDialog(this, "Appointment scheduled successfully!");
                    updateScheduleTable(doctorID);
                } else {
                    JOptionPane.showMessageDialog(this, "Selected time slot is not available. Please choose a different time slot.");
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        });
        appointmentSelectionPanel.add(selectButton);

        JTable scheduleTable = createScheduleTable(doctorID);
        JScrollPane scheduleScrollPane = new JScrollPane(scheduleTable);
        scheduleScrollPane.setPreferredSize(new Dimension(1480, 540));
        scheduleScrollPane.getViewport().setBackground(new Color(224, 251, 226));
        scheduleScrollPane.setBorder(BorderFactory.createLineBorder(new Color(224, 251, 226)));
        scheduleScrollPane.setBounds(200, 200, 1480, 540);

        appointmentSelectionPanel.add(scheduleScrollPane);
    }

//    Validate timeslots
    private List<String> loadTimeSlotsFromFile(String fileName) {
        List<String> timeSlots = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                timeSlots.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeSlots;
    }

//    Schedule Table display
    private JTable createScheduleTable(String doctorID) throws IOException {
        String[] columnNames = {"Available dates", "Start time", "End time"};
        Object[][] filteredData = getFilteredScheduleData(doctorID); // Use filtered data

        model = new DefaultTableModel(filteredData, columnNames);
        JTable scheduleTable = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(47, 39, 206, 20) : Color.WHITE);
                }
                return c;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        JTableHeader header = scheduleTable.getTableHeader();
        header.setDefaultRenderer(new UploadSchedule.HeaderRenderer(scheduleTable));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        scheduleTable.setRowHeight(scheduleTable.getRowHeight() + 20);
        for (int i = 0; i < scheduleTable.getColumnCount(); i++) {
            TableColumn column = scheduleTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }

        return scheduleTable;
    }

//    Get the schedule with updated one
    private Object[][] getFilteredScheduleData(String doctorID) throws IOException {
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
            String doctorIDRetreived = schedule.getDoctorID();
            LocalDate scheduleDate = schedule.getAvailableDate();
            LocalTime scheduleEndTime = schedule.getEndTime();

            if ((scheduleDate.isAfter(currentDateFormatted) || (scheduleDate.isEqual(currentDateFormatted) && scheduleEndTime.isAfter(currentTimeFormatted))) && doctorID.equals(doctorIDRetreived)) {
                validSchedules.add(new Object[]{
                    schedule.getAvailableDate().format(dateFormatter),
                    schedule.getStartTime().format(timeFormatter),
                    schedule.getEndTime().format(timeFormatter)
                });
            }
        }

//    Get the particular row
        Object[][] filteredData = new Object[validSchedules.size()][3];
        for (int i = 0; i < validSchedules.size(); i++) {
            filteredData[i] = validSchedules.get(i);
        }
        return filteredData;
    }

//    Update the schedule table when timeslots are updated
    private void updateScheduleTable(String doctorID) throws IOException {
        Object[][] filteredData = getFilteredScheduleData(doctorID);
        model.setDataVector(filteredData, new String[]{"Available dates", "Start time", "End time"});
        model.fireTableDataChanged();
    }

    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public Object stringToValue(String text) {
            return LocalDate.parse(text, dateFormatter);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
//                LocalDate date = (LocalDate) value;
                GregorianCalendar calendar = (GregorianCalendar) value;
                Instant instant = Instant.from(calendar.toInstant());
                LocalDate date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
                return date.format(dateFormatter);
            }
            return "";
        }
    }

    class HeaderRenderer extends JLabel implements TableCellRenderer {

        public HeaderRenderer(JTable table) {
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

    public JPanel getMainPanel() {
        return appointmentSelectionPanel;
    }
}
