package it.polimi.ingsw.Client.View;

import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.UI;

public class UI_SIMULATION implements UI {


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
    public void printMessage(String s){
        System.out.println(s);
    }

    @Override
    public int getAmmountToChange(){
        return ammountToChange;
    }

    @Override
    public int getDraftPoolIndex(){
        return drafFrom;
    }

    @Override
    public int getMatrixIndexFrom(){
        return matrixFrom;

    }

    @Override
    public int getMatrixIndexTo(){
        return matrixTo;
    }

    @Override
    public String chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {
        return null;
    }

    @Override
    public int getRoundTrackIndex() {
        return roundTrackIndex;
    }

    @Override
    public void updateGameStatus(GameStatus gameStatus) {

    }

    @Override
    public void activate() {

    }

    @Override
    public void allCurrentPlayers(String players) {

    }
}
