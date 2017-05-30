package ihm;

import core.Board;
import core.Player;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ScoreFXMLController implements Initializable {

    private Board board;

    @FXML
    private FlowPane ecoFP, relFP, polFP;

    @FXML
    private Text winner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setBoard(Board b) {
        board = b;
        Player winnerPlayer = b.winner();
        for (Player p : board.getPlayers()) {
            Label labelEco = new Label("Joueur " + p.getColor() + " : " + p.getEconomicScore());
            Label labelRel = new Label("Joueur " + p.getColor() + " : " + p.getReligiousScore());
            Label labelPol = new Label("Joueur " + p.getColor() + " : " + p.getPoliticalScore());
            ecoFP.getChildren().add(labelEco);
            relFP.getChildren().add(labelRel);
            polFP.getChildren().add(labelPol);
        }
        
        winner.setText("Joueur " + winnerPlayer.getColor()); 
       
    }

}
