/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminAppointment;

import AdminMainPage.AdminNavBar;
import Appointment.Appointment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author ASUS
 */
public class AdminViewAppointment extends JFrame {

//    public static void main(String[] args) throws IOException {
//        AdminViewAppointment adminViewAppointment = new AdminViewAppointment();
//    }

    public AdminViewAppointment() throws IOException {
        setTitle("Appointment Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        AdminNavBar navBar = new AdminNavBar(this);
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(219, 208, 255, 90));
        centerPanel.setLayout(null);

        JLabel appointmentTitle = new JLabel("Appointments");
        appointmentTitle.setFont(new Font("Segeo UI", Font.BOLD, 30));
        appointmentTitle.setBounds(10, 0, 250, 100);
        centerPanel.add(appointmentTitle);

        JButton addWalkIn = new JButton("+ Walk In");
        addWalkIn.setFont(new Font("Segeo UI", Font.BOLD, 16));
        addWalkIn.setPreferredSize(new Dimension(180, 30));
        addWalkIn.setBounds(1300, 35, 180, 30);
        addWalkIn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addWalkIn.setBackground(Color.BLACK);
        addWalkIn.setForeground(Color.WHITE);
        addWalkIn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    WalkInAppointment walkInAppointment = new WalkInAppointment();
                } catch (IOException ex) {
                    Logger.getLogger(AdminViewAppointment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
        centerPanel.add(addWalkIn);

        JTable appointmentTable = createAppointmentTable();
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentTable);
        appointmentScrollPane.setPreferredSize(new Dimension(1480, 560));
        appointmentScrollPane.getViewport().setBackground(new Color(219, 208, 255, 90));
        appointmentScrollPane.setBorder(BorderFactory.createLineBorder(new Color(219, 208, 255, 90)));
        appointmentScrollPane.setBounds(WIDTH, 90, 1540, 600);
        centerPanel.add(appointmentScrollPane);
        add(centerPanel, BorderLayout.CENTER);
        add(navBar, BorderLayout.NORTH);

        setVisible(true);
    }

    private JTable createAppointmentTable() throws IOException {
        String[] columnNames = {"Appointment ID", "Patient Name", "Doctor Name", "Appointment Date", "Start Time", "End Time", "Appointment Status", "Visit Type"};
        List<Appointment> appointments = Appointment.readAppointment("src/TextFiles/Appointments.txt");
        Object[][] data = new Object[appointments.size()][columnNames.length];

//            Loop and read file
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            data[i][0] = appointment.getAppointmentID();
            data[i][1] = appointment.getPatientName();
            data[i][2] = appointment.getDoctorName();
            data[i][3] = appointment.getAppointmentDate();
            data[i][4] = appointment.getStartTime();
            data[i][5] = appointment.getEndTime();
            data[i][6] = appointment.getAppointmentStatus();
            data[i][7] = appointment.getVisitType();
        }

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

        JTableHeader header = appointmentTable.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(appointmentTable));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        Rectangle rect = appointmentTable.getCellRect(appointmentTable.getRowCount() - 1, 0, true);
        appointmentTable.scrollRectToVisible(rect);
        appointmentTable.setRowHeight(appointmentTable.getRowHeight() + 20);
        for (int i = 0; i < appointmentTable.getColumnCount(); i++) {
            TableColumn column = appointmentTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }
        return appointmentTable;
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
