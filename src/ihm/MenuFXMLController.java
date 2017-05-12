package ihm;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MenuFXMLController implements Initializable, ControlledScreen {

    ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void switchToMain() {
        myController.setScreen(Main.screen3ID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
