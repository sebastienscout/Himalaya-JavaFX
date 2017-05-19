package ihm;

import core.Action;
import core.Play;
import core.Player;
import ia.EvolutionaryAI;
import ia.RandomAI;
import ia.Population;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PlayGraphic extends Play {

    public PlayGraphic() {
        super();
    }

    public void run(ImageView background) {
        testVillages();

        for (Player p : board.getPlayers()) {
            if(p instanceof EvolutionaryAI){
                // population size of the parents
                int mu = 10;
                // population size of childrens
                int lambda = 50;
                // tournament size for selection
                int tournamentSize = 2;
                // rates of crossOver and mutation
                double crossOverRate = 0.8;
                // rates of mutation
                double mutationRate = 1.0;
                // maximum number of generation
                int maxGeneration = 200;
                
                Population population = new Population();

                ((EvolutionaryAI) p).run(population, mu, lambda, tournamentSize, crossOverRate, mutationRate, maxGeneration);

                for (Action action : ((EvolutionaryAI) p).getActions()) {
                    p.addAction(action);
                }
            } else if(p instanceof RandomAI){
                 for (int i = 0; i < 6; i++) {
                    Action action = ((RandomAI) p).getRandomAction();
                    p.addAction(action);
                }
            }
            else{
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ActionsFXML.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    ActionsFXMLController actionCtrl = fxmlLoader.getController();
                    actionCtrl.setPlayer(p);
                    actionCtrl.setBackground(background);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);

                    //Pour click sur close de action
                    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            // consume event
                            event.consume();
                            // show close dialog
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Pas de précipitation !");
                            alert.setHeaderText(null);
                            alert.setContentText("Vous devez choisir 6 actions !\nNe pas oublier de choisir la région pour les délégations.");
                            alert.showAndWait();
                        }
                    });
                    stage.setTitle("Choix des actions");
                    stage.setScene(new Scene(root1));
                    stage.showAndWait();

                } catch (IOException ex) {
                    Logger.getLogger(PlayGraphic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
