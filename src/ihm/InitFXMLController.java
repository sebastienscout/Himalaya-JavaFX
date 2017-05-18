package ihm;

import core.Board;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void validateButton(Event evt) {

        boolean test = true;

        if (p1TextField.getText().isEmpty() || p2TextField.getText().isEmpty() || p3TextField.getText().isEmpty()) {
            test = false;
        }
        if (board.getPlayers().size() == 4) {
            if (p1TextField.getText().isEmpty()) {
                test = false;
            }
        }

        if (test) {
            board.getPlayers().get(0).move(board.getVillageById(Integer.parseInt(p1TextField.getText())));
            board.getPlayers().get(1).move(board.getVillageById(Integer.parseInt(p2TextField.getText())));
            board.getPlayers().get(2).move(board.getVillageById(Integer.parseInt(p3TextField.getText())));
            if (board.getPlayers().size() == 4) {
                board.getPlayers().get(3).move(board.getVillageById(Integer.parseInt(p4TextField.getText())));
            }

            Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pas de précipitation !");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez choisir une position pour chaque joueur !");
            alert.showAndWait();
        }

    }

    void lauch(Board b) {

        board = b;

        p1Label.setText("Joueur " + board.getPlayers().get(0).getColor());
        p2Label.setText("Joueur " + board.getPlayers().get(1).getColor());
        p3Label.setText("Joueur " + board.getPlayers().get(2).getColor());
        if (board.getPlayers().size() == 4) {
            p4Label.setText("Joueur " + board.getPlayers().get(3).getColor());
        } else {
            p4Label.setVisible(false);
            p4TextField.setVisible(false);
        }

        ArrayList<TextField> tf = new ArrayList<>();
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
