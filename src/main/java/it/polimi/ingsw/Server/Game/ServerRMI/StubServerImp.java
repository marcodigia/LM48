package it.polimi.ingsw.Server.Game.ServerRMI;

import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StubServerImp extends UnicastRemoteObject implements StubServer{

    private WaitingRoom waitingRoom;
    private Game game;

    public StubServerImp(WaitingRoom waitingRoom, Game game) throws RemoteException {
        this.waitingRoom = waitingRoom;
        this.game = game;
    }

    @Override
    public void pingBack(String username) throws RemoteException {

        if(game.scanForUsername(username)!=null) {
            System.out.println("PingBack RMI");
            game.scanForUsername(username).setStillAlive(true); //Still alive in game
            game.scanForUsername(username).setIsConnected();
        }
    }

    @Override
    public void register(String username, ServerClientSender clientRef) throws RemoteException {
        if(!waitingRoom.getNoMorePlayerAccepted())
            waitingRoom.addClient(username, clientRef);
        System.out.println("register RMI: " + game.scanForUsername(username));
        if(game.scanForUsername(username)!=null){
            if(!game.scanForUsername(username).getConnected()) {
                game.scanForUsername(username).setServerClientSender(clientRef);
                game.scanForUsername(username).setStillAlive(true);
                game.scanForUsername(username).setIsConnected();
                game.scanForUsername(username).getvirtualView().sendGameStatus(game.getGameStatus());

            }
        }
    }

    @Override
    public void choosenWindowPattern(String id, String username) throws RemoteException {
        game.setWindowToPlayer(id,username);
    }

    //TODO eliminate instanceof
    @Override
    public void sendAction(Actions action, String username) throws RemoteException {
        if (action instanceof PlaceDiceAction)
            game.getGameStatus().getPlayerByName(username).setPlaceDiceOfTheTurn( (PlaceDiceAction) action) ;
        else
            game.getGameStatus().getPlayerByName(username).setUseToolCardOfTheTurn( (UseToolCardBasic) action); ;

        action.doAction(game.getGameStatus());
        for(Player p : game.getPlayers().keySet()) {
            p.getvirtualView().sendGameStatus(game.getGameStatus());
        }
    }

    @Override
    public void close() throws RemoteException {

    }
}
