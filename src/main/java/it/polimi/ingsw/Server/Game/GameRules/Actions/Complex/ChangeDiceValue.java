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

    public ChangeDiceValue(int ammount) {
        this.ammount = ammount;
    }

    @Override
    public void doAction(GameStatus gameStatus) {
        draftPool = gameStatus.getDraftPool();
        Dice diceToChange = draftPool.getDice(draftPoolIndex);


        if (ammount == 1) {
            if (Integer.parseInt(diceToChange.getValue()) < 6) {

                diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
            }
        } else if (ammount == -1) {
            if (Integer.parseInt(diceToChange.getValue()) > 1) {
                diceToChange.setValue(Integer.parseInt(diceToChange.getValue()) + ammount);
            }


        } else
            diceToChange.setValue(ammount-Integer.parseInt(diceToChange.getValue()) );   //This will turn the dice on opposite face
    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {


        draftPoolIndex = ui.getDraftPoolIndex();
        if (ammount!=7)
            ammount = ui.getAmmountToChange();


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
