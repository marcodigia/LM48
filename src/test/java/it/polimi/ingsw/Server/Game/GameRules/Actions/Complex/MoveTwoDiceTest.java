package it.polimi.ingsw.Server.Game.GameRules.Actions.Complex;

class MoveTwoDiceTest {
/*
    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions moveTwoDiceAction;
    GameContext gameContext;

    @BeforeEach
    void setUp() {
        WindowPatternCardFactory factory = new WindowPatternCardFactory("windowPatternCards.csv");
        WindowPatternCard windowPatternCard = null;

        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();

            windowPatternCard = (WindowPatternCard) deck.get("25");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        draftPool.extractNdice(2);
        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard);
    }

    //Move two Dices in two legal position
    @Test
    void doAction() {
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 2, true, true, true);

        //gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 5, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        moveTwoDiceAction = new MoveTwoDice(0, 12, 2, 10, gameContext);
        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(2));
        moveTwoDiceAction.doAction();
        assertNull(gameContext.getWindowPatternCard().getDice(0));
        assertNull(gameContext.getWindowPatternCard().getDice(2));
    }

    //Move same Dice in Two different Positions
    @Test
    void doAction2() {
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 2, true, true, true);

        //gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 5, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        moveTwoDiceAction = new MoveTwoDice(0, 12, 0, 10, gameContext);
        moveTwoDiceAction.doAction();
        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNull(gameContext.getWindowPatternCard().getDice(12));
        assertNull(gameContext.getWindowPatternCard().getDice(10));
    }

    //Move two dice in the same position
    @Test
    void doAction3() {
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 2, true, true, true);

        //gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 5, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        moveTwoDiceAction = new MoveTwoDice(0, 10, 2, 10, gameContext);
        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(2));
        moveTwoDiceAction.doAction();
        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(2));
        assertNull(gameContext.getWindowPatternCard().getDice(10));
    }

    //Move two Dices one in legal position the other one in an illegal position
    @Test
    void doAction4() {
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 0, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 2, true, true, true);

        //gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "1"), 5, true, true, true);
        gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, "2"), 6, true, true, true);

        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        moveTwoDiceAction = new MoveTwoDice(0, 12, 2, 19, gameContext);
        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(2));
        moveTwoDiceAction.doAction();
        assertNotNull(gameContext.getWindowPatternCard().getDice(0));
        assertNotNull(gameContext.getWindowPatternCard().getDice(2));
        assertNull(gameContext.getWindowPatternCard().getDice(12));
        assertNull(gameContext.getWindowPatternCard().getDice(19));

    }*/


}