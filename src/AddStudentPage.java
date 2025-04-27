import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;

/**
 * Represents the "Add Student" page in a student management system.
 * This page allows the user to view, add, update, and delete student records.
 */
public class AddStudentPage extends BasePage {
    private TableView<Student> studentTable;
    private ObservableList<Student> studentData;

    /**
     * Displays the "Add Student" page.
     *
     * @param app The main application instance.
     */
    @Override
    public void showPage(StudentManagementSystem app) {
        Stage primaryStage = app.getPrimaryStage();
        VBox sidebar = createSidebar(app, "addStudents");
        VBox rightSidebar = createRightSidebar();
        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);
        layout.setCenter(rightSidebar);
        Scene scene = new Scene(layout, 900, 600);
        primaryStage.setTitle("Add Students");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates the right sidebar containing the form and table for managing students.
     *
     * @return The right sidebar layout.
     */
    private VBox createRightSidebar() {
        VBox rightSidebar = new VBox(10);
        rightSidebar.setStyle("-fx-padding: 20px; -fx-background-color: #f0f5f4;");

        setupTable();
        ScrollPane scrollPane = new ScrollPane(studentTable);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setPrefHeight(400);

        GridPane formLayout = new GridPane();
        formLayout.setHgap(20);
        formLayout.setVgap(10);
        formLayout.setPrefWidth(900);

        TextField studentIdField = new TextField();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        DatePicker dobPicker = new DatePicker();
        ComboBox<String> genderField = new ComboBox<>(FXCollections.observableArrayList("male", "female"));
        TextField studyYearField = new TextField();
        TextField departmentField = new TextField();
        DatePicker enrollmentDatePicker = new DatePicker();
        TextField emailField = new TextField();
        TextField phoneNumberField = new TextField();
        ComboBox<String> statusField = new ComboBox<>(FXCollections.observableArrayList("active", "inactive"));

        addFormFields(formLayout, studentIdField, firstNameField, lastNameField, dobPicker,
                genderField, studyYearField, departmentField, enrollmentDatePicker,
                emailField, phoneNumberField, statusField);

        Button addButton = createStyledButton("Add", "#4CAF50");
        Button clearButton = createStyledButton("Clear", "#ff9800");
        Button deleteButton = createStyledButton("Delete", "#f44336");
        Button updateButton = createStyledButton("Update", "#2196F3");

        setupButtonActions(addButton, clearButton, deleteButton, updateButton,
                studentIdField, firstNameField, lastNameField, dobPicker,
                genderField, studyYearField, departmentField, enrollmentDatePicker,
                emailField, phoneNumberField, statusField);

        HBox buttonBox = new HBox(10, addButton, clearButton, deleteButton, updateButton);

        VBox finalLayout = new VBox(20);
        finalLayout.getChildren().addAll(scrollPane, formLayout, buttonBox);
        rightSidebar.getChildren().add(finalLayout);

        return rightSidebar;
    }

    /**
     * Configures the student table and populates it with data from the database.
     */
    private void setupTable() {
        studentTable = new TableView<>();
        studentTable.setPrefWidth(1100);
        studentTable.setPrefHeight(500);
        addTableColumns();
        studentData = fetchStudentsFromDatabase();
        studentTable.setItems(studentData);
    }

    /**
     * Adds columns to the student table for displaying student information.
     */
    private void addTableColumns() {
        TableColumn<Student, Integer> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(cellData -> cellData.getValue().studentIdProperty().asObject());
        studentIdCol.setPrefWidth(80);

        TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        firstNameCol.setPrefWidth(120);

        TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        lastNameCol.setPrefWidth(120);

        TableColumn<Student, String> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setCellValueFactory(cellData -> cellData.getValue().dobProperty());
        dobCol.setPrefWidth(120);

        TableColumn<Student, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(cellData -> cellData.getValue().genderProperty());
        genderCol.setPrefWidth(100);

        TableColumn<Student, Integer> studyYearCol = new TableColumn<>("Study Year");
        studyYearCol.setCellValueFactory(cellData -> cellData.getValue().studyYearProperty().asObject());
        studyYearCol.setPrefWidth(100);

        TableColumn<Student, String> departmentCol = new TableColumn<>("Department");
        departmentCol.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        departmentCol.setPrefWidth(150);

        TableColumn<Student, String> enrollmentDateCol = new TableColumn<>("Enrollment Date");
        enrollmentDateCol.setCellValueFactory(cellData -> cellData.getValue().enrollmentDateProperty());
        enrollmentDateCol.setPrefWidth(150);

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailCol.setPrefWidth(180);

        TableColumn<Student, String> phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        phoneNumberCol.setPrefWidth(120);

        TableColumn<Student, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusCol.setPrefWidth(100);

        studentTable.getColumns().addAll(
                studentIdCol, firstNameCol, lastNameCol, dobCol, genderCol,
                studyYearCol, departmentCol, enrollmentDateCol, emailCol,
                phoneNumberCol, statusCol
        );
    }

    /**
     * Adds labeled fields to the form layout for student data entry.
     *
     * @param formLayout           The {@link GridPane} to which the fields are added.
     * @param studentIdField       The {@link TextField} for entering the student's ID.
     * @param firstNameField       The {@link TextField} for entering the student's first name.
     * @param lastNameField        The {@link TextField} for entering the student's last name.
     * @param dobPicker            The {@link DatePicker} for selecting the student's date of birth.
     * @param genderField          The {@link ComboBox} for selecting the student's gender.
     * @param studyYearField       The {@link TextField} for entering the student's study year.
     * @param departmentField      The {@link TextField} for entering the student's department.
     * @param enrollmentDatePicker The {@link DatePicker} for selecting the student's enrollment date.
     * @param emailField           The {@link TextField} for entering the student's email.
     * @param phoneNumberField     The {@link TextField} for entering the student's phone number.
     * @param statusField          The {@link ComboBox} for selecting the student's status.
     */
    private void addFormFields(GridPane formLayout, TextField studentIdField, TextField firstNameField,
                               TextField lastNameField, DatePicker dobPicker, ComboBox<String> genderField,
                               TextField studyYearField, TextField departmentField, DatePicker enrollmentDatePicker,
                               TextField emailField, TextField phoneNumberField, ComboBox<String> statusField) {
        formLayout.add(new Label("Student ID"), 0, 0);
        formLayout.add(studentIdField, 1, 0);
        formLayout.add(new Label("First Name"), 0, 1);
        formLayout.add(firstNameField, 1, 1);
        formLayout.add(new Label("Last Name"), 0, 2);
        formLayout.add(lastNameField, 1, 2);
        formLayout.add(new Label("Date of Birth"), 0, 3);
        formLayout.add(dobPicker, 1, 3);
        formLayout.add(new Label("Gender"), 0, 4);
        formLayout.add(genderField, 1, 4);
        formLayout.add(new Label("Study Year"), 2, 0);
        formLayout.add(studyYearField, 3, 0);
        formLayout.add(new Label("Department"), 2, 1);
        formLayout.add(departmentField, 3, 1);
        formLayout.add(new Label("Enrollment Date"), 2, 2);
        formLayout.add(enrollmentDatePicker, 3, 2);
        formLayout.add(new Label("Email"), 2, 3);
        formLayout.add(emailField, 3, 3);
        formLayout.add(new Label("Phone Number"), 2, 4);
        formLayout.add(phoneNumberField, 3, 4);
        formLayout.add(new Label("Status"), 2, 5);
        formLayout.add(statusField, 3, 5);
    }

    /**
     * Creates a styled button with the given text and color.
     *
     * @param text  The text to display on the button.
     * @param color The background color of the button in CSS format.
     * @return A {@link Button} with the specified style and text.
     */
    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
        return button;
    }

    /**
     * Configures the actions for Add, Clear, Delete, and Update buttons.
     *
     * @param addButton       The Add button.
     * @param clearButton     The Clear button.
     * @param deleteButton    The Delete button.
     * @param updateButton    The Update button.
     * @param studentIdField  The {@link TextField} for the student's ID.
     * @param firstNameField  The {@link TextField} for the student's first name.
     * @param lastNameField   The {@link TextField} for the student's last name.
     * @param dobPicker       The {@link DatePicker} for the student's date of birth.
     * @param genderField     The {@link ComboBox} for the student's gender.
     * @param studyYearField  The {@link TextField} for the student's study year.
     * @param departmentField The {@link TextField} for the student's department.
     * @param enrollmentDatePicker The {@link DatePicker} for the student's enrollment date.
     * @param emailField      The {@link TextField} for the student's email.
     * @param phoneNumberField The {@link TextField} for the student's phone number.
     * @param statusField     The {@link ComboBox} for the student's status.
     */
    private void setupButtonActions(Button addButton, Button clearButton, Button deleteButton, Button updateButton,
                                    TextField studentIdField, TextField firstNameField, TextField lastNameField,
                                    DatePicker dobPicker, ComboBox<String> genderField, TextField studyYearField,
                                    TextField departmentField, DatePicker enrollmentDatePicker, TextField emailField,
                                    TextField phoneNumberField, ComboBox<String> statusField) {
        // Add button action
        addButton.setOnAction(e -> {
            if (validateFields(studentIdField, firstNameField, lastNameField, dobPicker,
                    genderField, studyYearField, departmentField, enrollmentDatePicker,
                    emailField, phoneNumberField, statusField)) {
                Student student = new Student(
                        Integer.parseInt(studentIdField.getText()),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        dobPicker.getValue().toString(),
                        genderField.getValue(),
                        Integer.parseInt(studyYearField.getText()),
                        departmentField.getText(),
                        enrollmentDatePicker.getValue().toString(),
                        emailField.getText(),
                        phoneNumberField.getText(),
                        statusField.getValue()
                );
                new AddButtonAction(student).execute();
                refreshTable();
            }
        });

        // Clear button action
        clearButton.setOnAction(e -> {
            new ClearButtonAction(new TextField[]{studentIdField, firstNameField, lastNameField, studyYearField,
                    departmentField, emailField, phoneNumberField}, new DatePicker[]{dobPicker, enrollmentDatePicker},
                    genderField, statusField).execute();
        });

        // Delete button action
        deleteButton.setOnAction(e -> {
            if (!studentIdField.getText().isEmpty()) {
                new DeleteButtonAction(Integer.parseInt(studentIdField.getText())).execute();
                refreshTable();
            }
        });

        // Update button action
        updateButton.setOnAction(e -> {
            if (validateFields(studentIdField, firstNameField, lastNameField, dobPicker,
                    genderField, studyYearField, departmentField, enrollmentDatePicker,
                    emailField, phoneNumberField, statusField)) {
                Student student = new Student(
                        Integer.parseInt(studentIdField.getText()),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        dobPicker.getValue().toString(),
                        genderField.getValue(),
                        Integer.parseInt(studyYearField.getText()),
                        departmentField.getText(),
                        enrollmentDatePicker.getValue().toString(),
                        emailField.getText(),
                        phoneNumberField.getText(),
                        statusField.getValue()
                );
                new UpdateButtonAction(student).execute();
                refreshTable();
            }
        });
    }
    /**
     * Validates the input fields to ensure all necessary fields contain valid data.
     *
     * @param studentIdField       The {@link TextField} containing the student ID.
     * @param firstNameField       The {@link TextField} containing the first name.
     * @param lastNameField        The {@link TextField} containing the last name.
     * @param dobPicker            The {@link DatePicker} for selecting the date of birth.
     * @param genderField          The {@link ComboBox} containing the gender options.
     * @param studyYearField       The {@link TextField} containing the study year.
     * @param departmentField      The {@link TextField} containing the department name.
     * @param enrollmentDatePicker The {@link DatePicker} for selecting the enrollment date.
     * @param emailField           The {@link TextField} containing the email address.
     * @param phoneNumberField     The {@link TextField} containing the phone number.
     * @param statusField          The {@link ComboBox} containing the status options.
     * @return {@code true} if all fields are valid; {@code false} otherwise.
     */
    
    private boolean validateFields(TextField studentIdField, TextField firstNameField, TextField lastNameField, DatePicker dobPicker, ComboBox<String> genderField, TextField studyYearField, TextField departmentField, DatePicker enrollmentDatePicker, TextField emailField, TextField phoneNumberField, ComboBox<String> statusField) {
        if (studentIdField.getText().isEmpty() || !studentIdField.getText().matches("\\d+")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Student ID must be a valid number.");
            return false;
        }
        if (firstNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "First Name cannot be empty.");
            return false;
        }
        if (lastNameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Last Name cannot be empty.");
            return false;
        }
        if (dobPicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Date of Birth must be selected.");
            return false;
        }
        if (genderField.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Gender must be selected.");
            return false;
        }
        if (studyYearField.getText().isEmpty() || !studyYearField.getText().matches("\\d+")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Study Year must be a valid number.");
            return false;
        }
        if (departmentField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Department cannot be empty.");
            return false;
        }
        if (enrollmentDatePicker.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Enrollment Date must be selected.");
            return false;
        }
        if (emailField.getText().isEmpty() || !emailField.getText().matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Email must be a valid email address.");
            return false;
        }
        if (phoneNumberField.getText().isEmpty() || !phoneNumberField.getText().matches("\\d{10,}")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Phone Number must be a valid number.");
            return false;
        }
        if (statusField.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Status must be selected.");
            return false;
        }
        return true;
    }
    
    /**
     * Refreshes the data in the student table by fetching the latest data from the database.
     */
    private void refreshTable() {
        studentData = fetchStudentsFromDatabase();
        studentTable.setItems(studentData);
    }

    /**
     * Fetches the list of students from the database.
     *
     * @return An {@link ObservableList} containing {@link Student} objects.
     */
    
    private ObservableList<Student> fetchStudentsFromDatabase() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM students";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management_system", "root", "root");
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String dob = resultSet.getString("date_of_birth");
                String gender = resultSet.getString("gender");
                int studyYear = resultSet.getInt("study_year");
                String department = resultSet.getString("department");
                String enrollmentDate = resultSet.getString("enrollment_date");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String status = resultSet.getString("status");

                studentList.add(new Student(studentId, firstName, lastName, dob, gender, studyYear, department, enrollmentDate, email, phoneNumber, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }
    
    /**
     * Displays an alert dialog with the specified type, title, and message.
     *
     * @param alertType The {@link AlertType} for the alert.
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
}
