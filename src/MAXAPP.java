import controller.RootLayoutController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import util.KeyboardEventPublisher;

import java.io.IOException;
/**
Klasse dient zum Starten des spiels, erzeugt Fenster, von welchem mehrer Spielläufe gestartet werden können

 */
public class MAXAPP extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button bt = new Button("Start Game");
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Erzeugt RootLayoutController über Rootlayout.fxml
                Stage stage = new Stage();
                Parent root=null;
                RootLayoutController controller=null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(
                            "view/RootLayout.fxml"));
                    root =loader.load();
                    controller=loader.getController();  //gibt zugang zu RootLayoutCOntroller objekt
                    controller.setStage(stage);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene = new Scene(root, 500, 500);
                scene.setOnKeyPressed(controller.keyboardEventPublisher::publish);  //onkeypressed wird die funktion publish ausgeführt
                stage.setScene(scene);
                stage.setTitle("MAX Game");
                stage.show();
            }
        });
        Scene scene = new Scene(bt, 200, 250);
        primaryStage.setTitle("Start Game"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage



    }


    public static void main(String[] args) {
        launch(args);
    }
}
