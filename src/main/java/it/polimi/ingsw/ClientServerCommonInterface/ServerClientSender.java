package it.polimi.ingsw.ClientServerCommonInterface;

import java.rmi.RemoteException;

public interface ServerClientSender {
    public void sendMessage(String message) throws RemoteException;
}
