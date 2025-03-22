/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DoctorMedicalHistory;

import DoctorPage.DoctorGUI;
import DoctorPage.DoctorGUI.MedicalRecordPanel;
/**
 *
 * @author guppy
 */
import Role.Patient;
import Role.UserFileReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchingPatientName extends JFrame {

    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;
    private MedicalRecordPanel medicalRecordPanel;

    public SearchingPatientName(String doctorID) throws IOException {
        setTitle("Patient Search");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // NEED CHANGE
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the panel for the message
        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(230, 230, 255)); // Light purple background
        JLabel messageLabel = new JLabel("Enter the valid patient name to create new medical record:");
        messagePanel.add(messageLabel);

        // Create the panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(255, 255, 255)); // Light purple background
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        searchLabel = new JLabel("Patient Name:");
        searchField = new JTextField(20);
        searchButton = new JButton("Next");

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(searchField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(searchButton, gbc);

        mainPanel.add(messagePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DoctorGUI doctorPage;
                try {
                    doctorPage = new DoctorGUI(doctorID);
                    doctorPage.showMedicalRecordPanel();
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(SearchingPatientName.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        List<Patient> patients = UserFileReader.readPatientsFromFile("src/textfile/PatientData.txt");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientName = searchField.getText().trim();
                boolean found = false;
                for (Patient patient : patients) {
                    String patientNameRetrieve = patient.getName();
                    if (patientNameRetrieve.equalsIgnoreCase(patientName)) {
                        found = true;
                        dispose();
                        try {
                            // Perform actions for found patient (e.g., open a new frame)
                            new AddMedicalRecord(patient.getId(), medicalRecordPanel, doctorID).setVisible(true);
                        } catch (IOException ex) {
                            Logger.getLogger(SearchingPatientName.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(SearchingPatientName.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(null, "Patient with name '" + patientName + "' not found.");
                }
            }
        });
    }
}
