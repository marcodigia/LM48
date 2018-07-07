package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.GameRules.EndGame.ScoreHandler;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static it.polimi.ingsw.Client.GUI.ControllerGame.gameWinner;
import static it.polimi.ingsw.Client.GUI.ControllerConnection.clientServerReciver;
import static it.polimi.ingsw.Client.GUI.ControllerGame.gameStatus;

public class ControllerScore extends AbstractGUI implements Initializable {

    public ImageView bgscore;
    public AnchorPane anchorscore;
    public Label p1, p2, p3, p4, s1, s2, s3, s4, winner;
    private ArrayList<Label> players = new ArrayList<>();
    private ArrayList<Label> scores = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            clientServerReciver.setUI(this);
        } catch (RemoteException e) {
            GUI.generiClient.manageDisconnection(GUI.username,GUI.ip,Integer.parseInt(GUI.port));
        }
        ScoreHandler scoreHandler = new ScoreHandler(gameStatus);

        setBackground(bgscore, anchorscore);

        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        scores.add(s1);
        scores.add(s2);
        scores.add(s3);
        scores.add(s4);

        for (int i = 0; i < gameStatus.getPlayer().size(); i++) {
            players.get(i).setText(gameStatus.getPlayer().get(i).getName());
            scores.get(i).setText(scoreHandler.getFinalScore().get(
                    gameStatus.getPlayerByName(players.get(i).getText())).toString());
        }
        winner.setText(gameWinner);
    }
}
