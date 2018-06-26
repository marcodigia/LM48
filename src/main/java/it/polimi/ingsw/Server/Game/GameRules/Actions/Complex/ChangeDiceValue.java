package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public class ChangeDiceValue implements Actions {

    private DraftPool draftPool;

    private int ammount=0;
    private int draftPoolIndex;
    private int type = 0 ;


    public ChangeDiceValue() {
    }

    public ChangeDiceValue(int ammount) {
        this.type = ammount;
    }

    @Override
    public void doAction(GameStatus gameStatus) {
        draftPool = gameStatus.getDraftPool();

        if (draftPoolIndex<0 || draftPoolIndex>draftPool.getDraft().size()){
           return;
        }
        Dice diceToChange = draftPool.getDice(draftPoolIndex);


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
                    newDice.setValue(ammount);
                }
                break;
            default:
                break;

               }


    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {




        draftPoolIndex = ui.getDraftPoolIndex();

        if (type!=2){
            ammount = ui.getAmmountToChange();
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
    }

    @Override
    public void setUserName(String userName) {

    }


    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(ChangeDiceValue.class.getName()).append(CONSTANT.ObjectDelimeterComplex);
        packet.append(ammount).append(CONSTANT.ElenemtsDelimenter).append(draftPoolIndex);
        return packet.toString();
    }
}
