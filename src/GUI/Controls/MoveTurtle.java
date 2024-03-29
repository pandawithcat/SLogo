package GUI.Controls;

import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;

import GUI.FrontendController;

public class MoveTurtle extends MenuButtonControl{
    private ResourceBundle languageResourceBundle;

    /**
     * @param context
     */
    public MoveTurtle(FrontendController context) {
        super(new Image(FrontendController.class.getResourceAsStream("/images/moveTurtle.png")), "/buttonProperties/TurtleMovements");
        languageResourceBundle = ResourceBundle.getBundle("/languageProperties/English");
        this.myContext = context;
    }

    @Override
    protected EventHandler<ActionEvent> action() {
        return event -> {
            MenuItem mItem = (MenuItem) event.getSource();
            String command = languageResourceBundle.getString(mItem.getText());
            String[] commands = command.split("\\|");
            myContext.sendCommandString(commands[1] + " 10");
        };
    }

    public void setResourceBundle(String myLanguage) {
        languageResourceBundle = ResourceBundle.getBundle("/languageProperties/" + myLanguage);
    }
}
