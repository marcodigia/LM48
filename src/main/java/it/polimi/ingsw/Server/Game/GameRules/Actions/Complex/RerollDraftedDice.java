package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;

public class RerollDraftedDice implements Actions {


    private int diceIndex;
    private String userName;

    public RerollDraftedDice(int diceIndex) {
        this.diceIndex = diceIndex;
    }

    @Override
    public void doAction(GameStatus gameStatus) {


        if (diceIndex==-1)
            gameStatus.getDraftPool().rerollAllDices();
        else
            gameStatus.getDraftPool().getDice(diceIndex).reroll();



    }

    @Override
    public void useAction(UI ui, GameStatus gameStatus, String userName) {

        this.userName = userName;
        if (diceIndex!=-1)
            diceIndex = ui.getDraftPoolIndex();


    }

    @Override
    public void setACTIVE(boolean b) {

    }

    @Override
    public void setUpAction(String packet) {

        diceIndex = Integer.parseInt(packet);
    }

    @Override
    public void setUserName(String userName) {

        this.userName = userName;
    }




    @Override
    public String toPacket() {

        StringBuilder packet =new StringBuilder();

        packet.append(RerollDraftedDice.class.getName()).append(CONSTANT.ObjectDelimeterComplex);
        packet.append(diceIndex);
        return packet.toString();
    }
}
