package it.polimi.ingsw.Server.Game.ServerRMI;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;

public class ServerRMI {
    private int port;
    private boolean bound = false;

    public ServerRMI(int port){
        this.port = port;
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
            StubServerImp stubServer = new StubServerImp();
            Naming.rebind("rmi://127.0.0.1/myabc",stubServer);
        } catch (RemoteException e) {


            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
    }
}
