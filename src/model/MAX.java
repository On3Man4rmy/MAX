package model;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.MathUtil;

import java.io.Serializable;


/**
 * MAX Game Hauptklasse
 *
 * @author Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 3.0 11/7/2018
 */
public class MAX implements Serializable {

    // Variables for playing ground properties
    public static final int END_X = 8;  //Definiert die Ränder
    public static final int END_Y = 8;
    public static final int START_X = 1;
    public static final int START_Y = 1;
    public static final Fraction SCORE_TARGET = new Fraction(80, 1); //Zile sind 80 punkte
    public static Fraction sum = new Fraction(0, 1);  //Zählt die Punkte die noch übrig sind, bei 0 uentschieden
    public Matrix<Fraction> mat = initMatrix(); //inititalisert Matrix mit Fraktions
    public Player player1 = new Player(new Position(4, 4), "RED", "R", Color.web("#e00202")); //Beide spieler definiert
    public Player player2 = new Player(new Position(5, 5), "YELLOW", "Y", Color.web("#fec500"));
    transient Player currentPlayer = player1; //Derzeiiger Spieler
    transient Player otherPlayer = player2;
    transient public Board board = new Board();   //Spielbrett
    transient public boolean changePlayer = true;  //Varaible, damit SPeiler sich nich wechselt wenn gegen wand oder anderen Spieler laufen
    transient private Stage stage;

    /**
     * Konstruktor, Board wird zum ersten Mal kreiert
     */
    public MAX() {
        board.update(player1, player2, currentPlayer, mat);
        currentPlayer.setIsSelectedProperty(true);
    }

    public void enterAction(Actions action) {  //Tasteneingabe

        /**
         * Bei eingabe von Pfeiltasten wird überprüft ob Wand oder anderer SPeiler im weg ist
         */
        if (action == Actions.UP
                && currentPlayer.position.y > START_Y
                && !isSamePosition(currentPlayer.peekDirection(Actions.UP), otherPlayer)) {
            currentPlayer.moveDirection(Actions.UP);
        } else if (action == Actions.LEFT
                && currentPlayer.position.x > START_X
                && !isSamePosition(currentPlayer.peekDirection(Actions.LEFT), otherPlayer)) {
            currentPlayer.moveDirection(Actions.LEFT);

        } else if (action == Actions.DOWN
                && currentPlayer.position.y < END_Y
                && !isSamePosition(currentPlayer.peekDirection(Actions.DOWN), otherPlayer)) {
            currentPlayer.moveDirection(Actions.DOWN);

        } else if (action == Actions.RIGHT
                && currentPlayer.position.x < END_X
                && !isSamePosition(currentPlayer.peekDirection(Actions.RIGHT), otherPlayer)) {
            currentPlayer.moveDirection(Actions.RIGHT);
        } else if (action == Actions.QUIT) {  //Bei Q wird spiel beendet
            EndGame end = new EndGame(spielstand());
            end.endgame();
            stage.hide();

        } else if (action == Actions.LOAD) {
            SaveLoadGame load = new SaveLoadGame(player1, player2, this, currentPlayer);
            load.loadGame(this);
            changePlayer = false;
            System.out.println("game loaded");
        } else if (action == Actions.SAVE) {
            SaveLoadGame save = new SaveLoadGame(player1, player2, this, currentPlayer);
            save.saveGame();
            changePlayer = false;
            System.out.println("Game saved");
        } else {
            changePlayer = false;
        }    //Wenn nichts zutrifft war entweder eingabe falsch, oder weg in Wand, kein Spielerwechsel bis korekkt eingabe

        // Update player score
        currentPlayer.setScore(currentPlayer.getScore().add(mat.getValue(currentPlayer.position.x, currentPlayer.position.y)));
        // Update score of remaining playing field points
        sum = sum.subtract(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
        // When player arrives field set field value to 0
        mat.setValue(currentPlayer.position.x, currentPlayer.position.y, Fraction.ZERO);

        // Rotate current player
        if (changePlayer) {
            if (currentPlayer == player1) {
                currentPlayer = player2;
                otherPlayer = player1;
            } else {
                currentPlayer = player1;
                otherPlayer = player2;
            }
            currentPlayer.setIsSelectedProperty(true);
            otherPlayer.setIsSelectedProperty(false);
        } else {
            changePlayer = true;
        }
        // Announce winner
        if (player1.getScore().compareTo(SCORE_TARGET) >= 1 ||
                player2.getScore().compareTo(SCORE_TARGET) >= 1 ||
                sum.compareTo(Fraction.ZERO) <= 0) {

            EndGame end = new EndGame(spielstand());
            end.endgame();
            stage.hide();
        }


        board.update(player1, player2, currentPlayer, mat);       //Spielbrett updaten
    }

    public String spielstand() {            //Erzeugt String mit Spielstand
        String ergebniss = "";

        if (player1.getScore().compareTo(SCORE_TARGET) >= 1) {
            ergebniss += (player1.getName() + " wins!\n");
        } else if (player2.getScore().compareTo(SCORE_TARGET) >= 1) {
            ergebniss += (player2.getName() + " wins!\n");
        }
        // Ende nicht durch Erreichen der Maxpunktzahl, entweder durch leeres Spielfeld, oder abbruch
        else {
            int i = player1.getScore().compareTo(player2.getScore()); //falls beide Gleichviele Punkte
            if (i == 0) ergebniss += ("Unentschieden\n");
            else {          //falls einer mehr Punkte hat als der andere
                ergebniss += (i == 1 ? player1.getName() + " wins!" : player2.getName() + " wins!\n");
            }
        }

        return ergebniss;
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

        // Output sum for debugging purposes
        //System.out.println(mat.reduce((acc, curr) -> acc.add(curr), Fraction.DEFAULT).intValue());
        return mat;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    //Updates the new Values when laoding a new game
    public void loadnewValues(Player player1, Player player2, Matrix<Fraction> mat) {

        this.player1 = player1;
        this.player2 = player2;
        this.mat = mat;
        currentPlayer = player1;
        otherPlayer = player2;
        currentPlayer.setIsSelectedProperty(true);
        otherPlayer.setIsSelectedProperty(false);
        board.update(player1, player2, currentPlayer, mat);

    }
}

