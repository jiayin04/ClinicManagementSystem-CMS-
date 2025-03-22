/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ViewProfile;

import Role.Doctor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.font.TextAttribute;
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
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author ASUS
 */
public class DoctorViewProfile extends JPanel {

    private JTextField nameField;
    private JTextField icField;
    private JTextField emailField;
    private JTextField contactNumberField;
    private JTextField specialtyField;
    private JTextField medicalField;
    private JTextArea addressField;
    private JPasswordField passwordField;
    private JButton backButton;
    private JPanel mainPanel;

    public DoctorViewProfile(String doctorID) throws IOException, ParseException {

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(1500, 700));
        add(mainPanel, BorderLayout.CENTER);

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(47, 39, 206, 20));

        JLabel titleLabel = new JLabel("Doctor View Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 35));
        titleLabel.setFont(titleLabel.getFont().deriveFont(TextAttribute.UNDERLINE_ON));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(47, 39, 206, 80), 2));
        formPanel.setPreferredSize(new Dimension(700, 600));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 5, 10);

        List<Doctor> doctors = Role.UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");

        for (Doctor doctor : doctors) {
            String doctorIDRetrieve = doctor.getId();
            if (doctorIDRetrieve.equals(doctorID)) {
                String name = doctor.getName().trim();
                String ic = doctor.getIc().trim();
                String email = doctor.getEmail().trim();
                String gender = doctor.getGender().trim();

                String dob = doctor.getDateOfBirth();
                String trimDOB = dob.trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                Date dobDate = dateFormat.parse(trimDOB);

                String address = doctor.getAddress().trim();
                String contactNumber = doctor.getContactNumber().trim();
                String specialty = doctor.getSpecialty().trim();
                String medicalDegree = doctor.getMedicalDegree().trim();
                String password = doctor.getPassword().trim();

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
                JLabel specialityLabel = new JLabel("Speciality:");
                specialityLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(specialityLabel, constraints);
                specialtyField = new JTextField(20);
                specialtyField.setFont(new Font("Segeo UI", Font.PLAIN, 16));
                specialtyField.setPreferredSize(new Dimension(180, 30));
                specialtyField.setText(specialty);
                constraints.gridx = 1;
                formPanel.add(specialtyField, constraints);

                constraints.gridy++;
                constraints.gridx = 0;
                JLabel medicalLabel = new JLabel("Medical Degree:");
                medicalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
                formPanel.add(medicalLabel, constraints);
                String[] medicalDegreeSelection = {"MD", "MBBS"};
                JComboBox<String> medicalDegComboBox = new JComboBox<>(medicalDegreeSelection);
                medicalDegComboBox.setBackground(new Color(217, 241, 253));
                medicalDegComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                medicalDegComboBox.setSelectedItem(medicalDegree);
                constraints.gridx = 1;
                formPanel.add(medicalDegComboBox, constraints);

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
                constraints.anchor = GridBagConstraints.CENTER;
                JButton saveButton = new JButton("Save Changes");
                saveButton.setBackground(Color.BLACK);
                saveButton.setForeground(Color.WHITE);
                saveButton.setPreferredSize(new Dimension(120, 30));

                saveButton.addActionListener((ActionEvent e) -> {
                    String nameValue = nameField.getText().trim();
                    String icValue = icField.getText();
                    Date selectedDate = dateOfBirthField.getDate();
                    String dobValue = new SimpleDateFormat("MMMM dd, yyyy").format(selectedDate);
                    String gender1 = (String) genderComboBox.getSelectedItem();
                    String emailValue = emailField.getText().trim();
                    String contactNum = contactNumberField.getText().trim();
                    String specialty1 = specialtyField.getText().trim();
                    String medicalDegreeValue = (String) medicalDegComboBox.getSelectedItem();
                    String addressValue = addressField.getText().trim();
                    String passwordValue = String.valueOf(passwordField.getPassword());
                    // Perform validation checks
                    if (nameValue.isEmpty() || icValue.isEmpty() || emailValue.isEmpty() || contactNum.isEmpty() || specialty1.isEmpty() || passwordValue.isEmpty()) {
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
                    //  Validate password
                    if (!passwordValue.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
                        JOptionPane.showMessageDialog(null, "Kindly fill in the password with at least 8 character, one upper case letter, one lower case letter, and a number.");
                        return;
                    }
                    // Update the patient object with the new values
                    for (Doctor doctor1 : doctors) {
                        if (doctor1.getId().equals(doctorID)) {
                            doctor1.setName(nameValue);
                            doctor1.setIc(icValue);
                            doctor1.setDateOfBirth(dobValue);
                            doctor1.setGender(gender1);
                            doctor1.setEmail(emailValue);
                            doctor1.setContactNumber(contactNum);
                            doctor1.setSpecialty(specialty1);
                            doctor1.setMedicalDegree(medicalDegreeValue);
                            doctor1.setAddress(addressValue);
                            doctor1.setPassword(passwordValue);
                            break;
                        }
                    }
                    try {
                        Role.UserFileWriter.writeDoctorsToFile("src/TextFiles/DoctorData.txt", doctors);
                        JOptionPane.showMessageDialog(null, "Doctor profile updated successfully.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to update doctor profile. Please try again.");
                    }
                });

                formPanel.add(saveButton, constraints);

                // Centering the form panel
                JPanel centerPanel = new JPanel(new GridBagLayout());
                centerPanel.setBackground(new Color(157, 226, 191, 70));
                GridBagConstraints centerConstraints = new GridBagConstraints();
                centerConstraints.insets = new Insets(0, 100, 0, 100); // Adjust insets here

                //        Image
                JPanel adminPanel = new JPanel();
                adminPanel.setPreferredSize(new Dimension(50, 100));
                JLabel adminLabel = new JLabel();
                ImageIcon adminIcon = new ImageIcon("src/images/doctor.png");
                adminLabel.setIcon(adminIcon);
                centerPanel.add(adminLabel, centerConstraints);

                // Add form panel to center panel 
                centerPanel.add(formPanel, centerConstraints);
                mainPanel.add(centerPanel, BorderLayout.CENTER);

                mainPanel.setVisible(true);
            }

        }
    }

    public JPanel getDoctorViewPanel() {
        return this;
    }
}
