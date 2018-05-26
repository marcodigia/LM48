package it.polimi.ingsw.Client.GUI.ControllerJavaFX;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
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

import static it.polimi.ingsw.Client.GUI.GUIimpl.username;
import static it.polimi.ingsw.Client.GUI.GUIimpl.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.id1;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.id2;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.id3;
import static it.polimi.ingsw.Client.GUI.ControllerJavaFX.ControllerJavaFXChooseWP.id4;


public class ControllerJavaFXLobby extends GUI implements Initializable{

    public Label player1, player2, player3, player4;
    public Button startbutton;
    public AnchorPane anchorlobby;
    public ImageView bg3;
    protected static ArrayList<Label> players = new ArrayList<>();
    protected static ArrayList<String> playersName = new ArrayList<>();

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
       // players.add(player4);

        playersName.add(username);
        playersName.add(username);
        playersName.add(username);
        //playersName.add(username);
        
        for (int i=0; i<players.size(); i++){
            players.get(i).setText(playersName.get(i));
        }
        //players.get(0).setText(playersName.get(0));
    }

    @FXML
    private void handleButtonStart(ActionEvent event) throws IOException {
        String fxml = "/ChooseWP.fxml";
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

        System.out.println("lobby");
    }

    @Override
    public String chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {

        id1 = wp1fronte;
        id2 = wp2retro;
        id3 = wp3fronte;
        id4 = wp4retro;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    handleButtonStart(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return  null;
    }

}