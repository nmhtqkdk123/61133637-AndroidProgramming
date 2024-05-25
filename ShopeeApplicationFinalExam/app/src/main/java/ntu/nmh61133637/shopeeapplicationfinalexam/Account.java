package ntu.nmh61133637.shopeeapplicationfinalexam;

public class Account {
    int id;
    String name, studentID, className, email, phoneNumber;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Account(int id, String name, String studentID, String className, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.studentID = studentID;
        this.className = className;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Account() {
    }
}
