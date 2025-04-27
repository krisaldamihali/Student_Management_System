import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.*;

/**
 * Represents the page where available courses are displayed in the student management system.
 * It allows the user to add, update, delete, and view available courses.
 */
public class AvailableCoursesPage extends BasePage {

    /**
     * Displays the available courses page with its sidebar and content.
     * @param app The main application instance.
     */
    @Override
    public void showPage(StudentManagementSystem app) {
        Stage primaryStage = app.getPrimaryStage();

        // Create the shared sidebar
        VBox sidebar = createSidebar(app, "availableCourses");

        // Create the right content section for available courses
        VBox rightSidebar = createRightSidebar();

        // Combine sidebar and content into the main layout
        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);
        layout.setCenter(rightSidebar);

        // Set up and display the scene
        Scene scene = new Scene(layout, 900, 600);
        primaryStage.setTitle("Available Courses");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the right sidebar which contains the course table and course input form.
     * @return The VBox containing the right sidebar layout.
     */
    private VBox createRightSidebar() {
        VBox rightSidebar = new VBox(10);
        rightSidebar.setStyle("-fx-padding: 20px; -fx-background-color: #f0f5f4;");

        // Table for displaying courses
        TableView<Course> courseTable = new TableView<>();
        courseTable.setPrefWidth(800);
        courseTable.setPrefHeight(400);

        // Columns for the table
        TableColumn<Course, String> courseIdCol = new TableColumn<>("Course ID");
        courseIdCol.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty());
        courseIdCol.setPrefWidth(150);

        TableColumn<Course, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
        courseNameCol.setPrefWidth(300);

        TableColumn<Course, String> departmentCol = new TableColumn<>("Department");
        departmentCol.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        departmentCol.setPrefWidth(150);

        TableColumn<Course, String> instructorCol = new TableColumn<>("Instructor");
        instructorCol.setCellValueFactory(cellData -> cellData.getValue().instructorProperty());
        instructorCol.setPrefWidth(200);

        courseTable.getColumns().addAll(courseIdCol, courseNameCol, departmentCol, instructorCol);

        // Fetch courses from the database and populate the table
        ObservableList<Course> courseData = fetchCoursesFromDatabase();
        courseTable.setItems(courseData);

        // ScrollPane for the course table
        ScrollPane scrollPane = new ScrollPane(courseTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(400);

        // Form layout for course input
        GridPane formLayout = new GridPane();
        formLayout.setHgap(20);
        formLayout.setVgap(10);

        // Input fields for course details
        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Course ID");
        formLayout.add(new Label("Course ID"), 0, 0);
        formLayout.add(courseIdField, 1, 0);

        TextField courseNameField = new TextField();
        courseNameField.setPromptText("Course Name");
        formLayout.add(new Label("Course Name"), 0, 1);
        formLayout.add(courseNameField, 1, 1);

        TextField departmentField = new TextField();
        departmentField.setPromptText("Department");
        formLayout.add(new Label("Department"), 0, 2);
        formLayout.add(departmentField, 1, 2);

        TextField instructorField = new TextField();
        instructorField.setPromptText("Instructor");
        formLayout.add(new Label("Instructor"), 0, 3);
        formLayout.add(instructorField, 1, 3);

        // Buttons for actions
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white;");
        
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        
        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        HBox buttonBox = new HBox(10, addButton, clearButton, deleteButton, updateButton);

        VBox finalLayout = new VBox(20, scrollPane, formLayout, buttonBox);
        rightSidebar.getChildren().add(finalLayout);

        // Button actions
        addButtonAction(addButton, courseIdField, courseNameField, departmentField, instructorField);
        clearButtonAction(clearButton, courseIdField, courseNameField, departmentField, instructorField);
        deleteButtonAction(deleteButton, courseIdField);
        updateButtonAction(updateButton, courseIdField, courseNameField, departmentField, instructorField);

        return rightSidebar;
    }

    /**
     * Set the action for the "Add" button.
     * @param addButton The button to attach the action to.
     * @param courseIdField The course ID input field.
     * @param courseNameField The course name input field.
     * @param departmentField The department input field.
     * @param instructorField The instructor input field.
     */
    public void addButtonAction(Button addButton, TextField courseIdField, TextField courseNameField, TextField departmentField, TextField instructorField) {
        addButton.setOnAction(event -> addCourse(courseIdField, courseNameField, departmentField, instructorField));
    }

    /**
     * Set the action for the "Clear" button.
     * @param clearButton The button to attach the action to.
     * @param courseIdField The course ID input field.
     * @param courseNameField The course name input field.
     * @param departmentField The department input field.
     * @param instructorField The instructor input field.
     */
    public void clearButtonAction(Button clearButton, TextField courseIdField, TextField courseNameField, TextField departmentField, TextField instructorField) {
        clearButton.setOnAction(event -> clearFields(courseIdField, courseNameField, departmentField, instructorField));
    }

    /**
     * Set the action for the "Delete" button.
     * @param deleteButton The button to attach the action to.
     * @param courseIdField The course ID input field.
     */
    public void deleteButtonAction(Button deleteButton, TextField courseIdField) {
        deleteButton.setOnAction(event -> deleteCourse(courseIdField));
    }

    /**
     * Set the action for the "Update" button.
     * @param updateButton The button to attach the action to.
     * @param courseIdField The course ID input field.
     * @param courseNameField The course name input field.
     * @param departmentField The department input field.
     * @param instructorField The instructor input field.
     */
    public void updateButtonAction(Button updateButton, TextField courseIdField, TextField courseNameField, TextField departmentField, TextField instructorField) {
        updateButton.setOnAction(event -> updateCourse(courseIdField, courseNameField, departmentField, instructorField));
    }

    /**
     * Adds a new course to the database.
     * @param courseIdField The course ID input field.
     * @param courseNameField The course name input field.
     * @param departmentField The department input field.
     * @param instructorField The instructor input field.
     */
    private void addCourse(TextField courseIdField, TextField courseNameField, TextField departmentField, TextField instructorField) {
        String courseId = courseIdField.getText();
        String courseName = courseNameField.getText();
        String department = departmentField.getText();
        String instructor = instructorField.getText();

        if (courseId.isEmpty() || courseName.isEmpty() || department.isEmpty() || instructor.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill all the fields.");
            return;
        }

        String sql = "INSERT INTO courses (course_id, course_name, department, instructor) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, courseId);
            preparedStatement.setString(2, courseName);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, instructor);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while adding the course.");
        }
    }

    /**
     * Clears all the input fields.
     * @param courseIdField The course ID input field.
     * @param courseNameField The course name input field.
     * @param departmentField The department input field.
     * @param instructorField The instructor input field.
     */
    private void clearFields(TextField courseIdField, TextField courseNameField, TextField departmentField, TextField instructorField) {
        courseIdField.clear();
        courseNameField.clear();
        departmentField.clear();
        instructorField.clear();
    }

    /**
     * Deletes a course based on the provided course ID.
     * @param courseIdField The course ID input field.
     */
    private void deleteCourse(TextField courseIdField) {
        String courseId = courseIdField.getText();
        if (courseId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter the Course ID.");
            return;
        }

        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Course not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while deleting the course.");
        }
    }

    /**
     * Updates the course details based on the provided inputs.
     * @param courseIdField The course ID input field.
     * @param courseNameField The course name input field.
     * @param departmentField The department input field.
     * @param instructorField The instructor input field.
     */
    private void updateCourse(TextField courseIdField, TextField courseNameField, TextField departmentField, TextField instructorField) {
        String courseId = courseIdField.getText();
        if (courseId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter the Course ID.");
            return;
        }

        String sql = "UPDATE courses SET ";
        List<String> parameters = new ArrayList<>();
        boolean hasUpdate = false;

        if (!courseNameField.getText().isEmpty()) {
            sql += "course_name = ?, ";
            parameters.add(courseNameField.getText());
            hasUpdate = true;
        }
        if (!departmentField.getText().isEmpty()) {
            sql += "department = ?, ";
            parameters.add(departmentField.getText());
            hasUpdate = true;
        }
        if (!instructorField.getText().isEmpty()) {
            sql += "instructor = ? ";
            parameters.add(instructorField.getText());
            hasUpdate = true;
        }

        if (!hasUpdate) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter at least one field to update.");
            return;
        }

        sql += "WHERE course_id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i));
            }

            preparedStatement.setString(parameters.size() + 1, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Course not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the course.");
        }
    }

    
    /**
     * Fetches the list of available courses from the database.
     * @return A list of courses.
     */
    private ObservableList<Course> fetchCoursesFromDatabase() {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        String sql = "SELECT * FROM courses";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Course course = new Course(resultSet.getString("course_id"), resultSet.getString("course_name"),
                        resultSet.getString("department"), resultSet.getString("instructor"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /**
     * Displays an alert with the provided message and alert type.
     * @param type The alert type (information, error, etc.).
     * @param title The title of the alert.
     * @param message The message to display in the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
