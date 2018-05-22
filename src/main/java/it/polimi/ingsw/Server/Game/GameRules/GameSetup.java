package it.polimi.ingsw.Server.Game.GameRules;

import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Components.Boards.BoardRound;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.DiceBag;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;

import java.io.FileNotFoundException;
import java.util.*;

public class GameSetup {

    private LinkedHashMap<Player,Boolean> players;
    private DiceBag diceBag;
    private DraftPool draftPool;
    private BoardRound boardRound;
    private ArrayList<ToolCard> toolCards;
    private ArrayList<PublicObjectiveCard> publicObjectiveCards;
    private ArrayList<WindowPatternCard> windowPatternCards;
    private ArrayList<PrivateObjectiveCard> privateObjectiveCards;

    public GameSetup(LinkedHashMap<Player,Boolean> players){
        this.players = players;
        toolCardsGet();
        publicObjectiveGet();
        windowPatternGet();
    }

    //After that players have send their WP choice
    //conclude the setup process extracting private card
    public void concludeSetUp(HashMap<Player,Boolean> playersLeft){
        ArrayList<Player> playerBoardRound = new ArrayList<Player>();
        privateObjectiveGet(playersLeft.size());
        diceBag = new DiceBag();
        draftPool = new DraftPool(diceBag);
        for(Player p : playersLeft.keySet())
            playerBoardRound.add(p);
        boardRound = new BoardRound(playerBoardRound);
    }

    public HashMap<Player, Boolean> getPlayers() {
        return players;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public DraftPool getDraftPool() {
        return draftPool;
    }

    public BoardRound getBoardRound() {
        return boardRound;
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public ArrayList<WindowPatternCard> getWindowPatternCards() {
        return windowPatternCards;
    }

    public ArrayList<PrivateObjectiveCard> getPrivateObjectiveCards() {
        return privateObjectiveCards;
    }

    private void toolCardsGet(){
        AbstractCardFactory CardFactory = new ToolCardFactory(CONSTANT.toolcardfile);
        Hashtable<String, Drawable> toolcarddeck;
        try {
            toolcarddeck = CardFactory.getNewCardDeck();
            toolCards = extractRandom(toolcarddeck,CONSTANT.toolCardNumber,CONSTANT.toolCardToExtract);
        } catch (FileNotFoundException e) {
            System.out.println("File tool card non è stato caricato");
        }
    }

    private void publicObjectiveGet(){
        AbstractCardFactory CardFactory = new PublicObjectiveCardFactory(CONSTANT.publicObjectivefile);
        Hashtable<String, Drawable> publicobjectivedeck;
        try {
            publicobjectivedeck = CardFactory.getNewCardDeck();
            publicObjectiveCards = extractRandom(publicobjectivedeck,CONSTANT.publicCardNumber,CONSTANT.publicCardToExtract);
        } catch (FileNotFoundException e) {
            System.out.println("File public card non è stato caricato");
        }
    }

    private void privateObjectiveGet(int playersLeft){
        AbstractCardFactory CardFactory = new PrivateObjectiveCardFactory(CONSTANT.privateObjectivefile);
        Hashtable<String, Drawable> privateobjectivedeck;
        try {
            privateobjectivedeck = CardFactory.getNewCardDeck();
            privateObjectiveCards = extractRandom(privateobjectivedeck,CONSTANT.privateCardNumber, playersLeft);
        } catch (FileNotFoundException e) {
            System.out.println("File private card non è stato caricato");
        }
    }

    private void windowPatternGet(){
        AbstractCardFactory CardFactory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);
        Hashtable<String, Drawable> windowpatterndeck;
        try {
            windowpatterndeck = CardFactory.getNewCardDeck();
            windowPatternCards = extractRandomWindow(windowpatterndeck, CONSTANT.windowCardNumber/2-1,players.size()*2);
        } catch (FileNotFoundException e) {
            System.out.println("File window card non è stato caricato");
        }
    }

    private  <T> ArrayList<T> extractRandom(Hashtable<String, Drawable> deck, int upperBound, int numcard){
        Random random = new Random();
        ArrayList<T> result = new ArrayList<>();
        ArrayList<String> alreadyExtracted = new ArrayList<>();
        for(int i=0; i<numcard;){
            String index = Integer.toString(random.nextInt(upperBound)+1);
            if(!alreadyExtracted.contains(index)){
                alreadyExtracted.add(index);
                result.add((T)deck.get(index));

                i++;
            }
        }
        return result;
    }

    private ArrayList<WindowPatternCard> extractRandomWindow(Hashtable<String, Drawable> deck, int upperbound, int numcard){
        Random random = new Random();
        ArrayList<Integer> alreadyExtracted = new ArrayList<>();
        ArrayList<WindowPatternCard> result = new ArrayList<>();
        for(int i=0;i<numcard;){
            int n = (random.nextInt(upperbound))*2 + 1;
            if(!alreadyExtracted.contains(n)){
                alreadyExtracted.add(n);
                result.add((WindowPatternCard) deck.get(Integer.toString(n)));
                result.add((WindowPatternCard) deck.get(Integer.toString(n+1)));
                i++;
            }
        }
        return result;
    }


}
