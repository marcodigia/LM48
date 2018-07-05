package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerLogin.clientServerSender;
import static it.polimi.ingsw.Client.GUI.ControllerLogin.playersName;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Board;
import static it.polimi.ingsw.Client.GUI.ControllerGame.gameStatus;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.ChooseWP;
import static it.polimi.ingsw.Client.GUI.GUI.username;
import static it.polimi.ingsw.Server.Game.Utility.CONSTANT.Lobby;

public class ControllerLobby extends AbstractGUI implements Initializable{

    public Label player1, player2, player3, player4;
    public AnchorPane anchorlobby;
    public ImageView bg3;
    private ArrayList<Label> players;

    static String id1;
    static String id2;
    static String id3;
    static String id4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        players = new ArrayList<>();

        try {
            ControllerConnection.clientServerReciver.setUI(this);
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

        Platform.runLater(() -> switchScene(ChooseWP));
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
        Platform.runLater(() -> {
            String[] names = players.split("\\s*,\\s*");
            playersName = new ArrayList<>();

            for (String name : names) {
                playersName.add(name);
                System.out.println("Lobby: " + name);
            }
            switchScene(Lobby);
        });
    }

}