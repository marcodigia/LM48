package it.polimi.ingsw;

import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;

public interface UI {

    //THOSE METHOD SHOULD UNLOCK A SPECIFC ZONE IN THE GUI IN ORDER TO ALLOW THE INPUT, AND JUST PRINT THE MESSAGE IN THE CLI
    //es. getDraftPoolIndex shuld unlock the draftpool so the user can select a Dice.

    // NB ALL FUNCIONS RETURN -1 default

    public void printMessage(String s);

    //NB this function must return 0 default
    public int getAmmountToChange();

    //UI must verify that value is in between bounds
    public int getDraftPoolIndex();

    //UI must verify that value is in between bounds
    public int getMatrixIndexFrom();

    //UI must verify that value is in between bounds
    public int getMatrixIndexTo();

    public void chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro);

    public int getRoundTrackIndex();

    public void updateGameStatus(GameStatus gameStatus);

    //UI active
    public void activate();

    public void disable();

    public void pingBack();

    public void allCurrentPlayers(String players);

    ToolCard getChoosenToolCard();

    int getRoundIndex();
    int getDiceIndexFromRound();


}
