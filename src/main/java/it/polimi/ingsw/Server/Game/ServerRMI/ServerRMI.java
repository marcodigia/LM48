package it.polimi.ingsw.Server.Game.ServerRMI;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;

public class ServerRMI {
    public void start(){
        try {
            //TODO connect to another port if this one is occupied
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            StubServerImp stubServer = new StubServerImp();
            Naming.rebind("rmi://192.168.43.243/myabc",stubServer);
        } catch (RemoteException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }
    }
}
