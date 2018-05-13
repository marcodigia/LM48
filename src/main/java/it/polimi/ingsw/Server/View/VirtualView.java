package it.polimi.ingsw.Server.View;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Score;

public interface VirtualView {

    public void sendMessage(String message);
    public void chooseWindowPattern(String id1, String id2, String id3, String id4);
    public void timerEnd();
    public void timerStart();
    public void sendGameStatus(GameStatus gameStatus);
    public void sendScore(Score score);

}
