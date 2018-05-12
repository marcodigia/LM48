package it.polimi.ingsw.Server.Game.ServerRMI;

import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StubServerImp extends UnicastRemoteObject implements StubServer{
    private WaitingRoom waitingRoom;
    public StubServerImp(WaitingRoom waitingRoom) throws RemoteException {
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void register(String username, ServerClientSender clientRef) throws RemoteException {
        waitingRoom.addClient(username, clientRef);
    }

    @Override
    public void unregister(String username) throws RemoteException {
        waitingRoom.removeClient(username);
    }
}
