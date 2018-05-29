package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;

public class ChangeDiceValueByOne implements Actions {

    private DraftPool draftPool;

    private int ammount;
    private int draftPoolIndex;
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

        }
    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        draftPoolIndex = ui.getDraftPoolIndex();
        ammount = ui.getAmmountToChange();


    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpAction(String packet) {

        String[] elements = packet.split("\\"+CONSTANT.ElenemtsDelimenter);


        System.out.println("Change Dice VAl by one " + packet );
        ammount = Integer.parseInt(elements[0]);
        draftPoolIndex=Integer.parseInt(elements[1]);
    }

    @Override
    public void setUserName(String userName) {

    }


    @Override
    public String toPacket() {

        StringBuilder packet = new StringBuilder();
        packet.append(ChangeDiceValueByOne.class.getName()).append(CONSTANT.ObjectDelimeterComplex);
        packet.append(ammount).append(CONSTANT.ElenemtsDelimenter).append(draftPoolIndex);
        return packet.toString();
    }
}
