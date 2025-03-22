package AdminMedicalHistory;

import AdminMainPage.AdminNavBar;
import MedicalRecord.MediRecFileReader;
import MedicalRecord.mediRecordClass;
import static MedicalRecord.mediRecordClass.getDoctorById;
import static MedicalRecord.mediRecordClass.getPatientById;
import Role.Doctor;
import Role.Patient;
import Role.UserFileReader;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class AdminMediRecManagement extends JFrame {

    private JTable mediManageTable;

    public AdminMediRecManagement() throws IOException {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AdminNavBar navBar = new AdminNavBar(this);

        String[] columnNames = {"Medical Record ID", "DoctorID", "DoctorName", "PatientID", "PatientName", "Date Created"};
        List<mediRecordClass> records = MediRecFileReader.readMediRecFromFile("src/TextFiles/MedicalRecord.txt");

        Object[][] data = new Object[records.size()][columnNames.length];

        for (int i = 0; i < records.size(); i++) {
            mediRecordClass record = records.get(i);
            Doctor doctor = getDoctorById(record.getDoctorID());
            Patient patient = getPatientById(record.getPatientID());
            data[i][0] = record.getMediRecID();
            data[i][1] = record.getDoctorID();
            data[i][2] = doctor != null ? doctor.getName() : "";
            data[i][3] = record.getPatientID();
            data[i][4] = patient != null ? patient.getName() : "";
            data[i][5] = record.getCreateDate();
        }

        mediManageTable = new JTable(new DefaultTableModel(data, columnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(157, 226, 191, 90) : Color.WHITE);
                }

                if (c instanceof JComponent jComponent) {
                    jComponent.setBorder(BorderFactory.createEmptyBorder());
                }
                return c;
            }
        };

        JTableHeader header = mediManageTable.getTableHeader();
        header.setDefaultRenderer(new AdminMediRecManagement.HeaderRenderer(mediManageTable));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        mediManageTable.setRowHeight(mediManageTable.getRowHeight() + 20);
        for (int i = 0; i < mediManageTable.getColumnCount(); i++) {
            TableColumn column = mediManageTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }

        mediManageTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = mediManageTable.getSelectedRow();
                if (selectedRow != -1) {
                    String mediRecID = mediManageTable.getValueAt(selectedRow, 0).toString();
                    try {
                        dispose();
                        showMediRecDisplay(mediRecID);
                    } catch (IOException | ParseException ex) {
                        java.util.logging.Logger.getLogger(AdminMediRecManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }

            private void showMediRecDisplay(String mediRecID) throws IOException, ParseException {
                AdminMediRecDisplay displayPatientMediRec = new AdminMediRecDisplay(mediRecID);
                displayPatientMediRec.setVisible(true);
            }
        });

        JPanel backgroundPanel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.blue);
        JScrollPane scrollPane = new JScrollPane(mediManageTable);
        scrollPane.getViewport().setBackground(new Color(219, 208, 255, 90));
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.add(Box.createRigidArea(new Dimension(200, 0)), BorderLayout.WEST);
        backgroundPanel.add(Box.createRigidArea(new Dimension(200, 0)), BorderLayout.EAST);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);
        backgroundPanel.setBackground(new Color(219, 208, 255, 90));

        add(navBar, BorderLayout.NORTH);
        add(backgroundPanel, BorderLayout.CENTER);
        setVisible(true);
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
}
