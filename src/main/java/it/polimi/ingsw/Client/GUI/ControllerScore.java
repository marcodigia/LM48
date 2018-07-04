package it.polimi.ingsw.Client.GUI;

import it.polimi.ingsw.Server.Game.GameRules.EndGame.ScoreHandler;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class ControllerScore extends AbstractGUI implements Initializable {

    public ImageView bgscore;
    public AnchorPane anchorscore;
    public static GameStatus gameStatus;
    public Label p1, p2, p3, p4, s1, s2, s3, s4;
    private ArrayList<Label> players = new ArrayList<>();
    private ArrayList<Label> scores = new ArrayList<>();
    private ScoreHandler scoreHandler = new ScoreHandler(gameStatus);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
            scores.get(i).setText(scoreHandler.getFinalScore().get(players.get(i).getText()).toString());
        }

    }
}
