import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

/**
 * The {@code ClearButtonAction} class provides an implementation of an action
 * to clear input fields in a form. It handles clearing multiple {@link TextField}s,
 * {@link DatePicker}s, and {@link ComboBox} selections.
 */
public class ClearButtonAction extends AbstractButtonAction {
    private final TextField[] textFields; // Array of TextFields to be cleared.
    private final DatePicker[] datePickers; // Array of DatePickers to be cleared.
    private final ComboBox<String> genderField; // ComboBox for gender selection to be cleared.
    private final ComboBox<String> statusField; // ComboBox for status selection to be cleared.

    /**
     * Constructs a {@code ClearButtonAction} with the specified fields to clear.
     *
     * @param textFields  an array of {@link TextField} instances to be cleared
     * @param datePickers an array of {@link DatePicker} instances to be cleared
     * @param genderField the {@link ComboBox} for gender selection to be cleared
     * @param statusField the {@link ComboBox} for status selection to be cleared
     */
    public ClearButtonAction(TextField[] textFields, DatePicker[] datePickers,
                             ComboBox<String> genderField, ComboBox<String> statusField) {
        super("ClearAction");
        this.textFields = textFields;
        this.datePickers = datePickers;
        this.genderField = genderField;
        this.statusField = statusField;
    }

    /**
     * Executes the clear action by resetting all input fields to their default state.
     * <ul>
     *   <li>Clears all {@link TextField}s.</li>
     *   <li>Clears all {@link DatePicker}s.</li>
     *   <li>Resets {@link ComboBox} selections for gender and status to {@code null}.</li>
     *   <li>Displays an information alert indicating the fields have been cleared.</li>
     * </ul>
     */
    @Override
    public void execute() {
        // Clear all TextFields.
        for (TextField textField : textFields) {
            textField.clear();
        }

        // Clear all DatePickers.
        for (DatePicker datePicker : datePickers) {
            datePicker.setValue(null);
        }

        // Reset ComboBox selections to null.
        genderField.setValue(null);
        statusField.setValue(null);

        // Display an information alert.
        showAlert(Alert.AlertType.INFORMATION, "Clear Fields", "All input fields have been cleared!");
    }
}
