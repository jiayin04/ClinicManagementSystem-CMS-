package PatientMedicalHistory;

import MedicalRecord.MediRecFileReader;
import MedicalRecord.mediRecordClass;
import static MedicalRecord.mediRecordClass.getDoctorById;
import static MedicalRecord.mediRecordClass.getPatientById;
import Role.Doctor;
import Role.Patient;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.GroupLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class PatientMediRecDisplay extends JFrame {
    
    private JLabel MmediRecID;
    private JLabel createDate;
    private JLabel PpatientID;
    private JLabel patientName;
    private JLabel patientContact;
    private JLabel patientBloodType;
    private JLabel patientWeight;
    private JLabel patientHeight;
    private JLabel DdoctorID;
    private JLabel doctorName;
    private JTextArea diagnose;
    private JTextArea prescription;

    public PatientMediRecDisplay(String mediRecID) throws IOException, ParseException {
        setTitle("Patient Medical Record");
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
            String rDoctorID = mediRecRetrieve.getDoctorID();
            String rBloodType = mediRecRetrieve.getBloodType();
            String rWeight = mediRecRetrieve.getWeight();
            String rHeight = mediRecRetrieve.getHeight();
            String rDiagnose = mediRecRetrieve.getDiagnose();
            String rPrescription = mediRecRetrieve.getPrescription();
            
            MmediRecID = new JLabel(rMediRecID);
            createDate = new JLabel(rCreateDate);
            PpatientID = new JLabel(rPatientID);
            patientBloodType = new JLabel(rBloodType);
            patientWeight = new JLabel(rWeight);
            patientHeight = new JLabel(rHeight);
            diagnose = new JTextArea(rDiagnose);
            prescription = new JTextArea(rPrescription);

            // Enable wrapping and make the text area read-only
            diagnose.setLineWrap(true);
            diagnose.setWrapStyleWord(true);
            diagnose.setEditable(false);

            prescription.setLineWrap(true);
            prescription.setWrapStyleWord(true);
            prescription.setEditable(false);

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

        patientIDLabel.setText("PatientID :");
        patientNameLabel.setText("Name :");
        patientContactLabel.setText("Contact Number :");
        bloodTypeLabel.setText("Blood Type :");
        weightLabel.setText("Weight (kg):");
        heightLabel.setText("Height (cm):");

        doctorIDLabel.setText("DoctorID :");
        doctorNameLabel.setText("Doctor Name:");
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
                                    .addComponent(doctorNameLabel, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(doctorName, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
                                .addComponent(diagnoseLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator2, GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                                .addComponent(diagnoseScrollPane))
                            .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(closeButton)
                        .addComponent(clinicNameLabel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
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
                    .addContainerGap(63, Short.MAX_VALUE))
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
        setVisible(true);
    }

    private mediRecordClass getRecordByMediRecId(String mediRecID) throws IOException, ParseException {
        List<mediRecordClass> mediRecordData = MediRecFileReader.readMediRecFromFile("src/TextFiles/MedicalRecord.txt");
        for (mediRecordClass mediRec : mediRecordData) {
            if (mediRec.getMediRecID().equals(mediRecID)) {
                return mediRec;
            }
        }
        return null;
    }

    private void closeButtonActionPerformed(ActionEvent evt) throws IOException {
        this.dispose();
    }
}
