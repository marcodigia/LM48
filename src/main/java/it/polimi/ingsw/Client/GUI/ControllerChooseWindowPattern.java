package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Cards.CardsUtility.DinamicCardCreator;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Board;
import static it.polimi.ingsw.Client.GUI.ControllerGame.gameStatus;
import static it.polimi.ingsw.Client.GUI.ControllerGame.attivo;
import static it.polimi.ingsw.Client.GUI.GUI.username;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Create;

public class ControllerChooseWindowPattern extends AbstractGUI implements Initializable{

    public ImageView bgChooseWP;
    public AnchorPane anchorChooseWP;
    public Button selectButton, createButton;
    public GridPane wp1, wp2, wp3, wp4;

    private GridPane gpSelected = null;
    private int click = 0;

    private static ArrayList<Label> selected = new ArrayList<>();
    private ArrayList<Label> wp1Labels = new ArrayList<>();
    private ArrayList<Label> wp2Labels = new ArrayList<>();
    private ArrayList<Label> wp3Labels = new ArrayList<>();
    private ArrayList<Label> wp4Labels = new ArrayList<>();

    private static WindowPatternCard windowPatternCard1;
    private static WindowPatternCard windowPatternCard2;
    private static WindowPatternCard windowPatternCard3;
    private static WindowPatternCard windowPatternCard4;

    private WindowPatternCard windowPatternCardSelected;

    private Hashtable<String,Drawable> deck = new Hashtable<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            ControllerConnection.clientServerReciver.setUI(this);
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

        windowPatternCard1 = (WindowPatternCard) deck.get(ControllerLobby.id1); //setUpWindowPattern(pattern1);
        windowPatternCard2 = (WindowPatternCard) deck.get(ControllerLobby.id2); //setUpWindowPattern(pattern2);
        windowPatternCard3 = (WindowPatternCard) deck.get(ControllerLobby.id3); //setUpWindowPattern(pattern3);
        windowPatternCard4 = (WindowPatternCard) deck.get(ControllerLobby.id4); //setUpWindowPattern(pattern4);

        populateGridPane(wp1, wp1Labels, windowPatternCard1);
        populateGridPane(wp2, wp2Labels, windowPatternCard2);
        populateGridPane(wp3, wp3Labels, windowPatternCard3);
        populateGridPane(wp4, wp4Labels, windowPatternCard4);
    }

    /**
     * @param gridPane gridPane that has to be populated with labels
     * @param arrayList arrayList containing labels that have to populate the gridPane
     * @param windowPatternCard window pattern card that has the restrictions needed to populate the gridPane
     */
    private void populateGridPane(GridPane gridPane, ArrayList<Label> arrayList, WindowPatternCard windowPatternCard) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Label l = new Label();
                l.setGraphic(toImage(windowPatternCard.getRestrictionAtIndex(5*i + j)));
                GridPane.setConstraints(l, j, i);
                gridPane.getChildren().add(l);
                arrayList.add(l);
            }
        }
    }

    /**
     * @param actionEvent event caused by the user (eg. clicking a button)
     */
    public void handleSelectButton(ActionEvent actionEvent){

        if (selected != null){
            try {
                ControllerLogin.clientServerSender.choosenWindowPattern(windowPatternCardSelected.getID(), username);
            } catch (RemoteException e) {
                GUI.generiClient.manageDisconnection(GUI.username,GUI.ip,Integer.parseInt(GUI.port));
            }
        }
        else {
            createAlertBox("Select a Window Pattern first");
        }
    }

    /**
     * @param restriction restriction whose image is required
     * @return imageview of the restriction
     */
    private ImageView toImage(Restriction restriction) {
        Image image = new Image(restriction.getRestrictionImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
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

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
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

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
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

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
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

    /**
     * @param gameStat status of the game (window pattern cards, draft pool, dice bag, round track, ...)
     */
    @Override
    public void updateGameStatus(GameStatus gameStat) {
        Platform.runLater(() -> {
            gameStatus = gameStat;
            switchScene(Board);
        });
    }

    @Override
    public void activate() {
        Platform.runLater(() -> {
            attivo = true;
            switchScene(Board);
        });
    }

    public void pingBack(){
        System.out.println("PingBackCW " + username);
        try {
            ControllerLogin.clientServerSender.pingBack(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void handleCreateButton(ActionEvent actionEvent) {
        Platform.runLater(() -> switchScene(Create));
    }
}
