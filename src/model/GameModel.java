package model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameModel {
    BoardElement[][] boardElements = new BoardElement[8][8];  //Array von Boardelemnts, stellt Feld da
    Player player1 = new Player(new Position(0,0), "undefined", "undefined", Color.WHITE);
    Player player2 = new Player(new Position(0,0), "undefined", "undefined", Color.WHITE);

    public BoardElement[][] getBoardElements() {
        return boardElements;
    }

    public GameModel() {                    //Konstruktor, füllt Array mit BoardElemnts
        for(int i = 0; i<8; i++) {
            for(int j = 0; j < 8; j++) {
                boardElements[i][j] = new BoardElement();
            }
        }
    }

    //Matrix wird geupdated
    public void update(Player p1, Player p2, Player currentPlayer, Matrix<Fraction> mat) {
        for(int y = MAX.START_Y; y <= MAX.END_Y; y++) {     //geht bis zum Rand
            for(int x = MAX.START_X; x <= MAX.END_X; x++) {
                BoardElement boardElement = boardElements[x-1][y-1];
                if(p1.getPosition().x == x && p1.getPosition().y == y) {  //wenn amn an p1 position ist
                    boardElement.setTextProperty(p1.getShortName());
                    boardElement.setFillProperty(p1.getFill());
                    mat.setValue(x,y,Fraction.ZERO);
                } else if(p2.getPosition().x == x && p2.getPosition().y == y) {       //p2 position
                    boardElement.setTextProperty(p2.getShortName());
                    boardElement.setFillProperty(p2.getFill());
                    mat.setValue(x,y,Fraction.ZERO);
                } else {            //normale fractions
                    boardElement.setTextProperty(mat.getValue(x,y).toString());
                    boardElement.setFillProperty(Color.WHITE);
                }
            }
        }
    }
}
