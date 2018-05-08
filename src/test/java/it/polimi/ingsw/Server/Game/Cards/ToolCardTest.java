package it.polimi.ingsw.Server.Game.Cards;

class ToolCardTest {
   /* DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    Actions moveOneDiceAction;
    GameContext gameContext;
    WindowPatternCard windowPatternCard = null;
    WindowPatternCardFactory factory;

    @BeforeEach
    void setUp() {

        factory = new WindowPatternCardFactory("windowPatternCards.csv");

        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();

            windowPatternCard = (WindowPatternCard) deck.get("25");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard);

        draftPool.extractNdice(2);


    }


    @Test
    void toolCard1() {
        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("1");
            gameContext.getDraftPool().getDice(0).setValue(2);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertEquals(gameContext.getDraftPool().getDice(0).getValue(), "2");
            actions.doAction();
            assertEquals(gameContext.getDraftPool().getDice(0).getValue(), "3");

        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toolCard2() {
        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("2");
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 0, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 13, true, true, true);

            assertNotNull(toolCard);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertNull(gameContext.getWindowPatternCard().getDice(19));
            actions.doAction();
            assertNotNull(gameContext.getWindowPatternCard().getDice(19));

        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toolCard3() {

        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();

            windowPatternCard = (WindowPatternCard) deck.get("26");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        gameContext = new GameContext(draftPool, diceBag, null, windowPatternCard);


        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("3");
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 0, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 13, true, true, true);

            assertNotNull(toolCard);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertNull(gameContext.getWindowPatternCard().getDice(19));
            actions.doAction();
            assertNotNull(gameContext.getWindowPatternCard().getDice(19));

        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }

    @Test
    void toolCard4_1() {
        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("4");
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 0, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 2, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 6, true, true, true);
            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.RED, "2"), 13, true, true, true);

            assertNotNull(toolCard);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertNull(gameContext.getWindowPatternCard().getDice(19));
            assertNotNull(gameContext.getWindowPatternCard().getDice(0));
            assertNotNull(gameContext.getWindowPatternCard().getDice(2));
            actions.doAction();
            assertNotNull(gameContext.getWindowPatternCard().getDice(19));


        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }

    @Test
    void doAction6() {
        while (draftPool.getDice(0).getDiceColor().equals(DiceColor.RED)) {
            draftPool.extractNdice(1);

        }
        ToolCardFactory Toolfactory = new ToolCardFactory("toolCards.csv");
        ToolCard toolCard;
        try {

            Hashtable<String, Drawable> deck = Toolfactory.getNewCardDeck();

            toolCard = (ToolCard) deck.get("6");
            gameContext.getDraftPool().getDice(0).setValue(2);
            Actions actions = toolCard.getActions(new UI_SIMULATION(), gameContext);
            assertNull(gameContext.getWindowPatternCard().getDice(17));
            
            String value = "1";
            if (draftPool.getDice(0).getValue().equals("1"))
                value = "2";

            gameContext.getWindowPatternCard().placeDice(new Dice(DiceColor.BLUE, value), 13, true, true, true);
            actions.doAction();
            assertNotNull(gameContext.getWindowPatternCard().getDice(17));

        } catch (FileNotFoundException | NoPossibleValidMovesException e) {
            e.printStackTrace();
        }
    }
    */
}