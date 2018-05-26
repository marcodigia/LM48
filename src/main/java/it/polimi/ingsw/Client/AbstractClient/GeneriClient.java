package it.polimi.ingsw.Client.AbstractClient;

import it.polimi.ingsw.Client.ClientRMI.ClientRMI;
import it.polimi.ingsw.Client.ClientRMI.SkeletonClientImp;
import it.polimi.ingsw.Client.ClientSocket.ClientSocketHandler;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.SkeletonClient;
import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class GeneriClient {

    private ClientServerSender clientServerSender; //stub -- ClientServerSender
    private ClientServerReciver clientServerReciver; //skeleton -- ClientServerReciver
    private LinkClientServer linkClientServer;
    private String username;

    public GeneriClient(){

    }

    public void setLinkClientServer(String ipServer, int portServer){
        linkClientServer = new ClientSocketHandler(ipServer, portServer);
    }

    public void setClientServerReciver(){
        clientServerReciver = linkClientServer.getClientServerReciver();
    }

    public void setClientServerSender(){
        clientServerSender = linkClientServer.getClientServerSender();
    }

    public void setLinkClientServerRMI(){
        linkClientServer = new ClientRMI();
    }

    public void setClientServerReciverRMI(){
        clientServerReciver = linkClientServer.getClientServerReciver();
    }

    public ClientServerSender getClientServerSender() {
        return clientServerSender;
    }

    public ClientServerReciver getClientServerReciver() {
        return clientServerReciver;
    }

    public void register(String username){
        this.username = username;
        if(linkClientServer instanceof ClientSocketHandler){
            try {
                //Reference to ServerClientSender is not going to be used
                clientServerSender.register(this.username,null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                SkeletonClientImp sc = (SkeletonClientImp)clientServerReciver;
                sc.setUsername(username);
                clientServerSender = (StubServer) Naming.lookup("rmi://127.0.0.1/myabc");
                clientServerSender.register(this.username, sc);
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregister(){
        try {
            clientServerSender.unregister(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
