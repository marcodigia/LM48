package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXLogin.clientServerSender;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXLogin.playersName;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXConnection.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.id1;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.id2;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.id3;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.id4;
import static it.polimi.ingsw.Client.GUI.GUIimpl.username;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.ChooseWP;


public class ControllerJavaFXLobby extends GUI implements Initializable{

    public Label player1, player2, player3, player4;
    public Button startbutton;
    public AnchorPane anchorlobby;
    public ImageView bg3;
    private ArrayList<Label> players = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            clientServerReciver.setUI(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        setBackground(bg3, anchorlobby);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

       for (int i=0; i<playersName.size(); i++){
            players.get(i).setText(playersName.get(i));
        }
    }


    /**
     * @param wp1fronte front side of 1st window pattern card that player has to choose
     * @param wp2retro back side of 1st window pattern card that player has to choose
     * @param wp3fronte front side of 2nd window pattern card that player has to choose
     * @param wp4retro back side of 2nd window pattern card that player has to choose
     */
    @Override
    public void chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {

        id1 = wp1fronte;
        id2 = wp2retro;
        id3 = wp3fronte;
        id4 = wp4retro;

        Platform.runLater(new Runnable() {
            @Override
            public void run() { switchScene(ChooseWP); }
        });
    }

    public void pingBack(){
        try {
            clientServerSender.pingBack(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param players string containing all players
     */
    @Override
    public void allCurrentPlayers(String players){
        for (int i=0; i<playersName.size(); i++){
            this.players.get(i).setText(playersName.get(i));
        }
    }
}