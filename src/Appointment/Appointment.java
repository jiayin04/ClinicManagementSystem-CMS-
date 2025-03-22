/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Appointment;

import Role.Doctor;
import Role.Patient;
import Role.UserFileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author ASUS
 */
public class Appointment {

    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String appointmentStatus;
    private String visitType;

    // Constructor
    public Appointment(String appointmentID, String patientID, String doctorID,
            LocalDate appointmentDate, LocalTime startTime, LocalTime endTime,
            String appointmentStatus, String visitType) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentStatus = appointmentStatus;
        this.visitType = visitType;
    }

    // Getter and Setter methods
    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getDoctorName() throws IOException {
        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(this.doctorID)) {
                return doctor.getName();
            }
        }
        return null;
    }

    public String getPatientName() throws IOException {
        List<Patient> patients = UserFileReader.readPatientsFromFile("src/TextFiles/PatientData.txt");
        for (Patient patient : patients) {
            if (patient.getId().equals(this.patientID)) {
                return patient.getName();
            }
        }
        return null;
    }

    public static List<Appointment> readAppointment(String filePath) {
        List<Appointment> appointments = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/Appointments.txt"));//appointment file path
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (fields.length == 8) {
                    String appointmentID = fields[0].trim();
                    String patientID = fields[1].trim();
                    String doctorID = fields[2].trim();
                    LocalDate appointmentDate = LocalDate.parse(fields[3].trim(), dateFormatter);
                    LocalTime startTime = LocalTime.parse(fields[4].trim(), timeFormatter); // Parsing string to LocalTime
                    LocalTime endTime = LocalTime.parse(fields[5].trim(), timeFormatter);
                    String appointmentStatus = fields[6].trim();
                    String visitType = fields[7].trim();
                    appointments.add(new Appointment(appointmentID, patientID, doctorID, appointmentDate, startTime, endTime, appointmentStatus, visitType));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return appointments;
    }

    public static void writeAppointmentToFile(String filePath, List<Appointment> appointments) throws IOException {
        writeAppointmentToFile(filePath, appointments, false);
    }

    public static void writeAppointmentToFileAppend(String filePath, List<Appointment> appointments) throws IOException {
        writeAppointmentToFile(filePath, appointments, true);
    }

    public static void writeAppointmentToFile(String filePath, List<Appointment> appointments, boolean append) throws IOException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (BufferedWriter bw = new BufferedWriter(new BufferedWriter(new FileWriter(filePath, append)))) {
            for (Appointment appointment : appointments) {
                String line = String.format("%s |%s |%s |%s |%s |%s |%s |%s",
                        appointment.getAppointmentID(),
                        appointment.getPatientID(),
                        appointment.getDoctorID(),
                        appointment.getAppointmentDate().format(dateFormatter),
                        appointment.getStartTime().format(timeFormatter),
                        appointment.getEndTime().format(timeFormatter),
                        appointment.getAppointmentStatus(),
                        appointment.getVisitType()
                );
                bw.write(line);
                bw.newLine();
            }
        }
    }
    // Method to add a new appointment

    public static void addAppointment(List<Appointment> appointments, Appointment appointment) {
        appointments.add(appointment);
    }

    // Method to cancel an appointment
    public static void cancelAppointment(List<Appointment> appointments, String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointment.setAppointmentStatus("CANCELLED");
                break;
            }
        }
    }
}
