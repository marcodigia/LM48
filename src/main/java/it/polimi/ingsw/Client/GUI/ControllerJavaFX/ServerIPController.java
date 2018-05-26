package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerIPController extends GUI implements Initializable {

    public AnchorPane anchorip;
    public ImageView bg;
    public Button button;
    public TextField serverIP;

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

    public void handleClickButton(ActionEvent event) {
        if (serverIP.getLength() > 0) {
            String fxml = "/Login.fxml";
            try {
                switchScene(fxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            createAlertBox("Error", "Wrong server", "Please enter a valid one");
        }
    }
}
