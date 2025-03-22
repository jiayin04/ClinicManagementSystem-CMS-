package MedicalRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author guppy
 */
//medi record writer
//medicalRecordID |  bloodType | weight | height | diagnose | prescription | patientID | doctorID

public class MediRecFileWriter {
    public static void writeRecordToFile(String filePath, List<mediRecordClass> MediRec) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new BufferedWriter(new FileWriter(filePath)))){
            for (mediRecordClass record : MediRec){
                String line = String.format("%s |%s |%s |%s |%s |%s |%s |%s |%s", 
                        record.getMediRecID(),
                        record.getCreateDate(),
                        record.getBloodType(),
                        record.getWeight(),
                        record.getHeight(),
                        record.getDiagnose(),
                        record.getPrescription(),
                        record.getPatientID(),
                        record.getDoctorID()
                );
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
