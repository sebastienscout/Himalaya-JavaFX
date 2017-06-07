package ihm;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
        stage.setTitle("Himalaya - Le jeu");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        PlayConsole play = new PlayConsole();
//        play.addEvolAI("Blanc");
//        play.addRandomAI("Bleu");
//        play.addRandomAI("Vert");
//        play.run();

        launch(args);
    }

}
