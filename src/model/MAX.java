package model;

import javafx.scene.paint.Color;
import util.MathUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static javafx.application.Application.launch;

/**
 * MAX Game
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 2.0 08.01.2018
 */

public class MAX {

    // Variables for playing ground properties
    public static final int END_X = 8;  //Definiert die Ränder
    public static final int END_Y = 8;
    public static final int START_X = 1;
    public static final int START_Y = 1;
    public static final Fraction SCORE_TARGET = new Fraction(80, 1); //Zile sind 80 punkte
    public static Fraction sum = new Fraction(0,1);  //Zählt die Punkte die noch übrig sind, bei 0 uentschieden
    public Matrix<Fraction> mat = initMatrix(); //inititalisert Matrix mit Fraktions
    public Player player1 = new Player(new Position(4, 4), "RED", "R", Color.web("#e00202")); //Beide spieler definiert
    public Player player2 = new Player(new Position(5, 5), "YELLOW", "Y", Color.web("#fec500"));
    Player currentPlayer = player1; //Derzeiiger Spieler
    Player otherPlayer = player2;
    public Board board = new Board();   //Spielbrett
    public boolean changePlayer =true;  //Varaible, damit SPeiler sich nich wechselt wenn gegen wand oder anderen Spieler laufen

    /**
     * Konstruktor, Board wird zum ersten Mal kreiert
     *
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
                && !isSamePosition(currentPlayer.peekDirection(Direction.UP), otherPlayer)) {
            currentPlayer.moveDirection(Direction.UP);
        }
        else if (action == Actions.LEFT
                && currentPlayer.position.x > START_X
                && !isSamePosition(currentPlayer.peekDirection(Direction.LEFT), otherPlayer)) {
            currentPlayer.moveDirection(Direction.LEFT);
        }
        else if (action == Actions.DOWN
                && currentPlayer.position.y < END_Y
                && !isSamePosition(currentPlayer.peekDirection(Direction.DOWN), otherPlayer)) {
            currentPlayer.moveDirection(Direction.DOWN);
        }
       else if (action == Actions.RIGHT
                && currentPlayer.position.x < END_X
                && !isSamePosition(currentPlayer.peekDirection(Direction.RIGHT), otherPlayer)) {
            currentPlayer.moveDirection(Direction.RIGHT);
        }
        else if(action == Actions.QUIT){  //Bei Q wird spiel beendet
            EndGame end=new EndGame(spielstand());
            end.endgame();

        }
        else{changePlayer=false;}    //Wenn nichts zutrifft war entweder eingabe falsch, oder weg in Wand, kein Spielerwechsel bis korekkt eingabe

        // Update player score
        currentPlayer.setScore(currentPlayer.getScore().add(mat.getValue(currentPlayer.position.x, currentPlayer.position.y)));
        // Update score of remaining playing field points
        sum = sum.subtract(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
        // When player arrives field set field value to 0
        mat.setValue(currentPlayer.position.x, currentPlayer.position.y, Fraction.ZERO);

        // Rotate current player
        if(changePlayer) {
            if (currentPlayer == player1) {
                currentPlayer = player2;
                otherPlayer = player1;
            } else {
                currentPlayer = player1;
                otherPlayer = player2;
            }
            currentPlayer.setIsSelectedProperty(true);
            otherPlayer.setIsSelectedProperty(false);
        }
        else{changePlayer=true;}
        // Announce winner
        if (player1.getScore().compareTo(SCORE_TARGET) >= 1||
                player2.getScore().compareTo(SCORE_TARGET) >= 1||
                sum.equals(Fraction.ZERO)) {
            EndGame end=new EndGame(spielstand());
            end.endgame();
        }
        /*
        if () {
            System.out.println(player2.getName() + " wins!");
        }
        // Announce tie
        if(){
            int i=player1.getScore().compareTo(player2.getScore());
            if(i==0) System.out.println("Unentschieden");
            else {
                System.out.println(i == 1 ? player1.getName() + " wins!" : player2.getName() + " wins!");
            }
        }
        */

        board.update(player1, player2, currentPlayer, mat);       //Spielbrett updaten
    }

    public String spielstand() {            //Erzeugt String mit Spielstand
        String ergebniss = "";

        if (player1.getScore().compareTo(SCORE_TARGET) >= 1) {
            ergebniss += (player1.getName() + " wins!\n");
        }
        else if (player2.getScore().compareTo(SCORE_TARGET) >= 1) {
            ergebniss += (player2.getName() + " wins!\n");
        }
        // Ende nicht durch Erreichen der Maxpunktzahl, entweder durch leeres Spielfeld, oder abbruch
        else  {
            int i = player1.getScore().compareTo(player2.getScore()); //falls beide Gleichviele Punkte
            if (i == 0) System.out.println("Unentschieden");
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
}

