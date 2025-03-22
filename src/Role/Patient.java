/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Role;

/**
 *
 * @author ASUS
 */
public class Patient extends User {
    public Patient(String id, String name, String ic, String email, String gender, String dateOfBirth, String address,
                   String contactNumber, String emergencyContactNumber, String password) {
        super(id, name, ic, email, gender, dateOfBirth, address, contactNumber, emergencyContactNumber, password);
    }

}