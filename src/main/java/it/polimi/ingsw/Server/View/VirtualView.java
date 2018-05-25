package it.polimi.ingsw.Server.View;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Score;

public interface VirtualView {

    void sendMessage(String message);
    void chooseWindowPattern(String id1, String id2, String id3, String id4);
    void timerEnd();
    void timerStart();
    void sendGameStatus(GameStatus gameStatus);
    void sendScore(Score score);

}
