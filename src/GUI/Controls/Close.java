package GUI.Controls;

import GUI.Modules.Module;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Close extends ButtonControl {
    private Module context;
    private Class clazz;

    /**
     * TODO: finish JavaDoc
     *
     * @param context
     */
    public Close(Module context, Class clazz) {
        super(new Image(Module.class.getResourceAsStream("/images/close.png")));
        myImage.setFitHeight(10.0);
        myImage.setFitWidth(10.0);
        this.myButton.setGraphic(myImage);
        this.myButton.getStyleClass().add("close-button");
        this.context = context;
        this.clazz = clazz;
    }

    @Override
    protected EventHandler<MouseEvent> action() {
        return event -> context.close(clazz);
    }
}
