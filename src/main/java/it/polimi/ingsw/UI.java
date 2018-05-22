package it.polimi.ingsw;

import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.GameRules.Player;

public abstract class UI {

    //THOSE METHOD SHOULD UNLOCK A SPECIFC ZONE IN THE GUI IN ORDER TO ALLOW THE INPUT, AND JUST PRINT THE MESSAGE IN THE CLI
    //es. getDraftPoolIndex shuld unlock the draftpool so the user can select a Dice.

    // NB ALL FUNCIONS RETURN -1 default

    public abstract void printMessage(String s);

    //NB this function must return 0 default
    public abstract int getAmmountToChange();

    //UI must verify that value is in between bounds
    public abstract int getDraftPoolIndex();

    //UI must verify that value is in between bounds
    public abstract int getMatrixIndexFrom();

    //UI must verify that value is in between bounds
    public abstract int getMatrixIndexTo();

    public abstract String chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro);

    public abstract int getRoundTrackIndex();

    public abstract void UpdateDraftPol();

    public abstract void updateWindowPattern(Player player);

    public abstract void updateRoundTrack();

    //UI active
    public abstract void activate();

}
