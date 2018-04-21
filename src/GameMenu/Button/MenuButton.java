package GameMenu.Button;

import javafx.beans.NamedArg;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class MenuButton extends Label {
    public MenuButton(@NamedArg("text") String text, @NamedArg("onMouseClick") EventHandler<MouseEvent> onMouseClick) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuButtonView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setText(text);
        this.setOnMouseClicked(onMouseClick);
    }
}
