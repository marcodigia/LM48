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
    public Label p4;
    private ArrayList<Label> cells;
    private String buffer;
    private boolean put = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("loaded");
        p4.setText(user);
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
