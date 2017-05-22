package ihm;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ScreensController extends StackPane {

    private HashMap<String, Node> screens = new HashMap<>();
    private FXMLLoader myLoader;

    public ScreensController() {
        super();
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    //Charge le fichier FXML et l'ajoute à la collection
    public boolean loadScreen(String name, String resource) {

        try {
            myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ScreensController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean setScreen(final String name) {
        ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
        if (screens.get(name) != null) { //Screen loaded

            if (!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screens.get(name));
                myScreenControler.initScreen();//Event à l'affichage du screen
            } else {
                getChildren().add(screens.get(name));
            }
            return true;
        } else {
            System.out.println("Impossible de charger le screen");
            return false;
        }

    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Le screen n'existe pas");
            return false;
        } else {
            return true;
        }
    }

}
