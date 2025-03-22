package Patient;

import Appointment.Appointment;
import Login.LoginForm;
import PatientAppointment.BookAppointment;
import PatientAppointment.SearchDoctor;
import PatientAppointment.ViewAppointment;
import PatientMedicalHistory.PatientMediRecManagement;
import Role.Doctor;
import ViewProfile.PatientViewProfile;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientPageGUI extends JFrame implements ActionListener {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTextField appointmentIDField;
//    private PatientPageLogic logic;
    private List<Doctor> doctors;
    private String patientID;
    private Timer timer;
    private DefaultTableModel tableModel;
    private JPanel viewBookingsPanel;
    private List<Appointment> appointments;
    private static final String APPOINTMENT_FILE_PATH = "src/TextFiles/Appointments.txt";

//    public static void main(String[] args) throws IOException, ParseException {
//        String patientID = "P001";
//        PatientPageGUI patientPageGUI = new PatientPageGUI(patientID);
//    }
    public PatientPageGUI(String patientID) throws IOException, ParseException {
        this.patientID = patientID;
        setTitle("Patient Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a card panel with CardLayout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Initialize the table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Appointment ID");
        tableModel.addColumn("Doctor");
        tableModel.addColumn("Date");
        tableModel.addColumn("Time");

        // Add different "pages" (panels) to the card panel
        cardPanel.add(createMyProfilePanel(), "MyProfilePanel");
        cardPanel.add(createBookAppointmentPanel(), "BookAppointmentPanel");
        cardPanel.add(createViewBookingsPanel(), "ViewBookingsPanel");
        cardPanel.add(createSearchDoctorPanel(), "SearchDoctorPanel");
        cardPanel.add(createMedicalHistoryPanel(), "Medical History");

        add(cardPanel, BorderLayout.CENTER);

        // Initially show the My Profile page
        cardLayout.show(cardPanel, "MyProfilePanel");

        // Add buttons to trigger page navigation
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton myProfileBtn = createButton("My Profile");
        myProfileBtn.setBackground(new Color(169, 220, 247));
        JButton bookAppointmentBtn = createButton("Book Appointment");
        bookAppointmentBtn.setBackground(new Color(169, 220, 247));
        JButton viewBookingBtn = createButton("View Booking");
        viewBookingBtn.setBackground(new Color(169, 220, 247));
        JButton searchDoctorBtn = createButton("Search Doctor");
        searchDoctorBtn.setBackground(new Color(169, 220, 247));
        JButton viewPatientMedicalRecord = createButton("Medical History");
        viewPatientMedicalRecord.setBackground(new Color(169, 220, 247));
        JButton logoutBtn = createButton("Logout"); // Create the logout button
        logoutBtn.setBackground(new Color(169, 220, 247));

        buttonPanel.add(myProfileBtn);
        buttonPanel.add(bookAppointmentBtn);
        buttonPanel.add(viewBookingBtn);
        buttonPanel.add(searchDoctorBtn);
        buttonPanel.add(viewPatientMedicalRecord);
        buttonPanel.add(logoutBtn); // Add the logout button to the button panel

        add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);

    }

    // Method to create a button with specified text and action command
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        return button;
    }

    // Method to create the My Profile panel
    private JPanel createMyProfilePanel() throws IOException, ParseException {
        JPanel myProfilePanel = new JPanel();
        myProfilePanel.setBackground(Color.WHITE);
        myProfilePanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Patient View Profile");
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        myProfilePanel.add(label, BorderLayout.NORTH);
        PatientViewProfile patientViewProfile = new PatientViewProfile(patientID);
        myProfilePanel.add(patientViewProfile.getMainPanel(), BorderLayout.CENTER);
        return myProfilePanel;
    }

    // Method to create the Book Appointment panel
    private JPanel createBookAppointmentPanel() throws IOException {
        JPanel bookAppointmentPanel = new JPanel();
        bookAppointmentPanel.setBackground(new Color(255, 254, 224));
        bookAppointmentPanel.setLayout(null);

//        BookAppointment bookAppointment = new BookAppointment(patientID);
        BookAppointment bookAppointment = new BookAppointment(patientID) {
            @Override
            protected void afterAppointmentBooked() {
                try {
                    updateViewBookingsPanel(); // Update the view booking panel after appointment booking
                } catch (IOException ex) {
                    Logger.getLogger(PatientPageGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        JPanel bookAppointmentContent = bookAppointment.getMainPanel();
        bookAppointmentContent.setBackground(new Color(255, 254, 224));
        bookAppointmentContent.setBounds(50, 50, 1450, 540); // Set bounds for BookAppointment panel
        bookAppointmentPanel.add(bookAppointmentContent);

        return bookAppointmentPanel;
    }

//  VIEW BOOKING AND CANCEL BOOKING
    private JPanel createViewBookingsPanel() throws IOException {

        viewBookingsPanel = new JPanel(new BorderLayout());
        viewBookingsPanel.setBackground(Color.WHITE);
        viewBookingsPanel.setLayout(new BorderLayout());

        // This method will be called to recreate the ViewAppointment panel
        updateViewBookingsPanel();

        return viewBookingsPanel;
    }

    // Method to update the table model with booking history data
    private void updateTableModel(DefaultTableModel tableModel) throws IOException {
        // Clear existing data
        tableModel.setRowCount(0);

        // Populate table model with booking history data
        appointments = Appointment.readAppointment(APPOINTMENT_FILE_PATH);
        if (appointments == null) {
            appointments = new ArrayList<>(); // Initialize to an empty list if null
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for (Appointment appointment : appointments) {
            Object[] rowData = {
                appointment.getAppointmentID(),
                appointment.getDoctorID(),
                appointment.getAppointmentDate().format(dateFormatter),
                appointment.getStartTime().format(timeFormatter) + " - " + appointment.getEndTime().format(timeFormatter), //                appointment.getAppointmentStatus()
            };
            tableModel.addRow(rowData);
        }
    }

    private JPanel createSearchDoctorPanel() throws IOException {
        JPanel searchDoctorPanel = new JPanel(new BorderLayout());
        searchDoctorPanel.setBackground(Color.WHITE);

        SearchDoctor searchDoctor = new SearchDoctor();
        searchDoctorPanel.add(searchDoctor.getMainPanel(), BorderLayout.CENTER);

        return searchDoctorPanel;
    }

    private JPanel createMedicalHistoryPanel() throws IOException, ParseException {
        JPanel createMedicalHistoryPanel = new JPanel();
        createMedicalHistoryPanel.setBackground(new Color(255, 254, 224));
        createMedicalHistoryPanel.setLayout(new BorderLayout());

        PatientMediRecManagement patientMediRecManagement = new PatientMediRecManagement(patientID);
        createMedicalHistoryPanel.add(patientMediRecManagement.getMediPanel(), BorderLayout.CENTER);

        return createMedicalHistoryPanel;
    }

    // Method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        // Show the corresponding "page" when a button is clicked
        String command = e.getActionCommand();
        switch (command) {
            case "My Profile" -> {
                cardLayout.show(cardPanel, "MyProfilePanel");
                cardPanel.revalidate();
                cardPanel.repaint();
            }
            case "Book Appointment" -> {
                cardLayout.show(cardPanel, "BookAppointmentPanel");
                cardPanel.revalidate();
                cardPanel.repaint();
            }
            case "View Booking" -> {
                try {
                    updateViewBookingsPanel();
                } catch (IOException ex) {
                    Logger.getLogger(PatientPageGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                cardLayout.show(cardPanel, "ViewBookingsPanel");
                cardPanel.revalidate();
                cardPanel.repaint();
            }
            case "Search Doctor" -> {
                cardLayout.show(cardPanel, "SearchDoctorPanel");
                cardPanel.revalidate();
                cardPanel.repaint();
            }
            case "Medical History" -> {
                cardLayout.show(cardPanel, "Medical History");
                cardPanel.revalidate();
                cardPanel.repaint();
            }
            case "Logout" ->
                logout();
            default -> {
            }
        }
    }

    private void updateViewBookingsPanel() throws IOException {
        // Remove all components from viewBookingsPanel
        viewBookingsPanel.removeAll();

        // Refresh the data in the table model
        updateTableModel(tableModel);

        // Create a new ViewAppointment with updated data and add it to the viewBookingsPanel
        ViewAppointment viewAppointment = new ViewAppointment(patientID);
        viewBookingsPanel.add(viewAppointment.getMainPanel(), BorderLayout.CENTER);

        // Repaint and revalidate the view bookings panel
        viewBookingsPanel.revalidate();
        viewBookingsPanel.repaint();
    }

    // Method to handle logout action
    private void logout() {
        dispose(); // Close the current window
        LoginForm loginForm = new LoginForm();
    }

}
