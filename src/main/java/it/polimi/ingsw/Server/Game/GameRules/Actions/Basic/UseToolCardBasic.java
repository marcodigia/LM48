package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UseToolCardBasic implements Actions {


    private boolean ACTIVE = true;

    private Actions toolCardAction;
    private String userName;
    public UseToolCardBasic() {

    }

    @Override
    public void doAction(GameStatus gameStatus) {

        if (!ACTIVE)
            return;

        System.out.println(ANSI_COLOR.BACKGROUND_YELLOW+"Do action use tool card " + ANSI_COLOR.ANSI_RESET);
        toolCardAction.doAction(gameStatus);
        ACTIVE = false;

        System.out.println(ANSI_COLOR.BACKGROUND_GREEN + gameStatus.toPacket() + ANSI_COLOR.ANSI_RESET);
    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        this.userName = userName;
        if(!ACTIVE){
          ui.printMessage("UUUeeè Cumpà , hai gia usato la toolcard in questo turno!");
          return;
        }


        toolCardAction= ui.getChoosenToolCard().getActions();
        toolCardAction.useAction(ui,gameStatus,userName);

    }

    @Override
    public void setUpAction(String packet) {

        Actions action = null;

        String[] elements =packet.split(CONSTANT.ObjectDelimeterComplex);




        //Create an istance of the class from the className
        Class<?> o;
        Constructor<?> constructor;
        try {
            o = Class.forName(elements[0]);
            Class<?>[] types = new Class<?>[]{};

            constructor = ((Class<?>) o).getConstructor(types);

            action =(Actions) constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        action.setUpAction(elements[1]);

        toolCardAction = action;
        toolCardAction.setUserName(userName);

    }


    @Override
    public void setUserName(String userName) {

        System.out.println("use tool card " + userName);
        toolCardAction.setUserName(userName);
        this.userName = userName;
    }

    public void setToolCardAction(Actions actions) {

        toolCardAction = actions;
    }

    public void setACTIVE(boolean b){
        ACTIVE = b;
    }

    public boolean actionState(){
        return ACTIVE ;
    }

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(UseToolCardBasic.class.getName());


        packet.append(CONSTANT.ObjectDelimeter).append(userName);
        packet.append(CONSTANT.ObjectDelimeter).append(ACTIVE);

        packet.append(CONSTANT.ObjectDelimeter).append(toolCardAction.toPacket());

        return packet.toString();
    }


}
