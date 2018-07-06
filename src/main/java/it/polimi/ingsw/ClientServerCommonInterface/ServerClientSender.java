package it.polimi.ingsw.ClientServerCommonInterface;


import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerClientSender {
    /**
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    void ping() throws RemoteException;

    /**
     * This method is used by the server to send a generic message to a specific client.
     *
     * @param message Message that server wants to send to client.
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    void sendMessage(String message) throws RemoteException;

    /**
     * This method is used by the server to send 4 WindowPatternCard to client.
     *
     * @param id1 id of first WindowPatternCard (front)
     * @param id2 id of first WindowPatternCard (back)
     * @param id3 id of second WindowPatternCard (front)
     * @param id4 id of second WindowPatternCard (back)
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    void chooseWindowPattern(String id1, String id2, String id3, String id4) throws RemoteException;

    /**
     * This method notifies end of client's turn.
     *
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    void timerEnd() throws RemoteException;

    /**
     * This method notifies start of client's turn.
     *
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    void timerStart() throws RemoteException;

    /**
     * This method sends client current gameStatus.
     *
     * @param gameStatus It has all the relevant information about the game.
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    void sendGameStatus(GameStatus gameStatus) throws RemoteException;

    /**
     * This method sends client's score.
     *
     *
     * @param player@throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     *
     * */
    void sendScore(Player player) throws RemoteException;

    /**
     * This method sends to client the list of all player.
     *
     * @param player List of all player who are playing or are going to play.
     * @throws RemoteException This interface is used by RMI connection so RemoteException must be used.
     * */
    void sendCurrentPlayers(ArrayList<String> player) throws RemoteException;

}
