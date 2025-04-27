import javafx.scene.control.Alert;

/**
 * An abstract class that defines a framework for button-related actions.
 * Provides common functionality such as showing alerts and retrieving the action name.
 */
public abstract class AbstractButtonAction {
    private final String actionName;

    /**
     * Constructs an AbstractButtonAction with the specified action name.
     *
     * @param actionName the name of the action, used for identification
     */
    public AbstractButtonAction(String actionName) {
        this.actionName = actionName;
    }

    /**
     * Executes the action associated with the button.
     * Subclasses must provide the implementation for this method.
     */
    public abstract void execute();

    /**
     * Displays an alert dialog with the specified type, title, and message.
     *
     * @param alertType the type of alert (e.g., INFORMATION, WARNING, ERROR)
     * @param title     the title of the alert dialog
     * @param message   the content message of the alert dialog
     */
    protected void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Retrieves the name of the action.
     *
     * @return the name of the action
     */
    public String getActionName() {
        return actionName;
    }
}
