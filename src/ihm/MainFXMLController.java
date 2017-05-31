package ihm;

import core.Player;
import core.Region;
import core.Village;
import ia.EvolutionaryAI;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList<Label> playerLabels;

    private final long timeAnimation = 500;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        playG = new PlayGraphic();

        villagesPane = new ArrayList<>();
        regionsPane = new ArrayList<>();
        playerLabels = new ArrayList<>();

        villagesPane.addAll(Arrays.asList(village1, village2, village3, village4,
                village5, village6, village7, village8, village9, village10,
                village11, village12, village13, village14, village15, village16,
                village17, village18, village19, village20));

        regionsPane.addAll(Arrays.asList(region1, region2, region3, region4,
                region5, region6, region7, region8));

        playerLabels.addAll(Arrays.asList(player1ResLabel, player2ResLabel,
                player3ResLabel, player4ResLabel));

    }

    @FXML
    private void nextTurnButton() {

        playTurn.setDisable(true);

        playG.setBackground(background);
        playG.run();

        playG.getBoard().prepareActions();

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    for (int i = 0; i < 6; i++) {
                        for (Player p : playG.getBoard().getPlayers()) {
                            Thread.sleep(timeAnimation);
                            playG.getBoard().executeAction(i, p);
                            Platform.runLater(() -> displayElementsMap());
                            Platform.runLater(() -> displayPlayersInformations());
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
                    for (Player p : playG.getBoard().getPlayers()) {
                        if (p instanceof EvolutionaryAI) {
                            ((EvolutionaryAI) p).closeFile();
                        }
                    }
                } else {
                    playG.refillVillages();
                    Platform.runLater(() -> displayElementsMap());
                    Platform.runLater(() -> displayPlayersInformations());
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
                testVillageLabel.setStyle("-fx-background-color: rgba(150,200,150,.8);");
                villagesPane.get(i).getChildren().add(testVillageLabel);
            } else if (v.getOrder() != null) {
                Label testVillageLabel = new Label("Commande (" + v.getOrder().getValue() + ")\n" + v.getOrder().getResources().toString());
                testVillageLabel.setStyle("-fx-background-color: rgba(255,200,150,.8);");
                villagesPane.get(i).getChildren().add(testVillageLabel);
            }

            //Gestions des stupas
            if (v.getStupa() != null) {
                Image img = new Image("resources/stupa/" + v.getStupa().getColor() + ".png");
                ImageView iv1 = new ImageView();
                iv1.setFitHeight(50.0);
                iv1.setPreserveRatio(true);
                iv1.setImage(img);
                villagesPane.get(i).getChildren().add(iv1);
            }

            for (Player player : playG.getBoard().getPlayers()) {
                if (player.getPosition() != null && player.getPosition().equals(v)) {
                    Image img = new Image("resources/player/" + player.getColor() + ".png");
                    ImageView iv1 = new ImageView();
                    iv1.setFitWidth(60.0);
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

            for (Map.Entry<String, Integer> delegation : r.getDelegations().entrySet()) {

                Player player = playG.getBoard().getPlayerByColor(delegation.getKey());
                Integer nbDelegation = delegation.getValue();

                for (int j = 0; j < nbDelegation; j++) {
                    Image img = new Image("resources/delegation/delegation_" + player.getColor() + ".png");
                    ImageView iv1 = new ImageView();
                    iv1.setFitHeight(35.0);
                    iv1.setPreserveRatio(true);
                    iv1.setImage(img);

                    regionsPane.get(i).getChildren().add(iv1);
                }
            }
        }
    }

    public void displayPlayersInformations() {
        setPlayersInformation(playG.getBoard().getPlayers().get(0), player1ResLabel);
        setPlayersInformation(playG.getBoard().getPlayers().get(1), player2ResLabel);
        setPlayersInformation(playG.getBoard().getPlayers().get(2), player3ResLabel);
        if (playG.getBoard().getPlayers().size() == 4) {
            setPlayersInformation(playG.getBoard().getPlayers().get(3), player4ResLabel);
        } else {
            player4ResLabel.setVisible(false);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    private void setPlayersInformation(Player p, Label playerLabel) {
        FlowPane playerPane = (FlowPane) playerLabel.getParent();
        if (playerPane.getChildren().size() == 2) {
            playerPane.getChildren().remove(0);
        }
        Image img = new Image("resources/player/" + p.getColor() + ".png");
        ImageView iv = new ImageView();
        iv.setFitWidth(30.0);
        iv.setPreserveRatio(true);
        iv.setImage(img);
        playerLabel.setText("Joueur " + p.getColor() + " > Ressources : " + p.getResources()
                + " | Nombre de Yacks : " + p.getEconomicScore());
        playerPane.getChildren().add(0, iv);
    }

    //événement à l'affichage de la page
    @Override
    public void initScreen() {

        playG.refillVillages();

        displayElementsMap();
        displayPlayersInformations();

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
