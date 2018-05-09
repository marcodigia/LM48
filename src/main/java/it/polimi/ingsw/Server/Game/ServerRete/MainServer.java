package it.polimi.ingsw.Server.Game.ServerRete;

import it.polimi.ingsw.Server.Game.ServerRMI.ServerRMI;
import it.polimi.ingsw.Server.Game.ServerSocket.ServerSocketAccept;

public class MainServer {
    public static void main(String[] args){
        ServerRMI serverRMI = new ServerRMI();
        ServerSocketAccept serverSocketAccept = new ServerSocketAccept(2000);
        Thread t = new Thread(serverSocketAccept);

        serverRMI.start();
        t.start();

    }
}
