package it.polimi.ingsw.Server.View;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Score;

import java.util.ArrayList;

public interface VirtualView {

    void sendCurrentPlayers(ArrayList<String> player);
    void sendMessage(String message);
    void chooseWindowPattern(String id1, String id2, String id3, String id4);
    void timerEnd();
    void timerStart();
    void sendGameStatus(GameStatus gameStatus);
    void sendScore(Score score);

}
