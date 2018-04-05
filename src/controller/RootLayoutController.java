package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import model.Actions;
import model.BoardElement;
import model.MAX;
import util.KeyboardEventPublisher;

import java.awt.*;

import static javafx.scene.input.KeyCode.SHIFT;
import static model.Direction.*;

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
                FractionController fractionView = new FractionController(game.board.getBoardElements()[i][j]);
                playerMap.add(fractionView, i, j);
            }
        }

        KeyboardEventPublisher.subscribe(event -> {
            switch (event.getCode()) {
                case UP:    game.enterAction(Actions.UP); break;
                case DOWN:  game.enterAction(Actions.DOWN); break;
                case LEFT:  game.enterAction(Actions.LEFT); break;
                case RIGHT: game.enterAction(Actions.RIGHT); break;
            }
        });

        rootLayout.add(playerMap, 0, 1);
    }
}
