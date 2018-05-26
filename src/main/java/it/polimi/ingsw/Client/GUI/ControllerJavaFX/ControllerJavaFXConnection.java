package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.GUIimpl.generiClient;


public class ControllerJavaFXConnection extends GUI implements Initializable {

    public Button rmibutton, socketbutton;
    public AnchorPane anchorconnection;
    public ImageView bg2;
    public static ClientServerReciver clientServerReciver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg2, anchorconnection);
        bg2.autosize();
    }

    @FXML
    private void handleButtonRMI(ActionEvent event) throws IOException {
        String fxml = "/Login.fxml";
        generiClient = new GeneriClient();
        generiClient.setLinkClientServerRMI();
        generiClient.setClientServerReciverRMI();
        clientServerReciver = generiClient.getClientServerReciver();
        switchScene(fxml);
    }

    @FXML
    private void handleButtonSocket(ActionEvent event) throws IOException {
        String fxml = "/ServerIP.fxml";
        switchScene(fxml);
    }

    @Override
    public int getWPindexDice() {
        return 0;
    }

    @Override
    public void resetWPindex() {

    }

    @Override
    public int getDiceClickedindexDraftpool() {
        return 0;
    }

    @Override
    public void resetDraftPoolindex() {

    }

    @Override
    public void updateGameStatus(GameStatus gameStatus) {

    }

    @Override
    public void activate() {

    }

    @Override
    public void disable() {

    }
}