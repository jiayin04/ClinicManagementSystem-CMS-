package DoctorMedicalHistory;

import Role.Doctor;
import Role.Patient;
import MedicalRecord.MediRecFileReader;
import MedicalRecord.MediRecFileWriter;
import MedicalRecord.mediRecordClass;
import static MedicalRecord.mediRecordClass.getDoctorById;
import static MedicalRecord.mediRecordClass.getPatientById;
import static MedicalRecord.mediRecordClass.getRecordByMediRecId;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.GroupLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DoctorMediRecDisplay extends JFrame {

    private JLabel MmediRecID;
    private JLabel createDate;
    private JLabel PpatientID;
    private JLabel patientName;
    private JLabel patientContact;
    private JTextField patientBloodType;
    private JTextField patientWeight;
    private JTextField patientHeight;
    private JLabel DdoctorID;
    private JLabel doctorName;
    private JTextArea diagnose;
    private JTextArea prescription;
    private String rDoctorID;

    DoctorMediRecDisplay(String mediRecID) throws IOException, ParseException {
        setTitle("New Patient Medical Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocation(500, 0);

        JPanel jPanel1 = new JPanel();
        JLabel clinicNameLabel = new JLabel();
        JSeparator jSeparator1 = new JSeparator();
        JLabel patientIDLabel = new JLabel();
        JLabel patientNameLabel = new JLabel();
        JLabel patientContactLabel = new JLabel();
        JLabel bloodTypeLabel = new JLabel();
        JLabel weightLabel = new JLabel();
        JLabel heightLabel = new JLabel();
        JSeparator jSeparator2 = new JSeparator();
        JLabel doctorIDLabel = new JLabel();
        JLabel doctorNameLabel = new JLabel();
        JLabel diagnoseLabel = new JLabel();
        JLabel prescriptionLabel = new JLabel();
        JLabel createDateLabel = new JLabel();
        JButton closeButton = new JButton();
        JButton updateButton = new JButton();
        JScrollPane diagnoseScrollPane = new JScrollPane();
        JScrollPane prescriptionScrollPane = new JScrollPane();

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Retrieve medical record by ID
        mediRecordClass mediRecRetrieve = getRecordByMediRecId(mediRecID);
        if (mediRecRetrieve != null) {
            String rMediRecID = mediRecRetrieve.getMediRecID();
            String rCreateDate = mediRecRetrieve.getCreateDate();
            String rPatientID = mediRecRetrieve.getPatientID();
            rDoctorID = mediRecRetrieve.getDoctorID();
            String rBloodType = mediRecRetrieve.getBloodType();
            String rWeight = mediRecRetrieve.getWeight();
            String rHeight = mediRecRetrieve.getHeight();
            String rDiagnose = mediRecRetrieve.getDiagnose();
            String rPrescription = mediRecRetrieve.getPrescription();

            MmediRecID = new JLabel(rMediRecID);
            createDate = new JLabel(rCreateDate);
            PpatientID = new JLabel(rPatientID);
            patientBloodType = new JTextField(rBloodType);
            patientWeight = new JTextField(rWeight);
            patientHeight = new JTextField(rHeight);
            diagnose = new JTextArea(rDiagnose);
            prescription = new JTextArea(rPrescription);

            // Enable wrapping and make the text area read-only
            patientBloodType.setEditable(true);
            patientWeight.setEditable(true);
            patientHeight.setEditable(true);

            diagnose.setLineWrap(true);
            diagnose.setWrapStyleWord(true);
            diagnose.setEditable(true);

            prescription.setLineWrap(true);
            prescription.setWrapStyleWord(true);
            prescription.setEditable(true);

            diagnoseScrollPane.setViewportView(diagnose);
            prescriptionScrollPane.setViewportView(prescription);

            // Retrieve patient information by patient ID
            Patient patientIDRetrieve = getPatientById(rPatientID);
            if (patientIDRetrieve != null) {
                String rPatientName = patientIDRetrieve.getName();
                String rContactNum = patientIDRetrieve.getContactNumber();

                patientName = new JLabel(rPatientName);
                patientContact = new JLabel(rContactNum);
            }

            // Retrieve doctor information by doctor ID
            Doctor doctorIDRetrieve = getDoctorById(rDoctorID);
            if (doctorIDRetrieve != null) {
                String rDoctorName = doctorIDRetrieve.getName();
                doctorName = new JLabel(rDoctorName);
            }

            DdoctorID = new JLabel(rDoctorID);
        }

        clinicNameLabel.setFont(new Font("Segoe UI", 0, 36));
        clinicNameLabel.setText("AppointWell Clinic");

        patientIDLabel.setText("UserID :");
        patientNameLabel.setText("Name :");
        patientContactLabel.setText("Contact Number :");
        bloodTypeLabel.setText("Blood Type :");
        weightLabel.setText("Weight(kg):");
        heightLabel.setText("Height(cm):");

        doctorIDLabel.setText("DoctorID :");
        doctorNameLabel.setText("Doctor Name :");
        diagnoseLabel.setText("Diagnose :");
        prescriptionLabel.setText("Prescription :");
        createDateLabel.setText("Create Date :");

        closeButton.setText("Close");
        closeButton.addActionListener(evt -> {
            try {
                closeButtonActionPerformed(evt);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        List<mediRecordClass> newRecord = MediRecFileReader.readMediRecFromFile("src/TextFiles/MedicalRecord.txt");

        updateButton.setText("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldcreateDate = createDate.getText().trim();
                String oldPatientID = PpatientID.getText().trim();
                String oldDoctorID = DdoctorID.getText().trim();
                String newbloodType = patientBloodType.getText().trim();
                String newweight = patientWeight.getText().trim();
                String newheight = patientHeight.getText().trim();
                String newdiagnose = diagnose.getText().trim();
                String newpresciption = prescription.getText().trim();

                if (newbloodType.isEmpty() || newweight.isEmpty() || newheight.isEmpty() || newdiagnose.isEmpty() || newpresciption.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in the required field.");
                    return;
                }

                if (!newweight.matches("\\d+(\\.\\d{1,2})?")) {
                    JOptionPane.showMessageDialog(null, "Please fill in the correct weight.");
                    return;
                }

                if (!newheight.matches("\\d+(\\.\\d{1,2})?")) {
                    JOptionPane.showMessageDialog(null, "Please fill in the correct height.");
                    return;
                }
                // Update the patient object with the new values
                for (mediRecordClass record : newRecord) {
                    if (record.getMediRecID().equals(mediRecID)) {
                        record.setCreateDate(oldcreateDate);
                        record.setBloodType(newbloodType);
                        record.setWeight(newweight);
                        record.setHeight(newheight);
                        record.setDiagnose(newdiagnose);
                        record.setPrescription(newpresciption);
                        record.setPatientID(oldPatientID);
                        record.setDoctorID(oldDoctorID);
                        break;
                    }
                }

                try {
                    MediRecFileWriter.writeRecordToFile("src/TextFiles/MedicalRecord.txt", newRecord);
                    JOptionPane.showMessageDialog(null, "Patient profile updated successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Failed to update patient profile. Please try again.");
                    ex.printStackTrace();
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
                                                                .addComponent(clinicNameLabel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(MmediRecID, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(createDate, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)) // Added createDate label
                                                        .addGap(358, 358, 358)
                                                        .addComponent(closeButton)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(patientContactLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(patientNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(patientIDLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
                                                .addGap(28, 28, 28)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(patientContact, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(PpatientID, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(patientName, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(bloodTypeLabel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(weightLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(heightLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(patientHeight, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(patientBloodType, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(patientWeight, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                                .addGap(206, 206, 206))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(prescriptionScrollPane, GroupLayout.Alignment.LEADING)
                                                        .addComponent(prescriptionLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                .addComponent(doctorIDLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(DdoctorID, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                                .addComponent(doctorNameLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(doctorName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(diagnoseLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jSeparator2, GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                                                        .addComponent(diagnoseScrollPane))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(updateButton)
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
                                .addComponent(MmediRecID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(createDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE) // Added createDate label
                                .addGap(46, 46, 46)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(patientIDLabel)
                                        .addComponent(bloodTypeLabel)
                                        .addComponent(PpatientID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(patientBloodType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(patientNameLabel)
                                        .addComponent(weightLabel)
                                        .addComponent(patientName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(patientWeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(patientContactLabel)
                                                .addComponent(patientContact, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(heightLabel)
                                                .addComponent(patientHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(31, 31, 31)
                                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(doctorIDLabel)
                                        .addComponent(DdoctorID))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(doctorNameLabel)
                                        .addComponent(doctorName))
                                .addGap(26, 26, 26)
                                .addComponent(diagnoseLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(diagnoseScrollPane, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(prescriptionLabel)
                                .addGap(18, 18, 18)
                                .addComponent(prescriptionScrollPane, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(updateButton)
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

    private void closeButtonActionPerformed(ActionEvent evt) throws IOException {
        dispose();
         new DoctorMediRecManagement(DoctorMediRecDisplay.this, rDoctorID).setVisible(true);
    }

}
