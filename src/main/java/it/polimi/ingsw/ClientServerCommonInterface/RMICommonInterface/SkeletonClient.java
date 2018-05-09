package it.polimi.ingsw.ClientServerCommonInterface.RMICommonInterface;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import java.rmi.Remote;

public interface SkeletonClient extends Remote, ServerClientSender, ClientServerReciver {
}
