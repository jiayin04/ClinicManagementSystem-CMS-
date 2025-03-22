/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminMainPage;

import AdminAppointment.AdminViewAppointment;
import AdminMedicalHistory.AdminMediRecManagement;
import Payment.PaymentPage;
import UserManagement.PatientUserManagement;
import ViewProfile.AdminViewProfile;
import Login.LoginForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author ASUS
 */
public class AdminNavBar extends JPanel implements MenuListener {

    private final JFrame frame;
    JMenuBar menuBar;
    JMenu appointmentMenu;
    JMenu mediRecordMenu;
    JMenu paymentMenu;
    JMenu userManagementMenu;
    JLabel exitButton;
    JLabel profileImage;
    static String adminID;

    public AdminNavBar(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JPanel navBar = new JPanel();
        navBar.setBackground(Color.WHITE);
        navBar.setPreferredSize(new Dimension(100, 100));
        add(navBar, BorderLayout.NORTH);

        navBar.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 30));
        navBar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JPanel clinic = new JPanel(); //logo
        JLabel logo = new JLabel();
        ImageIcon clinicLogo = new ImageIcon("src/images/clinicLogo.png");
        Image img = clinicLogo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));
        logo.setPreferredSize(new Dimension(40, 40));
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    frame.dispose();
                    AdminMainPage adminMainPage = new AdminMainPage(adminID);
                } catch (IOException ex) {
                    Logger.getLogger(AdminNavBar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JLabel clinicName = new JLabel("AppointWell");
        clinicName.setFont(new Font("Segeo UI", Font.BOLD, 14));
        clinic.setBackground(Color.WHITE);
        clinic.add(logo);
        clinic.add(clinicName);

//            Menu abr
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(169, 220, 247));
        menuBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Appointment menu
        appointmentMenu = new JMenu("Appointment");
        appointmentMenu.setFont(new Font("Segeo UI", Font.BOLD, 16));
        appointmentMenu.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        appointmentMenu.setBackground(new Color(169, 220, 247));
        appointmentMenu.addMenuListener(this);

        mediRecordMenu = new JMenu("Medical Record");
        mediRecordMenu.setFont(new Font("Segeo UI", Font.BOLD, 16));
        mediRecordMenu.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        mediRecordMenu.setBackground(new Color(169, 220, 247));
        mediRecordMenu.addMenuListener(this);

        paymentMenu = new JMenu("Payment");
        paymentMenu.setFont(new Font("Segeo UI", Font.BOLD, 16));
        paymentMenu.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        paymentMenu.setBackground(new Color(169, 220, 247));
        paymentMenu.addMenuListener(this);

        userManagementMenu = new JMenu("User Management");
        userManagementMenu.setFont(new Font("Segeo UI", Font.BOLD, 16));
        userManagementMenu.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        userManagementMenu.setBackground(new Color(169, 220, 247));
        userManagementMenu.addMenuListener(this);

        menuBar.add(appointmentMenu);
        menuBar.add(mediRecordMenu);
        menuBar.add(paymentMenu);
        menuBar.add(userManagementMenu);

        navBar.add(clinic);
        navBar.add(menuBar);

        JPanel iconPanel = new JPanel();
        iconPanel.setPreferredSize(new Dimension(200, 50));
//        iconPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        iconPanel.setBackground(Color.WHITE);
        iconPanel.setLayout(null);

        profileImage = new JLabel();
        profileImage.setBounds(0, 8, 40, 40);
        ImageIcon profile = new ImageIcon("src/images/profileImage.png");
        Image imgprofile = profile.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        profileImage.setIcon(new ImageIcon(imgprofile));
        profileImage.setVerticalAlignment(JLabel.CENTER);
        profileImage.setHorizontalAlignment(JLabel.CENTER);

        // Add mouse listener to profile image
        profileImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose(); // Dispose the current frame
                try {
                    AdminViewProfile adminViewProfile = new AdminViewProfile(adminID);
                } catch (IOException | ParseException ex) {
                    JOptionPane.showMessageDialog(frame, "Error while directing to view profile page.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        iconPanel.add(profileImage);

        exitButton = new JLabel();
        exitButton.setBounds(80, 8, 40, 40);
        ImageIcon exit = new ImageIcon("src/images/exit.png");
        Image exitImg = exit.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        exitButton.setIcon(new ImageIcon(exitImg));
        exitButton.setVerticalAlignment(JLabel.CENTER);
        exitButton.setHorizontalAlignment(JLabel.CENTER);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                LoginForm loginForm = new LoginForm();
            }
        });

        iconPanel.add(exitButton);

        navBar.add(iconPanel);

        setVisible(true);

    }

    public static void setAdminID(String id) {
        // Method to set adminID
        adminID = id;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource() == appointmentMenu) {
            frame.dispose();
            try {
                AdminViewAppointment adminViewAppointment = new AdminViewAppointment();
            } catch (IOException ex) {
                Logger.getLogger(AdminNavBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == mediRecordMenu) {
            frame.dispose();
            try {
                AdminMediRecManagement adminMediRecManagement = new AdminMediRecManagement();
            } catch (IOException ex) {
                Logger.getLogger(AdminNavBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == paymentMenu) {
            frame.dispose();
            try {
                PaymentPage paymentPage = new PaymentPage();
            } catch (IOException ex) {
                Logger.getLogger(AdminNavBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == userManagementMenu) {
            frame.dispose();
            try {
                PatientUserManagement patientUserManagement = new PatientUserManagement();
            } catch (IOException ex) {
                Logger.getLogger(AdminNavBar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Error happening.");
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {
        //No implementation
    }

    @Override
    public void menuCanceled(MenuEvent e) {
        //No implementation
    }

}
