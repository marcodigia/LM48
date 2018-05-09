package it.polimi.ingsw.Client.ClientRMI;

import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.SkeletonClient;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SkeletonClientImp extends UnicastRemoteObject implements SkeletonClient{
     public SkeletonClientImp() throws RemoteException {
    }

    @Override
    public void sendHello() throws RemoteException {
        System.out.println("Hello");
    }
}
