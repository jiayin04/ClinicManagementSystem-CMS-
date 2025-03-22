/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Role;

/**
 *
 * @author ASUS
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
List: Admin, Doctor, and Patient
Array: Each user role information 
 */
public class UserFileReader {

    public static List<Admin> readAdminsFromFile(String filePath) throws IOException {
        List<Admin> admins = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/AdminData.txt"));//admin file path
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (fields.length == 10) {
                    String id = fields[0].trim();
                    String name = fields[1].trim();
                    String ic = fields[2].trim();
                    String email = fields[3].trim();
                    String gender = fields[4].trim();
                    String dob = fields[5].trim();
                    String address = fields[6].trim();
                    String contactNum = fields[7].trim();
                    String emergencyContactNum = fields[8].trim();
                    String password = fields[9].trim();
                    admins.add(new Admin(id, name, ic, email, gender, dob, address, contactNum, emergencyContactNum, password));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return admins;
    }

    public static List<Doctor> readDoctorsFromFile(String filePath) throws IOException {
        List<Doctor> doctors = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/DoctorData.txt"));//doctor file path
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (fields.length == 11) {
                    String id = fields[0].trim();
                    String name = fields[1].trim();
                    String ic = fields[2].trim();
                    String email = fields[3].trim();
                    String gender = fields[4].trim();
                    String dob = fields[5].trim();
                    String address = fields[6].trim();
                    String contactNum = fields[7].trim();
                    String specialty = fields[8].trim();
                    String medicalDegree = fields[9].trim();
                    String password = fields[10].trim();
                    doctors.add(new Doctor(id, name, ic, email, gender, dob, address, contactNum, specialty, medicalDegree, password));
                }
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return doctors;
    }

    public static List<Patient> readPatientsFromFile(String filePath) throws IOException {
        List<Patient> patients = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/PatientData.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (fields.length == 10) {
                    String id = fields[0].trim();
                    String name = fields[1].trim();
                    String ic = fields[2].trim();
                    String email = fields[3].trim();
                    String gender = fields[4].trim();
                    String dob = fields[5].trim();
                    String address = fields[6].trim();
                    String contactNum = fields[7].trim();
                    String emergencyContactNum = fields[8].trim();
                    String password = fields[9].trim();
                    patients.add(new Patient(id, name, ic, email, gender, dob, address, contactNum, emergencyContactNum, password));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error:" + e);
        }

        return patients;
    }
}