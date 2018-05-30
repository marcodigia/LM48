package it.polimi.ingsw.Client.ClientSocket;

import it.polimi.ingsw.Client.AbstractClient.LinkClientServer;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketHandler extends LinkClientServer{

    private String ipServer;
    private int portServer;
    private Socket serverSocket;
    public ClientServerReciverImp clientServerReciver=null;
    public ClientServerSenderImp clientServerSenderImp=null;

    public ClientSocketHandler(String ipServer, int portServer){
        this.ipServer = ipServer;
        this.portServer = portServer;
        try{
            serverSocket = new Socket(ipServer, portServer);
            clientServerReciver = new ClientServerReciverImp(serverSocket);
            clientServerSenderImp = new ClientServerSenderImp(serverSocket);
            Thread t = new Thread(clientServerReciver);
            t.start();
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public ClientServerSender getClientServerSender() {
        return clientServerSenderImp;

    }

    @Override
    public ClientServerReciver getClientServerReciver() {
        return clientServerReciver;
    }
}
