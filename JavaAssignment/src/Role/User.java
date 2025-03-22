package Role;

/**
 *
 * @author ASUS
 */
public class User {
    private String id;
    private String name;
    private String ic;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String address;
    private String contactNumber;
    private String emergencyContactNumber; // Optional
    private String password;

    // Constructor without emergency contact number
    public User(String id, String name, String ic, String email, String gender, String dateOfBirth, String address,
                String contactNumber, String password) {
        this.id = id;
        this.name = name;
        this.ic = ic;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.contactNumber = contactNumber;
        this.password = password;
    }

    // Full user constructor
    public User(String id, String name, String ic, String email, String gender, String dateOfBirth, String address,
                String contactNumber, String emergencyContactNumber, String password) {
        this(id, name, ic, email, gender, dateOfBirth, address, contactNumber, password);
        this.emergencyContactNumber = emergencyContactNumber;
    }

    // Getters and setters for all attributes
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}