package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.Logger;
import it.polimi.ingsw.Server.Game.Utility.SpecialBoolean;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public class ChangeDiceValue implements Actions {

    private DraftPool draftPool;

    private int ammount=0;
    private int draftPoolIndex;
    private int type = 0 ;
    private String username;

    public ChangeDiceValue() {
    }

    public ChangeDiceValue(int type) {
        this.type = type;
    }

    /**
     * This method executes the action , based on the type provviden by the constructor it will change dice value in
     * in different ways
     * 1 = increase or decrease the value by one
     * 2 = turn the dice on the opposite side
     * 3 = extracts a new dice and than set the new value
     * @param gameStatus the gamestatus that will be affect from the action
     */
    @Override
    public void doAction(GameStatus gameStatus) {
        draftPool = gameStatus.getDraftPool();

        if (draftPoolIndex<0 || draftPoolIndex>draftPool.getDraft().size()){
           return;
        }
        Dice diceToChange = draftPool.getDice(draftPoolIndex);


        System.out.println("Change dice VALUE dp index" + draftPoolIndex + " dicetochange " + diceToChange + " type " + type + " Ammount " + ammount);
        switch (type){
            case 1:
                if (ammount == 1) {
                    if (Integer.parseInt(diceToChange.getValue()) < 6) {

                        diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
                    }
                } else {
                    if (Integer.parseInt(diceToChange.getValue()) > 1) {
                        diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
                    }


                    }
                    break;
            case 2:
                diceToChange.setValue(7-Integer.parseInt(diceToChange.getValue()) );   //This will turn the dice on opposite face
                break;
            case 3:

                if (diceToChange!=null){
                    gameStatus.getDiceBag().putDice(diceToChange);
                    Dice newDice = gameStatus.getDiceBag().getNdices(1).get(0);
                    gameStatus.getDraftPool().removeDice(diceToChange);
                    newDice.setValue(ammount);
                    gameStatus.getDraftPool().putDice(newDice);

                }
                break;
            default:
                break;

               }


    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName, SpecialBoolean check) {



        Logger.log("Seleziona dado dalla DraftPool\n");
        if(check.isFlag())
            draftPoolIndex = ui.getDraftPoolIndex();
        else
            return;

        if (type!=2){
            Logger.log("Decidi la variazione del dado\n");
            if (type == 3){
                if (!check.isFlag())
                    return;
                ammount = ui.getAmmountToChange(1);
            }

            else{
                if (!check.isFlag())
                    return;
                ammount = ui.getAmmountToChange(0);
            }

        }


    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpAction(String packet) {

        String[] elements = packet.split("\\"+CONSTANT.ElenemtsDelimenter);

        ammount = Integer.parseInt(elements[0]);
        draftPoolIndex=Integer.parseInt(elements[1]);
        type =Integer.parseInt(elements[2]);
    }

    @Override
    public void setUserName(String userName) {
        this.username=userName;
    }


    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(ChangeDiceValue.class.getName()).append(CONSTANT.ObjectDelimeterComplex);
        packet.append(ammount).append(CONSTANT.ElenemtsDelimenter).append(draftPoolIndex);
        packet.append(CONSTANT.ElenemtsDelimenter).append(type);
        return packet.toString();
    }
}
