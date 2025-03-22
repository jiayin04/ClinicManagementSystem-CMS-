package Login;

import AdminMainPage.AdminMainPage;
import DoctorPage.DoctorGUI;
import Patient.PatientPageGUI;
import Role.Admin;
import Role.Doctor;
import Role.Patient;
import Role.UserFileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author harra
 */
public class Login {

    public boolean isPatientLogin(String email, char[] password) throws IOException, ParseException {

        List<Patient> patients = UserFileReader.readPatientsFromFile("src/TextFiles/PatientData.txt");

        for (Patient patient : patients) {
            String emails = patient.getEmail();
            String passwords = patient.getPassword();
            String patientID = patient.getId();

            if (emails.equals(email) && Arrays.equals(passwords.toCharArray(), password)) {
                PatientPageGUI patientForm = new PatientPageGUI(patientID);
                patientForm.setVisible(true);
                return true;
            }

        }
        return false;
    }

    public boolean isAdminLogin(String email, char[] password) throws IOException {

        List<Admin> admins = UserFileReader.readAdminsFromFile("src/TextFiles/AdminData.txt");

        for (Admin admin : admins) {
            String emails = admin.getEmail();
            String passwords = admin.getPassword();
            String adminID = admin.getId();

            if (emails.equals(email) && Arrays.equals(passwords.toCharArray(), password)) {
                AdminMainPage adminMainPage = new AdminMainPage(adminID);
                adminMainPage.setVisible(true);
                return true;
            }

        }
        return false;
    }

    public boolean isDoctorLogin(String email, char[] password) throws IOException, ParseException{
        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");

        for (Doctor doctor : doctors) {
            String emails = doctor.getEmail();
            String passwords = doctor.getPassword();
            String doctorID = doctor.getId();

            if (emails.equals(email) && Arrays.equals(passwords.toCharArray(), password)) {
                DoctorGUI doctorGUI = new DoctorGUI(doctorID);
                doctorGUI.setVisible(true);
                return true;
            }

        }
        return false;
    }
}
