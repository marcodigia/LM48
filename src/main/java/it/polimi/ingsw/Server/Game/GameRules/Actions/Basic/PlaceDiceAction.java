package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.PlaceDice;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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


        System.out.println("do action --->" + ANSI_COLOR.BACKGROUND_GREEN + ANSI_COLOR.ANSI_RED + action.toPacket() + ANSI_COLOR.ANSI_RESET);
        System.out.println("game status --->" + ANSI_COLOR.BACKGROUND_GREEN + ANSI_COLOR.ANSI_RED + gameStatus.toPacket() + ANSI_COLOR.ANSI_RESET);
        action.setUserName(userName);
        action.doAction(gameStatus);

    }


    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName){

        action.useAction(ui, gameStatus, userName);
    }


    public void setACTIVE(boolean b){
        ACTIVE = b;
    }

    public boolean actionState(){
        return ACTIVE ;
    }


    public void setUpAction(String packet) {
        Actions action = null;

        String[] el = packet.split(CONSTANT.ObjectDelimeterComplex);


        System.out.println("packet --->" + ANSI_COLOR.BACKGROUND_BLUE + ANSI_COLOR.ANSI_RED + packet + ANSI_COLOR.ANSI_RESET);


        //Create an istance of the class from the className
        Class<?> o;
        Constructor<?> constructor;
        try {
            o = Class.forName(el[0]);
            Class<?>[] types = new Class<?>[]{};

            constructor = ((Class<?>) o).getConstructor(types);

            action = (Actions) constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }


        System.out.println("el[1] --->" + ANSI_COLOR.BACKGROUND_BLUE + ANSI_COLOR.ANSI_RED + el[1] + ANSI_COLOR.ANSI_RESET);
        action.setUpAction(el[1]);
        System.out.println("userName --->" + ANSI_COLOR.BACKGROUND_BLUE + ANSI_COLOR.ANSI_RED + userName + ANSI_COLOR.ANSI_RESET);
        action.setUserName(userName);
        this.action = action;

    }
    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(PlaceDiceAction.class.getName());
        packet.append(CONSTANT.ObjectDelimeter).append(userName);
        packet.append(CONSTANT.ObjectDelimeter).append(ACTIVE);
        packet.append(CONSTANT.ObjectDelimeter).append(action.toPacket());


        System.out.println("to packet--->" + ANSI_COLOR.BACKGROUND_BLUE + ANSI_COLOR.ANSI_RED + packet.toString() + ANSI_COLOR.ANSI_RESET);
        return packet.toString();
    }
}
