package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import javafx.application.Platform;
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
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.*;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXConnection.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXLogin.clientServerSender;
import static it.polimi.ingsw.Client.GUI.GUIimpl.username;

public class ControllerJavaFXGame extends GUI implements Initializable {

    public static GameStatus gameStatus;
    private PlaceDiceAction placeDiceAction;

    public Label p1, p2, p3, p4;
    public MenuItem showpublic, showprivate, showtool, showcopyright;
    public ImageView bg4;
    public AnchorPane anchorgame;
    public GridPane gp1, gp2, gp3, gp4, gpround, gpdraft;
    public HBox hboxgp1, hboxgp2, hboxgp3, hboxgp4, hboxl1, hboxl2, hboxl3, hboxl4;

    private int indice_dado = -1;
    private int draftpoolindex = -1;
    private DraftPool draftPool;

    public static boolean attivo = false;

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
        for (int i = 0; i < draftPool.getDraft().size(); i++) {
            draftPoolLabel.get(i).setGraphic(toImage(draftPool.getDraft().get(i)));
        }

        if(attivo){
            anchorgame.setDisable(false);
            for (GridPane gp : gridPanes
                 ) {
                if (!gp.equals(gp4))
                    gp.setDisable(true);
            }
        }
        else {
            anchorgame.setDisable(true);
        }


        placeDiceAction = gameStatus.getPlayerByName(username).getPlaceDiceOfTheTurn();

    }

    public void handleClickDraftPool(MouseEvent mouseEvent) {
        Label eventDraft = (Label) mouseEvent.getSource();
        ButtonBar.ButtonData clicked = createConfirmationBox("Confirm Dice", "Do you want to place this dice?", "y/n");
        if (clicked.equals(ButtonBar.ButtonData.OK_DONE)) {
            draftpoolindex = draftPoolLabel.indexOf(eventDraft);
        }
    }

    private void handleClickWindowPattern(MouseEvent mouseEvent) {
        Label event = (Label) mouseEvent.getSource();
        indice_dado = cells4.indexOf(event);
        if(draftpoolindex != -1) {
            placeDiceAction.useAction(this, gameStatus, username);
            try {
                clientServerSender.sendAction(placeDiceAction, username);
            } catch (RemoteException e) {
                e.printStackTrace();
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

        int i = 0;

        switch (gameStatus.getPlayer().size()) {
        /*    case 1:
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
                */
            case 2:
                hboxgp2.getChildren().remove(gp2);
                hboxl2.getChildren().remove(p2);
                hboxgp3.getChildren().remove(gp3);
                hboxl3.getChildren().remove(p3);
                gridPanes.add(gp1);
                gridPanes.add(gp4);

                p4.setText(username);
                if (gameStatus.getPlayer().get(i).getName().equals(username))
                    i++;
                p1.setText(gameStatus.getPlayer().get(i).getName() );
                i++;

                populateGridPane(gp4, gameStatus.getPlayerByName(p4.getText()));
                populateGridPane(gp1, gameStatus.getPlayerByName(p1.getText()));
                break;
            case 3:
                hboxgp1.getChildren().remove(gp1);

                hboxl1.getChildren().remove(p1);

                gridPanes.add(gp2);
                gridPanes.add(gp3);
                gridPanes.add(gp4);

                p4.setText(username);
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

                p4.setText(username);

                if (gameStatus.getPlayer().get(i).getName().equals(username))
                    i++;
                p1.setText(gameStatus.getPlayer().get(i).getName() );
                i++;
                if (gameStatus.getPlayer().get(i).getName().equals(username))
                    i++;
                p2.setText(gameStatus.getPlayer().get(i).getName() );
                i++;
                if (gameStatus.getPlayer().get(i).getName().equals(username))
                    i++;
                p3.setText(gameStatus.getPlayer().get(i).getName());

                populateGridPane(gp1, gameStatus.getPlayerByName(p1.getText()));
                populateGridPane(gp2, gameStatus.getPlayerByName(p2.getText()));
                populateGridPane(gp3, gameStatus.getPlayerByName(p3.getText()));
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
                if (((WindowPatternCard) gameStatus.getPlayerCards().get(player).get(0)).getDice(4*i + j) != null)
                    l.setGraphic(toImage(((WindowPatternCard)gameStatus.getPlayerCards().get(player).get(0)).getDice(4*i + j)));
                GridPane.setConstraints(l, j, i);
                gridPane.getChildren().add(l);
                l.setOnMouseClicked(event -> handleClickWindowPattern(event));
                if (player.getName().equals(username))
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    switchScene("/Board.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void activate(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                attivo = true;
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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                attivo = false;
                try {
                    switchScene("/Board.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void allCurrentPlayers(String players) {

    }
}