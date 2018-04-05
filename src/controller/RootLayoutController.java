package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class RootLayoutController {
    @FXML
    GridPane fractionMap;

    public void initialize() {
        FractionController fractionView = new FractionController();
        fractionView.setText("Hallo Welt");
        FractionController fractionView2 = new FractionController();
        fractionView2.bindProperty(fractionView.textProperty());

        fractionMap.add(fractionView, 0,0 );
        fractionMap.add(fractionView2, 0,1 );
    }
}
