package it.polimi.ingsw.Server.View;

import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.UI;

public class FakeUI implements UI {
    @Override
    public void printMessage(String s) {

    }

    @Override
    public int getAmmountToChange(int ammountType) {
        return -2;
    }

    @Override
    public int getDraftPoolIndex() {
        return -1;
    }

    @Override
    public int getMatrixIndexFrom() {
        return -1;
    }

    @Override
    public int getMatrixIndexTo() {
        return -1;
    }

    @Override
    public boolean askForAnotherDice() {
        return false;
    }

    @Override
    public void chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {

    }

    @Override
    public void updateGameStatus(GameStatus gameStatus) {

    }

    @Override
    public void activate() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void pingBack() {

    }

    @Override
    public void allCurrentPlayers(String players) {

    }

    @Override
    public ToolCard getChoosenToolCard() {
        return null;
    }

    @Override
    public int getRoundIndex() {
        return -1;
    }

    @Override
    public int getDiceIndexFromRound() {
        return -1;
    }

    @Override
    public void endGame(String winner) {

    }
}
