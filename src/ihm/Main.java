package ihm;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author sebastien
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        BorderPane border = new BorderPane();
        

       // border.setCenter(addGridPane());
       // border.setRight(addFlowPane());

        Button btn = new Button();
        btn.setText("Dire 'Bonjour'");

        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Bonjour !");
        });

        StackPane pane = new StackPane();

        float width = 1500;
        float height = 920;

        Image background = new Image("file:resources/board.jpg", width, height, true, true);
        ImageView iv = new ImageView(background);

        pane.getChildren().add(iv);
        pane.getChildren().add(btn);

        Scene scene = new Scene(pane, width, height);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
