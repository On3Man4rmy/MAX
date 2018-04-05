package model;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLOutput;


public class MAX extends Application {
    public static final int END_X = 8;
    public static final int END_Y = 8;
    public static final int START_X = 1;
    public static final int START_Y = 1;
    public static final Fraction SCORE_TARGET = new Fraction(80, 1);
    public static final Fraction ZERO=new Fraction(0,1);
    public static Fraction sum = new Fraction(0, 1);
    Player p1 = new Player(new Position(4, 4), "red", "R");
    Player p2 = new Player(new Position(5, 5), "green", "G");
    Player currentPlayer = p1;
    Player otherPlayer = p2;
    Matrix<Fraction> mat;


    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        primaryStage.setTitle("Spiel");
        mat = initMatrix();
        Text feld = new Text(Board.draw(p1, p2, currentPlayer, mat));
        pane.getChildren().add(feld);
        Scene scene = new Scene(pane, 900, 500);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                  @Override
                                  public void handle(KeyEvent event) {
                                      switch (event.getCode()) {

                                          case UP:
                                              if (currentPlayer.position.y > START_Y
                                                      && !isSamePosition(currentPlayer.peekDirection(Direction.UP), otherPlayer)) {
                                                  currentPlayer.moveDirection(Direction.UP);
                                              }
                                              break;
                                          case DOWN:
                                              if (currentPlayer.position.y < END_Y
                                                      && !isSamePosition(currentPlayer.peekDirection(Direction.DOWN), otherPlayer)) {
                                                  currentPlayer.moveDirection(Direction.DOWN);
                                              }

                                              break;
                                          case LEFT:
                                              if (currentPlayer.position.x > START_X
                                                      && !isSamePosition(currentPlayer.peekDirection(Direction.LEFT), otherPlayer)) {
                                                  currentPlayer.moveDirection(Direction.LEFT);
                                              }
                                              break;
                                          case RIGHT:
                                              if (currentPlayer.position.x < END_X
                                                      && !isSamePosition(currentPlayer.peekDirection(Direction.RIGHT), otherPlayer)) {
                                                  currentPlayer.moveDirection(Direction.RIGHT);
                                              }
                                              break;
                                          case Q:
                                              Stage stage = new Stage();
                                              stage.setTitle("Score");
                                              Pane ergebniss=new Pane();
                                              pane.setPadding(new Insets(5, 5, 5, 5));
                                              Text ergebnissText = new Text(displayScore());
                                              ergebniss.getChildren().add(ergebnissText);

                                              System.out.println(displayScore());
                                              stage.setScene(new Scene(ergebniss,400,500));

                                              stage.show(); // Display the stage
                                              primaryStage.close();

                                      }


                                      // Update player score
                                      currentPlayer.score = currentPlayer.score.add(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
                                      // Update score of remaining playing field points
                                      sum = sum.subtract(mat.getValue(currentPlayer.position.x, currentPlayer.position.y));
                                      // When player arrives field set field value to 0
                                      mat.setValue(currentPlayer.position.x, currentPlayer.position.y, Fraction.ZERO);

                                      // Rotate current player
                                      if (currentPlayer == p1) {
                                          currentPlayer = p2;
                                          otherPlayer = p1;
                                      } else {
                                          currentPlayer = p1;
                                          otherPlayer = p2;
                                      }

                                      feld.setText(Board.draw(p1, p2, currentPlayer, mat));

                                  }
                              }
        );
        primaryStage.setScene(scene);
        primaryStage.show();


    }


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

    public String displayScore() {
        // Announce winner
        String ergebniss="";
        if (p1.score.compareTo(SCORE_TARGET) >= 1) {
            ergebniss+=p1.getName() + " wins!";
        }
        if (p2.score.compareTo(SCORE_TARGET) >= 1) {
            ergebniss+=p2.getName() + " wins!";
        }
        // Announce tie
        if (p1.score.compareTo(p2.score)==0) {

            ergebniss+="Unentschieden";
        }
        ergebniss+="\n"+p1.getName()+" hat "+p1.score.floatValue()+" punkte.\n";
        ergebniss+=p2.getName()+" hat "+p2.score.floatValue()+" punkte.\n";
        return ergebniss;
    }

    public static Matrix<Fraction> initMatrix() {
        int rows = END_X - START_X + 1;
        int columns = END_Y - START_Y + 1;

        Matrix<Fraction> mat = new Matrix<>(rows, columns, Fraction.DEFAULT);

        mat.map(MAX::getGameFraction);
        System.out.println(mat.reduce((acc, curr) -> acc.add(curr), Fraction.DEFAULT).intValue());
        return mat;
    }

    public void run(){
        launch();
    }
    public static Boolean isSamePosition(Player p1, Player p2) {
        return p1.position.equals(p2.position);
    }


}