package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.PublicObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static it.polimi.ingsw.Client.GUI.ControllerConnection.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.ControllerLogin.clientServerSender;
import static it.polimi.ingsw.Client.GUI.GUI.username;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.*;

public class ControllerGame extends AbstractGUI implements Initializable {

    public static GameStatus gameStatus;
    static boolean attivo = false;
    private boolean showDraftpoolAlert = true;

    public Label p1, p2, p3, p4;
    public MenuItem showpublic, showprivate, showtool, showcopyright;
    public ImageView bg4;
    public AnchorPane anchorgame;
    public GridPane gp1, gp2, gp3, gp4, gpround, gpdraft;
    public HBox hboxgp1, hboxgp2, hboxgp3, hboxgp4, hboxl1, hboxl2, hboxl3, hboxl4;

    private Stage toolCardStage;
    private Stage amountStage;
    private Stage anotherDice;


    public Text text;
    private int indice_dado = -1;
    private int indice_dadoPrecedente = -1;
    private int draftpoolindex = -1;
    private int roundIndex = -1;
    private int diceRoundIndex = -1;
    private int toolCardSelected = -1;
    private int indiceWPFrom = -1;
    private int amountIndex = 0;
    private int askAnotherReady = -1;


    private final Object lockAskanother = new Object();
    private final Object lockRoundIndex = new Object();
    private final Object lockDiceRoundIndex = new Object();
    private Object lockToolCardSelected = new Object();
    private final Object lockWPFrom = new Object();
    private final Object lockWPTo = new Object();
    private final Object lockDraftPool = new Object();
    private final Object lockAmount = new Object();
    private final Object lockAnother = new Object();

    private PlaceDiceAction placeDiceAction;
    private UseToolCardBasic useToolCardBasic;

    private ArrayList<ArrayList<Dice>> roundTrack;

    private PrivateObjectiveCard privateObjectiveCard;

    private ArrayList<ToolCard> toolCards = new ArrayList<>();
    private ArrayList<PublicObjectiveCard> publicObjectiveCards = new ArrayList<>();
    private ArrayList<Label> toolCardsLabel = new ArrayList<>();
    private ArrayList<Label> draftPoolLabel = new ArrayList<>();
    private ArrayList<Label> cells4 = new ArrayList<>();
    private ArrayList<Label> names = new ArrayList<>();
    private ArrayList<Label> round = new ArrayList<>();
    private ArrayList<GridPane> gridPanes = new ArrayList<>();
    public static String gameWinner;



    public boolean GO = true;


    public void setGO(boolean GO) {
        this.GO = GO;
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {

        try {
            clientServerReciver.setUI(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        publicObjectiveCards = gameStatus.getPublicObjectiveCards();
        privateObjectiveCard = gameStatus.getPlayerPrivateObjectiveCards(username);

        setBackground(bg4, anchorgame);
        setUpGame();
        DraftPool draftPool = gameStatus.getDraftPool();
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
            round.get(i).setText(String.valueOf(i+1));
        }

        if(attivo){
            anchorgame.setDisable(false);
            for (GridPane gp : gridPanes) {
                if (!gp.equals(gp4))
                    gp.setDisable(true);
            }
        }
        else {
            gpdraft.setDisable(true);
            for (GridPane gp: gridPanes) {
                gp.setDisable(true);
            }

        }
        placeDiceAction = gameStatus.getPlayerByName(username).getPlaceDiceOfTheTurn();
        useToolCardBasic = gameStatus.getPlayerByName(username).getUseToolCardOfTheTurn();
        toolCards = gameStatus.getToolCards();

        Logger.setTextfx(text);
    }

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
    public void handleClickDraftPool(MouseEvent mouseEvent) {
        Label eventDraft = (Label) mouseEvent.getSource();
        ButtonBar.ButtonData clicked = createConfirmationBox("Do you want to select this dice?");
        if (clicked.equals(ButtonBar.ButtonData.OK_DONE)) {
            draftpoolindex = draftPoolLabel.indexOf(eventDraft);
        }
        synchronized (lockDraftPool){
            lockDraftPool.notifyAll();
        }
    }

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
    private void handleClickWindowPattern(MouseEvent mouseEvent) {
        Label event = (Label) mouseEvent.getSource();

        indice_dadoPrecedente = indice_dado;
        indice_dado = cells4.indexOf(event);

        Thread t = new Thread(()->{
                placeDiceAction.useAction(this, gameStatus, username);
                try {
                        clientServerSender.sendAction(placeDiceAction, username);
                } catch (RemoteException e) {
                    GUI.generiClient.manageDisconnection(GUI.username,GUI.ip,Integer.parseInt(GUI.port));
                }
                synchronized (lockWPTo){
                    lockWPTo.notifyAll();
                }
            });

            if (toolCardSelected ==-1)
                t.start();
             else {
                 Thread t2 = new Thread(()->{
                     synchronized (lockWPFrom){

                         lockWPFrom.notifyAll();
                     }
                     synchronized (lockWPTo){
                         lockWPTo.notifyAll();
                     }
                 });
                t2.start();

            }
    }

    /**
     * @param event event caused by the user (eg. clicking button)
     */
    public void handleCopyright(ActionEvent event) {
        createInfoBox("Copiright ©\nSoftware Engineering Project\nAll rights reserved\nSagrada\nby Marco Di Giacomantonio, Matthias Carretta and Fabio Dalle Rive\n:D");
    }

    /**
     * @param event event caused by the user (eg. clicking button)
     */
    public void handleShow(ActionEvent event) {
        if (event.getSource().equals(showtool))
            openToolCards("Tool Cards");
        if (event.getSource().equals(showpublic))
            openPublicCards("Public Objective Cards");
        if (event.getSource().equals(showprivate))
            openPrivateCards("Private Objective Cards");
    }

    /**
     * @param title string used to set the title of the stage
     */
    private void openPrivateCards(String title) {
        Stage window = new Stage();
        //window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        HBox layout = new HBox(30);
        layout.setAlignment(Pos.CENTER);
        Label label = new Label();
        label.setGraphic(toImage(privateObjectiveCard));
        layout.getChildren().add(label);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setMinHeight(400);
        window.setMinWidth(700);
        window.showAndWait();
    }

    /**
     * @param title string used to set the title of the stage
     */
    private void openPublicCards(String title) {
        Stage window = new Stage();
        //window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        HBox layout = new HBox(30);
        layout.setAlignment(Pos.CENTER);
        for (PublicObjectiveCard poc : publicObjectiveCards) {
            Label label = new Label();
            label.setGraphic(toImage(poc));
            layout.getChildren().add(label);
        }
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setMinHeight(400);
        window.setMinWidth(700);
        window.showAndWait();
    }

    /**
     * @param title string used to set the title of the stage
     */
    private void openToolCards(String title) {
        toolCardStage = new Stage();
        toolCardStage.setTitle(title);
        HBox layout = new HBox(30);
        layout.setAlignment(Pos.CENTER);
        toolCardsLabel = new ArrayList<>();
        for (ToolCard tc : toolCards) {
            Label label = new Label();
            label.setGraphic(toImage(tc));
            label.setOnMouseClicked(this::handleClickToolCard);
            layout.getChildren().add(label);
            toolCardsLabel.add(label);
        }
        Scene scene = new Scene(layout);
        toolCardStage.setScene(scene);
        toolCardStage.setMinHeight(400);
        toolCardStage.setMinWidth(700);
        toolCardStage.show();
    }

    private void handleClickToolCard(MouseEvent mouseEvent) {
        Label event = (Label) mouseEvent.getSource();
        toolCardSelected = toolCardsLabel.indexOf(event);
        Thread t = new Thread(() -> {
           if (attivo) {
                useToolCardBasic.useAction(this, gameStatus, username);
                try {
                    clientServerSender.sendAction(useToolCardBasic, username);
                    System.out.println("HANDLECLICK TC "+useToolCardBasic.toPacket());
                } catch (RemoteException e) {
                    GUI.generiClient.manageDisconnection(GUI.username, GUI.ip, Integer.parseInt(GUI.port));
                }
           }
        });
        t.start();
        toolCardStage.close();
    }

    private void setUpGame() {

        int i = 0;

        switch (gameStatus.getPlayer().size()) {
            case 1:
                endGame(username);
                break;
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
        //Node grid = gridPane.getChildren().get(0);
        //gridPane.getChildren().clear();
        //gridPane.getChildren().add(0, grid);
        for (Node n : gridPane.getChildren()) {
            if (n instanceof Label)
                gridPane.getChildren().remove(n);
        }
                for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Label l = new Label();
                l.setGraphic(toImage(((WindowPatternCard)gameStatus.getPlayerCards().get(player).get(0)).getRestrictionAtIndex(5 * row + column)));
                if (((WindowPatternCard) gameStatus.getPlayerCards().get(player).get(0)).getDice(5 * row + column) != null)
                    l.setGraphic(toImage(((WindowPatternCard)gameStatus.getPlayerCards().get(player).get(0)).getDice(5 * row + column)));
                GridPane.setConstraints(l, column, row);
                gridPane.getChildren().add(l);
                l.setOnMouseClicked(this::handleClickWindowPattern);
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

    private ImageView toImage(PublicObjectiveCard publicObjectiveCard) {
        Image image = new Image(publicObjectiveCard.getPublicObjectiveCardImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(200);
        return imageView;
    }

    private ImageView toImage(PrivateObjectiveCard privateObjectiveCard) {
        Image image = new Image(privateObjectiveCard.getPrivateObjectiveCardImage());
        ImageView imageView = new ImageView(image);
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
     * @param toolCard toolcard whose image is required
     * @return imageview of the toolcard
     */
    private ImageView toImage(ToolCard toolCard) {
        Image image = new Image(toolCard.getToolCardImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(200);
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
    public int getDiceClickedIndexDraftpool(){
        return draftpoolindex;
    }

    /**
     * @return index of window pattern card's cell clicked
     */
    public int getWPindexDice(){
        return indice_dado;
    }

    /**
     * @param gameStat status of the game (window pattern cards, draft pool, dice bag, round track, ...)
     */
    @Override
    public void updateGameStatus(GameStatus gameStat) {
        gameStatus = gameStat;
        Platform.runLater(() -> switchScene(Board));
        resetAllIndex();
        setGO(false);
    }

    @Override
    public void activate(){
        Platform.runLater(() -> {
            attivo = true;
            switchScene(Board);
        });
    }

    @Override
    public void disable() {
        Platform.runLater(() -> {
            attivo = false;
            switchScene(Board);
        });
    }

    public void pingBack(){
        System.out.println("PingBackGame: " + username);
        try {
            clientServerSender.pingBack(username);
        } catch (RemoteException e) {
            GUI.generiClient.manageDisconnection(GUI.username,GUI.ip,Integer.parseInt(GUI.port));
        }
    }

    @Override
    public ToolCard getChoosenToolCard(){
        return gameStatus.getToolCards().get(toolCardSelected);
    }

    @Override
    public int getDraftPoolIndex() {
            Thread t = new Thread(() -> {
                synchronized (lockDraftPool) {
                    while (draftpoolindex == -1 && GO) {
                        try {
                            lockDraftPool.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!GO)
                        return;
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int indice = draftpoolindex;
            return indice;
    }

    @Override
    public int getMatrixIndexFrom(){
        Thread t = new Thread(() -> {
            synchronized (lockWPFrom) {
                while ((indice_dado == -1)&& GO) {
                    try {
                        lockWPFrom.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }if (!GO)
                    return;
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int indice = indice_dado;
        return indice;

    }

    @Override
    public int getMatrixIndexTo() {

        Thread t = new Thread(() -> {
            synchronized (lockWPTo) {
                while (indice_dadoPrecedente==-1&& GO){
                    try {
                        lockWPTo.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!GO)
                return;
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int indice = indice_dado;
        indice_dadoPrecedente = -1;
        indice_dado = -1;
        return indice;

    }

    private void resetAllIndex(){
        draftpoolindex = -1;
        indiceWPFrom = -1;
        indice_dado = -1;
        roundIndex = -1;
        diceRoundIndex = -1;
        toolCardSelected = -1;
        amountIndex = 0;
        askAnotherReady= -1;
    }

    @Override
    public int getAmmountToChange(int ammountType) {
        if (ammountType == 0) {
            Platform.runLater((() -> {
                amountStage = new Stage();
                amountStage.setTitle("Get amount to change");
                HBox layoutH = new HBox(30);
                VBox layoutV = new VBox(30);
                layoutV.setAlignment(Pos.CENTER);
                layoutH.setAlignment(Pos.CENTER);
                Label text = new Label("Increase or decrease dice amount by 1?");
                Button increase = new Button("Increase");
                increase.setOnAction(e -> {
                    amountIndex = 1;
                    synchronized (lockAmount) {
                        lockAmount.notifyAll();
                    }
                    amountStage.close();
                });
                Button decrease = new Button("Decrease");
                decrease.setOnAction(e -> {
                    amountIndex = -1;
                    synchronized (lockAmount) {
                        lockAmount.notifyAll();
                    }
                    amountStage.close();
                });
                layoutV.getChildren().addAll(text, layoutH);
                layoutH.getChildren().addAll(increase, decrease);
                Scene scene = new Scene(layoutV);
                amountStage.setScene(scene);
                amountStage.show();
            }));

            Thread t = new Thread(() -> {
                synchronized (lockAmount) {
                    while (amountIndex == 0 && GO) {
                        try {
                            lockAmount.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                if (!GO)
                    return;

            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return amountIndex;
        }
        else {
            Platform.runLater((() -> {
                amountStage = new Stage();
                amountStage.setTitle("Get amount to change");
                HBox layoutH = new HBox(30);
                VBox layoutV = new VBox(30);
                layoutV.setAlignment(Pos.CENTER);
                layoutH.setAlignment(Pos.CENTER);
                Label text = new Label("Choose new dice value");
                createAmountButtons(layoutH);
                layoutV.getChildren().addAll(text, layoutH);
                Scene scene = new Scene(layoutV);
                amountStage.setScene(scene);
                amountStage.show();
            }));

            Thread t = new Thread(() -> {
                synchronized (lockAmount) {
                    while (amountIndex == 0&& GO) {
                        try {
                            lockAmount.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
                if (!GO)
                    return;

            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return amountIndex;
        }
    }

    private void createAmountButtons(HBox hBox){
        Button one = new Button("1");
        one.setOnAction(e -> {
            amountIndex = 1;
            synchronized (lockAmount) {
                lockAmount.notifyAll();
            }
            amountStage.close();
        });
        Button two = new Button("2");
        two.setOnAction(e -> {
            amountIndex = 2;
            synchronized (lockAmount) {
                lockAmount.notifyAll();
            }
            amountStage.close();
        });
        Button three = new Button("3");
        three.setOnAction(e -> {
            amountIndex = 3;
            synchronized (lockAmount) {
                lockAmount.notifyAll();
            }
            amountStage.close();
        });
        Button four = new Button("4");
        four.setOnAction(e -> {
            amountIndex = 4;
            synchronized (lockAmount) {
                lockAmount.notifyAll();
            }
            amountStage.close();
        });
        Button five = new Button("5");
        five.setOnAction(e -> {
            amountIndex = 5;
            synchronized (lockAmount) {
                lockAmount.notifyAll();
            }
            amountStage.close();
        });
        Button six = new Button("6");
        six.setOnAction(e -> {
            amountIndex = 6;
            synchronized (lockAmount) {
                lockAmount.notifyAll();
            }
            amountStage.close();
        });

        hBox.getChildren().addAll(one, two, three, four, five, six);
    }

    /**
     * @param mouseEvent event caused by the user (eg. clicking mouse)
     */
    private void handleClickBoardRound(MouseEvent mouseEvent) {
            roundIndex = Integer.parseInt(((Label) mouseEvent.getSource()).getText());
        synchronized (lockRoundIndex){
            lockRoundIndex.notifyAll();
        }
            Platform.runLater(() -> {
            if (roundTrack.size() >= roundIndex){
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Dices");
                window.setMinWidth(250);
                window.setMinHeight(100);
                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10,10,10,10));
                for (int j = 0; j < roundTrack.get(roundIndex-1).size(); j++) {
                    Label label = new Label();
                    int finalJ = j;
                    label.setGraphic(toImage(roundTrack.get(roundIndex-1).get(j)));
                    layout.getChildren().add(label);
                    label.setOnMouseClicked(e -> {
                        handleClickDiceRound(e, finalJ);
                        window.close();
                    });
                }
                layout.setAlignment(Pos.TOP_CENTER);
                Scene scene = new Scene(layout);
                window.setScene(scene);
                window.showAndWait();

            }
            else{
                createAlertBox("This turn has to be played, no dices");
            }
        });
    }

    private void handleClickDiceRound(MouseEvent mouseEvent, int index){
        Platform.runLater(() -> {
            ButtonBar.ButtonData clicked = createConfirmationBox("Do you want to choose this dice?");
            if (clicked.equals(ButtonBar.ButtonData.OK_DONE)) {
                diceRoundIndex = index;
            }
            synchronized (lockDiceRoundIndex){
                lockDiceRoundIndex.notifyAll();
            }
        });

    }

    @Override
    public int getRoundIndex(){
        Thread t = new Thread(() -> {
            synchronized (lockRoundIndex) {
                while (roundIndex == -1&& GO) {
                    try {
                        lockRoundIndex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!GO)
                    return;
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return roundIndex-1;
    }

    @Override
    public int getDiceIndexFromRound() {
        Thread t = new Thread(() -> {
            synchronized (lockDiceRoundIndex) {
                while (diceRoundIndex == -1&& GO) {
                    try {
                        lockDiceRoundIndex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!GO)
                    return;
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return diceRoundIndex;
    }

    @Override
    public boolean askForAnotherDice() {
        final boolean[] ans = new boolean[1];
        Platform.runLater((() -> {
            anotherDice = new Stage();
            anotherDice.setTitle("Place another dice?");
            HBox layoutH = new HBox(30);
            VBox layoutV = new VBox(30);
            layoutV.setAlignment(Pos.CENTER);
            layoutH.setAlignment(Pos.CENTER);
            Label text = new Label("Do you want to place another dice?");
            Button yes = new Button("Yes");
            yes.setOnAction(e -> {
                ans[0] = true;
                askAnotherReady = 0;
                synchronized (lockAskanother){
                    lockAskanother.notify();
                }

                anotherDice.close();
            });
            Button no = new Button("No");
            no.setOnAction(e -> {
                ans[0] = false;
                askAnotherReady= 0;
                synchronized (lockAskanother){
                    lockAskanother.notify();
                }
                anotherDice.close();
            });
            layoutV.getChildren().addAll(text, layoutH);
            layoutH.getChildren().addAll(yes, no);
            Scene scene = new Scene(layoutV);
            anotherDice.setScene(scene);
            anotherDice.show();
        }));



        Thread t1 = new Thread(()->{
            synchronized (lockAskanother){
                while(askAnotherReady==-1 && GO){
                    try {
                        lockAskanother.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!GO)
                    return;
            }
        });

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return ans[0];



    }

    @Override
    public void endGame(String winner){
        Platform.runLater(() -> {
            gameWinner = winner;
            switchScene(Score);
        });
    }

}