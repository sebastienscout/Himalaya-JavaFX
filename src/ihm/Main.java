package ihm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
            Scene scene = new Scene(root,Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PlayConsole play = new PlayConsole();
        //play.addPlayer("Rouge", 1);
        play.addAI("Rouge", 1);
        play.addAI("Bleu", 1);
        play.addAI("Vert", 1);
        play.run();
        
        //launch(args);
    }

}
