package it.polimi.ingsw.Client.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import static it.polimi.ingsw.Client.GUI.ControllerLobby.diceToPut;
import static it.polimi.ingsw.Client.GUI.ControllerLogin.user;

public class ControllerGame implements Initializable {

    public Label cellp400, cellp401, cellp402, cellp403, cellp404,
            cellp410, cellp411, cellp412, cellp413, cellp414,
            cellp420, cellp421, cellp422, cellp423, cellp424,
            cellp430, cellp431, cellp432, cellp433, cellp434,
            draft1, draft2, draft3, draft4, draft5, draft6, draft7, draft8, draft9,
            p4;
    private static Label draftToDisable;
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
        if (mouseEvent.getSource().equals(draft1)) {
            System.out.println("Draft1");
            confirmDice();
            buffer = draft1.getText();
            draftToDisable = draft1;
        }
        else if (mouseEvent.getSource().equals(draft2)){
            System.out.println("Draft2");
            confirmDice();
            buffer = draft2.getText();
            draftToDisable = draft2;
        }
        else if (mouseEvent.getSource().equals(draft3)){
            System.out.println("Draft3");
            confirmDice();
            buffer = draft3.getText();
            draftToDisable = draft3;
        }
        else if (mouseEvent.getSource().equals(draft4)){
            System.out.println("Draft4");
            confirmDice();
            buffer = draft4.getText();
            draftToDisable = draft4;
        }
        else if (mouseEvent.getSource().equals(draft5)){
            System.out.println("Draft5");
            confirmDice();
            buffer = draft5.getText();
            draftToDisable = draft5;
        }
        else if (mouseEvent.getSource().equals(draft6)){
            System.out.println("Draft6");
            confirmDice();
            buffer = draft6.getText();
            draftToDisable = draft6;
        }
        else if (mouseEvent.getSource().equals(draft7)){
            System.out.println("Draft7");
            confirmDice();
            buffer = draft7.getText();
            draftToDisable = draft7;
        }
        else if (mouseEvent.getSource().equals(draft8)){
            System.out.println("Draft8");
            confirmDice();
            buffer = draft8.getText();
            draftToDisable = draft8;
        }
        else if (mouseEvent.getSource().equals(draft9)){
            System.out.println("Draft9");
            confirmDice();
            buffer = draft9.getText();
            draftToDisable = draft9;
        }
        put = false;
    }

    @FXML
    public void handleClickWP(MouseEvent mouseEvent) {
        diceToPut = buffer;
        System.out.println("WP");
        if (mouseEvent.getSource().equals(cellp400) && put==false){
            System.out.println("WP in");
            cellp400.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp401) && put==false){
            System.out.println("WP in");
            cellp401.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp402) && put==false){
            System.out.println("WP in");
            cellp402.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp403) && put==false){
            System.out.println("WP in");
            cellp403.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp404) && put==false){
            System.out.println("WP in");
            cellp404.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp410) && put==false){
            System.out.println("WP in");
            cellp410.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp411) && put==false){
            System.out.println("WP in");
            cellp411.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp412) && put==false){
            System.out.println("WP in");
            cellp412.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp413) && put==false){
            System.out.println("WP in");
            cellp413.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp414) && put==false){
            System.out.println("WP in");
            cellp414.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp420) && put==false){
            System.out.println("WP in");
            cellp420.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp421) && put==false){
            System.out.println("WP in");
            cellp421.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp422) && put==false){
            System.out.println("WP in");
            cellp422.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp423) && put==false){
            System.out.println("WP in");
            cellp423.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp424) && put==false){
            System.out.println("WP in");
            cellp424.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp430) && put==false){
            System.out.println("WP in");
            cellp430.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp431) && put==false){
            System.out.println("WP in");
            cellp431.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp432) && put==false){
            System.out.println("WP in");
            cellp432.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp433) && put==false){
            System.out.println("WP in");
            cellp433.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else if (mouseEvent.getSource().equals(cellp434) && put==false){
            System.out.println("WP in");
            cellp434.setText(diceToPut);
            draftToDisable.setDisable(true);
            put = true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            String s = "You already placed this dice!" + "Please select another dice.";
            alert.setContentText(s);
            alert.showAndWait();
        }
    }

    private void confirmDice(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dice");
        String s = "Do you want to place this dice?";
        alert.setContentText(s);
        alert.showAndWait();
    }
}