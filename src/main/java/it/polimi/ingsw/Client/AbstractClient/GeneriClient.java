package it.polimi.ingsw.Client.AbstractClient;

import it.polimi.ingsw.Client.ClientRMI.ClientRMI;
import it.polimi.ingsw.Client.ClientSocket.ClientSocketHandler;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.SkeletonClient;
import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerClientSenderImp;

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

    public GeneriClient(String ipServer, int portServer){
        linkClientServer = new ClientSocketHandler(ipServer, portServer);
        clientServerReciver = linkClientServer.getClientServerReciver();
        clientServerSender = linkClientServer.getClientServerSender();
    }
    public GeneriClient(){
        linkClientServer = new ClientRMI();
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
                clientServerSender.register(username,null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                clientServerSender = (StubServer) Naming.lookup("rmi://192.168.43.243/myabc");
                clientServerSender.register(username, (SkeletonClient)clientServerReciver);
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
