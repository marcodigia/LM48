package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Complex.ComplexAction;

public class UseToolCardBasic extends BasicAction {

    private ToolCard toolCard;
    private ComplexAction action; //ComplexAction

    public UseToolCardBasic(ToolCard toolCard, UI ui, DraftPool draftPool, WindowPatternCard windowPatternCard) {
        this.toolCard = toolCard;
        action = toolCard.getActions(ui);
        action.setWindowPatter(windowPatternCard);
        action.setDraftPool(draftPool);
    }

    @Override
    public void doAction() {
        System.out.println("Used tool card" + toolCard.getID());
        //Complex action set Window Pattern , Complex action set Draft
        action.doAction();
    }
}
