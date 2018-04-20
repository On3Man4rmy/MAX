package Board;

import Board.Fraction.FractionController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.*;

public class BoardController {
    @FXML
    GridPane boardView;
    FractionController[][] boardElements = new FractionController[8][8];  //Array von Boardelemnts, stellt Feld da

    public void initialize() {
        for(int i = 0; i < 8; i++) {
            RowConstraints row = new RowConstraints();
            ColumnConstraints column = new ColumnConstraints();
            row.setVgrow(Priority.SOMETIMES);
            column.setHgrow(Priority.SOMETIMES);
            boardView.getRowConstraints().add(row);
            boardView.getColumnConstraints().add(column);

            for(int j = 0; j < 8; j++) {
                boardElements[i][j] = new FractionController();
                boardView.add(boardElements[i][j], i, j);
            }
        }
    }

    //Matrix wird geupdated
    public void setBoardElement(StringProperty text, ObjectProperty<Paint> color, int x, int y) {
        boardElements[x][y].getTextProperty().set(text.get());
        if(color != null) {
            boardElements[x][y].getFillProperty().set(color.get());
        } else  {
            boardElements[x][y].getFillProperty().set(Color.WHITE);
        }
    }
}
