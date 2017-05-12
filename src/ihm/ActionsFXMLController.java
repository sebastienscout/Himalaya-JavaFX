package ihm;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class ActionsFXMLController implements Initializable {

    @FXML
    private ComboBox action1, action2, action3, action4, action5, action6;

    @FXML
    private ComboBox region1, region2, region3, region4, region5, region6;

    ArrayList<ComboBox> choiceBoxes = new ArrayList<>();
    ArrayList<ComboBox> regionBoxes = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] actionChoices = {
            "Chemin de terre", "Chemin de pierre",
            "Chemin de glace", "Pause",
            "Transaction", "Troc",
            "Delegation", "Offrande"
        };

        Integer[] regionsID = {1, 2, 3, 4, 5, 6, 7, 8};

        choiceBoxes.add(action1);
        choiceBoxes.add(action2);
        choiceBoxes.add(action3);
        choiceBoxes.add(action4);
        choiceBoxes.add(action5);
        choiceBoxes.add(action6);

        regionBoxes.add(region1);
        regionBoxes.add(region2);
        regionBoxes.add(region3);
        regionBoxes.add(region4);
        regionBoxes.add(region5);
        regionBoxes.add(region6);

        for (ComboBox box : choiceBoxes) {
            box.setItems(FXCollections.observableArrayList(Arrays.asList(actionChoices)));
        }
        for (ComboBox box : regionBoxes) {
            box.setItems(FXCollections.observableArrayList(Arrays.asList(regionsID)));
        }
    }

    @FXML
    public void validationButtonClick() {
        System.out.println("Bouton click");
        for (int i = 0; i < 6; i++) {
            ComboBox action = choiceBoxes.get(i);
            if(action.getSelectionModel().getSelectedItem().equals("Delegation")){
                ComboBox region = regionBoxes.get(i);
                System.out.println(action.getId() + " : Delegation => Region n°" + region.getSelectionModel().getSelectedItem());
            }
            else {
                System.out.println(action.getId() + " : " + action.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    public void comboBoxValueChanged(Event evt) {
        ComboBox actionBox = ((ComboBox) evt.getSource());
        System.out.println("ID ComboBox = " + actionBox.getId() + " => " + actionBox.getSelectionModel().getSelectedItem());

        switch (actionBox.getId()) {
            case "action1":
                if (actionBox.getSelectionModel().getSelectedItem().equals("Delegation")) {
                    region1.setDisable(false);
                } else {
                    region1.setDisable(true);
                }
                break;
            case "action2":
                if (actionBox.getSelectionModel().getSelectedItem().equals("Delegation")) {
                    region2.setDisable(false);
                } else {
                    region2.setDisable(true);
                }
                break;
            case "action3":
                if (actionBox.getSelectionModel().getSelectedItem().equals("Delegation")) {
                    region3.setDisable(false);
                } else {
                    region3.setDisable(true);
                }
                break;
            case "action4":
                if (actionBox.getSelectionModel().getSelectedItem().equals("Delegation")) {
                    region4.setDisable(false);
                } else {
                    region4.setDisable(true);
                }
                break;
            case "action5":
                if (actionBox.getSelectionModel().getSelectedItem().equals("Delegation")) {
                    region5.setDisable(false);
                } else {
                    region5.setDisable(true);
                }
                break;
            case "action6":
                if (actionBox.getSelectionModel().getSelectedItem().equals("Delegation")) {
                    region6.setDisable(false);
                } else {
                    region6.setDisable(true);
                }
                break;
        }
    }
}
