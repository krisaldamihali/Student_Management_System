import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents an action for adding a student to the database.
 * Extends {@code AbstractButtonAction} and provides the logic for inserting
 * student details into the database.
 */
public class AddButtonAction extends AbstractButtonAction {

    /**
     * The student to be added.
     */
    private final Student student;

    /**
     * Constructs an {@code AddButtonAction} with the specified student.
     *
     * @param student the {@code Student} object containing the details of the student to be added
     */
    public AddButtonAction(Student student) {
        super("AddAction");
        this.student = student;
    }

    /**
     * Executes the action to add a student to the database.
     * Inserts the student data into the "students" table and shows an alert
     * indicating success or failure.
     */
    @Override
    public void execute() {
        String sql = "INSERT INTO students (student_id, first_name, last_name, date_of_birth, gender, study_year, " +
                     "department, enrollment_date, email, phone_number, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the student details in the prepared statement
            statement.setInt(1, student.getStudentId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setString(4, student.getDob());
            statement.setString(5, student.getGender());
            statement.setInt(6, student.getStudyYear());
            statement.setString(7, student.getDepartment());
            statement.setString(8, student.getEnrollmentDate());
            statement.setString(9, student.getEmail());
            statement.setString(10, student.getPhoneNumber());
            statement.setString(11, student.getStatus());

            // Execute the SQL statement
            statement.executeUpdate();

            // Show success alert
            showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Show error alert
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add student.");
        }
    }
}
