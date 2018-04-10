package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Actions;
import model.MAX;
import model.Player;
import util.KeyboardEventPublisher;

public class RootLayoutController {
    @FXML
    GridPane rootLayout;
    @FXML
    GridPane playerScores;
    public KeyboardEventPublisher keyboardEventPublisher=new KeyboardEventPublisher();
    MAX game;


    public void initialize() {
        GridPane playerMap = new GridPane();
        game = new MAX();
        Player player1 = game.player1;
        Player player2 = game.player2;

        PlayerScoreController playerScore1 = new PlayerScoreController(player1);
        PlayerScoreController playerScore2 = new PlayerScoreController(player2);
        playerScore1.setAlignment(Pos.BOTTOM_LEFT);
        playerScore2.setAlignment(Pos.TOP_LEFT);


        for(int i = 0; i < 8; i++) {
            RowConstraints row = new RowConstraints();
            ColumnConstraints column = new ColumnConstraints();
            row.setVgrow(Priority.SOMETIMES);
            column.setHgrow(Priority.SOMETIMES);
            playerMap.getRowConstraints().add(row);
            playerMap.getColumnConstraints().add(column);

            for(int j = 0; j < 8; j++) {
                FractionController fractionView = new FractionController(game.board.getBoardElements()[i][j]);
                playerMap.add(fractionView, i, j);
            }
        }


        keyboardEventPublisher.subscribe(event -> {
            switch (event.getCode()) {
                case UP:    game.enterAction(Actions.UP); break;
                case DOWN:  game.enterAction(Actions.DOWN); break;
                case LEFT:  game.enterAction(Actions.LEFT); break;
                case RIGHT: game.enterAction(Actions.RIGHT); break;
                case Q: game.enterAction(Actions.QUIT); break;
            }
        });

        playerScores.add(playerScore1, 0,0);
        playerScores.add(playerScore2, 0,1);
        rootLayout.add(playerMap, 0, 1);
    }

    public void setStage(Stage stage) {
        game.setStage(stage);
    }
}
