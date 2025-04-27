import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the Student Management System.
 * This class handles scene transitions and initializes the primary stage.
 */
public class StudentManagementSystem extends Application {

    private Stage primaryStage;
    private Scene loginScene;
    private Scene registerScene;

    /**
     * Entry point for the JavaFX application.
     *
     * @param primaryStage The primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.loginScene = new LoginPage(this).createLoginForm();
        this.registerScene = new RegisterPage(this).createRegisterForm();

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Student Management System");
        primaryStage.setResizable(false);
        primaryStage.setWidth(900); // Fixed size
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    /**
     * Displays the login page.
     */
    public void showLoginPage() {
        primaryStage.setScene(loginScene);
    }

    /**
     * Displays the register page.
     */
    public void showRegisterPage() {
        primaryStage.setScene(registerScene);
    }

    /**
     * Displays the dashboard page.
     */
    public void showDashboardPage() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.showPage(this);
    }

    /**
     * Displays the add student page.
     */
    public void showAddStudentPage() {
        AddStudentPage addStudentPage = new AddStudentPage();
        addStudentPage.showPage(this);
    }

    /**
     * Displays the available courses page.
     */
    public void showAvailableCoursesPage() {
        AvailableCoursesPage availableCoursesPage = new AvailableCoursesPage();
        availableCoursesPage.showPage(this);
    }

    /**
     * Displays the grades page.
     */
    public void showGradesPage() {
        GradesPage gradesPage = new GradesPage();
        gradesPage.showPage(this);
    }

    /**
     * Returns the primary stage of the application.
     *
     * @return The primary stage.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
