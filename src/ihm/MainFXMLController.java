package ihm;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainFXMLController implements Initializable, ControlledScreen {

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void play() {
        System.out.println("Clic sur le bouton 'Jouer'.");
    }

    @FXML
    private void clickOnVillage() {

        System.out.println("Clic sur le bouton Village.");
    }

    @FXML
    private void displayImage() {

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
