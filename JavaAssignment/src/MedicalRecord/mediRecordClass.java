package MedicalRecord;

import Role.Doctor;
import Role.Patient;
import Role.UserFileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class mediRecordClass {

    private String mediRecID;
    private String createDate;
    private String bloodType;
    private String weight;
    private String height;
    private String diagnose;
    private String prescription;
    private String patientID;
    private String doctorID;

    public mediRecordClass(String mediRecID, String createDate, String bloodType, String weight, String height, String diagnose, String prescription, String patientID, String doctorID) {
        this.mediRecID = mediRecID;
        this.createDate = createDate;
        this.bloodType = bloodType;
        this.weight = weight;
        this.height = height;
        this.diagnose = diagnose;
        this.prescription = prescription;
        this.patientID = patientID;
        this.doctorID = doctorID;
        

    }

    public String getMediRecID() {
        return mediRecID;
    }
    
    public String getCreateDate() {
        return createDate;
    }
    
    public String getBloodType() {
        return bloodType;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setMediRecID(String newMediRecID) {
        this.mediRecID = newMediRecID;
    }
    
    public void setCreateDate(String newCreateDate) {
        this.createDate = newCreateDate;
    }
    
    public void setBloodType(String newBloodType) {
        this.bloodType = newBloodType;
    }

    public void setWeight(String newWeight) {
        this.weight = newWeight;
    }

    public void setHeight(String newHeight) {
        this.height = newHeight;
    }

    public void setDiagnose(String newDiagnose) {
        this.diagnose = newDiagnose;
    }

    public void setPrescription(String newPrescription) {
        this.prescription = newPrescription;
    }
    
    public void setPatientID(String newPatientID){
        this.patientID = newPatientID;
    }
    
    public void setDoctorID(String newDoctorID){
        this.doctorID = newDoctorID;
    }
    
    public static mediRecordClass getRecordByMediRecId(String mediRecID) throws IOException, ParseException {
        List<mediRecordClass> mediRecordData = MediRecFileReader.readMediRecFromFile("src/TextFiles/MedicalRecord.txt");
        for (mediRecordClass mediRec : mediRecordData) {
            if (mediRec.getMediRecID().equals(mediRecID)) {
                return mediRec;
            }
        }
        return null;
    }

    public static Patient getPatientById(String patientID) throws IOException {
        List<Patient> patientData = UserFileReader.readPatientsFromFile("src/TextFiles/PatientData.txt");
        for (Patient patient : patientData) {
            if (patient.getId().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }

    public static Doctor getDoctorById(String doctorID) throws IOException {
        List<Doctor> doctorData = UserFileReader.readDoctorsFromFile("src/TextFiles/DoctorData.txt");
        for (Doctor doctor : doctorData) {
            if (doctor.getId().equals(doctorID)) {
                return doctor;
            }
        }
        return null;
    }

}
