package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

class TakeDiceBasicTest {

   /* DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);
    WindowPatternCard windowPatternCard;

    @BeforeEach
    void setUp() {
        WindowPatternCardFactory factory = new WindowPatternCardFactory("windowPatternCards.csv");
        try {

            Hashtable<String, Drawable> deck = factory.getNewCardDeck();
            windowPatternCard = (WindowPatternCard) deck.get("25");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        draftPool.extractNdice(1);
    }

    @Test
    void doAction() {

        BasicAction takeDice = new TakeDiceBasic(windowPatternCard, draftPool);
        ((TakeDiceBasic) takeDice).takeDice(0, 0);
        assertNull(windowPatternCard.getDice(0));
        takeDice.doAction();
        assertNotNull(windowPatternCard.getDice(0));
    }

    @Test
    void doAction2() {

        BasicAction takeDice = new TakeDiceBasic(windowPatternCard, draftPool);
        ((TakeDiceBasic) takeDice).takeDice(0, 7);
        assertNull(windowPatternCard.getDice(7));
        takeDice.doAction();
        assertNull(windowPatternCard.getDice(7));
    }*/
}