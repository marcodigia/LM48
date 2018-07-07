package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.SpecialBoolean;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;
import it.polimi.ingsw.UI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UseToolCardBasic implements Actions {


    private boolean ACTIVE = true;

    private Actions toolCardAction;
    private String userName;

    private  String toolcardID;
    public UseToolCardBasic() {

    }

    public Actions getToolCardAction() {
        return toolCardAction;
    }

    @Override
    public void doAction(GameStatus gameStatus) {

        if (!ACTIVE)
            return;

        ToolCard tc =(ToolCard) Unpacker.TCDeck.get(toolcardID);
        if (tc.isUsable( gameStatus.getPlayerByName(userName).getWallet()))
        {
            toolCardAction.doAction(gameStatus);
            tc.use(gameStatus.getPlayerByName(userName).getWallet());
            ACTIVE = false;
        }
    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName, SpecialBoolean check) {

        this.userName = userName;
        if(!ACTIVE){
          ui.printMessage("UUUeeè Cumpà , hai gia usato la toolcard in questo turno!");
          return;
        }
        ToolCard tc ;
        if (check.isFlag()){
            tc = ui.getChoosenToolCard();
            if (tc==null)
                return;
        }
        else
            return;


            toolcardID= tc.getID();

        toolCardAction= tc.getActions();
        if (tc.isUsable( gameStatus.getPlayerByName(userName).getWallet()))
        {
            toolCardAction.useAction(ui,gameStatus,userName, check);
            gameStatus.getPlayerByName(userName).getWallet().useToken(tc.getCost());
            toolCardAction.setUserName(userName);
        }

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

    public void setToolcardID(String tcID){
        toolcardID = tcID;
    }

    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();

        packet.append("BT");

        packet.append(CONSTANT.ObjectDelimeter).append(userName);
        packet.append(CONSTANT.ObjectDelimeter).append(ACTIVE);
        packet.append(CONSTANT.ObjectDelimeter).append(toolCardAction.toPacket());
        packet.append(CONSTANT.ObjectDelimeter).append(toolcardID);

        return packet.toString();
    }


}
