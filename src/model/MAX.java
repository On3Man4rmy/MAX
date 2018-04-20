package model;

import GameWindow.GamePane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.MathUtil;

import java.io.File;
import java.io.Serializable;


/**
 * MAX Game Hauptklasse
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 4.0 19/04/2018
 */
public class MAX implements Serializable {

    // Variables for playing ground properties
    public static final int END_X = 8;  //Definiert die Ränder
    public static final int END_Y = 8;
    public static final int START_X = 1;
    public static final int START_Y = 1;
    public static final Fraction SCORE_TARGET = new Fraction(80, 1); //Zile sind 80 punkte
    public static Fraction sum = new Fraction(0, 1);  //Zählt die Punkte die noch übrig sind, bei 0 uentschieden
    private Matrix<Fraction> mat = initMatrix(); //inititalisert Matrix mit Fraktions
    private Player player1 = new Player(new Position(4, 4), "RED", "R", Color.web("#e00202")); //Beide spieler definiert
    private Player player2 = new Player(new Position(5, 5), "YELLOW", "Y", Color.web("#fec500"));
    private transient Player currentPlayer = getPlayer1(); //Derzeiiger Spieler
    private transient Player otherPlayer = getPlayer2();
    transient public Board board = new Board();   //Spielbrett
    transient public boolean changePlayer = true;  //Varaible, damit SPeiler sich nich wechselt wenn gegen wand oder anderen Spieler laufen
    transient private Stage stage;
    transient GamePane controller;
    transient private BooleanProperty isGameDone = new SimpleBooleanProperty();
    public BooleanProperty isGameDoneProperty() {
        return isGameDone;
    }

    /**
     * Konstruktor, Board wird zum ersten Mal kreiert
     */
    public MAX(GamePane controller) {
        board.update(player1, player2, currentPlayer, mat);
        getCurrentPlayer().setIsSelectedProperty(true);
        this.controller = controller;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void enterAction(Actions action) {  //Tasteneingabe
        /**
         * Bei eingabe von Pfeiltasten wird überprüft ob Wand oder anderer SPeiler im weg ist
         */
        if (action == Actions.UP
                && getCurrentPlayer().position.y > START_Y
                && !isSamePosition(getCurrentPlayer().peekDirection(Actions.UP), getOtherPlayer())) {
            getCurrentPlayer().moveDirection(Actions.UP);
        } else if (action == Actions.LEFT
                && getCurrentPlayer().position.x > START_X
                && !isSamePosition(getCurrentPlayer().peekDirection(Actions.LEFT), getOtherPlayer())) {
            getCurrentPlayer().moveDirection(Actions.LEFT);

        } else if (action == Actions.DOWN
                && getCurrentPlayer().position.y < END_Y
                && !isSamePosition(getCurrentPlayer().peekDirection(Actions.DOWN), getOtherPlayer())) {
            getCurrentPlayer().moveDirection(Actions.DOWN);

        } else if (action == Actions.RIGHT
                && getCurrentPlayer().position.x < END_X
                && !isSamePosition(getCurrentPlayer().peekDirection(Actions.RIGHT), getOtherPlayer())) {
            getCurrentPlayer().moveDirection(Actions.RIGHT);
        } else if (action == Actions.QUIT) {  //Bei Q wird spiel beendet
            isGameDone.setValue(true);

            //Bei L eingabe wird der FileLoader feöffnet, und ein Savegame kann gewäählt werden
        } else if (action == Actions.LOAD) {
            Stage stage =new Stage();
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("./"));    //start Directory
            File file= fileChooser.showOpenDialog(stage);
            if(file!=null) {                    //wenn der Loader nicht geschlossen wird
                //load new and update controller
                SaveLoadGame load = new SaveLoadGame(getPlayer1(), getPlayer2(), this, getCurrentPlayer());
                MAX newGame = load.loadGame(file, this);
                controller.loadGame(newGame);

                System.out.println("model loaded");
            }
            changePlayer = false;


        } else if (action == Actions.SAVE) {                //Save model
            SaveLoadGame save = new SaveLoadGame(getPlayer1(), getPlayer2(), this, getCurrentPlayer());
            save.saveGame();
            changePlayer = false;
        } else {
            changePlayer = false;
        }    //Wenn nichts zutrifft war entweder eingabe falsch, oder weg in Wand, kein Spielerwechsel bis korekkt eingabe

        // Update player score
        getCurrentPlayer().setScore(getCurrentPlayer().getScore().add(getMat().getValue(getCurrentPlayer().position.x, getCurrentPlayer().position.y)));
        // Update score of remaining playing field points
        sum = sum.subtract(getMat().getValue(getCurrentPlayer().position.x, getCurrentPlayer().position.y));
        // When player arrives field set field value to 0
        getMat().setValue(getCurrentPlayer().position.x, getCurrentPlayer().position.y, Fraction.ZERO);

        // Rotate current player
        if (changePlayer) {
            if (getCurrentPlayer() == getPlayer1()) {
                setCurrentPlayer(getPlayer2());
                setOtherPlayer(getPlayer1());
            } else {
                setCurrentPlayer(getPlayer1());
                setOtherPlayer(getPlayer2());
            }
            getCurrentPlayer().setIsSelectedProperty(true);
            getOtherPlayer().setIsSelectedProperty(false);
        } else {
            changePlayer = true;
        }
        // Announce winner
        if (getPlayer1().getScore().compareTo(SCORE_TARGET) >= 1 ||
                getPlayer2().getScore().compareTo(SCORE_TARGET) >= 1 ||
                sum.compareTo(Fraction.ZERO) <= 0) {
            isGameDone.setValue(true);
        }


        board.update(getPlayer1(), getPlayer2(), getCurrentPlayer(), getMat());       //Spielbrett updaten
    }


    public Player getWinner() {            //Erzeugt String mit Spielstand
        int i = getPlayer1().getScore().compareTo(getPlayer2().getScore()); //falls beide Gleichviele Punkte
        Player winner = null;
        if (i >= 1) {
            winner = getPlayer1();
        } else if (i <= -1) {
            winner = getPlayer2();
        }
        return winner;
    }

    // Check if players have the same position
    public static Boolean isSamePosition(Player p1, Player p2) {
        return p1.position.equals(p2.position);
    }

    // Create Fraction with random value
    public static Fraction getGameFraction() {
        int minNumerator, maxNumerator, randomNumerator;
        int minDenominator, maxDenominator, randomDenominator;

        minNumerator = 10;
        maxNumerator = 99;
        randomNumerator = MathUtil.randomRange(minNumerator, maxNumerator);

        minDenominator = randomNumerator / 10;
        maxDenominator = randomNumerator;
        randomDenominator = MathUtil.randomRange(minDenominator, maxDenominator);

        Fraction frac = new Fraction(randomNumerator, randomDenominator);
        sum = sum.add(frac);
        return frac;
    }


    // create Matrix representing the playing field
    public static Matrix<Fraction> initMatrix() {
        int rows = END_X - START_X + 1;
        int columns = END_Y - START_Y + 1;

        Matrix<Fraction> mat = new Matrix<>(rows, columns, Fraction.DEFAULT);
        mat.map(MAX::getGameFraction);

        return mat;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    //Updates the new Values when laoding a new model
    public void loadnewValues(Player player1, Player player2, Matrix<Fraction> mat) {
        player1.setIsSelectedProperty(true);
        player2.setIsSelectedProperty(false);
        setCurrentPlayer(player1);
        setOtherPlayer(player2);
        setMat(mat);
        board.update(player1, player2, getCurrentPlayer(), mat);
    }

    public Matrix<Fraction> getMat() {
        return mat;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public void setMat(Matrix<Fraction> mat) {
        this.mat = mat;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }
}

