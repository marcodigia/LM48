package it.polimi.ingsw.Client.ClientRMI;

import it.polimi.ingsw.Client.AbstractClient.LinkClientServer;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;
import java.rmi.RemoteException;

public class ClientRMI extends LinkClientServer{

    private StubServer stubServer;
    private SkeletonClientImp skeletonClient;

    public ClientRMI(){
        try {
            skeletonClient = new SkeletonClientImp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    public ClientServerSender getClientServerSender() {
        return stubServer;
    }

    @Override
    public ClientServerReciver getClientServerReciver() {
        return skeletonClient;
    }
}
