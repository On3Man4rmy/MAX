package controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Actions;
import model.MAX;
import model.Player;
import util.KeyboardEventPublisher;
/**
 * Controller für RootLayout.fxml
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 1.0 30.03.2018
 */
public class RootLayoutController {
    @FXML
    GridPane rootLayout;
    @FXML
    GridPane playerScores;
    @FXML
    VBox menu;
    @FXML
    Parent btnSave;
    @FXML
    Parent btnLoad;
    public KeyboardEventPublisher keyboardEventPublisher=new KeyboardEventPublisher(); //erzeugt KeyBoardEventPublisher
    public MAX game;   //erzeugt MAXGame
    GridPane playerMap = new GridPane();


    public void initialize() {
        loadGame(new MAX());
    }

    public void loadGame(MAX game) {
         playerScores.getChildren().clear();
         rootLayout.getChildren().remove(playerMap);

        playerMap = new GridPane();
        this.game = game;
        Player player1 = game.player1;
        Player player2 = game.player2;
        //erzeugtPlayerScoreCOntrollers
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
                case ESCAPE: toogleMenuVisibility(); break;
                case S: game.enterAction(Actions.SAVE); break;
                case L: game.enterAction(Actions.LOAD); break;
            }
        });

        playerScores.add(playerScore1, 0,0);
        playerScores.add(playerScore2, 0,1);
        rootLayout.add(playerMap, 0, 1);
    }

    public void setStage(Stage stage) {
        game.setStage(stage);
    }

    public void actionSaveGame() {
        game.enterAction(Actions.SAVE);
        System.out.println("Save Game");
        toogleMenuVisibility();
    }

    public void actionLoadGame() {
        System.out.println("Load Game");
        game.enterAction(Actions.LOAD);
        toogleMenuVisibility();
    }

    public void toogleMenuVisibility() {
        if(menu.isVisible()) {
            menu.setVisible(false);
            rootLayout.setEffect(null);
        } else {
            menu.setVisible(true);
            rootLayout.setEffect(new GaussianBlur());
        }
    }
}
