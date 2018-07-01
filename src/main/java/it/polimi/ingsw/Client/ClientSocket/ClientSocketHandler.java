package it.polimi.ingsw.Client.ClientSocket;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.Client.AbstractClient.LinkClientServer;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketHandler extends LinkClientServer{

    private String ipServer;
    private int portServer;
    private Socket serverSocket;
    private ClientServerReciverImp clientServerReciver=null;
    private ClientServerSenderImp clientServerSenderImp=null;
    private GeneriClient generiClient;

    public ClientSocketHandler(String ipServer, int portServer, GeneriClient generiClient){
        this.ipServer = ipServer;
        this.portServer = portServer;
        this.generiClient = generiClient;
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
