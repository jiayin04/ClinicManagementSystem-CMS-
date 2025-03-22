package MedicalRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author guppy
 */
//medi record reader
//medicalRecordID |  bloodType | weight | height | diagnose | prescription | patientID | doctorID
public class MediRecFileReader {
    public static List<mediRecordClass> readMediRecFromFile(String filePath) throws IOException {
        List<mediRecordClass> MediRec = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/MedicalRecord.txt")) //admin file path
            ) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split("\\|");
                    if (fields.length == 9) {
                        String mediRecID = fields[0].trim();
                        String date = fields[1].trim();
                        String bloodType = fields[2].trim();
                        String weight = fields[3].trim();
                        String height = fields[4].trim();
                        String diagnose = fields[5].trim();
                        String prescription = fields[6].trim();
                        String patientID = fields[7].trim();
                        String doctorID = fields[8].trim();
                        MediRec.add(new mediRecordClass(mediRecID, date, bloodType, weight, height, diagnose, prescription, patientID, doctorID));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
        return MediRec;
    }

}
