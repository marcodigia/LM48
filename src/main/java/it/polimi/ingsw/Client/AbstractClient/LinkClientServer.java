package it.polimi.ingsw.Client.AbstractClient;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;

public abstract class LinkClientServer {
    public abstract ClientServerSender getClientServerSender();
    public abstract ClientServerReciver getClientServerReciver();
}
