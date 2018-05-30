package it.polimi.ingsw.ClientServerCommonInterface;


import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.GameRules.Score;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerClientSender {
    void ping() throws RemoteException;
    void sendMessage(String message) throws RemoteException;
    void chooseWindowPattern(String id1, String id2, String id3, String id4) throws RemoteException;
    void timerEnd() throws RemoteException;
    void timerStart() throws RemoteException;
    void sendGameStatus(GameStatus gameStatus) throws RemoteException;
    void sendScore(Score score) throws RemoteException;
    void sendCurrentPlayers(ArrayList<String> player) throws RemoteException;
    //In severclientsenderImp metti ping per gestione disconnessioni
}
