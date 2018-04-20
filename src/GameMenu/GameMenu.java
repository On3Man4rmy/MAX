package GameMenu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class GameMenu extends AnchorPane {
    @FXML
    VBox vboxMenu;
    public GameMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameMenuView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setChildren(Node[] nodes) {
        vboxMenu.getChildren().clear();
        for(Node node : nodes) {
            vboxMenu.getChildren().add(node);
        }
    }
}
