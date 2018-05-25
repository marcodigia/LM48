package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.*;
import static it.polimi.ingsw.Client.GUI.GUIimpl.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.GUIimpl.clientServerSender;
import static it.polimi.ingsw.Client.GUI.GUIimpl.username;

public class ControllerJavaFXGame extends GUI implements Initializable {

    private static Label draftToDisable;

    private GameContext gameContext;
    public static GameStatus gameStatus;
    private PlaceDiceAction placeDiceAction = new PlaceDiceAction();

    public Label p1, p2, p3, p4;
    public MenuItem showpublic, showprivate, showtool, showcopyright;
    public ImageView bg4;
    public AnchorPane anchorgame;
    public GridPane gp1, gp2, gp3, gp4, gpround, gpdraft;
    public HBox hboxgp1, hboxgp2, hboxgp3, hboxgp4, hboxl1, hboxl2, hboxl3, hboxl4;

    private int indice_dado = -1;
    private int draftpoolindex = -1;
    private boolean put = false;
    private boolean firstTourn = true;
    private DraftPool draftPool;

    private ArrayList<Label> draftPoolLabel = new ArrayList<>();
    private ArrayList<Label> cells4 = new ArrayList<>();
    private ArrayList<Label> names = new ArrayList<>();
    private ArrayList<Label> round = new ArrayList<>();
    private ArrayList<GridPane> gridPanes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            clientServerReciver.setUI(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        setBackground(bg4, anchorgame);

        setUpGame();
        draftPool = gameStatus.getDraftPool();

        populateGridPane(gpdraft, 1, 9, draftPoolLabel, "");
        populateGridPane(gpround, 1, 10, round, "#");

        names.add(p1);
        names.add(p2);
        names.add(p3);
        names.add(p4);

        //show dices image in DraftPool
        for (int i = 0; i < 9; i++) {
            draftPoolLabel.get(i).setGraphic(toImage(draftPool.getDraft().get(i)));
        }

        gameContext = new GameContext(draftPool, null, null, windowPatternCard4, null);

    }

    public void handleClickDraftPool(MouseEvent mouseEvent) {
        Label eventDraft = (Label) mouseEvent.getSource();
        ButtonBar.ButtonData clicked = createConfirmationBox("Confirm Dice", "Do you want to place this dice?", "y/n");
        if (clicked.equals(ButtonBar.ButtonData.OK_DONE)) {
            draftpoolindex = draftPoolLabel.indexOf(eventDraft);
            draftToDisable = eventDraft;
            put = false;
        }
    }

    private void handleClickWindowPattern(MouseEvent mouseEvent) {
        placeDiceAction.useAction(this, gameContext);
        Label event = (Label) mouseEvent.getSource();
        indice_dado = cells4.indexOf(event);
        if(draftpoolindex != -1) {
            if (windowPatternCard4.getDice(indice_dado) == null && !put) {
                if (firstTourn) {
                    if (windowPatternCard4.isPlaceable(draftPool.getDice(draftpoolindex), indice_dado, false, false, true)) {
                        windowPatternCard4.placeDice(draftPool.getDice(draftpoolindex), indice_dado, false, false, true);
                        updateWindowPattern(windowPatternCard4);
                        draftToDisable.setGraphic(null);
                        put = true;
                    } else {
                        createAlertBox("Error!", "Wrong action",
                                "Restrictions havn't been respected! " +
                                        "Please perform a correct move.");
                    }
                } else {
                    if (windowPatternCard4.isPlaceable(draftPool.getDice(draftpoolindex), indice_dado, false, false, false)) {
                        windowPatternCard4.placeDice(draftPool.getDice(draftpoolindex), indice_dado, false, false, false);
                        updateWindowPattern(windowPatternCard4);
                        draftToDisable.setDisable(true);
                        put = true;
                    } else {
                        createAlertBox("Error!", "Wrong action",
                                "Restrictions havn't been respected! " +
                                        "Please perform a correct move.");
                    }
                }
            } else {
                createAlertBox("Error!", "Wrong action", "You already placed this dice " +
                        "or in this cell has already been placed a dice " +
                        "or restrictions havn't been respected! " +
                        "Please perform a correct move.");
            }
        }
        resetDraftPoolindex();
        resetWPindex();
    }

    public void handleShow(ActionEvent event) {
        if (event.getSource().equals(showtool))
            openWindowFromMenu("Tool Cards");
        if (event.getSource().equals(showpublic))
            openWindowFromMenu("Public Objective Cards");
        if (event.getSource().equals(showprivate))
            openWindowFromMenu("Private Objective Cards");
    }

    public void handleCopyright(ActionEvent event) {
        createInfoBox("Copiright ©", "Software Engineering Project\nAll rights reserved", "Sagrada\nby Marco Di Giacomantonio, Matthias Carretta and Fabio Dalle Rive\n:D");
    }

    private void updateWindowPattern(WindowPatternCard windowPatternCard) {
        for (int i = 0; i < selected.size(); i++) {
            if (windowPatternCard.getDice(i) != null) {
                cells4.get(i).setGraphic(toImage(windowPatternCard.getDice(i)));
                cells4.get(i).toFront();
            }
        }
    }

    private ImageView toImage(Dice dice) {
        Image image = new Image(dice.getDiceImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    private void openWindowFromMenu(String string) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(string);
        window.setMinWidth(250);
        window.setMinHeight(100);
        Label label = new Label(string);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private void setUpGame() {

        //TODO fare come caso tre a tutti

        switch (gameStatus.getPlayer().size()) {
            case 1:
                hboxgp1.getChildren().remove(gp1);
                hboxl1.getChildren().remove(p1);
                hboxgp2.getChildren().remove(gp2);
                hboxl2.getChildren().remove(p2);
                hboxgp3.getChildren().remove(gp3);
                hboxl3.getChildren().remove(p3);
                gridPanes.add(gp4);
                p4.setText(gameStatus.getPlayer().get(0).getName());
                populateGridPane(gp4, gameStatus.getPlayerByName(p4.getText()));
                break;
            case 2:
                hboxgp2.getChildren().remove(gp2);
                hboxl2.getChildren().remove(p2);
                hboxgp3.getChildren().remove(gp3);
                hboxl3.getChildren().remove(p3);
                gridPanes.add(gp1);
                gridPanes.add(gp4);
                p1.setText(gameStatus.getPlayer().get(1).getName());
                populateGridPane(gp1, gameStatus.getPlayerByName(p1.getText()));
                p4.setText(gameStatus.getPlayer().get(0).getName());
                populateGridPane(gp4, gameStatus.getPlayerByName(p4.getText()));
                break;
            case 3:
                hboxgp1.getChildren().remove(gp1);
                hboxl1.getChildren().remove(p1);
                gridPanes.add(gp2);
                gridPanes.add(gp3);
                gridPanes.add(gp4);

                p4.setText(username);
                int i = 0;
                if (gameStatus.getPlayer().get(i).getName().equals(username))
                    i++;
                p2.setText(gameStatus.getPlayer().get(i).getName() );
                i++;
                if (gameStatus.getPlayer().get(i).getName().equals(username))
                    i++;
                p3.setText(gameStatus.getPlayer().get(i).getName());

                populateGridPane(gp4, gameStatus.getPlayerByName(p4.getText()));
                populateGridPane(gp2, gameStatus.getPlayerByName(p2.getText()));
                populateGridPane(gp3, gameStatus.getPlayerByName(p3.getText()));

                break;
            case 4:
                gridPanes.add(gp1);
                gridPanes.add(gp2);
                gridPanes.add(gp3);
                gridPanes.add(gp4);
                p1.setText(gameStatus.getPlayer().get(3).getName());
                populateGridPane(gp1, gameStatus.getPlayerByName(p1.getText()));
                p2.setText(gameStatus.getPlayer().get(2).getName());
                populateGridPane(gp2, gameStatus.getPlayerByName(p2.getText()));
                p3.setText(gameStatus.getPlayer().get(1).getName());
                populateGridPane(gp3, gameStatus.getPlayerByName(p3.getText()));
                p4.setText(gameStatus.getPlayer().get(0).getName());
                populateGridPane(gp4, gameStatus.getPlayerByName(p4.getText()));
                break;
        }
    }

    private void populateGridPane(GridPane gridPane, int rows, int cols, ArrayList<Label> arrayList, String labelContent) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Label l = new Label(labelContent);
                GridPane.setConstraints(l, i, j);
                gridPane.getChildren().add(l);
                l.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    switch (typeOfGridPane(gridPane)){
                        case 0:
                            handleClickWindowPattern(mouseEvent);
                            break;
                        case 1:
                            break;
                        case 2:
                            handleClickDraftPool(mouseEvent);
                            break;
                    }
                });
                arrayList.add(l);
            }
        }
    }

    private void populateGridPane(GridPane gridPane, Player player) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Label l = new Label();
                l.setGraphic(toImage(((WindowPatternCard)gameStatus.getPlayerCards().get(player).get(0)).getRestrictionAtIndex(4*i + j)));
                GridPane.setConstraints(l, j, i);
                gridPane.getChildren().add(l);
                l.setOnMouseClicked(event -> handleClickWindowPattern(event));
                cells4.add(l);
            }
        }
    }

    private ImageView toImage(Restriction restriction) {
        Image image = new Image(restriction.getRestrictionImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    private int typeOfGridPane(GridPane gridPane){
        if (gridPane.equals(gp1) || gridPane.equals(gp2) || gridPane.equals(gp3) || gridPane.equals(gp4))
            return 0;
        if (gridPane.equals(gpround))
            return 1;
        if (gridPane.equals(gpdraft))
            return 2;
        return -1;
    }

    public int getDiceClickedindexDraftpool(){
        return draftpoolindex;
    }

    public int getWPindexDice(){
        return indice_dado;
    }

    public void resetWPindex(){
        indice_dado = -1;
    }

    public void resetDraftPoolindex(){
        draftpoolindex = -1;
    }

    @Override
    public void updateGameStatus(GameStatus gameStat) {
        gameStatus = gameStat;
    }
}