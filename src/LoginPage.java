import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The LoginPage class creates and handles the user interface for the login screen
 * of the Student Management System application.
 * 
 * This class defines the layout and behavior for the login form, including the 
 * username and password input fields, the login button, and the registration option.
 */
public class LoginPage {

    // Reference to the main application instance
    private StudentManagementSystem mainApp;

    /**
     * Constructor to initialize the LoginPage with the main application.
     * 
     * @param app - The main application instance of type StudentManagementSystem.
     */
    public LoginPage(StudentManagementSystem app) {
        this.mainApp = app;
    }

    /**
     * Creates and returns the login form scene with the required UI components.
     * 
     * @return Scene - The JavaFX Scene object representing the login form.
     */
    public Scene createLoginForm() {
        // VBox for the left side containing the logo and title
        VBox leftPane = new VBox(20);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(20));
        leftPane.setStyle("-fx-background-color: linear-gradient(to bottom, #1db1a4, #0f8ba5);");

        // Logo of the application
        Label logo = new Label("🎓");
        logo.setFont(new Font(180));
        logo.setTextFill(Color.WHITE);

        // Title of the application
        Label appTitle = new Label("Student Management System");
        appTitle.setTextFill(Color.WHITE);
        appTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        // Adding logo and title to the left pane
        leftPane.getChildren().addAll(logo, appTitle);

        // VBox for the right side containing the login form
        VBox rightPane = new VBox(15);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(30));
        rightPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        rightPane.setPrefWidth(400);

        // Icon representing the user input field
        Label userIcon = new Label("👤");
        userIcon.setFont(new Font(80));
        userIcon.setTextFill(Color.BLACK);

        // Welcome label above the input fields
        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeLabel.setTextFill(Color.BLACK);

        // TextField for entering the username
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(300);
        usernameField.setMaxWidth(300);

        // PasswordField for entering the password
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);
        passwordField.setMaxWidth(300);

        // Button to submit the login form
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-font-weight: bold;");
        loginButton.setPrefWidth(200);

        /**
         * Action for the login button. 
         * It authenticates the user and navigates to the dashboard if successful,
         * otherwise it displays an error message.
         */
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (DatabaseConnection.authenticateUser(username, password)) {
                mainApp.showDashboardPage();
            } else {
                showErrorMessage("Invalid username or password!");
            }

            usernameField.clear();
            passwordField.clear();
        });

        // HBox for switching to the registration page
        HBox registerBox = new HBox(5);
        registerBox.setAlignment(Pos.CENTER);
        Label noAccountLabel = new Label("Don't have an account?");
        noAccountLabel.setTextFill(Color.BLACK);
        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #0056b3; -fx-font-weight: bold; -fx-underline: true;");

        /**
         * Action for the register button. 
         * It navigates to the registration page.
         */
        registerButton.setOnAction(e -> {
            mainApp.showRegisterPage();
        });

        registerBox.getChildren().addAll(noAccountLabel, registerButton);

        // Adding all components to the right pane
        rightPane.getChildren().addAll(userIcon, welcomeLabel, usernameField, passwordField, loginButton, registerBox);

        // Root container to combine left and right panes
        HBox root = new HBox();
        root.getChildren().addAll(leftPane, rightPane);
        root.setPrefSize(900, 600);
        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        return new Scene(root);
    }

    /**
     * Displays an error message using a JavaFX Alert.
     * 
     * @param message - The error message to display.
     */
    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
