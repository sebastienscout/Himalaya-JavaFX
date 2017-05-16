package ihm;

import core.Player;
import core.Village;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class MainFXMLController implements Initializable, ControlledScreen {

    ScreensController myController;

    PlayGraphic playG;

    @FXML
    private Label turnLabel;

    @FXML
    private Label player1Label, player2Label, player3Label, player4Label;

    @FXML
    private Label player1ResLabel, player2ResLabel, player3ResLabel, player4ResLabel;

    @FXML
    private FlowPane village1, village2, village3, village4, village5,
            village6, village7, village8, village9, village10,
            village11, village12, village13, village14, village15,
            village16, village17, village18, village19, village20;

    private ArrayList<FlowPane> villagesPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playG = new PlayGraphic();
        villagesPane = new ArrayList<>();
        villagesPane.add(village1);
        villagesPane.add(village2);
        villagesPane.add(village3);
        villagesPane.add(village4);
        villagesPane.add(village5);
        villagesPane.add(village6);
        villagesPane.add(village7);
        villagesPane.add(village8);
        villagesPane.add(village9);
        villagesPane.add(village10);
        villagesPane.add(village11);
        villagesPane.add(village12);
        villagesPane.add(village13);
        villagesPane.add(village14);
        villagesPane.add(village15);
        villagesPane.add(village16);
        villagesPane.add(village17);
        villagesPane.add(village18);
        villagesPane.add(village19);
        villagesPane.add(village20);
    }

    @FXML
    private void nextTurnButton() {
        playG.run();

        playG.getBoard().prepareActions();

        for (int i = 0; i < 6; i++) {
            playG.getBoard().executeAction(i);

            displayElementsMap();

            player1ResLabel.setText("Ressources : " + playG.getBoard().getPlayers().get(0).getResources().toString());
            //player2ResLabel.setText("Ressources : " + playG.getBoard().getPlayers().get(1).getResources().toString());
            //player3ResLabel.setText("Ressources : " + playG.getBoard().getPlayers().get(2).getResources().toString());
            //player4ResLabel.setText("");
        }

        playG.getBoard().afterActions();

        turnLabel.setText("Tour " + Integer.toString(playG.getBoard().getNbTurn()));
    }

    @FXML
    private void testResourcesButton() {

        player1Label.setText("Joueur " + playG.getBoard().getPlayers().get(0).getColor() + " > ");
//        player2Label.setText("Joueur " + playG.getBoard().getPlayers().get(1).getColor() + " > ");
//        player3Label.setText("Joueur " + playG.getBoard().getPlayers().get(2).getColor() + " > ");
//        player4Label.setText("");

        playG.testVillages();
        playG.displayInfoBoard();
        displayElementsMap();
    }

    private void displayElementsMap() {
        for (int i = 0; i < 20; i++) {
            villagesPane.get(i).getChildren().clear();
            Village v = playG.getBoard().getVillages().get(i);
            if (v.getResources().size() > 0) {
                Label testVillageLabel = new Label("Ressources\n" + v.getResources().toString());
                testVillageLabel.setStyle("-fx-background-color: white;");
                villagesPane.get(i).getChildren().add(testVillageLabel);
            } else if (v.getOrder() != null) {
                Label testVillageLabel = new Label("Commande\n" + v.getOrder().getResources().toString());
                testVillageLabel.setStyle("-fx-background-color: white;");
                villagesPane.get(i).getChildren().add(testVillageLabel);
            }
            for (Player player : playG.getBoard().getPlayers()) {
                if (player.getPosition().equals(v)) {
                    Image img = new Image("resources/player/" + player.getColor() + ".png");
                    ImageView iv1 = new ImageView();
                    iv1.setFitWidth(50.0);
                    iv1.setPreserveRatio(true);
                    iv1.setImage(img);
                    villagesPane.get(i).getChildren().add(iv1);
                }
            }
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    //événement à l'affichage de la page
    @Override
    public void initScreen() {
        System.out.println("Test affichage du main ...");
    }
}
