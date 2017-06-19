package ihm;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Main extends Application {

    public static String screenMenuID = "menu";
    public static String screenMenuFile = "MenuFXML.fxml";
    public static String screenMainID = "main";
    public static String screenMainFile = "MainFXML.fxml";

    @Override
    public void start(Stage stage) {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(screenMenuID, screenMenuFile);
        mainContainer.loadScreen(screenMainID, screenMainFile);

        mainContainer.setScreen(screenMenuID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
        
        //Music Theme Himalya
        File filestring = new File("src/resources/music/music_menu.mp3");
        Media file = new Media(filestring.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(file);
        mediaPlayer.autoPlayProperty();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        PlayConsole play = new PlayConsole();
//        play.addPlayer("Michel", 1);
//        play.run();

        launch(args);
    }

}
