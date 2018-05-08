package it.polimi.ingsw.Server.Game.GameRules.Actions.Basic;

class UseToolCardBasicTest {

  /*  ToolCard toolCard;
    DiceBag diceBag = new DiceBag();
    DraftPool draftPool = new DraftPool(diceBag);

    @BeforeEach
    void setuP() {
        ToolCardFactory factory = new ToolCardFactory("toolCards.csv");
        try {
            Hashtable<String, Drawable> deck = factory.getNewCardDeck();
            toolCard = (ToolCard) deck.get("13");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        draftPool.extractNdice(1);
    }

    @Test
    void doAction() {


        BasicAction action = new UseToolCardBasic(toolCard, new UI_SIMULATION(), new GameContext(draftPool, diceBag, null, null));
        draftPool.getDice(0).setValue(4);
        action.doAction();
        System.out.println(draftPool.getDice(0).getValue());
        assertTrue(5 == Integer.parseInt(draftPool.getDice(0).getValue()));
    }*/
}