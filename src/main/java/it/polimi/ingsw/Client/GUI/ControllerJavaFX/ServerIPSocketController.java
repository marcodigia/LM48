package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.GUIimpl.generiClient;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXConnection.clientServerReciver;

public class ServerIPSocketController extends GUI implements Initializable {

    public AnchorPane anchorip;
    public ImageView bg;
    public Button button;
    public TextField serverIP;
    public TextField serverPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg, anchorip);
        bg.autosize();
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

    @Override
    public void allCurrentPlayers(String players) {

    }

    @Override
    public ToolCard getChoosenToolCard() {
        return null;
    }

    @FXML
    public void handleClickButton(ActionEvent event) {
        String fxml = "/Login.fxml";
        generiClient = new GeneriClient();
        generiClient.setLinkClientServer(serverIP.getText(), Integer.parseInt(serverPort.getText()));
        generiClient.setClientServerReciver();
        generiClient.setClientServerSender();
        clientServerReciver = generiClient.getClientServerReciver();
        try {
            switchScene(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
