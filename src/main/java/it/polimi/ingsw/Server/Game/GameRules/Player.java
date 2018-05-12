package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Exceptions.EndOfTurnException;
import it.polimi.ingsw.Server.View.VirtualView;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Client.View.UI_SIMULATION;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.PrivateObjectiveCard;
import it.polimi.ingsw.Server.Game.Cards.ToolCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.PlayerUtility.PlayerColor;

import java.util.ArrayList;

//moved player in game rules so doAction is package friendly
public class Player {



    private PrivateObjectiveCard privateObjectiveCard;
    private PlaceDiceAction placeDiceOfTheTurn;
    private UseToolCardBasic useToolCardOfTheTurn;
    private PlayerColor color;
    private String name;
    private GameContext gameContext;
    private ServerClientSender serverClientSender;
    private VirtualView virtualView;

    public Player(String username, ServerClientSender serverClientSender){
        name=username;
        this.serverClientSender = serverClientSender;
        virtualView = new VirtualView(serverClientSender);
    }

    public void startRound(){
        placeDiceOfTheTurn = new PlaceDiceAction();
        useToolCardOfTheTurn = new UseToolCardBasic();
    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }



    public ArrayList<Cell> getRow(int n) {
        return gameContext.getWindowPatternCard().getRow(n);
    }

    public WindowPatternCard getWindowPatternCard() {
        return gameContext.getWindowPatternCard();
    }




    public ServerClientSender getServerClientSender() {
        return serverClientSender;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getPlayerColor() {
        return color;
    }

}
