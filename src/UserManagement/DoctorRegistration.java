/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserManagement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class DoctorRegistration extends JFrame {

    DoctorRegistration() {
        this.setTitle("Doctor Registration"); //set the title of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
        setExtendedState(JFrame.MAXIMIZED_BOTH); //maximize the width and height
        setLayout(null);
        this.setVisible(true); //make frame visible
        this.setBackground(new Color(231, 235, 239));

        int fW = getWidth();
        int fH = getHeight();
        setVisible(false);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label = new JLabel();

        // Back Button
        ImageIcon back = new ImageIcon("src/images/backButton.png");
        Image backImg = back.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton backButton = new JButton(new ImageIcon(backImg));
        backButton.setPreferredSize(new Dimension(30, 30));
        backButton.addActionListener((ActionEvent e) -> {
            dispose();
            try {
                DoctorUserManagement doctorUserManagement = new DoctorUserManagement();
            } catch (IOException ex) {
                Logger.getLogger(PatientProfileManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        panel.add(backButton); // Add the back button to the panel

// Position the back button at the top left corner
        backButton.setBounds(10, 10, 30, 30); // Adjust the position as needed

//      Image
        ImageIcon image = new ImageIcon("src/images/DoctorRegistration.png"); //create an image icon
        label.setBounds(0, 0, 270, 500);
        label.setBackground(Color.black);
        label.setIcon(image);
        int iW = label.getWidth();
        int iH = label.getHeight();
        panel.setBounds(((fW * 2 / 3) - iW) / 2, (fH - iH) / 2, 270, 500);

        JLabel info = new JLabel("Sign Up to Get More Info");
//            info.setBackground(new Color(255, 0, 0, 100));
//            info.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.setFont(new Font("Segeo UI", Font.BOLD, 18));
        info.setForeground(Color.WHITE);
        info.setBounds((iW / 5 / 2), (iH / 4) * 3, 220, 50);
        panel.add(info);

///*********************************************************************************************************************
//          Information collection section
        JPanel panel2 = new JPanel();
        panel2.setBounds(panel.getX() + 270, panel.getY(), 550, 500);
        panel2.setBackground(Color.WHITE);
        panel2.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel signUp = new JLabel("Sign Up");
        signUp.setFont(new Font("Segeo UI", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        panel2.add(signUp, gbc);

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel2.add(nameLabel, gbc);

        JTextField nameField = new JTextField("Full Name as per IC");
        nameField.setBackground(new Color(217, 241, 253));
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(nameField, gbc);

        // IC/Passport ID
        JLabel icLabel = new JLabel("IC/Passport ID:");
        icLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel2.add(icLabel, gbc);

        JTextField icField = new JTextField("012345-67-8910");
        icField.setBackground(new Color(217, 241, 253));
        icField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel2.add(icField, gbc);

        // Email Address
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel2.add(emailLabel, gbc);

        JTextField emailField = new JTextField("email@mail.com");
        emailField.setBackground(new Color(217, 241, 253));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel2.add(emailField, gbc);

        // Contact Number
        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel2.add(contactLabel, gbc);

        JTextField contactField = new JTextField("012-3456789");
        contactField.setBackground(new Color(217, 241, 253));
        contactField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel2.add(contactField, gbc);

        // Specialty
        JLabel specialtyLabel = new JLabel("Specialty:");
        specialtyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel2.add(specialtyLabel, gbc);

        JTextField specialtyField = new JTextField("Specialty Field");
        specialtyField.setBackground(new Color(217, 241, 253));
        specialtyField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel2.add(specialtyField, gbc);

        // Medical Degree - WAITING FOR AMENDMENTS
        JLabel medicalDegLabel = new JLabel("Medical Degree:");
        medicalDegLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel2.add(medicalDegLabel, gbc);

        String[] medicalDegreeSelection = {"MD", "MBBS"};
        JComboBox<String> medicalDegComboBox = new JComboBox<>(medicalDegreeSelection);
        medicalDegComboBox.setBackground(new Color(217, 241, 253));
        medicalDegComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        panel2.add(medicalDegComboBox, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        panel2.add(passwordLabel, gbc);

        JTextField passwordField = new JTextField("********");
        passwordField.setBackground(new Color(217, 241, 253));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        panel2.add(passwordField, gbc);

        //  Random Generated button
        JButton randomButton = new JButton("Randomize");
        randomButton.setPreferredSize(new Dimension(100, 25));
        randomButton.setBackground(new Color(0, 159, 245));
        randomButton.setFocusPainted(false);
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generate a random password
                String generatedPassword = generateRandomPassword();
                passwordField.setText(generatedPassword); // set the text at the password field
            }

            // Method to generate a random password
            private String generateRandomPassword() {
                // Define the characters allowed in the password
                String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";

                // Define the length of the password
                int passwordLength = 8;

                // Use StringBuilder to efficiently build the password
                StringBuilder sb = new StringBuilder(passwordLength);

                // Randomly select characters from the allowedChars string to form the password
                for (int i = 0; i < passwordLength; i++) {
                    int index = (int) (Math.random() * allowedChars.length());
                    sb.append(allowedChars.charAt(index));
                }

                return sb.toString();
            }
        });
        panel2.add(randomButton, gbc);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(250, 35));
        registerButton.setBackground(new Color(0, 0, 0));
        registerButton.setForeground(new Color(255, 255, 255));
        registerButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel2.add(registerButton, gbc);

        // Action listener for the register button
        registerButton.addActionListener((ActionEvent e) -> {
            // Retrieve data from the text fields
            String name1 = nameField.getText().trim();
            String ic = icField.getText().trim();
            String email = emailField.getText().trim();
            String contact = contactField.getText().trim();
            String medicalDegree = (String) medicalDegComboBox.getSelectedItem();
            String specialty = specialtyField.getText().trim();
            String password = passwordField.getText().trim();
            // Perform validation checks
            if (name1.isEmpty() || ic.isEmpty() || email.isEmpty() || contact.isEmpty() || medicalDegree.isEmpty() || specialty.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
                return; // Exit method if any field is empty
            }
            // Name cannot have integer
            if (!name1.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Invalid name. Please enter only alphabetic characters.");
                return;
            }
            // Validate IC format
            if (!ic.matches("\\d{6}-\\d{2}-\\d{4}")) {
                JOptionPane.showMessageDialog(null, "Invalid IC format. Please use XXXXXX-XX-XXXX format.");
                return; // Exit the method if validation fails
            }
            // Validate email format
            if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
                return;
            }
            // Validate contact number format
            if (!contact.matches("01\\d-\\d{7,8}")) {
                JOptionPane.showMessageDialog(null, "Invalid contact number format. Please use 01X-XXXXXXX format.");
                return;
            }
            // Validate specialty
            if (!specialty.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Invalid specialty. Please enter only alphabetic characters.");
                return;
            }
            if (password.contains("********")) {
                JOptionPane.showMessageDialog(null, "Please randomize the password first.");
                return;
            }
            //                    Increment rowCount
            int rowCount = getRowCount("src/TextFiles/DoctorData.txt");
            int currentPatientID = rowCount + 1;
            String doctorID = "D" + String.format("%04d", currentPatientID);
            
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
            String formattedDate = currentDate.format(formatter);
            // Write data to a text file
            try {
                FileWriter fw = new FileWriter("src/TextFiles/DoctorData.txt", true);
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(doctorID + "|");
                    bw.write(name1 + "|");
                    bw.write(ic + "|");
                    bw.write(email + "|");
                    bw.write("NA |");
                    bw.write(formattedDate + "|");
                    bw.write("NA |");
                    bw.write(contact + "|");
                    bw.write(specialty + "|");
                    bw.write(medicalDegree + "|");
                    bw.write(password + "\n");
                    bw.flush(); // Flushes the stream
                    // Closes the stream
                }
                // Reset all text fields
                nameField.setText("");
                icField.setText("");
                emailField.setText("");
                contactField.setText("");
                specialtyField.setText("");
                // Reset password field to default
                passwordField.setText("********");
                JOptionPane.showMessageDialog(null, "Registration successful!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error occurred while registering.");
            }
        });

        panel.add(label);
        this.add(panel);
        this.add(panel2);

        setVisible(true);
    }

    private int getRowCount(String filePath) {
        int rowCount = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.readLine() != null) {
                rowCount++;
            }
        } catch (IOException e) {
        }
        return rowCount;
    }
}
