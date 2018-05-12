package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.UI;

public class UI_SIMULATION extends UI {


    int ammountToChange;
    int drafFrom;
    int matrixFrom;
    int matrixTo;
    int roundTrackIndex;

    public UI_SIMULATION(int ammountToChange, int drafFrom, int matrixFrom, int matrixTo, int roundTrackIndex) {
        this.ammountToChange = ammountToChange;
        this.drafFrom = drafFrom;
        this.matrixFrom = matrixFrom;
        this.matrixTo = matrixTo;
        this.roundTrackIndex = roundTrackIndex;
    }

    @Override
    public void printMessage(String s) throws EndOfTurnException {
        System.out.println(s);
    }

    @Override
    public int getAmmountToChange() throws EndOfTurnException {
        return ammountToChange;
    }

    @Override
    public int getDraftPoolIndex() throws EndOfTurnException {
        return drafFrom;
    }

    @Override
    public int getMatrixIndexFrom() throws EndOfTurnException {
        return matrixFrom;

    }

    @Override
    public int getMatrixIndexTo() throws EndOfTurnException {
        return matrixTo;
    }

    @Override
    public String chooseWP(String s, String s1) throws EndOfTurnException {
        return null;
    }

    @Override
    public int getRoundTrackIndex() {
        return roundTrackIndex;
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
