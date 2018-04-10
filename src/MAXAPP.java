import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.KeyboardEventPublisher;

import java.io.IOException;

public class MAXAPP extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button btOK = new Button("OK");
        btOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("view/RootLayout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 500, 500);
                scene.setOnKeyPressed(root.::publish);
                stage.setScene(scene);
                stage.setTitle("MAX Game");
                stage.show();
            }
        });
        Scene scene = new Scene(btOK, 200, 250);
        primaryStage.setTitle("MyJavaFX"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage



    }


    public static void main(String[] args) {
        launch(args);
    }
}
