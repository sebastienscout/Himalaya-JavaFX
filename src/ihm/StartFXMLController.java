package ihm;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class StartFXMLController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    public void switchToMenu(){
        myController.setScreen(Main.screen2ID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
}
