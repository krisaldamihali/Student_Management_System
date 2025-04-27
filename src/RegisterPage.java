import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the registration page for the Student Management System.
 * Allows users to register by providing a username, email, and password.
 */
public class RegisterPage {

    private final StudentManagementSystem mainApp;
    private final Stage stage;

    /**
     * Constructor for creating the RegisterPage.
     *
     * @param app the main application instance to handle navigation and data.
     */
    public RegisterPage(StudentManagementSystem app) {
        this.mainApp = app;
        this.stage = new Stage();
    }

    /**
     * Creates the registration form layout.
     *
     * @return a Scene containing the registration form.
     */
    public Scene createRegisterForm() {
        // Left pane containing the logo and application title.
        VBox leftPane = new VBox(20);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(20));
        leftPane.setStyle("-fx-background-color: linear-gradient(to bottom, #1db1a4, #0f8ba5);");

        Label logo = new Label("🎓");
        logo.setFont(new Font(180));
        logo.setTextFill(Color.WHITE);

        Label appTitle = new Label("Student Management System");
        appTitle.setTextFill(Color.WHITE);
        appTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        leftPane.getChildren().addAll(logo, appTitle);

        // Right pane containing the registration form fields and buttons.
        VBox rightPane = new VBox(15);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(30));
        rightPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        rightPane.setPrefWidth(400);

        Label registerLabel = new Label("Register");
        registerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        registerLabel.setTextFill(Color.BLACK);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(300);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-font-weight: bold;");
        registerButton.setPrefWidth(200);

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert(AlertType.ERROR, "Registration Failed", null, "All fields are required!");
                clearFields(usernameField, emailField, passwordField);
                return;
            }

            if (DatabaseConnection.isUsernameTaken(username)) {
                showAlert(AlertType.ERROR, "Username Taken", null, "The username is already taken. Please choose another one.");
                clearFields(usernameField, emailField, passwordField);
                return;
            }

            if (DatabaseConnection.isEmailTaken(email)) {
                showAlert(AlertType.ERROR, "Email Taken", null, "The email is already taken. Please choose another one.");
                clearFields(usernameField, emailField, passwordField);
                return;
            }

            if (!isValidEmail(email)) {
                showAlert(AlertType.ERROR, "Invalid Email", null, "Please enter a valid email address.");
                clearFields(usernameField, emailField, passwordField);
                return;
            }

            if (!isValidPassword(password)) {
                showAlert(AlertType.ERROR, "Invalid Password", null, "Password must be at least 8 characters long and contain a mix of uppercase, lowercase, numbers, and special characters.");
                clearFields(usernameField, emailField, passwordField);
                return;
            }

            if (DatabaseConnection.registerUser(username, email, password)) {
                showAlert(AlertType.INFORMATION, "Registration Successful", null, "You registered successfully!");
                clearFields(usernameField, emailField, passwordField);
                mainApp.showLoginPage();
            } else {
                showAlert(AlertType.ERROR, "Registration Failed", null, "There was an error while registering. Please try again.");
            }
        });

        HBox registerBox = new HBox(5);
        registerBox.setAlignment(Pos.CENTER);
        Label noAccountLabel = new Label("Already have an account?");
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #0056b3; -fx-font-weight: bold; -fx-underline: true;");

        loginButton.setOnAction(e -> mainApp.showLoginPage());

        registerBox.getChildren().addAll(noAccountLabel, loginButton);

        Label userIcon = new Label("👤");
        userIcon.setFont(new Font(80));

        rightPane.getChildren().addAll(registerLabel, userIcon, usernameField, emailField, passwordField, registerButton, registerBox);

        HBox root = new HBox();
        root.getChildren().addAll(leftPane, rightPane);
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        return scene;
    }

    /**
     * Returns the stage for this page.
     *
     * @return the stage.
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Validates the email format.
     *
     * @param email the email to validate.
     * @return true if the email format is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validates the password format.
     *
     * @param password the password to validate.
     * @return true if the password format is valid, false otherwise.
     */
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Displays an alert dialog with the specified parameters.
     *
     * @param alertType the type of the alert.
     * @param title     the title of the alert.
     * @param header    the header text of the alert (nullable).
     * @param content   the content of the alert.
     */
    private void showAlert(AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Clears the fields for username, email, and password.
     *
     * @param usernameField the username text field.
     * @param emailField    the email text field.
     * @param passwordField the password field.
     */
    private void clearFields(TextField usernameField, TextField emailField, PasswordField passwordField) {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
    }
}
