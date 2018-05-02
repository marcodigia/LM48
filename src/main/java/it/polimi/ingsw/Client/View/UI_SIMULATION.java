package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Server.Game.GameRules.Player;

public class UI_SIMULATION extends UI {

    int from = 0;
    int from2 = 2;
    int to = 17;
    int to2 = 19;
    boolean flag = true;
    boolean flag2 = true;
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
        if (flag) {
            flag = false;
            return from;
        } else {
            return from2;
        }

    }

    @Override
    public int getMatrixIndexTo() {
        if (flag2) {
            flag2 = false;
            return to2;
        } else {
            return to;
        }
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
