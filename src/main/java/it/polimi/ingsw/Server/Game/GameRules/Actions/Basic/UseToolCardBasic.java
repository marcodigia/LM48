package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Exceptions.NoPossibleValidMovesException;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;

public class UseToolCardBasic extends BasicAction {

    private ToolCard toolCard;
    private Actions action;
    private GameContext gameContext;

    private boolean active;

    public UseToolCardBasic(ToolCard toolCard, UI ui, GameContext gameContext) {
        this.toolCard = toolCard;
        active = true;

        try {
            action = toolCard.getActions(ui, gameContext);
        } catch (NoPossibleValidMovesException e) {
            active = false;
        }
    }

    @Override
    public void doAction(GameContext gameContext) {
        System.out.println("Used tool card" + toolCard.getID());
        //Complex action set Window Pattern , Complex action set Draft
        if (active)
            action.doAction(gameContext);
    }

    @Override
    public void useAction(UI ui, GameContext gameContext) {

    }
}
