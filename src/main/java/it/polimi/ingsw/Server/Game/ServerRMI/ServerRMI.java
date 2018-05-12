package it.polimi.ingsw.Server.Game.ServerRMI;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;
import it.polimi.ingsw.Server.Game.WaitingRoom.WaitingRoom;

public class ServerRMI {
    private int port;
    private boolean bound = false;
    private WaitingRoom waitingRoom;

    public ServerRMI(int port, WaitingRoom waitingRoom){
        this.port = port;
        this.waitingRoom = waitingRoom;
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
            StubServerImp stubServer = new StubServerImp(waitingRoom);
            Naming.rebind("rmi://127.0.0.1/myabc",stubServer);
        } catch (RemoteException e) {


            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
    }
}
