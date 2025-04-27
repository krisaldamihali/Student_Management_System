import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class represents the action of deleting a student record from the database.
 * It extends the {@link AbstractButtonAction} class and implements the {@link #execute()} method.
 */
public class DeleteButtonAction extends AbstractButtonAction {
    private final int studentId;

    /**
     * Constructor to initialize the DeleteButtonAction with a student ID.
     * 
     * @param studentId The ID of the student to be deleted.
     */
    public DeleteButtonAction(int studentId) {
        super("DeleteAction"); // Pass action name to the superclass constructor
        this.studentId = studentId;
    }

    /**
     * Executes the delete action by removing the student record with the specified student ID from the database.
     * It shows a success message if the student is deleted, a warning message if the student does not exist,
     * and an error message if there is a failure during the process.
     */
    @Override
    public void execute() {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Student deleted successfully!");
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Student ID does not exist.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete student.");
        }
    }
}
