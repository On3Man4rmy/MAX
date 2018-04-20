package GameWindow;

import App.App;
import GameMenu.Button.MenuButton;
import GameMenu.GameMenu;
import GameMenu.Label.MenuLabel;
import Fraction.FractionController;
import PlayerScoreView.PlayerScore;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.*;
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
    PlayerScore playerScore1;
    @FXML
    PlayerScore playerScore2;
    @FXML
    GameMenu gameMenu;
    Node[] standardMenuNodes = {
            new MenuButton("Load", event -> actionLoadGame()),
            new MenuButton("Save", event -> actionSaveGame()),
            new MenuButton("New Game", event -> actionNewGame()),
    };
    MenuLabel winnerInformation = new MenuLabel("Unentschieden");
    Node[] gameEndMenuNodes = {
            winnerInformation,
            new MenuButton("Retry?", event -> actionRestartGame()),
    };
    public KeyboardEventPublisher keyboardEventPublisher; //erzeugt KeyBoardEventPublisher
    public MAX model;   //erzeugt MAXGame
    App app;
    Stage stage;
    GridPane gameBoard;

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
        this.model = new MAX(this);

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
        model.isGameDoneProperty().addListener(observable -> this.announceGameEnd());
    }
    private void initializeGameBoard(){
        /*
        model.getPlayer1Property().addListener(observable -> {
            Player player = (Player) observable;
            int posX = player.position.x;
            int posY = player.position.y;
            StringProperty name = player.getShortNameProperty();
            ObjectProperty<Paint> paint = player.getFillProperty();
            gameBoardController.setBoardElement(name, paint, posX, posY);
        });
        model.getPlayer2Property().addListener(observable -> {
            Player player = (Player) observable;
            int posX = player.position.x;
            int posY = player.position.y;
            StringProperty name = player.getShortNameProperty();
            ObjectProperty<Paint> paint = player.getFillProperty();
            gameBoardController.setBoardElement(name, paint, posX, posY);
        });
        model.getMatProperty().addListener(observable -> {
            Matrix<Fraction> matrix = (Matrix<Fraction>) observable;
            for(int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    StringProperty name = matrix.getValue(i, j).getToStringProperty();
                    gameBoardController.setBoardElement(name, null, i, j);
                }
            }
        });*/
        rootLayout.getChildren().remove(gameBoard);
        gameBoard = new GridPane();
        for(int i = 0; i < 8; i++) {
            RowConstraints row = new RowConstraints();
            ColumnConstraints column = new ColumnConstraints();
            row.setVgrow(Priority.SOMETIMES);
            column.setHgrow(Priority.SOMETIMES);
            gameBoard.getRowConstraints().add(row);
            gameBoard.getColumnConstraints().add(column);

            for(int j = 0; j < 8; j++) {
                FractionController fractionView = new FractionController(model.board.getBoardElements()[i][j]);
                gameBoard.add(fractionView, i, j);
            }
        }

        rootLayout.add(gameBoard, 0, 1);
    }
    private void initalizePlayers(){
        playerScore1.bindPlayer(model.getPlayer1());
        playerScore2.bindPlayer(model.getPlayer2());
    }

    public void setApp (App app) {
        this.app = app;
    }
    public void setStage(Stage stage) {
        model.setStage(stage);
        this.stage = stage;
    }

    public void announceGameEnd() {
        Player winner = model.getWinner();
        if(winner != null) {
            winnerInformation.setText(winner.getName() + " gewinnt!");
            winnerInformation.setTextFill(winner.fillProperty.getValue());
        } else  {
            winnerInformation.setText("Untenschieden");
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
}
