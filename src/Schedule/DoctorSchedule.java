package Schedule;

import Appointment.Appointment;
import Role.Doctor;
import Role.UserFileReader;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoctorSchedule {

    private String scheduleID;
    private String doctorID;
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public DoctorSchedule(String scheduleID, String doctorID, LocalDate availableDate, LocalTime startTime, LocalTime endTime) {
        this.scheduleID = scheduleID;
        this.doctorID = doctorID;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
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

    public String getDoctorName() throws IOException {
        List<Doctor> doctors = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(this.doctorID)) {
                return doctor.getName();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return scheduleID + "|" + doctorID + " | " + availableDate.toString() + " | " + startTime.toString() + " | " + endTime.toString();
    }

    public static Set<LocalDate> getAvailableDates(List<DoctorSchedule> schedules) {
        Set<LocalDate> availableDates = new HashSet<>();
        for (DoctorSchedule schedule : schedules) {
            availableDates.add(schedule.getAvailableDate());
        }
        return availableDates;
    }

    // Method to read schedules from a file
    public static List<DoctorSchedule> readSchedulesFromFile(String fileName) {
        List<DoctorSchedule> schedules = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String scheduleID = parts[0].trim();
                String doctorID = parts[1].trim();
                LocalDate availableDate = LocalDate.parse(parts[2].trim());
                LocalTime startTime = LocalTime.parse(parts[3].trim());
                LocalTime endTime = LocalTime.parse(parts[4].trim());
                schedules.add(new DoctorSchedule(scheduleID, doctorID, availableDate, startTime, endTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    // Method to write schedules to a file
    public static void writeSchedulesToFile(List<DoctorSchedule> schedules, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (DoctorSchedule schedule : schedules) {
                writer.write(schedule.getScheduleID() + " | "
                        + schedule.getDoctorID() + " | "
                        + schedule.getAvailableDate() + " | "
                        + schedule.getStartTime() + " | "
                        + schedule.getEndTime());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isTimeSlotAvailable(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime, String fileNameSlots, String fileNameAppointment) {

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        
        if (date.isBefore(currentDate) || (date.isEqual(currentDate) && endTime.isBefore(currentTime))){
            return false;
        }

        List<DoctorSchedule> schedules = readSchedulesFromFile(fileNameSlots);
        for (DoctorSchedule schedule : schedules) {
            if (schedule.getDoctorID().equals(doctorID) && schedule.getAvailableDate().equals(date)) {
                if ((startTime.isBefore(schedule.getEndTime()) && endTime.isAfter(schedule.getStartTime()))
                        || (startTime.equals(schedule.getStartTime()) && endTime.equals(schedule.getEndTime()))) {
                    return false;
                }
            }
        }
        List<Appointment> appointments = Appointment.readAppointment(fileNameAppointment);
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID) && appointment.getAppointmentDate().equals(date)) {
                if ((startTime.isBefore(appointment.getEndTime()) && endTime.isAfter(appointment.getStartTime()))
                        || (startTime.equals(appointment.getStartTime()) && endTime.equals(appointment.getEndTime()))) {
                    return false;
                }
            }
        }
        return true;
    }
}
