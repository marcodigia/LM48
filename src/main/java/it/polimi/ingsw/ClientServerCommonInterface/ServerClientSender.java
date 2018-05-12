package it.polimi.ingsw.ClientServerCommonInterface;


import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Score;

import java.rmi.RemoteException;

public interface ServerClientSender {
    public void sendMessage(String message) throws RemoteException;
    public void chooseWindowPattern(String id1, String id2, String id3, String id4) throws RemoteException;
    public void timerEnd() throws RemoteException;
    public void timerStart() throws RemoteException;
    public void sendGameStatus(GameStatus gameStatus) throws RemoteException;
    public void sendScore(Score score) throws RemoteException;
    //In severclientsenderImp metti ping per gestione disconnessioni
}
