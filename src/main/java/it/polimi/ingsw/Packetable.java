package it.polimi.ingsw;

import java.io.Serializable;

public interface Packetable extends Serializable {

    String toPacket();
}
