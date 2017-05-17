package ihm;

import core.Action;
import core.Play;
import core.Player;
import core.Village;
import ia.RandomAI;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayGraphic extends Play {

    public PlayGraphic() {
        super();
    }

    public void run(ImageView background) {
        testVillages();
        
        for (Player p : board.getPlayers()) {
            if (!(p instanceof RandomAI)) {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ActionsFXML.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    ActionsFXMLController actionCtrl = fxmlLoader.getController();
                    actionCtrl.setPlayer(p);
                    actionCtrl.setBackground(background);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Choix des actions");
                    stage.setScene(new Scene(root1));
                    stage.showAndWait();

                } catch (IOException ex) {
                    Logger.getLogger(PlayGraphic.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                for (int i = 0; i < 6; i++) {
                    Action action = ((RandomAI) p).getRandomAction();
                    p.addAction(action);
                }
            }
        }
    }
}
