import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;

/**
 * The GradesPage class represents a user interface page in the Student Management System for handling student grades.
 * It extends the BasePage class and provides functionality to display, add, update, delete, and clear student grades.
 */
public class GradesPage extends BasePage {

    /**
     * Displays the GradesPage using the provided StudentManagementSystem application.
     *
     * @param app The instance of the StudentManagementSystem application.
     */
    @Override
    public void showPage(StudentManagementSystem app) {
        Stage primaryStage = app.getPrimaryStage();

        VBox sidebar = createSidebar(app, "grades"); // Sidebar for navigation
        VBox rightSidebar = createRightSidebar();  // Sidebar with grade data and controls

        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);
        layout.setCenter(rightSidebar);

        Scene scene = new Scene(layout, 900, 600);
        primaryStage.setTitle("Student Grades");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the right sidebar that contains the grade table and form to interact with grade data.
     *
     * @return A VBox containing the grade table and form controls.
     */
    private VBox createRightSidebar() {
        VBox rightSidebar = new VBox(10);  // Container with vertical spacing
        rightSidebar.setStyle("-fx-padding: 20px; -fx-background-color: #f0f5f4;");

        TableView<Grade> gradeTable = new TableView<>();
        gradeTable.setPrefWidth(800);

        // Table Columns for Grade Data
        TableColumn<Grade, String> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty());
        studentIdCol.setPrefWidth(150);

        TableColumn<Grade, String> courseIdCol = new TableColumn<>("Course ID");
        courseIdCol.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty());
        courseIdCol.setPrefWidth(150);

        TableColumn<Grade, Double> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(cellData -> cellData.getValue().gradeProperty().asObject());
        gradeCol.setPrefWidth(150);

        TableColumn<Grade, String> semesterCol = new TableColumn<>("Semester");
        semesterCol.setCellValueFactory(cellData -> cellData.getValue().semesterProperty());
        semesterCol.setPrefWidth(150);

        TableColumn<Grade, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());
        yearCol.setPrefWidth(150);

        gradeTable.getColumns().addAll(studentIdCol, courseIdCol, gradeCol, semesterCol, yearCol);

        // Fetching grade data from database
        ObservableList<Grade> gradeData = fetchGradesFromDatabase();
        gradeTable.setItems(gradeData);

        // Scrollable Table
        ScrollPane scrollPane = new ScrollPane(gradeTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(400);

        // Form Layout for Grade Addition/Modification
        GridPane formLayout = createGradeForm();
        rightSidebar.getChildren().addAll(scrollPane, formLayout, createButtonBox(formLayout));

        return rightSidebar;
    }

    /**
     * Creates the form layout for adding or updating grades.
     *
     * @return A GridPane containing input fields for grade data.
     */
    private GridPane createGradeForm() {
        GridPane formLayout = new GridPane();
        formLayout.setHgap(20);  // Horizontal gap between nodes
        formLayout.setVgap(10);  // Vertical gap between nodes

        // Form Fields
        TextField studentIdField = new TextField();
        studentIdField.setId("studentIdField");
        studentIdField.setPromptText("Student ID");
        formLayout.add(new Label("Student ID"), 0, 0);
        formLayout.add(studentIdField, 1, 0);

        TextField courseIdField = new TextField();
        courseIdField.setId("courseIdField");
        courseIdField.setPromptText("Course ID");
        formLayout.add(new Label("Course ID"), 0, 1);
        formLayout.add(courseIdField, 1, 1);

        TextField gradeField = new TextField();
        gradeField.setId("gradeField");
        gradeField.setPromptText("Grade");
        formLayout.add(new Label("Grade"), 0, 2);
        formLayout.add(gradeField, 1, 2);

        TextField semesterField = new TextField();
        semesterField.setId("semesterField");
        semesterField.setPromptText("Semester");
        formLayout.add(new Label("Semester"), 0, 3);
        formLayout.add(semesterField, 1, 3);

        TextField yearField = new TextField();
        yearField.setId("yearField");
        yearField.setPromptText("Year");
        formLayout.add(new Label("Year"), 0, 4);
        formLayout.add(yearField, 1, 4);

        return formLayout;
    }

    /**
     * Creates the button box containing controls for adding, clearing, deleting, and updating grades.
     *
     * @param formLayout The GridPane form layout used to fetch input fields.
     * @return An HBox containing buttons for grade operations.
     */
    private HBox createButtonBox(GridPane formLayout) {
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white;");
        addButton.setOnAction(event -> handleAddButton(formLayout));

        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #9e9e9e; -fx-text-fill: white;");
        clearButton.setOnAction(event -> handleClearButton(formLayout));

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> handleDeleteButton(formLayout));

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white;");
        updateButton.setOnAction(event -> handleUpdateButton(formLayout));

        return new HBox(10, addButton, clearButton, deleteButton, updateButton);
    }

    /**
     * Handles the action for adding a new grade to the database.
     *
     * @param formLayout The GridPane form layout containing the input fields.
     */
    private void handleAddButton(GridPane formLayout) {
        TextField studentIdField = (TextField) formLayout.lookup("#studentIdField");
        TextField courseIdField = (TextField) formLayout.lookup("#courseIdField");
        TextField gradeField = (TextField) formLayout.lookup("#gradeField");
        TextField semesterField = (TextField) formLayout.lookup("#semesterField");
        TextField yearField = (TextField) formLayout.lookup("#yearField");

        String studentId = studentIdField.getText();
        String courseId = courseIdField.getText();
        String gradeText = gradeField.getText();
        String semester = semesterField.getText();
        String yearText = yearField.getText();

        if (studentId.isEmpty() || courseId.isEmpty() || gradeText.isEmpty() || semester.isEmpty() || yearText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled in.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);
            int year = Integer.parseInt(yearText);

            String sql = "INSERT INTO grades (student_id, course_id, grade, semester, year) VALUES (?, ?, ?, ?, ?)";
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, studentId);
                preparedStatement.setString(2, courseId);
                preparedStatement.setDouble(3, grade);
                preparedStatement.setString(4, semester);
                preparedStatement.setInt(5, year);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Grade added successfully!");
                    handleClearButton(formLayout);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add grade.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while adding the grade.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Grade and Year must be numeric values.");
        }
    }

    /**
     * Handles the action to clear all input fields in the grade form.
     *
     * @param formLayout The GridPane form layout.
     */
    private void handleClearButton(GridPane formLayout) {
        for (javafx.scene.Node node : formLayout.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
        }
    }

    /**
     * Handles the action for deleting a grade record based on Student ID and Course ID.
     *
     * @param formLayout The GridPane form layout containing the input fields.
     */
    private void handleDeleteButton(GridPane formLayout) {
        TextField studentIdField = (TextField) formLayout.lookup("#studentIdField");
        TextField courseIdField = (TextField) formLayout.lookup("#courseIdField");

        String studentId = studentIdField.getText();
        String courseId = courseIdField.getText();

        if (studentId.isEmpty() || courseId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Student ID and Course ID are required.");
            return;
        }

        String sql = "DELETE FROM grades WHERE student_id = ? AND course_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Grade deleted successfully!");
                handleClearButton(formLayout);
            } else {
                showAlert(Alert.AlertType.ERROR, "Not Found", "No record found with the provided Student ID and Course ID.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while deleting the grade.");
        }
    }

    /**
     * Handles the action for updating an existing grade record.
     *
     * @param formLayout The GridPane form layout containing the input fields.
     */
    private void handleUpdateButton(GridPane formLayout) {
        TextField studentIdField = (TextField) formLayout.lookup("#studentIdField");
        TextField courseIdField = (TextField) formLayout.lookup("#courseIdField");
        TextField gradeField = (TextField) formLayout.lookup("#gradeField");
        TextField semesterField = (TextField) formLayout.lookup("#semesterField");
        TextField yearField = (TextField) formLayout.lookup("#yearField");

        String studentId = studentIdField.getText();
        String courseId = courseIdField.getText();
        String gradeText = gradeField.getText();
        String semester = semesterField.getText();
        String yearText = yearField.getText();

        if (studentId.isEmpty() || courseId.isEmpty() || gradeText.isEmpty() || semester.isEmpty() || yearText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields must be filled in.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);
            int year = Integer.parseInt(yearText);

            String sql = "UPDATE grades SET grade = ?, semester = ?, year = ? WHERE student_id = ? AND course_id = ?";
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setDouble(1, grade);
                preparedStatement.setString(2, semester);
                preparedStatement.setInt(3, year);
                preparedStatement.setString(4, studentId);
                preparedStatement.setString(5, courseId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Grade updated successfully!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Not Found", "No record found to update.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the grade.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Grade and Year must be numeric values.");
        }
    }

    /**
     * Displays an alert dialog to the user.
     *
     * @param alertType The type of alert (e.g., ERROR, INFORMATION, etc.).
     * @param title     The title of the alert.
     * @param message   The message to display in the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Fetches grade data from the database and returns it as an ObservableList.
     *
     * @return An ObservableList containing all grades from the database.
     */
    private ObservableList<Grade> fetchGradesFromDatabase() {
        ObservableList<Grade> gradeList = FXCollections.observableArrayList();
        String sql = "SELECT student_id, course_id, grade, semester, year FROM grades";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String courseId = resultSet.getString("course_id");
                double grade = resultSet.getDouble("grade");
                String semester = resultSet.getString("semester");
                int year = resultSet.getInt("year");

                gradeList.add(new Grade(studentId, courseId, grade, semester, year));
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while fetching grades.");
        }

        return gradeList;
    }
}
