package model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.paint.Color;

/**
 * Output class
 * @author Melanie Krugel 198991, Tobias Fetzer 198318, Simon Stratemeier 199067
 * @version 3.0 30.03.2018
 */
public class Board implements Observable {
    BoardElement[][] boardElements = new BoardElement[8][8];

    public BoardElement[][] getBoardElements() {
        return boardElements;
    }

    public Board() {
        for(int i = 0; i<8; i++) {
            for(int j = 0; j < 8; j++) {
                boardElements[i][j] = new BoardElement();
            }
        }
    }

    public void update(Player p1, Player p2, Player currentPlayer, Matrix<Fraction> mat) {
        for(int y = MAX.START_Y; y <= MAX.END_Y; y++) {
            for(int x = MAX.START_X; x <= MAX.END_X; x++) {
                var boardElement = boardElements[x-1][y-1];
                if(p1.position.x == x && p1.position.y == y) {
                    boardElement.setTextProperty(p1.getShortName());
                    boardElement.setFillProperty(p1.getFill());
                    mat.setValue(x,y,Fraction.ZERO);
                } else if(p2.position.x == x && p2.position.y == y) {
                    boardElement.setTextProperty(p2.getShortName());
                    boardElement.setFillProperty(p2.getFill());
                    mat.setValue(x,y,Fraction.ZERO);
                } else {
                    boardElement.setTextProperty(mat.getValue(x,y).toString());
                    boardElement.setFillProperty(Color.WHITE);
                }
            }
        }
    }


    //TODO find better way than HTML
    /*public static String draw(Player p1, Player p2, Player currentPlayer, Matrix<Fraction> mat) {
        StringBuilder s = new StringBuilder();
        s.append(String.join(
                "\n",
                playerScore(p1),
                playerScore(p2),
                currentPlayerInfo(currentPlayer),
                "\n"
        ));

        for(int y = MAX.START_Y; y <= MAX.END_Y; y++) {
            for(int x = MAX.START_X; x <= MAX.END_X; x++) {
                if(p1.position.x == x && p1.position.y == y) {
                    s.append(stringforamtter(p1.getShortName()));
                    mat.setValue(x,y,Fraction.ZERO);
                } else if(p2.position.x == x && p2.position.y == y) {
                    s.append(stringforamtter(p2.getShortName()));
                    mat.setValue(x,y,Fraction.ZERO);
                } else {
                    s.append(stringforamtter(mat.getValue(x,y).toString()));

                }
            }
            s.append("\n");
        }
        return s.toString();
    }
    public static String stringforamtter(String s) {
        if (s.length() == 1) {

            return "|"+"  " + s +"  "+ "|";
        }

        if (s.length() == 3) {

            return ("| " + s + " |");

        }
        if (s.length() == 4) {
           // String[] split = s.split("/");
            //if (split[0].length() == 1) s = " " + s;
            //if (split[1].length() == 1) s = s + " ";
            return "|"+s+" |";
        }
        if(s.length()==2){

            return "|  "+s+" |";
        }
        return "|"+s+"|";
    }
    private static String playerScore(Player p){
        return p.getName() + " Score: " + p.score.floatValue();
    }*/

    private static String currentPlayerInfo(Player p){
        return "Current Player: " + p.getName();
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
