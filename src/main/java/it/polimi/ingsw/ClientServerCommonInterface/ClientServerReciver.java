package it.polimi.ingsw.ClientServerCommonInterface;

import it.polimi.ingsw.UI;

import java.rmi.RemoteException;

public interface ClientServerReciver {

    /**
     * This method set the UI(User Interface) of client to the current one.
     *
     * @param ui UI(User Interface) that is currently used by this client.
     * */
    void setUI(UI ui) throws RemoteException;

    /**
     * This method closes client receiver side of a connection.
     *
     * */
    void close() throws RemoteException;

}
