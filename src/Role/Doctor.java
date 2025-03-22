/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Role;

/**
 *
 * @author ASUS
 */
public class Doctor extends User {

    private String specialty;
    private String medicalDegree;

    public Doctor(String id, String name, String ic, String email, String gender, String dateOfBirth, String address,
            String contactNumber, String specialty, String medicalDegree, String password) {
        
        super(id, name, ic, email, gender, dateOfBirth, address, contactNumber, password);
        this.specialty = specialty;
        this.medicalDegree = medicalDegree;
    }

    // Getters and setters for specialty and medicalDegree
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getMedicalDegree() {
        return medicalDegree;
    }

    public void setMedicalDegree(String medicalDegree) {
        this.medicalDegree = medicalDegree;
    }
}