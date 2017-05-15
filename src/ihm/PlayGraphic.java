package ihm;

import core.Action;
import core.Play;
import core.Player;
import core.Region;
import core.Resource;
import core.Village;
import ia.RandomAI;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PlayGraphic extends Play {

    public PlayGraphic() {
        super();
    }

    public void run() {
        testVillages();
        displayInfoBoard();
        
        for (Player p : board.getPlayers()) {
            if (!(p instanceof RandomAI)) {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ActionsFXML.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    ActionsFXMLController actionCtrl = fxmlLoader.getController();
                    actionCtrl.setPlayer(p);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Choix des actions : Joueur " + p.getColor());
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
        board.executeActions();
    }
    
    public void displayInfoBoard() {
        System.out.println("******* ETAT DU PLATEAU TOUR : " + board.getNbTurn() + " *******");
        for (Village village : board.getVillages()) {
            if (!village.getResources().isEmpty()) {
                System.out.print("Village " + village.getId() + " -> Resource[");
                for (Resource res : village.getResources()) {
                    System.out.print(res.getType() + ", ");
                }
                System.out.println(" ]");
            } else if (village.getOrder() != null) {
                System.out.print("Village " + village.getId() + " -> Commande[");
                for (Resource res : village.getOrder().getResources()) {
                    System.out.print(res.getType() + ", ");
                }
                System.out.println("]");
            }
        }
    }
}
