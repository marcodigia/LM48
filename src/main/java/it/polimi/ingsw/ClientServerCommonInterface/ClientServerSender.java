package it.polimi.ingsw.ClientServerCommonInterface;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

import java.rmi.RemoteException;

public interface ClientServerSender {
    /**
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    public void pingBack(String username) throws RemoteException;

    /**
     * This method is used to register a new client. If someone try
     * to register himself/herself with an already used name this
     * would be denied. If someone try to reconnect after he/she left
     * the game, same username will be required to access to the game.
     *
     * @param username Client's username to register.
     * @param clientRef Client's reference to save on server.
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    public void register(String username, ServerClientSender clientRef) throws RemoteException;

    /**
     * This method sends server information about the WindowPatternCard chosen by client
     * with username.
     *
     * @param username Client's username.
     * @param id id of the WindowPatternCard chosen by client.
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    public void choosenWindowPattern(String id, String username) throws RemoteException;

    /**
     * This method is the way through which a client notify the server about
     * what kind of action he/she want to use.
     * <h1>Example</h1>
     * Suppose that client <i>Fabio</i> wants to put a dice, in this case
     * <i>sendAction(PlaceDiceAction,Fabio)</i>
     * will be called.
     *
     * @param action Type of Action that is used by client.
     * @param username Client's username.
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    public void sendAction(Actions action, String username) throws RemoteException;

    /**
     * This method closes client sender side of a connection.
     *
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    public void close() throws RemoteException;
}
