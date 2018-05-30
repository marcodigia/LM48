package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXConnection.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXLogin.clientServerSender;
import static it.polimi.ingsw.Client.GUI.GUIimpl.username;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Board;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.COLUMNS;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.ROWS;

public class ControllerJavaFXGame extends GUI implements Initializable {

    public static GameStatus gameStatus;
    public static boolean attivo = false;

    public Label p1, p2, p3, p4;
    public MenuItem showpublic, showprivate, showtool, showcopyright;
    public ImageView bg4;
    public AnchorPane anchorgame;
    public GridPane gp1, gp2, gp3, gp4, gpround, gpdraft;
    public HBox hboxgp1, hboxgp2, hboxgp3, hboxgp4, hboxl1, hboxl2, hboxl3, hboxl4;

    private int indice_dado = -1;
    private int draftpoolindex = -1;

    private DraftPool draftPool;
    private PlaceDiceAction placeDiceAction;
    private ArrayList<ArrayList<Dice>> roundTrack;

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
        roundTrack = gameStatus.getBoardRound().getDices();

        populateGridPane(gpdraft, 1, 9, draftPoolLabel, "");
        populateGridPane(gpround, 1, 10, round, "");
        names.add(p1);
        names.add(p2);
        names.add(p3);
        names.add(p4);

        //show dices image in DraftPool
        for (int i = 0; i < draftPool.getDraft().size(); i++) {
            draftPoolLabel.get(i).setGraphic(toImage(draftPool.getDraft().get(i)));
        }

        //show round in Roundtrack
        for (int i = 0; i < round.size(); i++) {
            round.get(i).setText(String.valueOf(i));
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
            gpdraft.setDisable(true);
            for (GridPane gp: gridPanes
                 ) {
                gp.setDisable(true);
            }
        }
        placeDiceAction = gameStatus.getPlayerByName(username).getPlaceDiceOfTheTurn();
    }

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
    public void handleClickDraftPool(MouseEvent mouseEvent) {
        Label eventDraft = (Label) mouseEvent.getSource();
        ButtonBar.ButtonData clicked = createConfirmationBox("Confirm Dice", "Do you want to place this dice?", "y/n");
        if (clicked.equals(ButtonBar.ButtonData.OK_DONE)) {
            draftpoolindex = draftPoolLabel.indexOf(eventDraft);
        }
    }

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
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

    /**
     * @param event event caused by the user (eg. clicking button)
     */
    public void handleCopyright(ActionEvent event) {
        createInfoBox("Copiright ©", "Software Engineering Project\nAll rights reserved", "Sagrada\nby Marco Di Giacomantonio, Matthias Carretta and Fabio Dalle Rive\n:D");
    }

    /**
     * @param event event caused by the user (eg. clicking button)
     */
    public void handleShow(ActionEvent event) {
        if (event.getSource().equals(showtool))
            openToolCards("Tool Cards", null);
        if (event.getSource().equals(showpublic))
            openPublicCards("Public Objective Cards");
        if (event.getSource().equals(showprivate)){}
            openPrivateCards("Private Objective Cards");
    }

    /**
     * @param private_objective_cards string used to set the title of the stage
     */
    private void openPrivateCards(String private_objective_cards) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(private_objective_cards);
        window.setMinWidth(250);
        window.setMinHeight(100);
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        /* for (int j = 0; j < gameStatus.get; j++) {
            Label label = new Label();
            label.setGraphic(toImage());
            layout.getChildren().add(label);
        } */
        layout.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * @param public_objective_cards string used to set the title of the stage
     */
    private void openPublicCards(String public_objective_cards) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(public_objective_cards);
        window.setMinWidth(250);
        window.setMinHeight(100);
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        /* for (int j = 0; j < gameStatus.get; j++) {
            Label label = new Label();
            label.setGraphic(toImage());
            layout.getChildren().add(label);
        } */
        layout.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * @param title string used to set the title of the stage
     * @param toolCards arrayList containing the toolCards that can be used during the game
     */
    private void openToolCards(String title, ArrayList<ToolCard> toolCards) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(100);
        BorderPane borderPane = new BorderPane();
        Label label = new Label();
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.TOP_CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private void setUpGame() {

        int i = 0;

        System.out.println(gameStatus + " setUpGame");
        System.out.println(gameStatus.getPlayer() + " players");

        switch (gameStatus.getPlayer().size()) {
            /* case 1:
                hboxgp1.getChildren().remove(gp1);
                hboxl1.getChildren().remove(p1);
                hboxgp2.getChildren().remove(gp2);
                hboxl2.getChildren().remove(p2);
                hboxgp3.getChildren().remove(gp3);
                hboxl3.getChildren().remove(p3);
                gridPanes.add(gp4);
                p4.setText(gameStatus.getPlayer().get(0).getName());
                populateGridPane(gp4, gameStatus.getPlayerByName(p4.getText()));
                break; */
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

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
    private void handleClickBoardRound(MouseEvent mouseEvent) {
        int roundIndex = Integer.parseInt(((Label) mouseEvent.getSource()).getText());
        if (roundTrack.size() > roundIndex){
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Dices");
            window.setMinWidth(250);
            window.setMinHeight(100);
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10,10,10,10));
            for (int j = 0; j < roundTrack.get(roundIndex).size(); j++) {
                Label label = new Label();
                label.setGraphic(toImage(roundTrack.get(roundIndex).get(j)));
                layout.getChildren().add(label);
            }
            layout.setAlignment(Pos.TOP_CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        }
        else{
            createAlertBox("Error", "This turn has to be played", "No dices");
        }
    }

    /**
     * @param gridPane gridPane that has to be populated with labels
     * @param rows number of rows of the gridpane
     * @param cols number of columns of the gridpane
     * @param arrayList arrayList containing labels that have to populate the gridPane
     * @param labelContent string representing the content of the label
     */
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
                            handleClickBoardRound(mouseEvent);
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

    /**
     * @param gridPane gridPane that has to be populated with labels
     * @param player player owning this gridpane(window pattern card)
     */
    private void populateGridPane(GridPane gridPane, Player player) {
        Node grid = gridPane.getChildren().get(0);
        gridPane.getChildren().clear();
        gridPane.getChildren().add(0, grid);
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Label l = new Label();
                l.setGraphic(toImage(((WindowPatternCard)gameStatus.getPlayerCards().get(player).get(0)).getRestrictionAtIndex(5 * row + column)));
                if (((WindowPatternCard) gameStatus.getPlayerCards().get(player).get(0)).getDice(5 * row + column) != null)
                    l.setGraphic(toImage(((WindowPatternCard)gameStatus.getPlayerCards().get(player).get(0)).getDice(5 * row + column)));
                GridPane.setConstraints(l, column, row);
                gridPane.getChildren().add(l);
                l.setOnMouseClicked(event -> handleClickWindowPattern(event));
                if (player.getName().equals(username))
                    cells4.add(l);
            }
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
     * @param dice dice whose image is required
     * @return imageview of the dice
     */
    private ImageView toImage(Dice dice) {
        Image image = new Image(dice.getDiceImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    /**
     * @param gridPane gridpane that has to be identified (ed. round track, draft pool, window pattern card)
     * @return int representing the type (0 --> window pattern card, 1 --> round track, 2 --> draft pool)
     */
    private int typeOfGridPane(GridPane gridPane){
        if (gridPane.equals(gp1) || gridPane.equals(gp2) || gridPane.equals(gp3) || gridPane.equals(gp4))
            return 0;
        if (gridPane.equals(gpround))
            return 1;
        if (gridPane.equals(gpdraft))
            return 2;
        return -1;
    }

    /**
     * @return index of the dice taken from draft pool
     */
    public int getDiceClickedindexDraftpool(){
        return draftpoolindex;
    }

    /**
     * @return index of window pattern card's cell clicked
     */
    public int getWPindexDice(){
        return indice_dado;
    }

    public void resetWPindex(){
        indice_dado = -1;
    }

    public void resetDraftPoolindex(){
        draftpoolindex = -1;
    }

    /**
     * @param gameStat status of the game (window pattern cards, draft pool, dice bag, round track, ...)
     */
    @Override
    public void updateGameStatus(GameStatus gameStat) {
        gameStatus = gameStat;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                switchScene(Board);
            }
        });
    }

    @Override
    public void activate(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                attivo = true;
                switchScene(Board);
            }
        });
    }

    @Override
    public void disable() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                attivo = false;
                switchScene(Board);
            }
        });
    }

}