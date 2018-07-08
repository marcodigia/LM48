package it.polimi.ingsw.Client.AbstractClient;

import it.polimi.ingsw.Client.ClientRMI.ClientRMI;
import it.polimi.ingsw.Client.ClientRMI.SkeletonClientImp;
import it.polimi.ingsw.Client.ClientSocket.ClientSocketHandler;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface.StubServer;
import it.polimi.ingsw.Server.Game.TimerUtility.TimerUtility;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class GeneriClient {

    private ClientServerSender clientServerSender;   //StubServerImp -- ClientServerSenderImp
    private ClientServerReciver clientServerReciver; //SkeletonClientImp -- ClientServerReciverImp
    private LinkClientServer linkClientServer;
    private String username;

    public GeneriClient(){
    }

    /**
     * This method creates a new client which uses sockets.
     *
     * @param ipServer      This is the server's ip to which offers the game.
     * @param portServer    This is the server's port to which client socket have to be attached.
     * @author Fabio Dalle Rive.
     * */
    public void setLinkClientServer(String ipServer, int portServer){
        linkClientServer = new ClientSocketHandler(ipServer, portServer,this);
    }

    /**
     * This method sets receiver of a socket connection.
     *
     * @author Fabio Dalle Rive.
     * */
    public void setClientServerReciver(){
        clientServerReciver = linkClientServer.getClientServerReciver();
    }

    /**
     * This method sets sender of a socket connection.
     *
     * @author Fabio Dalle Rive.
     * */
    public void setClientServerSender(){
        clientServerSender = linkClientServer.getClientServerSender();
    }

    /**
     * This method creates a new client which uses RMI.
     *
     * @author Fabio Dalle Rive.
     * */
    public void setLinkClientServerRMI(){
        linkClientServer = new ClientRMI();
    }

    /**
     * This method sets the receiver of a RMI connection.
     *
     * @author Fabio Dalle Rive.
     * */
    public void setClientServerReciverRMI(){
        clientServerReciver = linkClientServer.getClientServerReciver();
    }

    /**
     * @return sender of a socket or RMI connection.
     * @author Fabio Dalle Rive.
     * */
    public ClientServerSender getClientServerSender() {
        return clientServerSender;
    }

    /**
     * @return receiver of a socket or RMI connection.
     * @author Fabio Dalle Rive.
     * */
    public ClientServerReciver getClientServerReciver() {
        return clientServerReciver;
    }

    /**
     * This method sets player's username with the one chosen by the user.
     * This method is used with socket connections.
     *
     * @param username This is the username chosen by user.
     * @author Fabio Dalle Rive.
     * */
    public void register(String username){
        this.username = username;
        try {
            clientServerSender.register(this.username,null);
        } catch (RemoteException e) {
            System.out.println("Server is still unreachable");
        }
    }

    /**
     * This method sets player's username with the one chosen by the user.
     * This method is used with RMI connections. When this method is used
     * server remote object is downloaded by client. After that client
     * object is able to register itself on server.
     *
     * @param username This is the username chosen by user.
     * @param ipRMI    This is the server's ip where remote object is exposed.
     * @param portRMI  This is the server's port where remote object is exposed.
     * @author Fabio Dalle Rive.
     * */
    public void register(String username, String ipRMI, int portRMI) throws RemoteException {
        this.username = username;
        try {
            SkeletonClientImp sc = (SkeletonClientImp)clientServerReciver;
            sc.setUsername(username);
            clientServerSender = (StubServer) Naming.lookup("rmi://"+ipRMI+":"+portRMI+"/myabc");
            clientServerSender.register(this.username, sc);
        } catch (NotBoundException e) {
            System.out.println("Server is still unreachable");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to close a connection.
     *
     * @author Fabio Dalle Rive.
     * */
    public void close(){
        if(clientServerSender!=null) {
            try {
                clientServerSender.close();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if(clientServerReciver != null) {
            try {
                clientServerReciver.close();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        clientServerSender = null;
        clientServerReciver = null;
        linkClientServer = null;
    }


    public void manageDisconnection(String username, String ipRMI, int portRMI){
        System.out.println("MANAGEdISCONNECTION");
        TimerUtility timerUtility = new TimerUtility();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    register(username, ipRMI, portRMI);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, timerUtility.readTimerFromFile(30, "timerDelayPing.txt"));
    }

}
