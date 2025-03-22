package DoctorMedicalHistory;

import DoctorPage.DoctorGUI;
import DoctorPage.DoctorGUI.MedicalRecordPanel;
import MedicalRecord.MediRecFileReader;
import MedicalRecord.mediRecordClass;
import static MedicalRecord.mediRecordClass.getDoctorById;
import static MedicalRecord.mediRecordClass.getPatientById;
import Role.Doctor;
import Role.Patient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddMedicalRecord extends JFrame {

    private JLabel createDate;
    private JLabel PpatientID;
    private JLabel patientContact;
    private JTextField bloodTypeValue;
    private JTextField weightValue;
    private JTextField heightValue;
    private JLabel doctorIDValue;
    private JLabel doctorNameValue;
    private JTextArea diagnoseTextArea;
    private JTextArea prescriptionTextArea;
    private JLabel nameValue;
    private MedicalRecordPanel medicalRecordPanel;

    public AddMedicalRecord(String patientID, MedicalRecordPanel medicalRecordPanel, String doctorID) throws IOException, ParseException {
        this.medicalRecordPanel = medicalRecordPanel;

        JPanel jPanel1 = new JPanel();
        JLabel clinicNameLabel = new JLabel();
        JButton closeButton = new JButton();
        JSeparator jSeparator1 = new JSeparator();
        JSeparator jSeparator2 = new JSeparator();
        JLabel nameLabel = new JLabel();
        JLabel bloodTypeLabel = new JLabel();
        JLabel patientIDLabel = new JLabel();
        JLabel weightLabel = new JLabel();
        JLabel heightLabel = new JLabel();
        JLabel doctorIDLabel = new JLabel();
        JLabel doctorNameLabel = new JLabel();
        JLabel diagnoseLabel = new JLabel();
        JLabel patientContactLabel = new JLabel();
        JScrollPane diagnoseScrollPane = new JScrollPane();
        JLabel prescriptionLabel = new JLabel();
        JScrollPane prescriptionScrollPane = new JScrollPane();
        JButton saveButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(450, 0);

        clinicNameLabel.setFont(new Font("AppointWell", Font.BOLD, 24)); // NOI18N
        clinicNameLabel.setText("AppointWell Clinic");

        closeButton.setText("Close");
        closeButton.addActionListener(evt -> {
            dispose();
            DoctorGUI doctorPage;
            try {
                doctorPage = new DoctorGUI(doctorID);
                doctorPage.showMedicalRecordPanel();
            } catch (IOException ex) {
                Logger.getLogger(AddMedicalRecord.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(AddMedicalRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Initialize patient ID and contact labels
        PpatientID = new JLabel();
        PpatientID.setText(patientID);
        patientIDLabel.setText("PatientID:");
        patientContactLabel.setText("Patient Contact:");
        patientContact = new JLabel();

        // Get today's date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormat.format(currentDate);
        createDate = new JLabel(currentDateTime);

        nameLabel.setText("Name:");
        Patient patientIDRetrieve = getPatientById(patientID);
        if (patientIDRetrieve != null) {
            String rPatientName = patientIDRetrieve.getName();
            String rContactNum = patientIDRetrieve.getContactNumber();

            nameValue = new JLabel(rPatientName);
            patientContact = new JLabel(rContactNum);
        }

        bloodTypeLabel.setText("Blood Type:");

        bloodTypeValue = new JTextField("");
        bloodTypeValue.setEditable(true);

        weightLabel.setText("Weight (kg):");
        weightValue = new JTextField("");
        weightValue.setEditable(true);

        heightLabel.setText("Height (cm):");
        heightValue = new JTextField("");
        heightValue.setEditable(true);

        Doctor doctorIDRetrieve = getDoctorById(doctorID);
        if (doctorIDRetrieve != null) {
            String rDoctorName = doctorIDRetrieve.getName();
            doctorIDValue = new JLabel(doctorID);
            doctorNameValue = new JLabel(rDoctorName);
        }

        doctorIDLabel.setText("DoctorID:");
        doctorNameLabel.setText("Doctor Name:");

        diagnoseLabel.setText("Diagnose:");

        diagnoseTextArea = new JTextArea();
        diagnoseTextArea.setColumns(20);
        diagnoseTextArea.setRows(5);
        diagnoseTextArea.setEditable(true);
        diagnoseScrollPane.setViewportView(diagnoseTextArea);

        prescriptionLabel.setText("Prescription:");

        prescriptionTextArea = new JTextArea();
        prescriptionTextArea.setColumns(20);
        prescriptionTextArea.setRows(5);
        prescriptionTextArea.setEditable(true);
        prescriptionScrollPane.setViewportView(prescriptionTextArea);

        List<mediRecordClass> newRecord = MediRecFileReader.readMediRecFromFile("src/TextFiles/MedicalRecord.txt");

        saveButton.setText("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve data from the text fields
                String date = createDate.getText();
                String bloodType = bloodTypeValue.getText();
                String weight = weightValue.getText();
                String height = heightValue.getText();
                String diagnose = diagnoseTextArea.getText();
                String prescription = prescriptionTextArea.getText();
                String patientID = PpatientID.getText();
                String doctorID = doctorIDValue.getText();

                if (bloodType.isEmpty() || weight.isEmpty() || height.isEmpty() || diagnose.isEmpty() || prescription.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in the required field.");
                    return;
                }

                if (!weight.matches("\\d+(\\.\\d{1,2})?")) {
                    JOptionPane.showMessageDialog(null, "Please fill in the correct weight.");
                    return;
                }

                if (!height.matches("\\d+(\\.\\d{1,2})?")) {
                    JOptionPane.showMessageDialog(null, "Please fill in the correct height.");
                    return;
                }

                int rowCount = getRowCount("src/TextFiles/MedicalRecord.txt");
                int currentMediRecID = rowCount + 1;
                String mediRecID = "MD" + String.format("%03d", currentMediRecID);

                // Write data to a text file
                try {
                    FileWriter fw = new FileWriter("src/TextFiles/MedicalRecord.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(mediRecID + "|");
                    bw.write(date + "|");
                    bw.write(bloodType + "|");
                    bw.write(weight + "|");
                    bw.write(height + "|");
                    bw.write(diagnose + "|");
                    bw.write(prescription + "|");
                    bw.write(patientID + "|");
                    bw.write(doctorID + "\n");

                    bw.flush(); // Flushes the stream
                    bw.close(); // Closes the stream

                    // Reset all text fields
                    bloodTypeValue.setText("");
                    weightValue.setText("");
                    heightValue.setText("");
                    diagnoseTextArea.setText("");
                    prescriptionTextArea.setText("");

                    JOptionPane.showMessageDialog(null, "Medical Record added");
                    DoctorGUI doctorGUI = new DoctorGUI(doctorID);
                    doctorGUI.showMedicalRecordPanel();
                    doctorGUI.setVisible(true);

                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while adding.");
                } catch (ParseException ex) {
                    Logger.getLogger(AddMedicalRecord.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 677, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(clinicNameLabel, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(createDate, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)) // Added createDate label
                                                        .addGap(358, 358, 358)
                                                        .addComponent(closeButton)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(patientContactLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(nameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(patientIDLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
                                                .addGap(28, 28, 28)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(patientContact, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(PpatientID, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(nameValue, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(bloodTypeLabel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(weightLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(heightLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(heightValue, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(bloodTypeValue, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(weightValue, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
                                                .addGap(206, 206, 206))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(prescriptionScrollPane, GroupLayout.Alignment.LEADING)
                                                        .addComponent(prescriptionLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                .addComponent(doctorIDLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(doctorIDValue, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                .addComponent(doctorNameLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(doctorNameValue, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(diagnoseLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jSeparator2, GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                                                        .addComponent(diagnoseScrollPane))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton)
                                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(closeButton)
                                        .addComponent(clinicNameLabel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(createDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE) // Added createDate label
                                .addGap(46, 46, 46)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(patientIDLabel)
                                        .addComponent(bloodTypeLabel)
                                        .addComponent(PpatientID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bloodTypeValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameLabel)
                                        .addComponent(weightLabel)
                                        .addComponent(nameValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(weightValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(patientContactLabel)
                                                .addComponent(patientContact, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(heightLabel)
                                                .addComponent(heightValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(31, 31, 31)
                                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(doctorIDLabel)
                                        .addComponent(doctorIDValue))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(doctorNameLabel)
                                        .addComponent(doctorNameValue))
                                .addGap(26, 26, 26)
                                .addComponent(diagnoseLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(diagnoseScrollPane, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(prescriptionLabel)
                                .addGap(18, 18, 18)
                                .addComponent(prescriptionScrollPane, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton)
                                .addGap(20, 20, 20))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)) // Add space on the right side
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)) // Add space at the bottom
        );

        pack();
    }

    private int getRowCount(String filePath) {
        int rowCount = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.readLine() != null) {
                rowCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    private void closeButtonActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
