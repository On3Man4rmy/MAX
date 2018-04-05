import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.KeyboardEventPublisher;

public class MAXAPP extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/RootLayout.fxml"));
        Scene scene = new Scene(root, 500, 500);
        scene.setOnKeyPressed(KeyboardEventPublisher::publish);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MAX Game");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
