package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.Controllers.ControllerLobby.users;

public class ControllerGame extends Controller implements Initializable {

    public Label p1, p2, p3, p4,
                 cellp400, cellp401, cellp402, cellp403, cellp404,
                 cellp410, cellp411, cellp412, cellp413, cellp414,
                 cellp420, cellp421, cellp422, cellp423, cellp424,
                 cellp430, cellp431, cellp432, cellp433, cellp434,
                 draft1, draft2, draft3, draft4, draft5, draft6, draft7, draft8, draft9,
                 round1, round2, round3, round4, round5, round6, round7, round8, round9, round10;
    public MenuItem showpublic, showprivate, showtool, showcopyright;
    public ImageView bg4;
    public AnchorPane anchorgame;
    public GridPane gp1, gp2, gp3, gp4;
    public HBox hboxgp1, hboxgp2, hboxgp3, hboxgp4, hboxl1, hboxl2, hboxl3, hboxl4;

    private int draftpoolindex = -1;
    private static Label draftToDisable;
    private WindowPatternCard windowPatternCard;
    private DraftPool draftPool;

    private ArrayList<Label> draftPoolLabel = new ArrayList<>();
    private ArrayList<Label> cells = new ArrayList<>();
    private ArrayList<Label> names = new ArrayList<>();
    private ArrayList<Label> round = new ArrayList<>();
    private ArrayList<String> pattern = new ArrayList<String>();

    private boolean put = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg4, anchorgame);

        round.add(round1);
        round.add(round2);
        round.add(round3);
        round.add(round4);
        round.add(round5);
        round.add(round6);
        round.add(round7);
        round.add(round8);
        round.add(round9);
        round.add(round10);

        names.add(p1);
        names.add(p2);
        names.add(p3);
        names.add(p4);

        cells.add(cellp400);
        cells.add(cellp401);
        cells.add(cellp402);
        cells.add(cellp403);
        cells.add(cellp404);
        cells.add(cellp410);
        cells.add(cellp411);
        cells.add(cellp412);
        cells.add(cellp413);
        cells.add(cellp414);
        cells.add(cellp420);
        cells.add(cellp421);
        cells.add(cellp422);
        cells.add(cellp423);
        cells.add(cellp424);
        cells.add(cellp430);
        cells.add(cellp431);
        cells.add(cellp432);
        cells.add(cellp433);
        cells.add(cellp434);

        draftPoolLabel.add(draft1);
        draftPoolLabel.add(draft2);
        draftPoolLabel.add(draft3);
        draftPoolLabel.add(draft4);
        draftPoolLabel.add(draft5);
        draftPoolLabel.add(draft6);
        draftPoolLabel.add(draft7);
        draftPoolLabel.add(draft8);
        draftPoolLabel.add(draft9);

        for (Label l: round
             ) {
            l.setText("#");
        }

        setUpBoards();
        setUpWP();
        setUpDP();

        for (int i = 0; i < 9; i++) {
            draftPoolLabel.get(i).setGraphic(toImage(draftPool.getDraft().get(i)));
        }
    }

    public void handleClickDraft(MouseEvent mouseEvent) {
        Label eventDraft = (Label) mouseEvent.getSource();
        createConfirmationBox("Confirm Dice", "Do you want to place this dice?", "y/n");
        draftpoolindex = draftPoolLabel.indexOf(eventDraft);
        draftToDisable = eventDraft;
        put = false;
    }

    public void handleClickWP(MouseEvent mouseEvent) {
        Label event = (Label) mouseEvent.getSource();
        int indice_dado = cells.indexOf(event);
        if (windowPatternCard.getDice(indice_dado)== null && !put) {
            windowPatternCard.placeDice(draftPool.getDice(draftpoolindex), indice_dado, true,true,true);
            event.setText("");
            updateWP();
            draftToDisable.setDisable(true);
            put = true;
            //updateDP();
        } else {
            createAlertBox("Error!", "Wrong action", "You already placed this dice or in this cell has already been placed a dice! Please perform a correct move.");
        }
    }

    public void handleShow(ActionEvent event) {
        if (event.getSource().equals(showtool))
            openWindowFromMenu("Tool Cards");
        if (event.getSource().equals(showpublic))
            openWindowFromMenu("Public Objective Cards");
        if (event.getSource().equals(showprivate))
            openWindowFromMenu("Private Objective Cards");
    }

    public void handleCR(ActionEvent event){
        createInfoBox("Copiright ©", "Software Engineering Project\nAll rights reserved", "Sagrada\nby Marco Di Giacomantonio, Matthias Carretta and Fabio Dalle Rive\n:D");
    }

    void setUpWP(){
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

    void setUpDP(){
        draftPool = new DraftPool(new DiceBag());
        draftPool.extractNdice(9);
    }

    private void updateWP(){
        for (int i=0; i<cells.size(); i++) {
            if (windowPatternCard.getDice(i)!=null)
                cells.get(i).setGraphic(toImage(windowPatternCard.getDice(i)));
        }
    }

    private ImageView toImage(Dice dice){
        Image image = new Image(dice.getDiceImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    private void openWindowFromMenu(String string){
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

    private void setUpBoards(){
        switch (users.size()) {
            case 1:
                hboxgp1.getChildren().remove(gp1);
                hboxl1.getChildren().remove(p1);
                hboxgp2.getChildren().remove(gp2);
                hboxl2.getChildren().remove(p2);
                hboxgp3.getChildren().remove(gp3);
                hboxl3.getChildren().remove(p3);
                p4.setText(users.get(0) + " (You)");
                break;
            case 2:
                hboxgp2.getChildren().remove(gp2);
                hboxl2.getChildren().remove(p2);
                hboxgp3.getChildren().remove(gp3);
                hboxl3.getChildren().remove(p3);
                p1.setText(users.get(1) + " (Opponent)");
                p4.setText(users.get(0) + " (You)");
                break;
            case 3:
                hboxgp1.getChildren().remove(gp1);
                hboxl1.getChildren().remove(p1);
                p2.setText(users.get(2) + " (Opponent)");
                p3.setText(users.get(1) + " (Opponent)");
                p4.setText(users.get(0) + " (You)");
                break;
            case 4:
                p1.setText(users.get(3) + " (Opponent)");
                p2.setText(users.get(2) + " (Opponent)");
                p3.setText(users.get(1) + " (Opponent)");
                p4.setText(users.get(0) + " (You)");
                break;
        }
    }
}