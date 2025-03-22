package PatientMedicalHistory;

import MedicalRecord.MediRecFileReader;
import MedicalRecord.mediRecordClass;
import static MedicalRecord.mediRecordClass.getDoctorById;
import static MedicalRecord.mediRecordClass.getPatientById;
import Role.Doctor;
import Role.Patient;
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
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComponent;
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
 * @author guppy
 */
public class PatientMediRecManagement extends JFrame {
    private JTable mediManageTable;
    private JPanel mediPanel;

    public PatientMediRecManagement(String thePatientID) throws IOException {
        mediPanel = new JPanel();
        mediPanel.setLayout(new BorderLayout());
        mediPanel.setBackground(new Color(255, 254, 224));

        String[] columnNames = {"Medical Record ID", "DoctorID", "DoctorName", "PatientID", "PatientName","Create Date"};
        List<mediRecordClass> records = MediRecFileReader.readMediRecFromFile("src/TextFiles/MedicalRecord.txt");
        
        List<mediRecordClass> filteredRecords = records.stream()
                .filter(record -> record.getPatientID().equals(thePatientID))
                .collect(Collectors.toList());

        Object[][] data = new Object[filteredRecords.size()][columnNames.length];

        for (int i = 0; i < filteredRecords.size(); i++) {
            mediRecordClass record = filteredRecords.get(i);
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
        header.setDefaultRenderer(new HeaderRendererPatient(mediManageTable));
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
//                        dispose();
                        showMediRecDisplay(mediRecID);
                    } catch (IOException | ParseException ex) {
                        java.util.logging.Logger.getLogger(PatientMediRecManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }

            private void showMediRecDisplay(String mediRecID) throws IOException, ParseException {
                PatientMediRecDisplay displayPatientMediRec = new PatientMediRecDisplay(mediRecID);
                displayPatientMediRec.setVisible(true);
            }
        });

        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(new Color(255, 254, 224));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.blue);
        JScrollPane scrollPane = new JScrollPane(mediManageTable);
        scrollPane.getViewport().setBackground(new Color(255, 254, 224));
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        backgroundPanel.add(Box.createRigidArea(new Dimension(200, 0)), BorderLayout.WEST);
        backgroundPanel.add(Box.createRigidArea(new Dimension(200, 0)), BorderLayout.EAST);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        mediPanel.add(backgroundPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    class HeaderRendererPatient extends JLabel implements TableCellRenderer {

        public HeaderRendererPatient(JTable table) {
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

    public JPanel getMediPanel(){
        return mediPanel;
    }
}


