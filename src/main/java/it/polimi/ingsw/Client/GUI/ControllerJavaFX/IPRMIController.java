package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import com.sun.media.sound.RIFFInvalidDataException;
import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXConnection.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.GUIimpl.generiClient;
import static it.polimi.ingsw.Client.GUI.GUIimpl.ip;

public class IPRMIController extends GUI implements Initializable{

    public static String rmiIP = new String("");

    public TextField RMIIP;
    public ImageView bg;
    public AnchorPane anchorip;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground(bg, anchorip);
        RMIIP.setText(ip);
        bg.autosize();
    }

    public void hanldeClickButton(ActionEvent event) {
        rmiIP = RMIIP.getText();
        String fxml = "/Login.fxml";
        try {
            switchScene(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEnter(ActionEvent enter) {
        hanldeClickButton(enter);
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
}
