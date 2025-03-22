/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewProfile;

import Role.Patient;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class PatientViewProfile extends JPanel {

    private JTextField nameField;
    private JTextField icField;
    private JTextField emailField;
    private JTextField contactNumberField;
    private JTextField emergencyContactNumberField;
    private JTextArea addressField;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private JButton reset;

    public PatientViewProfile(String patientID) throws IOException, ParseException {

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(47, 39, 206, 80), 2));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.insets = new Insets(20, 10, 10, 10);

        List<Patient> patients = Role.UserFileReader.readPatientsFromFile("src/TextFiles/PatientData.txt");

        for (Patient patient : patients) {
            String patientIDRetrieve = patient.getId();
            if (patientIDRetrieve.equals(patientID)) {
                String name = patient.getName();
                String ic = patient.getIc();
                String email = patient.getEmail();
                String gender = patient.getGender();

                String dob = patient.getDateOfBirth();
                String trimDOB = dob.trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                Date dobDate = dateFormat.parse(trimDOB);

                String address = patient.getAddress();
                String contactNumber = patient.getContactNumber();
                String emergencyContactNumber = patient.getEmergencyContactNumber();
                String password = patient.getPassword();

                // Add form components
                constraints.gridy = 0;
                constraints.gridx = 0;
                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(nameLabel, constraints);
                nameField = new JTextField(20);
                nameField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                nameField.setPreferredSize(new Dimension(180, 30));
                nameField.setText(name);
                constraints.gridx = 1;
                formPanel.add(nameField, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel icLabel = new JLabel("IC/Passport Number:");
                icLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(icLabel, constraints);
                icField = new JTextField(20);
                icField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                icField.setPreferredSize(new Dimension(180, 30));
                icField.setText(ic);
                icField.setEditable(false);
                constraints.gridx = 1;
                formPanel.add(icField, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel dobLabel = new JLabel("Date of birth:");
                dobLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(dobLabel, constraints);
                JDateChooser dateOfBirthField = new JDateChooser();
                dateOfBirthField.setForeground(Color.BLACK);
                dateOfBirthField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                dateOfBirthField.setPreferredSize(new Dimension(250, 30));
                dateOfBirthField.setDate(dobDate);
                constraints.gridx = 1;
                formPanel.add(dateOfBirthField, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel genderLabel = new JLabel("Gender:");
                genderLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(genderLabel, constraints);
                String[] genders = {"Female", "Male"};
                JComboBox<String> genderComboBox = new JComboBox<>(genders);
                genderComboBox.setSelectedItem(gender);
                constraints.gridx = 1;
                formPanel.add(genderComboBox, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel emailLabel = new JLabel("Email Address:");
                emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(emailLabel, constraints);
                emailField = new JTextField(20);
                emailField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                emailField.setPreferredSize(new Dimension(180, 30));
                emailField.setText(email);
                constraints.gridx = 1;
                formPanel.add(emailField, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel contactLabel = new JLabel("Contact Number:");
                contactLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(contactLabel, constraints);
                contactNumberField = new JTextField(20);
                contactNumberField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                contactNumberField.setPreferredSize(new Dimension(180, 30));
                contactNumberField.setText(contactNumber);
                constraints.gridx = 1;
                formPanel.add(contactNumberField, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel emergencyContactLabel = new JLabel("Emergency Contact Number:");
                emergencyContactLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(emergencyContactLabel, constraints);
                emergencyContactNumberField = new JTextField(20);
                emergencyContactNumberField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                emergencyContactNumberField.setPreferredSize(new Dimension(180, 30));
                emergencyContactNumberField.setText(emergencyContactNumber);
                constraints.gridx = 1;
                formPanel.add(emergencyContactNumberField, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel addressLabel = new JLabel("Address:");
                addressLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(addressLabel, constraints);

                addressField = new JTextArea(3, 20);
                addressField.setBorder(BorderFactory.createLineBorder(Color.GRAY, WIDTH));
                addressField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                addressField.setWrapStyleWord(true);
                addressField.setLineWrap(true);
                addressField.setText(address);

                JScrollPane addressFieldScrollPane = new JScrollPane(addressField);
                addressField.setPreferredSize(new Dimension(260, 100));
                constraints.gridx = 1;
                formPanel.add(addressFieldScrollPane, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel passwordLabel = new JLabel("Password:");
                passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(passwordLabel, constraints);
                passwordField = new JPasswordField(20);
                passwordField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                passwordField.setPreferredSize(new Dimension(180, 30));
                passwordField.setText(password);
                constraints.gridx = 1;
                formPanel.add(passwordField, constraints);

                // Add save button
                constraints.gridy++;
                constraints.gridx = 1;
                JButton saveButton = new JButton("Save Changes");
                saveButton.setBackground(Color.BLACK);
                saveButton.setForeground(Color.WHITE);
                saveButton.setPreferredSize(new Dimension(120, 30));

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nameValue = nameField.getText().trim();
                        String icValue = icField.getText();

                        Date selectedDate = dateOfBirthField.getDate();
                        String dobValue = new SimpleDateFormat("MMMM dd, yyyy").format(selectedDate);

                        String gender = (String) genderComboBox.getSelectedItem();
                        String emailValue = emailField.getText().trim();
                        String contactNum = contactNumberField.getText().trim();
                        String emergencyNum = emergencyContactNumberField.getText().trim();
                        String addressValue = addressField.getText().trim();
                        String passwordValue = String.valueOf(passwordField.getPassword());

                        // Perform validation checks
                        if (nameValue.isEmpty() || icValue.isEmpty() || emailValue.isEmpty() || contactNum.isEmpty() || emergencyNum.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                            return; // Exit method if any field is empty
                        }

                        // Name cannot have integer
                        if (!nameValue.matches("[a-zA-Z\\s]+")) {
                            JOptionPane.showMessageDialog(null, "Invalid name. Please enter only alphabetic characters.");
                            return;
                        }

                        // Validate email format
                        if (!emailValue.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                            JOptionPane.showMessageDialog(null, "Invalid email format.");
                            return;
                        }

                        // Validate contact number format 
                        if (!contactNum.matches("01\\d-\\d{7,8}")) {
                            JOptionPane.showMessageDialog(null, "Invalid contact number format. Please use 01X-XXXXXXX format.");
                            return;
                        }

                        // Validate emergency contact number format 
                        if (!emergencyNum.matches("01\\d-\\d{7,8}")) {
                            JOptionPane.showMessageDialog(null, "Invalid emergency contact number format. Please use 01X-XXXXXXX format.");
                            return;
                        }

                        //  Validate password
                        if (!passwordValue.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
                            JOptionPane.showMessageDialog(null, "Kindly fill in the password with at least 8 character, one upper case letter, one lower case letter, a symbol and a number.");
                            return;
                        }

                        // Update the patient object with the new values
                        for (Patient patient : patients) {
                            if (patient.getId().equals(patientID)) {
                                patient.setName(nameValue);
                                patient.setIc(icValue);
                                patient.setDateOfBirth(dobValue);
                                patient.setGender(gender);
                                patient.setEmail(emailValue);
                                patient.setContactNumber(contactNum);
                                patient.setEmergencyContactNumber(emergencyNum);
                                patient.setAddress(addressValue);
                                patient.setPassword(passwordValue);
                                break;
                            }
                        }

                        try {
                            Role.UserFileWriter.writePatientsToFile("src/TextFiles/PatientData.txt", patients);
                            JOptionPane.showMessageDialog(null, "Patient profile updated successfully.");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Failed to update patient profile. Please try again.");
                            ex.printStackTrace();
                        }
                    }
                });
                formPanel.add(saveButton, constraints);

                // Centering the form panel
                JPanel centerPanel = new JPanel(new GridBagLayout());
                centerPanel.setBackground(new Color(255, 254, 224));
                GridBagConstraints centerConstraints = new GridBagConstraints();
                centerConstraints.insets = new Insets(0, 80, 0, 80);

                //        Image
                JPanel adminPanel = new JPanel();
                adminPanel.setPreferredSize(new Dimension(50, 100));
                JLabel adminLabel = new JLabel();
                ImageIcon adminIcon = new ImageIcon("src/images/patient.png");
                adminLabel.setIcon(adminIcon);
                centerPanel.add(adminLabel, centerConstraints);

                // Add form panel to center panel 
                centerPanel.add(formPanel, centerConstraints);
                mainPanel.add(centerPanel, BorderLayout.CENTER);

                add(mainPanel, BorderLayout.CENTER);
//                pack();
//                setLocationRelativeTo(null);
//                setExtendedState(JFrame.MAXIMIZED_BOTH);
//                setDefaultCloseOperation(EXIT_ON_CLOSE);
//                setVisible(true);
            }

        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
