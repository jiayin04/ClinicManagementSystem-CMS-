/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DoctorMedicalHistory;

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
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author guppy
 */
public class DoctorMediRecManagement extends JPanel {

    private JTable mediManageTable;
    private JPanel doctorMediPanel;
    private String[] columnNames;
    private JScrollPane scrollPane;
    private JFrame parentFrame;

    public DoctorMediRecManagement(JFrame parentFrame, String doctorID) throws IOException {
        this.parentFrame = parentFrame;
        
        doctorMediPanel = new JPanel();
        doctorMediPanel.setLayout(new BorderLayout());
        doctorMediPanel.setBackground(new Color(255, 254, 224));
        doctorMediPanel.setPreferredSize(new Dimension(1500, 600));

        columnNames = new String[]{"Medical Record ID", "DoctorID", "DoctorName", "PatientID", "PatientName", "Create Date"}; // Initialize in the constructor
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
                    c.setBackground(row % 2 == 0 ? new Color(47, 39, 206, 20) : Color.WHITE);
                }

                if (c instanceof JComponent jComponent) {
                    jComponent.setBorder(BorderFactory.createEmptyBorder());
                }
                return c;
            }
        };

        JTableHeader header = mediManageTable.getTableHeader();
        header.setDefaultRenderer(new DoctorMediRecManagement.HeaderRendererDoctor(mediManageTable));
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
                        showMediRecDisplay(mediRecID);
                    } catch (IOException | ParseException ex) {
                        java.util.logging.Logger.getLogger(DoctorMediRecManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }

            private void showMediRecDisplay(String mediRecID) throws IOException, ParseException {
                DoctorMediRecDisplay displayPatientMediRec = new DoctorMediRecDisplay(mediRecID);
                displayPatientMediRec.setVisible(true);
            }
        });

        JPanel backgroundPanel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.blue);
        scrollPane = new JScrollPane(mediManageTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(224, 251, 226));

        // Add the button to the top-right corner
        JButton addButton = new JButton("Add Record");
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SearchingPatientName(doctorID).setVisible(true);
                    parentFrame.dispose();
                } catch (IOException ex) {
                    Logger.getLogger(DoctorMediRecManagement.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(224, 251, 226));
        topPanel.add(addButton, BorderLayout.EAST);

        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        doctorMediPanel.add(backgroundPanel, BorderLayout.CENTER);
        setVisible(true);

    }

    public void updateTableData() throws IOException {
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

        DefaultTableModel model = (DefaultTableModel) mediManageTable.getModel();
        model.setDataVector(data, columnNames);
        model.fireTableDataChanged();

        scrollPane.revalidate();
        scrollPane.repaint();
    }
    
    public void disposeParentFrame(){
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame jFrame){
            jFrame.dispose();
        }
    }

    class HeaderRendererDoctor extends JLabel implements TableCellRenderer {

        public HeaderRendererDoctor(JTable table) {
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

    public JPanel getMediPanel() {
        return doctorMediPanel;
    }
}
