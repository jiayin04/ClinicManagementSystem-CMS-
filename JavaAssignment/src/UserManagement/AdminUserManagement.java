/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserManagement;

import AdminMainPage.AdminNavBar;
import Role.Admin;
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
import static java.awt.image.ImageObserver.WIDTH;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AdminUserManagement extends JFrame {

    private JTable adminTable;

    public AdminUserManagement() throws IOException {
        this.setTitle("Admin User Management Page"); //set the title of the frame
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

        JButton addAdmin = new JButton("Add Admin");
        addAdmin.setPreferredSize(new Dimension(150, 30));
        addAdmin.setFont(new Font("Segeo UI", Font.BOLD, 18));
        addAdmin.setBackground(new Color(137, 207, 235));

        addAdmin.addActionListener((ActionEvent e) -> {
            dispose();
            AdminRegistration adminRegistration = new AdminRegistration();
        });

        info.add(addAdmin);

        JPanel userInfo = new JPanel();
        userInfo.setPreferredSize(new Dimension(1110, 590));
        userInfo.setBackground(new Color(217, 241, 253));
        userInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH));
        userInfo.setLayout(new FlowLayout(FlowLayout.LEADING));

        // Create a table with sample data
        String[] columnNames = {"Admin ID", "Name", "Email Address", "Contact Num.", "Emergency Contact Num."};
        List<Admin> admins = UserFileReader.readAdminsFromFile("src/TextFiles/AdminData.txt");
        Object[][] data = new Object[admins.size()][columnNames.length];

        for (int i = 0; i < admins.size(); i++) {
            Admin admin = admins.get(i);
            data[i][0] = admin.getId();
            data[i][1] = admin.getName();
            data[i][2] = admin.getEmail();
            data[i][3] = admin.getContactNumber();
            data[i][4] = admin.getEmergencyContactNumber();
        }

        adminTable = new JTable(new DefaultTableModel(data, columnNames)) {
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

        adminTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = adminTable.getSelectedRow();

                if (selectedRow != -1) {
                    String adminID = adminTable.getValueAt(selectedRow, 0).toString();
                    try {
                        showAdminProfileManagement(adminID);
                    } catch (IOException ex) {
                        Logger.getLogger(PatientUserManagement.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(PatientUserManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            private void showAdminProfileManagement(String adminID) throws IOException, ParseException {
                JOptionPane.showMessageDialog(null, "Redirecting to Admin Profile Management");
                dispose();
                AdminProfileManagement adminProfileManagement = new AdminProfileManagement(adminID);
            }
        });

        adminTable.setBackground(Color.WHITE);

        // Set custom header renderer
        JTableHeader header = adminTable.getTableHeader();

        header.setDefaultRenderer(
                new HeaderRenderer(adminTable));
        header.setPreferredSize(
                new Dimension(header.getPreferredSize().width, 40)); // Increase header height

        JScrollPane scrollPane = new JScrollPane(adminTable);

        scrollPane.setPreferredSize(
                new Dimension(1100, 570));
        scrollPane.getViewport()
                .setBackground(new Color(137, 207, 235, 80)); // Viewport Background
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(217, 241, 253, 80)));

        // Adjust the row and column size
        adminTable.setRowHeight(adminTable.getRowHeight() + 20);
        for (int i = 0;
                i < adminTable.getColumnCount();
                i++) {
            TableColumn column = adminTable.getColumnModel().getColumn(i);
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