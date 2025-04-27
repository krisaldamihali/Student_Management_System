import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The `UpdateButtonAction` class represents an action to update student information in the database.
 * It extends the `AbstractButtonAction` class and provides the specific SQL query to update student details.
 * This class handles database interaction, executes the update query, and displays appropriate alert messages.
 */
public class UpdateButtonAction extends AbstractButtonAction {

    // The student object containing the updated information.
    private final Student student;

    /**
     * Constructor that initializes the action with the student to be updated.
     * 
     * @param student - The student object containing updated information.
     */
    public UpdateButtonAction(Student student) {
        super("UpdateAction");
        this.student = student;
    }

    /**
     * Executes the action to update the student's information in the database.
     * 
     * The method prepares a SQL `UPDATE` query using the `student` object values and executes it.
     * After executing the query, it shows an appropriate alert message based on whether the update was successful.
     */
    @Override
    public void execute() {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, " +
                     "study_year = ?, department = ?, enrollment_date = ?, email = ?, phone_number = ?, " +
                     "status = ? WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Setting the values from the `student` object to the query's placeholders.
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getDob());
            statement.setString(4, student.getGender());
            statement.setInt(5, student.getStudyYear());
            statement.setString(6, student.getDepartment());
            statement.setString(7, student.getEnrollmentDate());
            statement.setString(8, student.getEmail());
            statement.setString(9, student.getPhoneNumber());
            statement.setString(10, student.getStatus());
            statement.setInt(11, student.getStudentId());

            // Executing the query and checking the number of rows affected.
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student updated successfully!");
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Student ID not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update student.");
        }
    }
}