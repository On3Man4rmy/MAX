package GameWindow;

import App.App;
import GameBoard.GameBoard;
import GameMenu.Button.MenuButton;
import GameMenu.GameMenu;
import GameMenu.Label.MenuLabel;
import PlayerScore.PlayerScore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import util.KeyboardEventPublisher;

import java.io.IOException;

/**
 * Controller für GameWindow.fxml
 * @author  Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 19/04/2018
 */
public class GameWindow extends AnchorPane {
    KeyboardEventPublisher keyboardEventPublisher; //erzeugt KeyBoardEventPublisher
    Stage stage;
    MAX model;   //erzeugt MAXGame
    App app;
    @FXML
    PlayerScore playerScore1;
    @FXML
    PlayerScore playerScore2;
    @FXML
    GridPane rootLayout;
    @FXML
    GameMenu gameMenu;
    @FXML
    GameBoard gameBoardController;
    MenuButton winnerInformation = new MenuButton("Unentschieden", event -> actionCloseWindow());
    Node[] gameEndMenuNodes = {
            winnerInformation,
            new MenuButton("Retry?", event -> actionRestartGame()),
    };
    Node[] standardMenuNodes = {
            new MenuButton("Load", event -> actionLoadGame()),
            new MenuButton("Save", event -> actionSaveGame()),
            new MenuButton("New Game", event -> actionNewGame()),
    };

    public GameWindow(Stage stage, App app){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameWindowView.fxml"));
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
        this.model = game;

        initializeGameBoard();
        initalizePlayers();
        initializeKeyBoardEvents();
        initializeGameMenu();
    }

    private void initializeKeyBoardEvents() {
        keyboardEventPublisher = new KeyboardEventPublisher();
        keyboardEventPublisher.subscribe(event -> {
            switch (event.getCode()) {
                case UP:    model.enterAction(Actions.UP); break;
                case DOWN:  model.enterAction(Actions.DOWN); break;
                case LEFT:  model.enterAction(Actions.LEFT); break;
                case RIGHT: model.enterAction(Actions.RIGHT); break;
                case Q: model.enterAction(Actions.QUIT); break;
                case S: model.enterAction(Actions.SAVE); break;
                case L: model.enterAction(Actions.LOAD); break;
                case R: actionRestartGame(); break;
                case ESCAPE:
                    if(!model.isGameDoneProperty().getValue()) {
                        toogleMenuVisibility();
                    }
                    break;
            }
        });
    }
    private void initializeGameMenu() {
        gameMenu.setChildren(standardMenuNodes);
        gameMenu.toFront();
        model.isGameDoneProperty().addListener((observable, oldValue, isGameDone) -> {
            if(isGameDone) {
                announceGameEnd();
            } else {
                gameMenu.setChildren(standardMenuNodes);
            }
        });
    }
    private void initializeGameBoard(){
        gameBoardController.setBoard(model.board);
    }

    private void initalizePlayers(){
        playerScore1.bindPlayer(model.getPlayer1());
        playerScore2.bindPlayer(model.getPlayer2());
    }

    public void announceGameEnd() {
        Player winner = model.getWinner();
        if(winner != null) {
            winnerInformation.setText(winner.getName() + " wins!");
            winnerInformation.setTextFill(winner.getFillProperty().getValue());
        } else  {
            winnerInformation.setText("Draw -.-\"");
        }

        gameMenu.setChildren(gameEndMenuNodes);
        toogleMenuVisibility();
    }

    public void toogleMenuVisibility() {
        if(gameMenu.isVisible()) {
            gameMenu.setVisible(false);
            rootLayout.setEffect(null);
        } else {
            gameMenu.setVisible(true);
            rootLayout.setEffect(new GaussianBlur());
        }
    }
    public void actionNewGame() {
        app.openNewGame();
    }

    public void actionCloseWindow() {
        stage.close();
    }

    public void actionSaveGame() {
        model.enterAction(Actions.SAVE);
        System.out.println("Save Game");
        toogleMenuVisibility();
    }
    public void actionRestartGame() {
        app.restartGame(stage);
    }

    public void actionLoadGame() {
        System.out.println("Load Game");
        model.enterAction(Actions.LOAD);
        toogleMenuVisibility();
    }

    public void setApp (App app) {
        this.app = app;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public KeyboardEventPublisher getKeyboardEventPublisher() {
        return keyboardEventPublisher;
    }
}
