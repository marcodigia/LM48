package it.polimi.ingsw.ClientServerCommonInterface;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

import java.rmi.RemoteException;

public interface ClientServerSender {
    public void register(String username, ServerClientSender clientRef) throws RemoteException;
    public void unregister(String username) throws RemoteException;
    public void choosenWindowPattern(String id) throws RemoteException;
    public void sendAction(Actions action) throws RemoteException;

}
