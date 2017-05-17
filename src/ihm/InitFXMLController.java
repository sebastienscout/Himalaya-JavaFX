package ihm;

import core.Board;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        board.getPlayers().get(0).move(board.getVillageById(Integer.parseInt(p1TextField.getText())));
        board.getPlayers().get(1).move(board.getVillageById(Integer.parseInt(p2TextField.getText())));
        board.getPlayers().get(2).move(board.getVillageById(Integer.parseInt(p3TextField.getText())));
        if (board.getPlayers().size() == 4) {
            board.getPlayers().get(3).move(board.getVillageById(Integer.parseInt(p4TextField.getText())));
        }

        Stage stage = (Stage) ((Node) evt.getSource()).getScene().getWindow();
        stage.close();
    }

    void lauch(Board b) {
        
        board = b;
        
        System.out.println("Nb player : " + board.getPlayers().size());
        p1Label.setText("Joueur " + board.getPlayers().get(0).getColor());
        p2Label.setText("Joueur " + board.getPlayers().get(1).getColor());
        p3Label.setText("Joueur " + board.getPlayers().get(2).getColor());
        if (board.getPlayers().size() == 4) {
            p4Label.setText("Joueur " + board.getPlayers().get(3).getColor());
        }
    }

}
