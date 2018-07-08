package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.Logger;
import it.polimi.ingsw.Server.Game.Utility.SpecialBoolean;
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

        gameStatus.getPlayerByName(username).getPlaceDiceOfTheTurn().changeRestricion(color,value,adjacency);
    }

    /**
     * @param ui         the ui of the client that is to be interacted with
     * @param gameStatus the gamesStatus that is a repprestantation of the actual gameStatus on the Server
     * @param userName   the username of the player that is actually using the action
     * @param check      Used to interrupt the action beetween threads
     */
    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName, SpecialBoolean check) {

        String azione ="";
        if (adjacency)
            azione+=" ignorare l'addiacenza ";
        if (color)
            azione+=" ignorare la restrizione colore";
        if (value)
            azione += " ignorare la restrizione di valore";
        if (!azione.equals(""))
            Logger.log("Fornita capacità di " + azione);
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
