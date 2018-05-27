package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.AbstractCardFactory;
import it.polimi.ingsw.Server.Game.Cards.Drawable;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCardFactory;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.GUIimpl.username;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXConnection.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXLogin.clientServerSender;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXGame.gameStatus;

public class ControllerJavaFXChooseWP extends GUI implements Initializable{

    public static ArrayList<Label> selected = new ArrayList<>();

    public static WindowPatternCard windowPatternCard1;
    public static WindowPatternCard windowPatternCard2;
    public static WindowPatternCard windowPatternCard3;
    public static WindowPatternCard windowPatternCard4;

    public ImageView bgChooseWP;
    public AnchorPane anchorChooseWP;
    public Button selectButton;
    public GridPane wp1, wp2, wp3, wp4;

    private ArrayList<Label> wp1Labels = new ArrayList<>();
    private ArrayList<Label> wp2Labels = new ArrayList<>();
    private ArrayList<Label> wp3Labels = new ArrayList<>();
    private ArrayList<Label> wp4Labels = new ArrayList<>();

    private GridPane gpSelected = null;
    private int click = 0;

    String fxml = "/Board.fxml";

    private WindowPatternCard windowPatternCardSelected;

    private Hashtable<String,Drawable> deck = new Hashtable<>();
    static String id1;
    static String id2;
    static String id4;
    static String id3;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            clientServerReciver.setUI(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        setBackground(bgChooseWP, anchorChooseWP);

        AbstractCardFactory factory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);

        try {
            deck = factory.getNewCardDeck();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        windowPatternCard1 = (WindowPatternCard) deck.get(id1);//setUpWindowPattern(pattern1);
        windowPatternCard2 = (WindowPatternCard) deck.get(id2);//setUpWindowPattern(pattern2);
        windowPatternCard3 = (WindowPatternCard) deck.get(id3);//setUpWindowPattern(pattern3);
        windowPatternCard4 = (WindowPatternCard) deck.get(id4);//setUpWindowPattern(pattern4);

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

    public void handleSelectButton(ActionEvent actionEvent) throws IOException{

        if (selected != null){
            //switchScene(fxml);
            clientServerSender.choosenWindowPattern(windowPatternCardSelected.getID(), username);
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
            windowPatternCardSelected = null;
            click = 0;
        }

            gpSelected = wp1;

        switch (click %2) {
            case 0:
                selected = wp1Labels;
                windowPatternCardSelected = windowPatternCard1;
                wp1.setOpacity(0.3);
                click = 1;
                break;
            case 1:
                selected = null;
                windowPatternCardSelected = null;
                wp1.setOpacity(1);
                click = 0;
                break;
        }
    }

    public void handleWP2selected(MouseEvent mouseEvent) {
        if (gpSelected!=null && !(gpSelected.equals(wp2))) {
            gpSelected.setOpacity(1);
            selected = null;
            windowPatternCardSelected = null;
            click = 0;
        }

            gpSelected = wp2;

        switch (click %2) {
            case 0:
                selected = wp2Labels;
                windowPatternCardSelected = windowPatternCard2;
                wp2.setOpacity(0.3);
                click = 1;
                break;
            case 1:
                selected = null;

                windowPatternCardSelected = null;
                wp2.setOpacity(1);
                click = 0;
                break;
        }
    }

    public void handleWP3selected(MouseEvent mouseEvent) {
        if (gpSelected!=null && !(gpSelected.equals(wp3))){
            gpSelected.setOpacity(1);
            selected=null;

            windowPatternCardSelected = null;
            click = 0;
        }

        gpSelected = wp3;

        switch (click %2) {
            case 0:
                selected = wp3Labels;
                windowPatternCardSelected = windowPatternCard3;
                wp3.setOpacity(0.3);
                click = 1;
                break;
            case 1:
                selected = null;

                windowPatternCardSelected = null;
                wp3.setOpacity(1);
                click = 0;
                break;
        }
    }

    public void handleWP4selected(MouseEvent mouseEvent) {
        if (gpSelected!=null && !(gpSelected.equals(wp4))){
            gpSelected.setOpacity(1);

            windowPatternCardSelected = null;
            selected=null;
            click = 0;
        }

        gpSelected = wp4;

        switch (click %2) {
            case 0:
                selected = wp4Labels;
                windowPatternCardSelected = windowPatternCard4;
                wp4.setOpacity(0.3);
                click = 1;
                break;
            case 1:
                selected = null;
                windowPatternCardSelected = null;
                wp4.setOpacity(1);
                click = 0;
                break;
        }
    }

    @Override
    public int getWPindexDice() {
        return 0;
    }

    @Override
    public void resetWPindex() {

    }

    @Override
    public int getDiceClickedindexDraftpool() {
        return 0;
    }

    @Override
    public void resetDraftPoolindex() {

    }

    @Override
    public void updateGameStatus(GameStatus gameStat) {
        System.out.println("CWP");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    gameStatus = gameStat;
                    switchScene(fxml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void activate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerJavaFXGame.attivo = true;
                try {
                    switchScene("/Board.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void disable() {

    }

    @Override
    public void allCurrentPlayers(String players) {

    }
}
