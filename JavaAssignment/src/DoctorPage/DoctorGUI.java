package DoctorPage;

import DoctorAppointment.ViewIndividualAppointment;
import DoctorMedicalHistory.DoctorMediRecManagement;
import Login.LoginForm;
import Schedule.UploadSchedule;
import ViewProfile.DoctorViewProfile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

public class DoctorGUI extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private String doctorID;
    private DoctorProfilePanel profilePanel;
    private UploadSchedulePanel uploadSchedulePanel;
    private ViewAppointmentsPanel viewAppointmentsPanel;
    public MedicalRecordPanel medicalRecordPanel;
    private JPanel viewAppointmentBookingPanel;

    public static void main(String[] args) throws IOException, ParseException {
        // Create a DoctorGUI instance for the doctor with ID "D001"
        DoctorGUI doctorGUI = new DoctorGUI("D004");
        MedicalRecordPanel medicalRecordPanel = doctorGUI.medicalRecordPanel;
        if (medicalRecordPanel != null) {
            medicalRecordPanel.updateTableData();
        } else {
            System.out.println("MedicalRecordPanel is null");
        }
    }

    public DoctorGUI(String doctorID) throws IOException, ParseException {
        this.doctorID = doctorID; // Store the doctor ID

        setTitle("Doctor Page");
        setSize(800, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Initialize card pages
        profilePanel = new DoctorProfilePanel();
        uploadSchedulePanel = new UploadSchedulePanel();
        viewAppointmentsPanel = new ViewAppointmentsPanel();
        medicalRecordPanel = new MedicalRecordPanel(this);

        // Add card pages to the card panel
        cardPanel.add(uploadSchedulePanel, "UploadSchedulePanel");
        cardPanel.add(viewAppointmentsPanel, "ViewAppointmentsPanel");
        cardPanel.add(profilePanel, "MyProfilePanel");
        cardPanel.add(medicalRecordPanel, "MedicalRecordPanel");

        add(cardPanel, BorderLayout.CENTER);

        // Create navigation buttons
        JButton uploadScheduleBtn = createButton("Upload Schedule");
        JButton viewAppointmentsBtn = createButton("View Appointments");
        JButton myProfileBtn = createButton("My Profile");
        JButton mediRecBtn = createButton("Medical History");
        JButton logoutBtn = createButton("Logout");
        logoutBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm();
            }
        
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(uploadScheduleBtn);
        buttonPanel.add(viewAppointmentsBtn);
        buttonPanel.add(myProfileBtn);
        buttonPanel.add(mediRecBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.NORTH);

        // Button actions to switch card panels
        uploadScheduleBtn.addActionListener(e -> cardLayout.show(cardPanel, "UploadSchedulePanel"));
        viewAppointmentsBtn.addActionListener(e -> cardLayout.show(cardPanel, "ViewAppointmentsPanel"));
        myProfileBtn.addActionListener(e -> cardLayout.show(cardPanel, "MyProfilePanel"));
        mediRecBtn.addActionListener(e -> cardLayout.show(cardPanel, "MedicalRecordPanel"));
        logoutBtn.addActionListener(e -> cardLayout.show(cardPanel, "LogoutPanel"));

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 30)); // Set button size
        button.setBackground(new Color(169, 220, 247));
        return button;
    }

    class DoctorProfilePanel extends JPanel {

        public DoctorProfilePanel() throws IOException, ParseException {
            setBackground(new Color(224, 251, 226));
//            setLayout(new BorderLayout());
            DoctorViewProfile doctorViewProfile = new DoctorViewProfile(doctorID);
            add(doctorViewProfile.getDoctorViewPanel());
            setVisible(true);

        }
    }

    class UploadSchedulePanel extends JPanel {

        public UploadSchedulePanel() throws IOException {
            setBackground(new Color(224, 251, 226));
            setLayout(new BorderLayout());

            UploadSchedule uploadSchedule = new UploadSchedule(doctorID);
            add(uploadSchedule.getMainPanel());

        }
    }

    class ViewAppointmentsPanel extends JPanel {

        public ViewAppointmentsPanel() throws IOException {
            setBackground(Color.WHITE);
            setLayout(new BorderLayout());

            ViewIndividualAppointment viewAppointment = new ViewIndividualAppointment(doctorID);
            add(viewAppointment.getMainPanel());
            viewAppointment.getMainPanel().updateUI();

        }
    }

    public class MedicalRecordPanel extends JPanel {

        private DoctorMediRecManagement doctorMediRecManagement;

        public MedicalRecordPanel(JFrame parentFrame) throws IOException {
            setBackground(new Color(224, 251, 226));
            doctorMediRecManagement = new DoctorMediRecManagement(parentFrame, doctorID);
            add(doctorMediRecManagement.getMediPanel());
            setVisible(true);
        }

        public void updateTableData() throws IOException {
            doctorMediRecManagement.updateTableData();
        }
    }
    
    public void showMedicalRecordPanel() {
        cardLayout.show(cardPanel, "MedicalRecordPanel");
    }
}
