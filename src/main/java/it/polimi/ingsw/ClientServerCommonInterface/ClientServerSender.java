package it.polimi.ingsw.ClientServerCommonInterface;

import java.rmi.RemoteException;

public interface ClientServerSender {
    public void register(String username, ServerClientSender clientRef) throws RemoteException;
}
