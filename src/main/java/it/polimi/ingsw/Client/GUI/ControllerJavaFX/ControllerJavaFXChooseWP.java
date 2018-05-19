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
import java.util.regex.Pattern;

public class ControllerJavaFXChooseWP extends ControllerJavaFX implements Initializable{

    public static ArrayList<Label> selected = new ArrayList<>();

    public static WindowPatternCard windowPatternCard;

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

    //TODO risolvere errori nel setup della window pattern e nella selezione

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bgChooseWP, anchorChooseWP);
        setUpWindowPattern(pattern1, windowPatternCard1);
        setUpWindowPattern(pattern2, windowPatternCard2);
        setUpWindowPattern(pattern3, windowPatternCard3);
        setUpWindowPattern(pattern4, windowPatternCard4);
        populateGridPane(wp1, wp1Labels, windowPatternCard1);
        populateGridPane(wp2, wp2Labels, windowPatternCard2);
        populateGridPane(wp3, wp3Labels, windowPatternCard3);
        populateGridPane(wp4, wp4Labels, windowPatternCard4);
    }

    private void populateGridPane(GridPane gridPane, ArrayList<Label> arrayList, WindowPatternCard windowPatternCard) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Label l = new Label();
                l.setGraphic(toImage(windowPatternCard1.getRestrictionAtIndex(4*i + j)));
                gridPane.setConstraints(l, j, i);
                gridPane.getChildren().add(l);
                arrayList.add(l);
            }
        }
    }

    private void setUpWindowPattern(ArrayList<String> pattern, WindowPatternCard windowPatternCard) {
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
        windowPatternCard1 = new WindowPatternCard(pattern);
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

    public void handleWP4selected(MouseEvent mouseEvent) {
/*        if (!(gpSelected.equals(wp4)) && gpSelected!=null){
            gpSelected.setOpacity(1);
            selected=null;
            click++;
        }
*/
        gpSelected = wp4;

        switch (click %2) {
            case 0:
                selected = wp4Labels;
                wp4.setOpacity(0.3);
                click++;
                break;
            case 1:
                selected = null;
                wp4.setOpacity(1);
                click++;
                break;
        }
    }
}
