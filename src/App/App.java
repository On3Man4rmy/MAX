package App;

import GameWindow.GameWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        //Erzeugt GameWindow über Rootlayout.fxml
        Stage stage = new Stage();
        GameWindow game = new GameWindow(stage, this);
        Scene scene = new Scene(game, 500, 500);
        scene.setOnKeyPressed(game.getKeyboardEventPublisher()::publish);  //onkeypressed wird die funktion publish ausgeführt
        stage.setScene(scene);
        stage.setTitle("MAX Game");
        stage.show();
    }

    public void restartGame(Stage stage) {
        GameWindow game = new GameWindow(stage, this);
        Scene scene = new Scene(game, 500, 500);
        scene.setOnKeyPressed(game.getKeyboardEventPublisher()::publish);  //onkeypressed wird die funktion publish ausgeführt
        stage.setScene(scene);
    }
}
