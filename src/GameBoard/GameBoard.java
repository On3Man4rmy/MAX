package GameBoard;

import Fraction.FractionController;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import model.Board;

public class GameBoard {
    @FXML
    GridPane gameBoardView;

    public void setBoard(Board model) {
        gameBoardView.getChildren().clear();
        for(int i = 0; i < 8; i++) {
            RowConstraints row = new RowConstraints();
            ColumnConstraints column = new ColumnConstraints();
            row.setVgrow(Priority.SOMETIMES);
            column.setHgrow(Priority.SOMETIMES);
            gameBoardView.getRowConstraints().add(row);
            gameBoardView.getColumnConstraints().add(column);

            for(int j = 0; j < 8; j++) {
                FractionController fractionView = new FractionController(model.getBoardElements()[i][j]);
                gameBoardView.add(fractionView, i, j);
            }
        }
    }
}
