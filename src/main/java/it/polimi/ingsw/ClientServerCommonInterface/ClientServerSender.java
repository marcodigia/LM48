package it.polimi.ingsw.ClientServerCommonInterface;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

import java.rmi.RemoteException;

public interface ClientServerSender {
    public void pingBack(String username) throws RemoteException;
    public void register(String username, ServerClientSender clientRef) throws RemoteException;
    public void unregister(String username) throws RemoteException;
    public void choosenWindowPattern(String id, String username) throws RemoteException;
    public void sendAction(Actions action, String username) throws RemoteException;
    public void endOfTurn(String username) throws RemoteException;
    public void close() throws RemoteException;
}
