/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DoctorAppointment;

import Appointment.Appointment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author ASUS
 */
public class ViewIndividualAppointment extends JPanel {

    private DefaultTableModel tableModel;
    private JTable bookingTable;
    private List<Appointment> appointments;
    private JPanel appointmentPanel;
    private static final String APPOINTMENT_FILE_PATH = "src/TextFiles/Appointments.txt";
    
    public ViewIndividualAppointment(String doctorID) throws IOException {

        appointmentPanel = new JPanel(new BorderLayout());
        // Create table model
        tableModel = new DefaultTableModel(new String[]{"Appointment ID", "Patient", "Date", "Time", "Appointment Status", "Visit Type"}, 0);
        bookingTable = new JTable(tableModel) {
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

        bookingTable.setBackground(Color.WHITE);
        // Customize table row and column's height and width
        JTableHeader header = bookingTable.getTableHeader();
        header.setDefaultRenderer(new ViewIndividualAppointment.HeaderRenderer(bookingTable));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        bookingTable.setRowHeight(bookingTable.getRowHeight() + 20);
        for (int i = 0; i < bookingTable.getColumnCount(); i++) {
            TableColumn column = bookingTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }

        for (int i = 0; i < bookingTable.getColumnCount(); i++) {
            TableColumn column = bookingTable.getColumnModel().getColumn(i);

        }

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.getViewport().setBackground(new Color(224, 251, 226));
        appointmentPanel.add(scrollPane, BorderLayout.CENTER);

        // Load and populate initial appointments
        appointments = Appointment.readAppointment(APPOINTMENT_FILE_PATH);
        populateTable(doctorID);

        // Add cancel appointment button
        LocalDate tdyDate = LocalDate.now();
        LocalTime now = LocalTime.now();
        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(timeformatter);

        JButton cancelAppointmentButton = new JButton("Cancel Appointment");
        cancelAppointmentButton.addActionListener(e -> {
            int selectedRow = bookingTable.getSelectedRow();
            if (selectedRow != -1) {
                String appointmentID = (String) bookingTable.getValueAt(selectedRow, 0);
                String appointmentDateStr = (String) bookingTable.getValueAt(selectedRow, 2);
                String status = (String) bookingTable.getValueAt(selectedRow, 4);

                LocalTime endTime = null;
                for (Appointment appointment : appointments) {
                    if (appointment.getDoctorID().equals(doctorID) && appointment.getAppointmentID().equals(appointmentID)) {
                        endTime = appointment.getEndTime();
                        break;
                    }
                }

                LocalDate appointmentDate = LocalDate.parse(appointmentDateStr);

                LocalTime currentTime = LocalTime.parse(formattedTime, timeformatter);

                if (tdyDate.isAfter(appointmentDate) || (tdyDate.isEqual(appointmentDate) && currentTime.isAfter(endTime))) {
                    JOptionPane.showMessageDialog(this, "The appointment has ended.");
                } else if ("CANCELLED".equals(status)) {
                    JOptionPane.showMessageDialog(this, "This appointment is already cancelled.");
                } else {
                    try {
                        Appointment.cancelAppointment(appointments, appointmentID);
                        Appointment.writeAppointmentToFile(APPOINTMENT_FILE_PATH, appointments);
                        populateTable(doctorID);
                        bookingTable.revalidate();
                        bookingTable.repaint();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelAppointmentButton);
        appointmentPanel.add(buttonPanel, BorderLayout.SOUTH);

    }

    private void populateTable(String doctorID) throws IOException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        tableModel.setRowCount(0);  // Clear existing rows

        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID)) {
                tableModel.addRow(new Object[]{
                    appointment.getAppointmentID(),
                    appointment.getPatientName(),
                    appointment.getAppointmentDate().format(dateFormatter),
                    appointment.getStartTime().format(timeFormatter) + " - " + appointment.getEndTime().format(timeFormatter),
                    appointment.getAppointmentStatus(),
                    appointment.getVisitType()
                });
            }
        }
    }

    //    Table customisation
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
        return appointmentPanel;
    }
}
