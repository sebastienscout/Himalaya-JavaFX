package ihm;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    @FXML
    public void testActionButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ActionsFXML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Choix des actions");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StartFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

}
