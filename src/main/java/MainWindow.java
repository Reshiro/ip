import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import yochan.YoChan;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private YoChan yoChan;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaNiko.jpg"));
    private Image yoChanImage = new Image(this.getClass().getResourceAsStream("/images/DaYoChan.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the YoChan instance */
    public void setYoChan(YoChan d) {
        yoChan = d;
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(getWelcomeMessage(), yoChanImage));
    }

    public String getWelcomeMessage() {
        return "Ough... What do you want?";
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        assert input != null : "User input should not be null";
        String response = yoChan.getResponse(input);
        assert response != null : "Chatbot response to user input should not be null";
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, yoChanImage)
        );
        userInput.clear();
    }
}
