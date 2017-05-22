package ihm;

import core.Player;
import core.Region;
import core.Village;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    @FXML
    private FlowPane region1, region2, region3, region4, region5, region6,
            region7, region8;

    @FXML
    private ImageView background;

    @FXML
    private Button playTurn;

    private ArrayList<FlowPane> villagesPane;
    private ArrayList<FlowPane> regionsPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        playG = new PlayGraphic();

        villagesPane = new ArrayList<>();
        regionsPane = new ArrayList<>();

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

        regionsPane.add(region1);
        regionsPane.add(region2);
        regionsPane.add(region3);
        regionsPane.add(region4);
        regionsPane.add(region5);
        regionsPane.add(region6);
        regionsPane.add(region7);
        regionsPane.add(region8);
    }

    @FXML
    private void nextTurnButton() {

        playTurn.setDisable(true);

        playG.run(background);

        playG.getBoard().prepareActions();

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    for (int i = 0; i < 6; i++) {
                        for (Player p : playG.getBoard().getPlayers()) {
                            Thread.sleep(500);
                            playG.getBoard().executeAction(i, p);
                            Platform.runLater(() -> displayElementsMap());

                            Player p1 = playG.getBoard().getPlayers().get(0);
                            Player p2 = playG.getBoard().getPlayers().get(1);
                            Player p3 = playG.getBoard().getPlayers().get(2);

                            Platform.runLater(() -> player1ResLabel.setText("Ressources : " + p1.getResources().toString()
                                    + " | Nombre de Yacks : " + p1.getEconomicScore()
//                                    + " | Rel : " + p1.getReligiousScore()
//                                    + " | Pol : " + p1.getPoliticalScore()
                            ));
                            Platform.runLater(() -> player2ResLabel.setText("Ressources : " + p2.getResources().toString()
                                    + " | Nombre de Yacks : " + p2.getEconomicScore()
//                                    + " | Rel : " + p1.getReligiousScore()
//                                    + " | Pol : " + p1.getPoliticalScore()
                            ));
                            Platform.runLater(() -> player3ResLabel.setText("Ressources : " + p3.getResources().toString()
                                    + " | Nombre de Yacks : " + p3.getEconomicScore()
//                                    + " | Rel : " + p1.getReligiousScore()
//                                    + " | Pol : " + p1.getPoliticalScore()
                            ));
                           

                            if (playG.getBoard().getPlayers().size() == 4) {
                                Player p4 = playG.getBoard().getPlayers().get(3);
                                Platform.runLater(() -> player4ResLabel.setText("Ressources : " + p4.getResources().toString()
                                        + " | Eco : " + p4.getEconomicScore()
                                        + " | Rel : " + p4.getReligiousScore()
                                        + " | Pol : " + p4.getPoliticalScore()
                                ));
                            }
                        }
                    }
                } catch (InterruptedException e) {
                }
                return null;
            }
        };

        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                playG.getBoard().afterActions();
                if (playG.getBoard().getNbTurn() == 13) {
                    turnLabel.setText("Terminé !");
                    displayScoreScreen();
                } else {
                    playG.testVillages();
                    Platform.runLater(() -> displayElementsMap());
                    turnLabel.setText("Tour " + Integer.toString(playG.getBoard().getNbTurn()));
                    playTurn.setDisable(false);
                }
            }
        });

        new Thread(sleeper).start();
    }

    private void displayScoreScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScoreFXML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            ScoreFXMLController scireCtrl = fxmlLoader.getController();
            scireCtrl.setBoard(playG.getBoard());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Résultats");
            stage.setScene(new Scene(root1));
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(PlayGraphic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayElementsMap() {
        for (int i = 0; i < 20; i++) {
            villagesPane.get(i).getChildren().clear();
            Village v = playG.getBoard().getVillages().get(i);
            if (v.getResources().size() > 0) {
                Label testVillageLabel = new Label("Ressources\n" + v.getResources().toString());
                testVillageLabel.setStyle("-fx-background-color: rgba(255,255,255,.8);");
                villagesPane.get(i).getChildren().add(testVillageLabel);
            } else if (v.getOrder() != null) {
                Label testVillageLabel = new Label("Commande (" + v.getOrder().getValue() + ")\n" + v.getOrder().getResources().toString());
                testVillageLabel.setStyle("-fx-background-color: rgba(255,255,255,.8);");
                villagesPane.get(i).getChildren().add(testVillageLabel);
            }
            
            //Gestions des stupas
            if (v.getStupa() != null) {
                Image img = new Image("resources/stupa/" + v.getStupa().getColor() + ".png");
                ImageView iv1 = new ImageView();
                iv1.setFitHeight(30.0);
                iv1.setPreserveRatio(true);
                iv1.setImage(img);
                villagesPane.get(i).getChildren().add(iv1);
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

        //Gestion des délégations
        for (int i = 0; i < 8; i++) {
            regionsPane.get(i).getChildren().clear();
            Region r = playG.getBoard().getRegions().get(i);

            for (Map.Entry<Player, Integer> delegation : r.getDelegations().entrySet()) {

                Player player = delegation.getKey();
                Integer nbDelegation = delegation.getValue();

                for (int j = 0; j < nbDelegation; j++) {
                    System.out.println("couleur " + player.getColor());
                    Image img = new Image("resources/delegation/delegation_" + player.getColor() + ".png");
                    ImageView iv1 = new ImageView();
                    iv1.setFitHeight(25.0);
                    iv1.setPreserveRatio(true);
                    iv1.setImage(img);

                    regionsPane.get(i).getChildren().add(iv1);
                }

            }

        }

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    private void setPlayersInformation(Player p, Label playerLabel) {
        Image img = new Image("resources/player/" + p.getColor() + ".png");
        ImageView iv = new ImageView();
        iv.setFitWidth(30.0);
        iv.setPreserveRatio(true);
        iv.setImage(img);
        ((FlowPane) playerLabel.getParent()).getChildren().add(0, iv);
        playerLabel.setText(" Joueur " + p.getColor() + " > ");
    }

    //événement à l'affichage de la page
    @Override
    public void initScreen() {

        setPlayersInformation(playG.getBoard().getPlayers().get(0), player1Label);
        setPlayersInformation(playG.getBoard().getPlayers().get(1), player2Label);
        setPlayersInformation(playG.getBoard().getPlayers().get(2), player3Label);
        if (playG.getBoard().getPlayers().size() == 4) {
            setPlayersInformation(playG.getBoard().getPlayers().get(3), player4Label);
        } else {
            player4Label.setVisible(false);
            player4ResLabel.setVisible(false);
        }

        playG.testVillages();

        displayElementsMap();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InitFXML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            InitFXMLController initCtrl = (InitFXMLController) (fxmlLoader.getController());
            initCtrl.lauch(playG.getBoard());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Choix du placement");
            stage.setScene(new Scene(root1));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(PlayGraphic.class.getName()).log(Level.SEVERE, null, ex);
        }

        displayElementsMap();

    }
}
