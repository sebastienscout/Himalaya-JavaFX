package ihm;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuFXMLController extends StackPane implements Initializable, ControlledScreen {

    public ScreensController myController = null;

    @FXML
    private ComboBox playerColor1, playerColor2, playerColor3, playerColor4;

    @FXML
    private ComboBox playerType1, playerType2, playerType3, playerType4;

    @FXML
    private Button addPlayer;

    @FXML
    private FlowPane player4;

    @FXML
    private Text testText;

    private double opacity = 1;

    ArrayList<ComboBox> choiceColors = new ArrayList<>();
    ArrayList<ComboBox> choiceTypes = new ArrayList<>();
    ArrayList<ComboBox> inputs = new ArrayList<>();
    ArrayList<String> colorChoices = new ArrayList<>();

    PlayGraphic playG;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colorChoices.add("Jaune");
        colorChoices.add("Rouge");
        colorChoices.add("Bleu");
        colorChoices.add("Noir");

        String[] typeChoices = {
            "Humain", "IA"
        };

        choiceColors.add(playerColor1);
        choiceColors.add(playerColor2);
        choiceColors.add(playerColor3);
        choiceColors.add(playerColor4);
        choiceTypes.add(playerType1);
        choiceTypes.add(playerType2);
        choiceTypes.add(playerType3);
        choiceTypes.add(playerType4);
        inputs.add(playerColor1);
        inputs.add(playerColor2);
        inputs.add(playerColor3);
        inputs.add(playerColor4);
        inputs.add(playerType1);
        inputs.add(playerType2);
        inputs.add(playerType3);
        inputs.add(playerType4);

        for (ComboBox box : choiceColors) {
            box.setItems(FXCollections.observableArrayList(colorChoices));
        }

        for (ComboBox box : choiceTypes) {
            box.setItems(FXCollections.observableArrayList(typeChoices));
        }

        playG = new PlayGraphic();

    }

    @FXML
    public void switchToMain() {

        //to do gérer les type de joueurs
        boolean testInput = true;

        if (!player4.isVisible()) {
            inputs.remove(playerColor4);
            inputs.remove(playerType4);
        }

        for (ComboBox input : inputs) {
            if (input.getSelectionModel().getSelectedItem() == null) {
                testInput = false;
            }
        }

        if (testInput) {
            playG.addPlayer(playerColor1.getValue().toString(), 1);
            playG.addPlayer(playerColor2.getValue().toString(), 1);
            playG.addPlayer(playerColor3.getValue().toString(), 1);

            System.out.println("Couleur player 1 : " + playG.getBoard().getPlayers().get(0).getColor());
            System.out.println("Couleur player 2 : " + playG.getBoard().getPlayers().get(1).getColor());
            System.out.println("Couleur player 3 : " + playG.getBoard().getPlayers().get(2).getColor());

            //Si tous les champs sont remplis on va sur le plateau
            myController.setScreen(Main.screenMainID);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pas de précipitation !");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez configurer les joueurs et leur type !");
            alert.showAndWait();
        }

    }

    @FXML
    public void addPlayer() {
        if (player4.isVisible()) {
            player4.setVisible(false);
            addPlayer.setText("Ajouter joueur");
        } else {
            player4.setVisible(true);
            addPlayer.setText("Retirer joueur");
        }
    }

    @FXML
    public void removeColor(Event event) {

        //TODO enlever couleur des autres listes
        colorChoices.remove(event.toString());

//        System.out.println("test : " + event.getSource().toString());
        for (ComboBox choiceColor : choiceColors) {
            if (choiceColor != event.getSource()) {
                choiceColor.setItems(FXCollections.observableArrayList(colorChoices));
            }
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    public void testText() {

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    for (int i = 0; i <= 10; i++) {
                        Thread.sleep(500);
                        System.out.println("test " + i);
                        testText.setText("Test " + i);

                    }
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

            }
        });
        new Thread(sleeper).start();

    }

    @Override
    public void initScreen() {

    }

}
