package it.polimi.ingsw.Server.View;


import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.UI;

import java.rmi.RemoteException;

public class VirtualView extends UI {

    ServerClientSender serverClientSender;

    public VirtualView(ServerClientSender serverClientSender) {
        this.serverClientSender = serverClientSender;
    }

    @Override
    public void printMessage(String s) throws EndOfTurnException {
        try {
            serverClientSender.sendMessage(s);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAmmountToChange() throws EndOfTurnException {
        return 0;
    }

    @Override
    public int getDraftPoolIndex() throws EndOfTurnException {
        return 0;
    }

    @Override
    public int getMatrixIndexFrom() throws EndOfTurnException {
        return 0;
    }

    @Override
    public int getMatrixIndexTo() throws EndOfTurnException {
        return 0;
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
