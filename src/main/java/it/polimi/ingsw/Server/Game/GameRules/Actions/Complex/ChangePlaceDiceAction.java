package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;

public class ChangePlaceDiceAction implements Actions {

    String username;
    boolean color , value , adjacency;


    public ChangePlaceDiceAction() {
    }

    public ChangePlaceDiceAction(boolean color, boolean value, boolean adjacency) {
        this.color = color;
        this.value = value;
        this.adjacency = adjacency;
    }

    @Override
    public void doAction(GameStatus gameStatus) {
        System.out.println("CHANGE DICE action " + color +value+adjacency);
        gameStatus.getPlayerByName(username).getPlaceDiceOfTheTurn().changeRestricion(color,value,adjacency);
    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpAction(String packet) {

        String[] elements = packet.split("\\" + CONSTANT.ElenemtsDelimenter);
        color = Boolean.parseBoolean(elements[0]);
        value = Boolean.parseBoolean(elements[1]);
        adjacency = Boolean.parseBoolean(elements[2]);
    }

    @Override
    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public String toPacket() {
        StringBuilder packet = new StringBuilder();
        packet.append(ChangePlaceDiceAction.class.getName());
        packet.append(CONSTANT.ObjectDelimeterComplex);
        packet.append(color).append(CONSTANT.ElenemtsDelimenter)
                .append(value).append(CONSTANT.ElenemtsDelimenter).append(adjacency);

        return packet.toString();
    }
}
