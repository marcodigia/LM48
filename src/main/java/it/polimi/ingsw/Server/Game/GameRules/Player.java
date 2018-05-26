package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.View.VirtualViewImp;
import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.PlayerUtility.PlayerColor;

import java.io.Serializable;
import java.util.ArrayList;

//moved player in game rules so doAction is package friendly
public class Player implements Serializable{

    private GameContext gameContext;
    private VirtualViewImp virtualView;    //Server send packets to client through virualView
    private PlaceDiceAction placeDiceOfTheTurn;
    private UseToolCardBasic useToolCardOfTheTurn;

    private String name;
    private PlayerColor color;

    private Boolean isConnected = false;    //Set this variable to true if connected

    public Player(String username, ServerClientSender serverClientSender){
        name=username;
        virtualView = new VirtualViewImp(serverClientSender);
        gameContext = new GameContext();
        placeDiceOfTheTurn = new PlaceDiceAction();
        useToolCardOfTheTurn = new UseToolCardBasic();
    }

    public void startRound(){
        placeDiceOfTheTurn = new PlaceDiceAction();
        useToolCardOfTheTurn = new UseToolCardBasic();
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public WindowPatternCard getWindowPatternCard() {
        return gameContext.getWindowPatternCard();
    }

    public VirtualViewImp getvirtualView() {
        return virtualView;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getPlayerColor() {
        return color;
    }

    public void setIsNotConnected(){this.isConnected = false;}

    public void setIsConnected(){this.isConnected = true;}

    public boolean getConnected(){return isConnected;}



    
    public PlaceDiceAction getPlaceDiceOfTheTurn() {
        return placeDiceOfTheTurn;
    }

    public UseToolCardBasic getUseToolCardOfTheTurn() {
        return useToolCardOfTheTurn;
    }

    public  boolean getPlaceDiceState(){
        return placeDiceOfTheTurn.actionState();
    }
    public boolean getUseToolCardState(){
        return useToolCardOfTheTurn.actionState();
    }
    public void setBasicActionState(boolean tca , boolean pda ){
        useToolCardOfTheTurn.setACTIVE(tca);
        placeDiceOfTheTurn.setACTIVE(pda);
    }


}
