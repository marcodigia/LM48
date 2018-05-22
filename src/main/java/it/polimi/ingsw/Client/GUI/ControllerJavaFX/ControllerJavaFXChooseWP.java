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

public class ControllerJavaFXChooseWP extends ControllerJavaFX implements Initializable{

    public static ArrayList<Label> selected = new ArrayList<>();
    public static WindowPatternCard windowPatternCard1;
    public static WindowPatternCard windowPatternCard2;
    public static WindowPatternCard windowPatternCard3;
    public static WindowPatternCard windowPatternCard4;

    public ImageView bgChooseWP;
    public AnchorPane anchorChooseWP;
    public Button playbutton;
    public GridPane wp1, wp2, wp3, wp4;

    private ArrayList<String> pattern1 = new ArrayList<String>();
    private ArrayList<Label> wp1Labels = new ArrayList<>();
    private ArrayList<String> pattern2 = new ArrayList<String>();
    private ArrayList<Label> wp2Labels = new ArrayList<>();
    private ArrayList<String> pattern3 = new ArrayList<String>();
    private ArrayList<Label> wp3Labels = new ArrayList<>();
    private ArrayList<String> pattern4 = new ArrayList<String>();
    private ArrayList<Label> wp4Labels = new ArrayList<>();

    private GridPane gpSelected = null;
    private int click = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bgChooseWP, anchorChooseWP);
        windowPatternCard1 = setUpWindowPattern(pattern1);
        windowPatternCard2 = setUpWindowPattern(pattern2);
        windowPatternCard3 = setUpWindowPattern(pattern3);
        windowPatternCard4 = setUpWindowPattern(pattern4);
        populateGridPane(wp1, wp1Labels, windowPatternCard1);
        populateGridPane(wp2, wp2Labels, windowPatternCard2);
        populateGridPane(wp3, wp3Labels, windowPatternCard3);
        populateGridPane(wp4, wp4Labels, windowPatternCard4);
    }

    private void populateGridPane(GridPane gridPane, ArrayList<Label> arrayList, WindowPatternCard windowPatternCard) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Label l = new Label();
                l.setGraphic(toImage(windowPatternCard.getRestrictionAtIndex(4*i + j)));
                GridPane.setConstraints(l, j, i);
                gridPane.getChildren().add(l);
                arrayList.add(l);
            }
        }
    }

    private WindowPatternCard setUpWindowPattern(ArrayList<String> pattern) {
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
        return new WindowPatternCard(pattern);
    }

    public void handlePlayButton(ActionEvent actionEvent) throws IOException{

        if (selected != null){
            URL url = new File("src/main/java/it/polimi/ingsw/Client/GUI/FXMLs/Board.fxml").toURL();
            switchScene(playbutton, url);
        }
        else {
            createAlertBox("Error!", "Select a Window Pattern first", "");
        }
    }

    private ImageView toImage(Restriction restriction) {
        Image image = new Image(restriction.getRestrictionImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    public void handleWP1selected(MouseEvent mouseEvent) {
        if (gpSelected!=null && !(gpSelected.equals(wp1))) {
            gpSelected.setOpacity(1);
            selected = null;
            click = 0;
        }

            gpSelected = wp1;

        switch (click %2) {
            case 0:
                selected = wp1Labels;
                wp1.setOpacity(0.3);
                click = 1;
                break;
            case 1:
                selected = null;
                wp1.setOpacity(1);
                click = 0;
                break;
        }
    }

    public void handleWP2selected(MouseEvent mouseEvent) {
        if (gpSelected!=null && !(gpSelected.equals(wp2))) {
            gpSelected.setOpacity(1);
            selected = null;
            click = 0;
        }

            gpSelected = wp2;

        switch (click %2) {
            case 0:
                selected = wp2Labels;
                wp2.setOpacity(0.3);
                click = 1;
                break;
            case 1:
                selected = null;
                wp2.setOpacity(1);
                click = 0;
                break;
        }
    }

    public void handleWP3selected(MouseEvent mouseEvent) {
        if (gpSelected!=null && !(gpSelected.equals(wp3))){
            gpSelected.setOpacity(1);
            selected=null;
            click = 0;
        }

        gpSelected = wp3;

        switch (click %2) {
            case 0:
                selected = wp3Labels;
                wp3.setOpacity(0.3);
                click = 1;
                break;
            case 1:
                selected = null;
                wp3.setOpacity(1);
                click = 0;
                break;
        }
    }

    public void handleWP4selected(MouseEvent mouseEvent) {
        if (gpSelected!=null && !(gpSelected.equals(wp4))){
            gpSelected.setOpacity(1);
            selected=null;
            click = 0;
        }

        gpSelected = wp4;

        switch (click %2) {
            case 0:
                selected = wp4Labels;
                wp4.setOpacity(0.3);
                click = 1;
                break;
            case 1:
                selected = null;
                wp4.setOpacity(1);
                click = 0;
                break;
        }
    }
}
