package ihm;

import core.Board;
import core.Player;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class InitFXMLController implements Initializable {

    @FXML
    private TextField p1TextField, p2TextField, p3TextField, p4TextField;

    @FXML
    private Label p1Label, p2Label, p3Label, p4Label;

    private Board board;
    private ArrayList<TextField> tf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tf = new ArrayList<>();
    }

    @FXML
    private void validateButton(Event evt) {

        boolean allFieldsOK = true;

        if (p1TextField.getText().isEmpty() || p2TextField.getText().isEmpty() || p3TextField.getText().isEmpty()) {
            allFieldsOK = false;
        }
        if (board.getPlayers().size() == 4 && p1TextField.getText().isEmpty()) {
            allFieldsOK = false;
        }

        // Vérification villages différents
        for (TextField textField : tf) {
            for (TextField textField1 : tf) {
                if(!textField.equals(textField1) && textField.getText().equals(textField1.getText())){
                    allFieldsOK = false;
                }
            }
        }

        if (allFieldsOK) {

            board.getPlayers().get(0).setPosition(board.getVillageById(Integer.parseInt(p1TextField.getText())));
            board.getPlayers().get(1).setPosition(board.getVillageById(Integer.parseInt(p2TextField.getText())));
            board.getPlayers().get(2).setPosition(board.getVillageById(Integer.parseInt(p3TextField.getText())));
            if (board.getPlayers().size() == 4) {
                board.getPlayers().get(3).setPosition(board.getVillageById(Integer.parseInt(p4TextField.getText())));
            }

            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.close();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pas de précipitation !");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez choisir une position différente pour chaque joueur !");
            alert.showAndWait();
        }
    }

    public void setPlayerInitialPosition(Player player, TextField tf) {
        int villageChoice = player.calculateInitPosition();
        if (villageChoice > 0 && villageChoice <= 20) {
            board.addChoiceVillage(villageChoice);
            tf.setText(Integer.toString(villageChoice));
        }
    }

    void lauch(Board b) {

        board = b;

        p1Label.setText("Joueur " + board.getPlayers().get(0).getColor());
        p2Label.setText("Joueur " + board.getPlayers().get(1).getColor());
        p3Label.setText("Joueur " + board.getPlayers().get(2).getColor());
        setPlayerInitialPosition(board.getPlayers().get(0), p1TextField);
        setPlayerInitialPosition(board.getPlayers().get(1), p2TextField);
        setPlayerInitialPosition(board.getPlayers().get(2), p3TextField);

        if (board.getPlayers().size() == 4) {
            p4Label.setText("Joueur " + board.getPlayers().get(3).getColor());
            setPlayerInitialPosition(board.getPlayers().get(3), p4TextField);
        } else {
            p4Label.setVisible(false);
            p4TextField.setVisible(false);
        }

        tf.add(p1TextField);
        tf.add(p2TextField);
        tf.add(p3TextField);
        tf.add(p4TextField);

        for (TextField textField : tf) {
            textField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent inputevent) {
                    if (!inputevent.getCharacter().matches("\\d")) {
                        inputevent.consume();
                    }
                }
            });
        }
    }

}
