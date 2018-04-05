package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import model.MAX;

import java.awt.*;

public class RootLayoutController {
    @FXML
    GridPane rootLayout;

    public void initialize() {
        GridPane playerMap = new GridPane();
        MAX game = new MAX();

        for(int i = 0; i < 8; i++) {
            RowConstraints row = new RowConstraints();
            ColumnConstraints column = new ColumnConstraints();
            row.setVgrow(Priority.SOMETIMES);
            column.setHgrow(Priority.SOMETIMES);
            playerMap.getRowConstraints().add(row);
            playerMap.getColumnConstraints().add(column);

            for(int j = 0; j < 8; j++) {
                FractionController fractionView = new FractionController(game.mat.getValue(i+1, j+1));
                playerMap.add(fractionView, i, j);
            }
        }

        rootLayout.add(playerMap, 0, 1);
    }
}
