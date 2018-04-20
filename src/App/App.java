package App;

import GameWindow.GamePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MAX;

/**
 * Klasse dient zum Starten des spiels, erzeugt Fenster,
 * von welchem mehrer Spielläufe gestartet werden können
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 30.03.2018

 */
public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        openNewGame();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void openNewGame() {
        //Erzeugt GamePane über Rootlayout.fxml
        Stage stage = new Stage();
        GamePane game = new GamePane(stage, this);
        Scene scene = new Scene(game, 500, 500);
        scene.setOnKeyPressed(game.keyboardEventPublisher::publish);  //onkeypressed wird die funktion publish ausgeführt
        stage.setScene(scene);
        stage.setTitle("MAX Game");
        stage.show();
    }

    public void restartGame(Stage stage) {
        GamePane game = new GamePane(stage, this);
        Scene scene = new Scene(game, 500, 500);
        scene.setOnKeyPressed(game.keyboardEventPublisher::publish);  //onkeypressed wird die funktion publish ausgeführt
        stage.setScene(scene);
    }
}
