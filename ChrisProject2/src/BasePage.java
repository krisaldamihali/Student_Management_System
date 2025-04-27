import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Represents the base class for all pages in the Student Management System application.
 * Provides common functionality such as creating a sidebar with navigation buttons.
 */
public abstract class BasePage {

    /**
     * Creates a sidebar with navigation buttons for the application.
     *
     * @param app          the main application instance
     * @param activeButton the identifier for the currently active button
     * @return a VBox containing the styled sidebar with navigation buttons
     */
    protected VBox createSidebar(StudentManagementSystem app, String activeButton) {
        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-background-color: #006666; -fx-padding: 15px; -fx-pref-width: 200px; -fx-pref-height: 600px;");

        Label welcomeLabel = new Label("Welcome!");
        welcomeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px;");

        Button homeButton = createSidebarButton("Home", "\u2302", app, activeButton.equals("home"));
        Button addStudentsButton = createSidebarButton("Add Students", "\u2795", app, activeButton.equals("addStudents"));
        Button availableCoursesButton = createSidebarButton("Available Courses", "\uD83D\uDCD6", app, activeButton.equals("availableCourses"));
        Button gradesButton = createSidebarButton("Grades of Students", "\uD83C\uDF10", app, activeButton.equals("grades"));
        Button signOutButton = createSidebarButton("Sign Out", "\uD83D\uDEAA", app, activeButton.equals("signOut"));

        // Customize the style for the "Sign Out" button
        signOutButton.setStyle(signOutButton.getStyle() + "-fx-background-color: #CC3300; -fx-text-fill: white;");

        sidebar.getChildren().addAll(welcomeLabel, homeButton, addStudentsButton, availableCoursesButton, gradesButton, signOutButton);
        return sidebar;
    }

    /**
     * Creates a styled button for the sidebar with an icon and text.
     *
     * @param text       the text displayed on the button
     * @param iconSymbol the symbol representing the icon for the button
     * @param app        the main application instance
     * @param isActive   indicates whether this button is currently active
     * @return the styled Button instance
     */
    private Button createSidebarButton(String text, String iconSymbol, StudentManagementSystem app, boolean isActive) {
        Label icon = new Label(iconSymbol);
        icon.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

        Button button = new Button(text, icon);
        button.setStyle("-fx-pref-width: 150px; -fx-alignment: center-left; -fx-padding: 5px 10px; -fx-background-color: #006666; -fx-text-fill: white;");

        // Highlight the active button with a different style
        if (isActive) {
            button.setStyle(button.getStyle() + "-fx-background-color: #004444; -fx-text-fill: white;");
        }

        // Set the button's action based on its label
        switch (text) {
            case "Home":
                button.setOnAction(e -> app.showDashboardPage());
                break;
            case "Add Students":
                button.setOnAction(e -> app.showAddStudentPage());
                break;
            case "Available Courses":
                button.setOnAction(e -> app.showAvailableCoursesPage());
                break;
            case "Grades of Students":
                button.setOnAction(e -> app.showGradesPage());
                break;
            case "Sign Out":
                button.setOnAction(e -> app.showLoginPage());
                break;
            default:
                break;
        }

        return button;
    }

    /**
     * Displays the page within the application.
     *
     * @param app the main application instance
     */
    public abstract void showPage(StudentManagementSystem app);
}
