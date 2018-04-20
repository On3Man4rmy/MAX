package GameMenu.Label;

import javafx.beans.NamedArg;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class MenuLabel extends Label {
    public MenuLabel(@NamedArg("text") String text) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuLabelView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setText(text);
    }
}
