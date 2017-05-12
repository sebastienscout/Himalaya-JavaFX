package ihm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Main extends Application {

    public static String screen1ID = "start";
    public static String screen1File = "StartFXML.fxml";
    public static String screen2ID = "menu";
    public static String screen2File = "MenuFXML.fxml";
    public static String screen3ID = "main";
    public static String screen3File = "MainFXML.fxml";

    @Override
    public void start(Stage stage) {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(screen1ID, screen1File);
        mainContainer.loadScreen(screen2ID, screen2File);
        mainContainer.loadScreen(screen3ID, screen3File);

        mainContainer.setScreen(screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root, Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
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
