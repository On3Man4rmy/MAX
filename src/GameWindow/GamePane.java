package GameWindow;

import App.App;
import Fraction.FractionController;
import PlayerScoreView.PlayerScore;
import WinnerInformation.WinnerInformation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Actions;
import model.MAX;
import model.Player;
import util.KeyboardEventPublisher;

import java.io.IOException;

/**
 * Controller für GamePane.fxml
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 19/04/2018
 */
public class GamePane extends AnchorPane {
    @FXML
    GridPane rootLayout;
    @FXML
    GridPane playerScores;
    @FXML
    VBox menu;
    public KeyboardEventPublisher keyboardEventPublisher; //erzeugt KeyBoardEventPublisher
    public MAX game;   //erzeugt MAXGame
    GridPane playerMap = new GridPane();
    App app;
    Stage stage;

    public GamePane(Stage stage, App app){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setStage(stage);
        setApp(app);
    }

    public void initialize() {
        loadGame(new MAX(this));
    }

    public void loadGame(MAX game) {
         playerScores.getChildren().clear();
         rootLayout.getChildren().remove(playerMap);

        playerMap = new GridPane();
        this.game = game;
        Player player1 = game.player1;
        Player player2 = game.player2;
        //erzeugtPlayerScoreCOntrollers
        PlayerScore playerScore1 = new PlayerScore(player1);
        PlayerScore playerScore2 = new PlayerScore(player2);
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

        keyboardEventPublisher = new KeyboardEventPublisher();
        keyboardEventPublisher.subscribe(event -> {
            switch (event.getCode()) {
                case UP:    game.enterAction(Actions.UP); break;
                case DOWN:  game.enterAction(Actions.DOWN); break;
                case LEFT:  game.enterAction(Actions.LEFT); break;
                case RIGHT: game.enterAction(Actions.RIGHT); break;
                case Q: game.enterAction(Actions.QUIT); break;
                case S: game.enterAction(Actions.SAVE); break;
                case L: game.enterAction(Actions.LOAD); break;
                case R: actionRestartGame(); break;
                case ESCAPE:
                    if(!game.isGameDoneProperty().getValue()) {
                        toogleMenuVisibility();
                    }
                    break;
            }
        });
        menu.toFront();
        game.isGameDoneProperty().addListener(observable -> this.announceGameEnd());

        playerScores.add(playerScore1, 0,0);
        playerScores.add(playerScore2, 0,1);
        rootLayout.add(playerMap, 0, 1);
    }

    public void setApp (App app) {
        this.app = app;
    }

    public void setStage(Stage stage) {
        game.setStage(stage);
        this.stage = stage;
    }

    public void announceGameEnd() {
        WinnerInformation winnerInformation = new WinnerInformation();
        winnerInformation.addRetryEventHandler(observable -> actionRestartGame());
        Player winner = game.getWinner();
        if(winner != null) {
            winnerInformation.setText(winner.getName() + " gewinnt!");
            winnerInformation.setTextFill(winner.fillProperty.getValue());
        } else  {
            winnerInformation.setText("Untenschieden");
        }
        menu.getChildren().clear();
        menu.getChildren().add(winnerInformation);
        toogleMenuVisibility();
    }

    public void actionNewGame() {
        app.openNewGame();
    }

    public void actionSaveGame() {
        game.enterAction(Actions.SAVE);
        System.out.println("Save Game");
        toogleMenuVisibility();
    }

    public void actionRestartGame() {
        app.restartGame(stage);
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
