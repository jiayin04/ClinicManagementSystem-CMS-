package AdminMainPage;

import Role.Admin;
import Role.Patient;
import Role.UserFileReader;
import Appointment.Appointment;
import AdminAppointment.WalkInAppointment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.lang.String;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminMainPage extends JFrame {

    // Main method to run the AdminMainPage independently
//    public static void main(String[] args) {
//        try {
//            // CHANGE ITTTTTTTTTTTTTTTTTTTTTTTTTTT
//            String adminID = "A0020";
//            new AdminMainPage(adminID);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public AdminMainPage(String id) throws IOException {

        setTitle("Admin Main Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        AdminNavBar navBar = new AdminNavBar(this);
        AdminNavBar.setAdminID(id);
        JPanel sideBar = createSideBar();
        JPanel footer = createFooter();
        JPanel info = createInfoPanel();
        JPanel infoP = createInfoPPanel();

        add(navBar, BorderLayout.NORTH);
        add(sideBar, BorderLayout.WEST);
        add(footer, BorderLayout.SOUTH);
        add(info, BorderLayout.CENTER);
        add(infoP, BorderLayout.EAST);

        setVisible(true);
    }

    private JPanel createSideBar() throws IOException {
        JPanel sideBar = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 10));
        sideBar.setPreferredSize(new Dimension(400, 100));
        sideBar.setBackground(new Color(47, 39, 206, 20));

        JPanel sidePanel1 = createSidePanel1();
        JPanel sidePanel2 = createSidePanel2();

        sideBar.add(createSectionPanel("Today's Appointment", 400, 1));
        sideBar.add(sidePanel1);
        sideBar.add(createSpacerPanel(300, 10));
        sideBar.add(sidePanel2);

        return sideBar;
    }

    private JPanel createSidePanel1() throws IOException {
        JPanel sidePanel1 = new JPanel(new GridLayout(0, 1, 0, 10));
        sidePanel1.setPreferredSize(new Dimension(400, 150));
        sidePanel1.setBackground(new Color(0, 0, 0, 0));
//        sidePanel1.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH));

        sidePanel1.add(createLabel("  Scheduled", Font.BOLD, 18));

        List<Appointment> appointments = Appointment.readAppointment("src/TextFiles/Appointment.txt");

        boolean hasAppointments = false;

        for (Appointment appointment : appointments) {
            String patientName = appointment.getPatientName();
            LocalDate appointmentDate = appointment.getAppointmentDate();
            LocalDate currentDate = LocalDate.now();
            String visitType = appointment.getVisitType();
            LocalTime startTime = appointment.getStartTime();
            LocalTime endTime = appointment.getEndTime();
            String appointmentStatus = appointment.getAppointmentStatus();

            if (appointmentDate.equals(currentDate) && visitType.equals("Scheduled") && appointmentStatus.equals("CONFIRMED")) {
                JLabel appointmentLabel = new JLabel(startTime + "-" + endTime + "   " + patientName);
                appointmentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                sidePanel1.add(appointmentLabel);
                hasAppointments = true;
            }
        }

        if (!hasAppointments) {
            JLabel noAppointment = new JLabel("  No appointment schedule today!");
            noAppointment.setFont(new Font("Segoe UI", Font.PLAIN, 15)); // Set font for consistency
            sidePanel1.add(noAppointment);
        }
        return sidePanel1;
    }

    private JPanel createSidePanel2() throws IOException {
        JPanel sidePanel2 = new JPanel(new GridLayout(0, 1, 0, 10));
        sidePanel2.setPreferredSize(new Dimension(400, 150));
        sidePanel2.setBackground(new Color(0, 0, 0, 0));

        sidePanel2.add(createLabel("  Walk In", Font.BOLD, 18));

        List<Appointment> appointments = Appointment.readAppointment("src/TextFiles/Appointment.txt");

        for (Appointment appointment : appointments) {
            String patientName = appointment.getPatientName();
            LocalDate appointmentDate = appointment.getAppointmentDate();
            LocalDate currentDate = LocalDate.now();
            String visitType = appointment.getVisitType();
            LocalTime startTime = appointment.getStartTime();
            LocalTime endTime = appointment.getEndTime();
            String appointmentStatus = appointment.getAppointmentStatus();

            if (appointmentDate.equals(currentDate) && visitType.equals("Walk-in") && appointmentStatus.equals("CONFIRMED")) {
                JLabel appointmentLabel = new JLabel(startTime + "-" + endTime + "   " + patientName);
                appointmentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                sidePanel2.add(appointmentLabel);
            }
        }

        JButton addWalkIn = new JButton("+ Add Walk In");
        addWalkIn.setBackground(Color.WHITE);
        addWalkIn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addWalkIn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    WalkInAppointment walkInAppointment = new WalkInAppointment();
                } catch (IOException ex) {
                    Logger.getLogger(AdminMainPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        });
        sidePanel2.add(addWalkIn);

        return sidePanel2;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new GridLayout(1, 4, 20, 10));
        footer.setBackground(new Color(217, 241, 253));

        footer.add(createContactPanel("Email:", "clinic@gmail.com"));
        footer.add(createContactPanel("Contact num:", "012-3456789"));
        footer.add(createContactPanel("Address:", "6, Jalan ABC, Taman XYZ, Kuala Lumpur"));
        footer.add(createLogoPanel());

        return footer;
    }

    private JPanel createContactPanel(String labelText, String valueText) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(217, 241, 253));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(label, constraints);

        constraints.gridy = 1;
        JLabel value = new JLabel(valueText, SwingConstants.CENTER);
        value.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panel.add(value, constraints);

        return panel;
    }

    private JPanel createLogoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(217, 241, 253));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.EAST;

        JLabel logo = new JLabel();
        ImageIcon img = new ImageIcon("src/images/clinicLogo.png");
        Image imgResized = img.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(imgResized));
        logo.setPreferredSize(new Dimension(40, 40));
        panel.add(logo, constraints);

        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        JLabel copyright = new JLabel("Copyright@AppointWell", SwingConstants.CENTER);
        copyright.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(copyright, constraints);

        return panel;
    }

    private JPanel createInfoPanel() throws IOException {
        JPanel info = new JPanel(new GridBagLayout());
        info.setBackground(Color.WHITE);

        JLabel welcome = new JLabel("Welcome back, Admin");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 35));
        info.add(welcome);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(20, 50, 25, 30); // top, left, bottom, right
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String tdyDate = formatter.format(date);
        info.add(createInfoPanelDetail("Today's Date:", tdyDate), gbc);
        gbc.gridy++;
        
        List<Patient> patients = UserFileReader.readPatientsFromFile("src/TextFiles/PatientData.txt");
        int numOfPatient = patients.size();
        String numOfPatientFormat = String.format("%s", numOfPatient);
        info.add(createInfoPanelDetail("Patient Registered:", numOfPatientFormat), gbc);
        gbc.gridy++;

        List<Appointment> appointments = Appointment.readAppointment("src/TextFiles/Appointment.txt");
        int appointNum = 0;
        LocalDate currentDate = LocalDate.now();
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentDate().equals(currentDate) && appointment.getAppointmentStatus().equals("CONFIRMED")) {
                appointNum += 1;
            }
        }
        String NumOfAppoint = Integer.toString(appointNum);
        info.add(createInfoPanelDetail("Total Appointments Received Today:", NumOfAppoint), gbc);

        return info;
    }

    private JPanel createInfoPanelDetail(String labelText, String valueText) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 10));
        panel.setBackground(new Color(217, 241, 253, 70));
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 3));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(label);

        JLabel value = new JLabel(valueText, SwingConstants.CENTER);
        value.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        panel.add(value);

        return panel;
    }

    private JPanel createInfoPPanel() throws IOException {
        JPanel infoP = new JPanel(new GridBagLayout());
        infoP.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 30, 10, 30); // top, left, bottom, right
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelP = new JLabel("Key Personnel - Admin");
        labelP.setFont(new Font("Segoe UI", Font.BOLD, 30));
        gbc.anchor = GridBagConstraints.CENTER;
        infoP.add(labelP, gbc);

        gbc.gridy++;
        infoP.add(createPersonnelListPanel(), gbc);

        return infoP;
    }

    private JScrollPane createPersonnelListPanel() throws IOException {
        JPanel personnelList = new JPanel(new GridLayout(0, 1, 0, 0));
        personnelList.setBackground(Color.WHITE);

        List<Admin> admins = UserFileReader.readAdminsFromFile("src/TextFiles/AdminData.txt");
        for (Admin admin : admins) {
            JPanel entryPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            entryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            entryPanel.setBackground(Color.WHITE);

            JLabel personnelLabel = new JLabel(admin.getName());
            personnelLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            entryPanel.add(personnelLabel);

            JLabel emailLabel = new JLabel(admin.getEmail());
            emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            entryPanel.add(emailLabel);

            personnelList.add(entryPanel);
        }

        JScrollPane scrollPane = new JScrollPane(personnelList);
        scrollPane.setPreferredSize(new Dimension(400, 380));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // Make it invisible

        return scrollPane;
    }

    private JLabel createLabel(String text, int style, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        return label;
    }

    private JPanel createSpacerPanel(int width, int height) {
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(width, height));
        spacer.setBackground(new Color(0, 0, 0, 0));
        return spacer;
    }

    private JPanel createSectionPanel(String text, int width, int rows) {
        JPanel panel = new JPanel(new GridLayout(rows, 1, 0, 1));
        panel.setPreferredSize(new Dimension(width, 50));
        panel.setBackground(new Color(0, 0, 0, 0));
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(label);
        return panel;
    }
}
