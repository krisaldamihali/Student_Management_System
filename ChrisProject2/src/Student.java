import javafx.beans.property.*;

/**
 * The Student class represents a student entity with properties such as ID, name, date of birth,
 * gender, study year, department, enrollment date, email, phone number, and status. 
 * This class uses JavaFX properties to bind and manage data efficiently.
 */
public class Student {
    
    // Integer property to store the student's ID
    private final IntegerProperty studentId;
    
    // String properties to store the student's first name, last name, date of birth, gender, department, enrollment date, email, phone number, and status
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty dob;
    private final StringProperty gender;
    private final IntegerProperty studyYear;
    private final StringProperty department;
    private final StringProperty enrollmentDate;
    private final StringProperty email;
    private final StringProperty phoneNumber;
    private final StringProperty status;

    /**
     * Constructor to initialize a new Student object with specified details.
     * 
     * @param studentId - The ID of the student.
     * @param firstName - The first name of the student.
     * @param lastName - The last name of the student.
     * @param dob - The date of birth of the student.
     * @param gender - The gender of the student.
     * @param studyYear - The year of study of the student.
     * @param department - The department the student belongs to.
     * @param enrollmentDate - The enrollment date of the student.
     * @param email - The email address of the student.
     * @param phoneNumber - The phone number of the student.
     * @param status - The current status of the student (e.g., enrolled, graduated, etc.).
     */
    public Student(int studentId, String firstName, String lastName, String dob, String gender, int studyYear,
                   String department, String enrollmentDate, String email, String phoneNumber, String status) {
        this.studentId = new SimpleIntegerProperty(studentId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dob = new SimpleStringProperty(dob);
        this.gender = new SimpleStringProperty(gender);
        this.studyYear = new SimpleIntegerProperty(studyYear);
        this.department = new SimpleStringProperty(department);
        this.enrollmentDate = new SimpleStringProperty(enrollmentDate);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.status = new SimpleStringProperty(status);
    }

    /**
     * Returns the student ID.
     * 
     * @return int - The student ID.
     */
    public int getStudentId() {
        return studentId.get();
    }

    /**
     * Returns the first name of the student.
     * 
     * @return String - The first name.
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Returns the last name of the student.
     * 
     * @return String - The last name.
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Returns the date of birth of the student.
     * 
     * @return String - The date of birth.
     */
    public String getDob() {
        return dob.get();
    }

    /**
     * Returns the gender of the student.
     * 
     * @return String - The gender.
     */
    public String getGender() {
        return gender.get();
    }

    /**
     * Returns the study year of the student.
     * 
     * @return int - The study year.
     */
    public int getStudyYear() {
        return studyYear.get();
    }

    /**
     * Returns the department the student belongs to.
     * 
     * @return String - The department.
     */
    public String getDepartment() {
        return department.get();
    }

    /**
     * Returns the enrollment date of the student.
     * 
     * @return String - The enrollment date.
     */
    public String getEnrollmentDate() {
        return enrollmentDate.get();
    }

    /**
     * Returns the email address of the student.
     * 
     * @return String - The email.
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * Returns the phone number of the student.
     * 
     * @return String - The phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /**
     * Returns the current status of the student.
     * 
     * @return String - The status.
     */
    public String getStatus() {
        return status.get();
    }

    /**
     * Returns the IntegerProperty for student ID, used for property binding.
     * 
     * @return IntegerProperty - The property for student ID.
     */
    public IntegerProperty studentIdProperty() {
        return studentId;
    }

    /**
     * Returns the StringProperty for the first name, used for property binding.
     * 
     * @return StringProperty - The property for first name.
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Returns the StringProperty for the last name, used for property binding.
     * 
     * @return StringProperty - The property for last name.
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Returns the StringProperty for the date of birth, used for property binding.
     * 
     * @return StringProperty - The property for date of birth.
     */
    public StringProperty dobProperty() {
        return dob;
    }

    /**
     * Returns the StringProperty for gender, used for property binding.
     * 
     * @return StringProperty - The property for gender.
     */
    public StringProperty genderProperty() {
        return gender;
    }

    /**
     * Returns the IntegerProperty for study year, used for property binding.
     * 
     * @return IntegerProperty - The property for study year.
     */
    public IntegerProperty studyYearProperty() {
        return studyYear;
    }

    /**
     * Returns the StringProperty for department, used for property binding.
     * 
     * @return StringProperty - The property for department.
     */
    public StringProperty departmentProperty() {
        return department;
    }

    /**
     * Returns the StringProperty for enrollment date, used for property binding.
     * 
     * @return StringProperty - The property for enrollment date.
     */
    public StringProperty enrollmentDateProperty() {
        return enrollmentDate;
    }

    /**
     * Returns the StringProperty for email, used for property binding.
     * 
     * @return StringProperty - The property for email.
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * Returns the StringProperty for phone number, used for property binding.
     * 
     * @return StringProperty - The property for phone number.
     */
    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    /**
     * Returns the StringProperty for status, used for property binding.
     * 
     * @return StringProperty - The property for status.
     */
    public StringProperty statusProperty() {
        return status;
    }
}
