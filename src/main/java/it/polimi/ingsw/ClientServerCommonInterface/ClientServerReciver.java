package it.polimi.ingsw.ClientServerCommonInterface;

import it.polimi.ingsw.UI;

import java.rmi.RemoteException;

public interface ClientServerReciver {
    void setUI(UI ui) throws RemoteException;
    void close() throws RemoteException;
}
