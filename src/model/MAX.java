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
    public static final int END_X = 8;
    public static final int END_Y = 8;
    public static final int START_X = 1;
    public static final int START_Y = 1;
    public static final Fraction SCORE_TARGET = new Fraction(80, 1);
    public static Fraction sum = new Fraction(0,1);
    public Matrix<Fraction> mat = initMatrix();
    public Player player1 = new Player(new Position(4, 4), "RED", "R", Color.web("#e00202"));
    public Player player2 = new Player(new Position(5, 5), "YELLOW", "Y", Color.web("#fec500"));
    Player currentPlayer = player1;
    Player otherPlayer = player2;
    public Board board = new Board();
    public boolean changePlayer =true;

    public MAX() {
        board.update(player1, player2, currentPlayer, mat);
        currentPlayer.setIsSelectedProperty(true);
    }

    public void enterAction(Actions action) {

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
        else if(action == Actions.QUIT){
            EndGame end=new EndGame(spielstand());
            end.endgame();

        }
        else{changePlayer=false;}

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
        if (player1.getScore().compareTo(SCORE_TARGET) >= 1) {
            System.out.println(player1.getName() + " wins!" );
        }
        if (player2.getScore().compareTo(SCORE_TARGET) >= 1) {
            System.out.println(player2.getName() + " wins!");
        }
        // Announce tie
        if(sum.equals(Fraction.ZERO)){
            int i=player1.getScore().compareTo(player2.getScore());
            if(i==0) System.out.println("Unentschieden");
            else {
                System.out.println(i == 1 ? player1.getName() + " wins!" : player2.getName() + " wins!");
            }
        }

        board.update(player1, player2, currentPlayer, mat);
    }

    public String spielstand() {
        String ergebniss = "";

        if (player1.getScore().compareTo(SCORE_TARGET) >= 1) {
            ergebniss += (player1.getName() + " wins!\n");
        }
        if (player2.getScore().compareTo(SCORE_TARGET) >= 1) {
            ergebniss += (player2.getName() + " wins!\n");
        }
        // Announce tie
        if (sum.equals(Fraction.ZERO)) {
            int i = player1.getScore().compareTo(player2.getScore());
            if (i == 0) System.out.println("Unentschieden");
            else {
                ergebniss += (i == 1 ? player1.getName() + " wins!" : player2.getName() + " wins!\n");
            }
        }

        return ergebniss;
    }
    public void run(){
               launch();
    }
    // Check if players have the same position
    public static Boolean isSamePosition(Player p1, Player p2) {
        return p1.position.equals(p2.position);
    }

    // Create Fraction with random (more or less) value
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

