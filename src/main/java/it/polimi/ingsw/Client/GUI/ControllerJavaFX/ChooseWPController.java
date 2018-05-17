package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseWPController extends Controller implements Initializable{

    public static ArrayList<Label> selected = new ArrayList<>();
    public static WindowPatternCard windowPatternCard;
    public ImageView bgChooseWP;
    public AnchorPane anchorChooseWP;
    public Button playbutton;
    public GridPane wp1, wp2, wp3, wp4;
    private ArrayList<String> pattern = new ArrayList<String>();
    private ArrayList<Label> wp4Labels = new ArrayList<>();

    private void populateGridPane(GridPane gridPane, ArrayList<Label> arrayList) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Label l = new Label();
                l.setGraphic(toImage(windowPatternCard.getRestrictionAtIndex(4*i + j)));
                gridPane.setConstraints(l, j, i);
                gridPane.getChildren().add(l);
                arrayList.add(l);
            }
        }
    }

    private void setUpWindowPattern() {
        pattern.add("2");
        pattern.add("Via Lux");
        pattern.add("4");
        pattern.add("Y");   //0
        pattern.add("0");   //1
        pattern.add("6");   //2...
        pattern.add("0");
        pattern.add("0");
        pattern.add("0");
        pattern.add("1");
        pattern.add("5");
        pattern.add("0");
        pattern.add("2");
        pattern.add("3");
        pattern.add("Y");
        pattern.add("R");
        pattern.add("P");
        pattern.add("0");
        pattern.add("0");
        pattern.add("0");
        pattern.add("4");
        pattern.add("3");
        pattern.add("R");   //19
        windowPatternCard = new WindowPatternCard(pattern);
    }

    public void handlePlayButton(ActionEvent actionEvent) throws IOException{

        URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/Board.fxml").toURL();
        switchScene(playbutton, url);
    }

    private ImageView toImage(Restriction restriction) {
        Image image = new Image(restriction.getRestrictionImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bgChooseWP, anchorChooseWP);
        setUpWindowPattern();
        populateGridPane(wp4, wp4Labels);
    }

    public void handleWPCselected(MouseEvent mouseEvent) {

        createConfirmationBox("Confirm WP", "Do you want to choose this WP?", "y/n");
        if (mouseEvent.getSource().equals(wp4)){
            selected = wp4Labels;
            wp4.setDisable(true);
        }
    }
}
