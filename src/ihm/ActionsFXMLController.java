package ihm;

import core.Action;
import core.Player;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ActionsFXMLController implements Initializable {

    ScreensController myScreensController;

    @FXML
    private ComboBox action1, action2, action3, action4, action5, action6;

    @FXML
    private ComboBox region1, region2, region3, region4, region5, region6;

    @FXML
    private Label playerColorLabel;

    private ArrayList<ComboBox> choiceBoxes = new ArrayList<>();
    private ArrayList<ComboBox> regionBoxes = new ArrayList<>();
    private Player player;
    private ImageView background;
    private Image backgroundRegion;
    private Image backgroundNoRegion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        backgroundRegion = new Image("resources/board_regions.jpg");
        backgroundNoRegion = new Image("resources/board.jpg");
        
        String[] actionChoices = {
            "Chemin de terre", "Chemin de pierre",
            "Chemin de glace", "Pause",
            "Transaction", "Troc",
            "Delegation", "Offrande"
        };

        Integer[] regionsID = {1, 2, 3, 4, 5, 6, 7, 8};

        choiceBoxes.addAll(Arrays.asList(action1, action2, action3, action4, action5, action6));
        regionBoxes.addAll(Arrays.asList(region1, region2, region3, region4, region5, region6));

        for (ComboBox box : choiceBoxes) {
            box.setItems(FXCollections.observableArrayList(Arrays.asList(actionChoices)));
        }
        for (ComboBox box : regionBoxes) {
            box.setItems(FXCollections.observableArrayList(Arrays.asList(regionsID)));
        }
    }

    @FXML
    public void validationButtonClick(Event evt) {
        boolean allGood = true;
        int count = 0;
        while (count < 6 && allGood == true) {
            ComboBox action = choiceBoxes.get(count);
            if (action.getValue() != null) {
                if (action.getValue().equals("Delegation")) {
                    ComboBox region = regionBoxes.get(count);
                    if (region.getValue() == null) {
                        allGood = false;
                    }
                }
            } else {
                allGood = false;
            }
            count++;
        }

        if (allGood) {
            validateAndClose(evt);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Pas de précipitation !");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez choisir 6 actions !\nNe pas oublier de choisir la région pour les délégations.");
            alert.showAndWait();
        }
    }

    private void validateAndClose(Event evt) {
        for (int i = 0; i < 6; i++) {
            ComboBox action = choiceBoxes.get(i);
            if (action.getValue().equals("Delegation")) {
                ComboBox region = regionBoxes.get(i);
                player.addAction(new Action(Action.Type.delegation, (int) region.getValue()));
            } else {
                System.out.println(action.getId() + " : " + action.getValue());
                switch ((String) action.getValue()) {
                    case "Chemin de pierre":
                        player.addAction(new Action(Action.Type.stone));
                        break;
                    case "Chemin de glace":
                        player.addAction(new Action(Action.Type.ice));
                        break;
                    case "Chemin de terre":
                        player.addAction(new Action(Action.Type.soil));
                        break;
                    case "Troc":
                        player.addAction(new Action(Action.Type.bartering));
                        break;
                    case "Transaction":
                        player.addAction(new Action(Action.Type.transaction));
                        break;
                    case "Offrande":
                        player.addAction(new Action(Action.Type.offering));
                        break;
                    case "Pause":
                        player.addAction(new Action(Action.Type.pause));
                        break;
                }
            }

        }

        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void comboBoxValueChanged(Event evt) {
        ComboBox actionBox = ((ComboBox) evt.getSource());

        switch (actionBox.getId()) {
            case "action1":
                region1.setDisable(!actionBox.getValue().equals("Delegation"));
                break;
            case "action2":
                region2.setDisable(!actionBox.getValue().equals("Delegation"));
                break;
            case "action3":
                region3.setDisable(!actionBox.getValue().equals("Delegation"));
                break;
            case "action4":
                region4.setDisable(!actionBox.getValue().equals("Delegation"));
                break;
            case "action5":
                region5.setDisable(!actionBox.getValue().equals("Delegation"));
                break;
            case "action6":
                region6.setDisable(!actionBox.getValue().equals("Delegation"));
                break;
        }
    }
    
    @FXML
    private void displayRegions(){
        if(background.getImage().equals(backgroundNoRegion)){
            background.setImage(backgroundRegion);
        }else {
            background.setImage(backgroundNoRegion);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
        playerColorLabel.setText("Joueur " + this.player.getColor());
    }

    public void setBackground(ImageView iv) {
        background = iv;
        background.setImage(backgroundNoRegion);
    }
}
