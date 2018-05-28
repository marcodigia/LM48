package it.polimi.ingsw.Server.Game.ServerRMI;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

import it.polimi.ingsw.Server.Game.ServerRete.Game;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

public class ServerRMI {

    private String ip;
    private int port;
    private boolean bound = false;
    private WaitingRoom waitingRoom;
    private Game game;

    public ServerRMI(int port, WaitingRoom waitingRoom, Game game, String ipRMI){
        this.port = port;
        this.waitingRoom = waitingRoom;
        this.game = game;
        ip = ipRMI;
    }
    public void start(){
        do{
            try{
                java.rmi.registry.LocateRegistry.createRegistry(port);
            }  catch(RemoteException e){
                if(e instanceof ExportException)
                    port++;
            }
        }while(!bound && port < 2000);
        try {
            StubServerImp stubServer = new StubServerImp(waitingRoom,game);
            Naming.rebind("rmi://"+ip+"/myabc",stubServer);
        } catch (RemoteException e) {


            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
    }
}
