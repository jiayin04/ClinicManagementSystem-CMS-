/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ASUS
 */
public class UserManagementSideBar extends JPanel {

    JPanel sideBar;
    JLabel userRole;
    JButton patientBtn;
    JButton doctorBtn;
    JButton adminBtn;

    public UserManagementSideBar(JFrame parentFrame) {
        setLayout(new BorderLayout());

        sideBar = new JPanel();
        sideBar.setBackground(new Color(47, 39, 206, 20));
        sideBar.setPreferredSize(new Dimension(400, 100));
        sideBar.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 20));
        add(sideBar, BorderLayout.WEST);

        userRole = new JLabel("User Role");
        userRole.setPreferredSize(new Dimension(380, 30));
        userRole.setFont(new Font("Segeo UI", Font.BOLD, 30));
        userRole.setAlignmentX(LEFT_ALIGNMENT);
        sideBar.add(userRole);

        patientBtn = new JButton("Patient");
        patientBtn.setPreferredSize(new Dimension(380, 30));
        patientBtn.setFont(new Font("Segeo UI", Font.BOLD, 18));
        patientBtn.setBackground(Color.WHITE);
        sideBar.add(patientBtn);

        doctorBtn = new JButton("Doctor");
        doctorBtn.setPreferredSize(new Dimension(380, 30));
        doctorBtn.setFont(new Font("Segeo UI", Font.BOLD, 18));
        doctorBtn.setBackground(Color.WHITE);
        sideBar.add(doctorBtn);

        adminBtn = new JButton("Admin");
        adminBtn.setPreferredSize(new Dimension(380, 30));
        adminBtn.setFont(new Font("Segeo UI", Font.BOLD, 18));
        adminBtn.setBackground(Color.WHITE);
        sideBar.add(adminBtn);

        patientBtn.addActionListener((ActionEvent e) -> {
            parentFrame.dispose();
            try {
                PatientUserManagement patientUserManagement = new PatientUserManagement();
                patientUserManagement.setVisible(true); // Set the new frame visible
            } catch (IOException ex) {
                Logger.getLogger(UserManagementSideBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        doctorBtn.addActionListener((ActionEvent e) -> {
            parentFrame.dispose();
            try {
                DoctorUserManagement doctorUserManagement = new DoctorUserManagement();
            } catch (IOException ex) {
                Logger.getLogger(UserManagementSideBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        adminBtn.addActionListener((ActionEvent e) -> {
            parentFrame.dispose();
            try {
                AdminUserManagement adminUserManagement = new AdminUserManagement();
            } catch (IOException ex) {
                Logger.getLogger(UserManagementSideBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}