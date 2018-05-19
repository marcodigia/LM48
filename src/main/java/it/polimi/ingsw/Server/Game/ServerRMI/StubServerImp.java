package it.polimi.ingsw.Server.Game.ServerRMI;

import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StubServerImp extends UnicastRemoteObject implements StubServer{

    private WaitingRoom waitingRoom;
    private Game game;

    public StubServerImp(WaitingRoom waitingRoom, Game game) throws RemoteException {
        this.waitingRoom = waitingRoom;
        this.game = game;
    }

    @Override
    public void register(String username, ServerClientSender clientRef) throws RemoteException {
        waitingRoom.addClient(username, clientRef);
    }

    @Override
    public void unregister(String username) throws RemoteException {
        waitingRoom.removeClient(username);
        game.setPlayerAsDisconnected(username);
    }

    @Override
    public void choosenWindowPattern(String id) throws RemoteException {

    }

    @Override
    public void sendAction(Actions action) throws RemoteException {

    }

    @Override
    public void endOfTurn() throws RemoteException {

    }
}
