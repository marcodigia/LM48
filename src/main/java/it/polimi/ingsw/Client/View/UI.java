package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Server.Game.GameRules.Player;

public abstract class UI {

    public abstract int getDraftPoolIndex();

    public abstract int getMatrixIndexFrom();

    public abstract int getMatrixIndexTo();

    public abstract int getRoundTrackIndex();

    public abstract void UpdateDraftPol();

    public abstract void updateWindowPattern(Player player);

    public abstract void updateRoundTrack();

}
