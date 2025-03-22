/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatientAppointment;

import Patient.PatientPageGUI;
import Role.Doctor;
import Role.UserFileReader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author ASUS
 */
public class SearchDoctor extends JPanel {
    private List<Doctor> doctors;
    JPanel searchPanel;

    public SearchDoctor() throws IOException {

        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        JComboBox<String> searchByComboBox = new JComboBox<>(new String[]{"Search by:", "Name", "Specialty"});
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        formPanel.add(new JLabel("Select Search Criteria:"));
        formPanel.add(searchByComboBox);
        formPanel.add(new JLabel("Enter Search Term:"));
        formPanel.add(searchField);
        formPanel.add(new JLabel(""));
        formPanel.add(searchButton);

        searchPanel.add(formPanel, BorderLayout.NORTH);

        DefaultTableModel searchTableModel = new DefaultTableModel(new String[]{"Doctor Name", "Specialty"}, 0);
        JTable searchResultsTable = new JTable(searchTableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(255, 254, 224) : Color.WHITE);
                }
                return c;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        updateSearchResults(searchTableModel);

        JScrollPane scrollPane = new JScrollPane(searchResultsTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        searchPanel.add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String searchBy = (String) searchByComboBox.getSelectedItem();
            String searchTerm = searchField.getText().trim();
            if (searchBy.equals("Name")) {
                try {
                    updateSearchResultsTableByName(searchTableModel, searchTerm);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (searchBy.equals("Specialty")) {
                try {
                    updateSearchResultsTableBySpecialty(searchTableModel, searchTerm);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    updateSearchResults(searchTableModel);
                } catch (IOException ex) {
                    Logger.getLogger(PatientPageGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JTableHeader header = searchResultsTable.getTableHeader();
        header.setDefaultRenderer(new PatientAppointment.HeaderRenderer(searchResultsTable));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        searchResultsTable.setRowHeight(searchResultsTable.getRowHeight() + 20);

    }

    private void updateSearchResults(DefaultTableModel tableModel) throws IOException {
        tableModel.setRowCount(0); // Clear existing data
        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");
        for (Doctor doctor : doctors) {
            Object[] rowData = {doctor.getName(), doctor.getSpecialty()};
            tableModel.addRow(rowData);
        }
    }

    private void updateSearchResultsTableByName(DefaultTableModel tableModel, String searchTerm) throws IOException {
        tableModel.setRowCount(0); // Clear existing data
        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");
        boolean found = false;
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(searchTerm)) {
                Object[] rowData = {doctor.getName(), doctor.getSpecialty()};
                tableModel.addRow(rowData);
                found = true;
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "No records found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateSearchResultsTableBySpecialty(DefaultTableModel tableModel, String searchTerm) throws IOException {
        tableModel.setRowCount(0); // Clear existing data
        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");
        boolean found = false;
        for (Doctor doctor : doctors) {
            if (doctor.getSpecialty().equalsIgnoreCase(searchTerm)) {
                Object[] rowData = {doctor.getName(), doctor.getSpecialty()};
                tableModel.addRow(rowData);
                found = true;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "No records found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public JPanel getMainPanel() {
        return searchPanel;
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
