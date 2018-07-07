package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.PlaceDice;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.SpecialBoolean;
import it.polimi.ingsw.UI;

public class PlaceDiceAction implements Actions {

    private boolean ACTIVE;
    private final int MatrixSixe = 20;


    private String userName;

    private Actions action;
    //This is called in TakeDiceBasicAction
    public PlaceDiceAction() {

        ACTIVE = true;
        action = new PlaceDice();
    }

    //This is called in RerollDraftedDiceAction




    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }



    //NB if the get to this method it means that the user has already confirm its action so if if is illegal the dice shuld be removed
    @Override
    public void doAction(GameStatus gameStatus) {

        if (!ACTIVE)
            return;

        action.setUserName(userName);
        action.doAction(gameStatus);

    }


    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName, SpecialBoolean check){

        action.useAction(ui, gameStatus, userName, check);
        action.setUserName(userName);
    }


    public void setACTIVE(boolean b){
        ACTIVE = b;
    }

    public boolean actionState(){
        return ACTIVE ;
    }


    public void setUpAction(String packet) {

        String[] el = packet.split(CONSTANT.ObjectDelimeterComplex);


        action.setUpAction(el[1]);
        action.setUserName(userName);

    }

    public void changeRestricion(boolean color , boolean value, boolean adjacency){
        ((PlaceDice) action ).changeRestristricion(color,value,adjacency);
    }
    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append("BP");
        packet.append(CONSTANT.ObjectDelimeter).append(userName);
        packet.append(CONSTANT.ObjectDelimeter).append(ACTIVE);
        packet.append(CONSTANT.ObjectDelimeter).append(action.toPacket());


        return packet.toString();
    }
}
