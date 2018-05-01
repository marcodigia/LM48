package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameSetUp;

public class UseToolCardBasic extends BasicAction {

    private ToolCard toolCard;
    private Actions action;
    private GameSetUp gameSetUp;

    public UseToolCardBasic(ToolCard toolCard, UI ui, GameSetUp gameSetUp) {
        this.toolCard = toolCard;
        action = toolCard.getActions(ui, gameSetUp);
    }

    @Override
    public void doAction() {
        System.out.println("Used tool card" + toolCard.getID());
        //Complex action set Window Pattern , Complex action set Draft
        action.doAction();
    }
}
