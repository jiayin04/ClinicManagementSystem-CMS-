/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Role;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author ASUS
 */
public class UserFileWriter {
    public static void writeAdminsToFile(String filePath, List<Admin> admins) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new BufferedWriter(new FileWriter(filePath)))){
            for (Admin admin: admins){
                String line = String.format("%s |%s |%s |%s |%s |%s |%s |%s |%s |%s", 
                        admin.getId(),
                        admin.getName(),
                        admin.getIc(),
                        admin.getEmail(),
                        admin.getGender(),
                        admin.getDateOfBirth(),
                        admin.getAddress(),
                        admin.getContactNumber(),
                        admin.getEmergencyContactNumber(),
                        admin.getPassword()
                );
                bw.write(line);
                bw.newLine();
            }
        }
    }
    public static void writePatientsToFile(String filePath, List<Patient> patients) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new BufferedWriter(new FileWriter(filePath)))){
            for (Patient patient: patients){
                String line = String.format("%s |%s |%s |%s |%s |%s |%s |%s |%s |%s", 
                        patient.getId(),
                        patient.getName(),
                        patient.getIc(),
                        patient.getEmail(),
                        patient.getGender(),
                        patient.getDateOfBirth(),
                        patient.getAddress(),
                        patient.getContactNumber(),
                        patient.getEmergencyContactNumber(),
                        patient.getPassword()
                );
                bw.write(line);
                bw.newLine();
            }
        }
    }
    public static void writeDoctorsToFile(String filePath, List<Doctor> doctors) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new BufferedWriter(new FileWriter(filePath)))){
            for (Doctor doctor: doctors){
                String line = String.format("%s |%s |%s |%s |%s |%s |%s |%s |%s |%s |%s", 
                        doctor.getId(),
                        doctor.getName(),
                        doctor.getIc(),
                        doctor.getEmail(),
                        doctor.getGender(),
                        doctor.getDateOfBirth(),
                        doctor.getAddress(),
                        doctor.getContactNumber(),
                        doctor.getSpecialty(),
                        doctor.getMedicalDegree(),
                        doctor.getPassword()
                );
                bw.write(line);
                bw.newLine();
            }
        }
    }
}

