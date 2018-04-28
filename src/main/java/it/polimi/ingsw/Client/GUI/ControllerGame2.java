package it.polimi.ingsw.Client.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerLobby.diceToPut;
import static it.polimi.ingsw.Client.GUI.ControllerLogin.user;

public class ControllerGame2 implements Initializable {

    private static Label draftToDisable;
    public Label cellp400, cellp401, cellp402, cellp403, cellp404,
            cellp410, cellp411, cellp412, cellp413, cellp414,
            cellp420, cellp421, cellp422, cellp423, cellp424,
            cellp430, cellp431, cellp432, cellp433, cellp434,
            draft1, draft2, draft3, draft4, draft5, draft6, draft7, draft8, draft9,
            p4;
    private ArrayList<Label> cells;
    private String buffer;
    private boolean put = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("loaded");
        p4.setText(user);
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

    }

    @FXML
    public void handleClickDraft(MouseEvent mouseEvent) {
        System.out.println("Draft");
        Label eventDraft = (Label) mouseEvent.getSource();
        System.out.println(eventDraft.getText());
        confirmDice();
        buffer = eventDraft.getText();
        draftToDisable = eventDraft;

    }

    @FXML
    public void handleClickWP(MouseEvent mouseEvent) {
        diceToPut = buffer;
        System.out.println("WP");
        Label event = (Label) mouseEvent.getSource();
        if (cells.contains(event) && !put) {
            System.out.println("WP in");
            event.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            String s = "You already placed this dice!" + "Please select another dice.";
            alert.setContentText(s);
            alert.showAndWait();
        }
    }

    private void confirmDice() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dice");
        String s = "Do you want to place this dice?";
        alert.setContentText(s);
        alert.showAndWait();
    }
}
