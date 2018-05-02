package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Server.Game.GameRules.Player;

public class UI_SIMULATION extends UI {
    @Override
    public void printMessage(String s) {
        System.out.println(s);
    }

    @Override
    public int getAmmountToChange() {
        return 1;
    }

    @Override
    public int getDraftPoolIndex() {
        return 0;
    }

    @Override
    public int getMatrixIndexFrom() {
        return 0;
    }

    @Override
    public int getMatrixIndexTo() {
        return 1;
    }

    @Override
    public int getRoundTrackIndex() {
        return 0;
    }

    @Override
    public void UpdateDraftPol() {

    }

    @Override
    public void updateWindowPattern(Player player) {

    }

    @Override
    public void updateRoundTrack() {

    }
}
