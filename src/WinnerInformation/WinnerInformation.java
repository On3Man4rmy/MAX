package WinnerInformation;

import javafx.beans.NamedArg;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import java.io.IOException;

public class WinnerInformation extends VBox {
    @FXML
    Label lblText;
    @FXML
    Label lblRetry;
    public WinnerInformation() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WinnerInformationView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRetryEventHandler(EventHandler<MouseEvent> eventHandler) {
        lblRetry.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public void setText(String text) {
        this.lblText.setText(text);
    }

    public void setTextFill(Paint color) {
        this.lblText.setTextFill(color);
    }
}
