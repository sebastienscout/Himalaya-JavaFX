package ihm;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;//DONT TOUCH 
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class MenuFXMLController extends StackPane implements Initializable, ControlledScreen {

    public ScreensController myController = null;

    @FXML
    private ComboBox playerColor1, playerColor2, playerColor3, playerColor4;

    @FXML
    private ComboBox playerType1, playerType2, playerType3, playerType4;

    @FXML
    private Button addPlayer, play;

    @FXML
    private FlowPane player4;

    private double opacity = 1;

    ArrayList<ComboBox> choiceColors = new ArrayList<>();
    ArrayList<ComboBox> choiceTypes = new ArrayList<>();
    ArrayList<ComboBox> inputs = new ArrayList<>();

    PlayGraphic playG;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        String[] colorChoices = {
            "Blanc", "Bleu", "Orange", "Rouge", "Vert", "Violet"
        };

        String[] typeChoices = {
            "Humain", "IA Aléatoire", "IA Evolutionnaire"
        };
        
        choiceColors.addAll(Arrays.asList(playerColor1, playerColor2, playerColor3, playerColor4));
        choiceTypes.addAll(Arrays.asList(playerType1, playerType2, playerType3, playerType4));
        inputs.addAll(choiceColors);
        inputs.addAll(choiceTypes);

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

        play.setDisable(true);

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

        playG.addEvolAI("Rouge");
        playG.addEvolAI("Bleu");
        playG.addEvolAI("Vert");
        playG.addEvolAI("Blanc");

        myController.setScreen(Main.screenMainID);

//        if (testInput) {
//
//            if (choiceTypes.get(0).getValue().equals("Humain")) {
//                playG.addPlayer(playerColor1.getValue().toString());
//            } else if (choiceTypes.get(0).getValue().equals("IA Aléatoire")) {
//                playG.addRandomAI(playerColor1.getValue().toString());
//            } else if (choiceTypes.get(0).getValue().equals("IA Evolutionnaire")) {
//                playG.addEvolAI(playerColor1.getValue().toString());
//            }
//            if (choiceTypes.get(1).getValue().equals("Humain")) {
//                playG.addPlayer(playerColor2.getValue().toString());
//            } else if (choiceTypes.get(1).getValue().equals("IA Aléatoire")) {
//                playG.addRandomAI(playerColor2.getValue().toString());
//            } else if (choiceTypes.get(1).getValue().equals("IA Evolutionnaire")) {
//                playG.addEvolAI(playerColor2.getValue().toString());
//            }
//            if (choiceTypes.get(2).getValue().equals("Humain")) {
//                playG.addPlayer(playerColor3.getValue().toString());
//            } else if (choiceTypes.get(2).getValue().equals("IA Aléatoire")) {
//                playG.addRandomAI(playerColor3.getValue().toString());
//            } else if (choiceTypes.get(2).getValue().equals("IA Evolutionnaire")) {
//                playG.addEvolAI(playerColor3.getValue().toString());
//            }
//
//            if (player4.isVisible()) {
//                if (choiceTypes.get(3).getValue().equals("Humain")) {
//                    playG.addPlayer(playerColor4.getValue().toString());
//                } else if (choiceTypes.get(3).getValue().equals("IA Aléatoire")) {
//                    playG.addRandomAI(playerColor4.getValue().toString());
//                } else if (choiceTypes.get(3).getValue().equals("IA Evolutionnaire")) {
//                    playG.addEvolAI(playerColor4.getValue().toString());
//                }
//            }
//
//            //Si tous les champs sont remplis on va sur le plateau
//            myController.setScreen(Main.screenMainID);
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Pas de précipitation !");
//            alert.setHeaderText(null);
//            alert.setContentText("Vous devez configurer les joueurs et leur type !");
//            alert.showAndWait();
//
//            play.setDisable(false);
//        }

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
//        colorChoices.remove(((ComboBox)event.getSource()).getValue());
//
////        System.out.println("test : " + event.getSource().toString());
//        for (ComboBox choiceColor : choiceColors) {
//            if (choiceColor != event.getSource()) {
//                choiceColor.setItems(FXCollections.observableArrayList(colorChoices));
//            }
//        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void initScreen() {

    }

}
