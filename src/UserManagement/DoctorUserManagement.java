/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserManagement;

import AdminMainPage.AdminNavBar;
import Role.Doctor;
import Role.UserFileReader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author ASUS
 */
public class DoctorUserManagement extends JFrame {

    JTable doctorTable;

    public DoctorUserManagement() throws IOException {
        this.setTitle("Doctor User Management Page"); //set the title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
        setExtendedState(JFrame.MAXIMIZED_BOTH); //maximize the width and height
        setLayout(new BorderLayout());

        AdminNavBar navBar = new AdminNavBar(this);

        UserManagementSideBar sideBar = new UserManagementSideBar(this);

        JPanel info = new JPanel();
        info.setBackground(Color.WHITE);
        info.setPreferredSize(new Dimension(300, 100));

        add(navBar, BorderLayout.NORTH);
        add(sideBar, BorderLayout.WEST);
        add(info, BorderLayout.CENTER);

//***************************** User Info ***************************************************
        info.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 20));

        JLabel userManagement = new JLabel("User Management");
        userManagement.setPreferredSize(new Dimension(900, 50));
//            userManagement.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH));
        userManagement.setFont(new Font("Segeo UI", Font.BOLD, 30));
        info.add(userManagement);

        JButton addDoctor = new JButton("Add Doctor");
        addDoctor.setPreferredSize(new Dimension(150, 30));
        addDoctor.setFont(new Font("Segeo UI", Font.BOLD, 18));
        addDoctor.setBackground(new Color(137, 207, 235));

        addDoctor.addActionListener((ActionEvent e) -> {
            dispose();
            DoctorRegistration doctorRegistration = new DoctorRegistration();
        });

        info.add(addDoctor);

        JPanel userInfo = new JPanel();
        userInfo.setPreferredSize(new Dimension(1110, 590));
        userInfo.setBackground(new Color(217, 241, 253));
        userInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH));
        userInfo.setLayout(new FlowLayout(FlowLayout.LEADING));

        // Create a table with sample data
        String[] columnNames = {"Doctor ID", "Name", "Email Address", "Contact Num.", "Specialty"};
        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");
        Object[][] data = new Object[doctors.size()][columnNames.length];

        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            data[i][0] = doctor.getId();
            data[i][1] = doctor.getName();
            data[i][2] = doctor.getEmail();
            data[i][3] = doctor.getContactNumber();
            data[i][4] = doctor.getSpecialty();
        }

        doctorTable = new JTable(new DefaultTableModel(data, columnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                // Alternate row color
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(157, 226, 191, 90) : Color.WHITE);
                }
                // Remove cell borders
                if (c instanceof JComponent jComponent) {
                    jComponent.setBorder(BorderFactory.createEmptyBorder());
                }
                return c;
            }
        };
        doctorTable.repaint();

        doctorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = doctorTable.getSelectedRow();

                if (selectedRow != -1) {
                    String doctorID = doctorTable.getValueAt(selectedRow, 0).toString();
                    try {
                        showDoctorProfileManagement(doctorID);
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(PatientUserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        java.util.logging.Logger.getLogger(PatientUserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }

            private void showDoctorProfileManagement(String doctorID) throws IOException, ParseException {
                JOptionPane.showMessageDialog(null, "Redirecting to Doctor Profile Management");
                dispose();
                DoctorProfileManagement doctorProfileManagement = new DoctorProfileManagement(doctorID);
            }
        });

        doctorTable.setBackground(Color.WHITE);

        // Set custom header renderer
        JTableHeader header = doctorTable.getTableHeader();

        header.setDefaultRenderer(
                new HeaderRenderer(doctorTable));
        header.setPreferredSize(
                new Dimension(header.getPreferredSize().width, 40)); // Increase header height

        JScrollPane scrollPane = new JScrollPane(doctorTable);

        scrollPane.setPreferredSize(
                new Dimension(1100, 570));
        scrollPane.getViewport()
                .setBackground(new Color(137, 207, 235, 80)); // Viewport Background
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(217, 241, 253, 80)));
        scrollPane.revalidate();
        // Adjust the row and column size
        doctorTable.setRowHeight(doctorTable.getRowHeight() + 20);
        for (int i = 0;
                i < doctorTable.getColumnCount();
                i++) {
            TableColumn column = doctorTable.getColumnModel().getColumn(i);
            if (i == 4) {
                column.setPreferredWidth(250);
            } else {
                column.setPreferredWidth(150);
            }
        }

        userInfo.add(scrollPane);

        info.add(userInfo);

        setVisible(true);

    }

    static class HeaderRenderer extends JLabel implements TableCellRenderer {

        public HeaderRenderer(JTable table) {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 18));
            setBackground(new Color(136, 146, 195, 80));
            setForeground(Color.BLACK);
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            return this;
        }
    }

}