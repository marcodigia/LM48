package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class ChooseWPController {

    private WindowPatternCard windowPatternCard;


    private void populateGridPane(GridPane gridPane, ArrayList<Label> arrayList) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                Label l = new Label();
                //l.setGraphic(toImage(windowPatternCard);
                gridPane.setConstraints(l, i, j);
                gridPane.getChildren().add(l);
                arrayList.add(l);
            }
        }
    }

    private ImageView toImage(Cell cell) {
        Image image = new Image(cell.getRestriction().getRestrictionImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }


}
