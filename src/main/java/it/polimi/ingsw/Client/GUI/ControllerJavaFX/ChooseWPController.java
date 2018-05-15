package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseWPController extends Controller implements Initializable{

    private WindowPatternCard windowPatternCard;
    public ImageView bgChooseWP;
    public AnchorPane anchorChooseWP;
    public Button playbutton;
    public GridPane wp1, wp2, wp3, wp4;
    private ArrayList<Label> wp4Labels = new ArrayList<>();

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

    public void handlePlayButton(ActionEvent actionEvent) throws IOException{

        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/Board.fxml").toURL();
        switchScene(playbutton, url);
    }

    private ImageView toImage(Cell cell) {
        Image image = new Image(cell.getRestriction().getRestrictionImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bgChooseWP, anchorChooseWP);
        //populateGridPane(wp4, wp4Labels);
    }
}
