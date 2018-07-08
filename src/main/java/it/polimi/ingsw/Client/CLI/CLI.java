package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Cards.CardsUtility.DinamicCardCreator;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.EndGame.ScoreHandler;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.SpecialBoolean;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.inheritedChannel;

public class CLI implements UI, Runnable{

    private static final int maxNameSize = 20;
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<String> playersNames = new ArrayList<>();
    private static ArrayList<WindowPatternCard> playersWindowPatternCard = new ArrayList<>();
    private static int height = 4;
    private static DraftPool draftPool;
    private static String ip;
    private static String port;
    private GameStatus gameStatus;
    private ClientServerSender clientServerSender;
    private ClientServerReciver clientServerReciver;
    private GeneriClient generiClient;
    private static String username;
    private boolean rmi = false;
    private WindowPatternCard windowPatternCard2;
    private WindowPatternCard windowPatternCard1;
    private WindowPatternCard windowPatternCard3;
    private WindowPatternCard windowPatternCard4;
    private UseToolCardBasic useToolCardBasic;
    private PlaceDiceAction placeDiceAction;



    private final Object Sam = new Object();
    private int roundID = -1;
    private int draftPoolIndex = -1;
    private int cellIndexTo = -1;
    private int cellIndexFrom = -1;
    private int diceIndex = -1;

    private Integer semaforo = 1;
    private String message;
    Scanner s = new Scanner(System.in);
    private boolean cleanPrint = false;
    private int answer;


    private int i = 0 ;


    private final InputStream sistemin = System.in;
    private final PrintStream sistemon = System.out;



    private SpecialBoolean THREAD_RUN = new SpecialBoolean(true);
    SerialReader serialReader = new SerialReader();
    SafePrinter safePrinter = new SafePrinter();

    public CLI(){

        Thread t = new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (true){
                if (scanner.hasNextLine())
                    serialReader.notifyAllWaitig(scanner.nextLine());
                else {
                    try {
                        wait(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    /**
     * @param ip String which represents server ip
     */
    private static void setIp(String ip) {
        CLI.ip = ip;
    }

    /**
     * @param port String which represents server port
     */
    private static void setPort(String port) {
        CLI.port = port;
    }

    /**
     * @param generiClient GeneriClient which represents an RMI or Socket client
     */
    public void setGeneriClient(GeneriClient generiClient){
        this.generiClient = generiClient;
    }

    /**
     * @param u String which represents username's name
     */
    public void setUsername(String u){
        username = u;
    }

    private void printBoard(WindowPatternCard windowPatternCard){

            Object o = new Object();
            safePrinter.registry(o);
            safePrinter.print(o,windowPatternCard.getID());
            ArrayList<Restriction> cells = new ArrayList<>();
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                cells.add(windowPatternCard.getRestrictionAtIndex(i));
                if (i == 5 || i == 10 || i == 15){
                    safePrinter.print(o,line.toString());
                    line = new StringBuilder();
                }
                switch (cells.get(i)) {
                    case ONE:
                        line.append(ANSI_COLOR.BOLD + "1" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case TWO:
                        line.append(ANSI_COLOR.BOLD + "2" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case THREE:
                        line.append(ANSI_COLOR.BOLD + "3" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case FOUR:
                        line.append(ANSI_COLOR.BOLD + "4" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case FIVE:
                        line.append(ANSI_COLOR.BOLD + "5" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case SIX:
                        line.append(ANSI_COLOR.BOLD + "6" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case GREEN:
                        line.append(ANSI_COLOR.ANSI_GREEN + "G" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case YELLOW:
                        line.append(ANSI_COLOR.ANSI_YELLOW + "Y" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case BLUE:
                        line.append(ANSI_COLOR.ANSI_BLUE + "B" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case RED:
                        line.append(ANSI_COLOR.ANSI_RED + "R" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case PURPLE:
                        line.append(ANSI_COLOR.ANSI_PURPLE + "P" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case NONE:
                        line.append("0" + " ");
                        break;
                }
            }

            safePrinter.print(o,line.toString());
            safePrinter.print(o,"\n");

    }


    private void printBoardGame(WindowPatternCard windowPatternCard) {

        StringBuilder line = new StringBuilder();

        Object o = new Object();
        safePrinter.registry(o);

        for (int i = 0; i < 20; i++) {
            if (windowPatternCard.getDice(i) != null)
                line.append(windowPatternCard.getDice(i).getDiceColor().getAnsiColor()).append(ANSI_COLOR.BOLD).append("[").
                        append(windowPatternCard.getDice(i).getValue()).append("]").append(ANSI_COLOR.ANSI_RESET);
            else {
                switch (windowPatternCard.getRestrictionAtIndex(i)) {
                    case ONE:
                        line.append(ANSI_COLOR.BOLD + "1" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case TWO:
                        line.append(ANSI_COLOR.BOLD + "2" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case THREE:
                        line.append(ANSI_COLOR.BOLD + "3" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case FOUR:
                        line.append(ANSI_COLOR.BOLD + "4" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case FIVE:
                        line.append(ANSI_COLOR.BOLD + "5" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case SIX:
                        line.append(ANSI_COLOR.BOLD + "6" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case GREEN:
                        line.append(ANSI_COLOR.ANSI_GREEN + "G" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case YELLOW:
                        line.append(ANSI_COLOR.ANSI_YELLOW + "Y" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case BLUE:
                        line.append(ANSI_COLOR.ANSI_BLUE + "B" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case RED:
                        line.append(ANSI_COLOR.ANSI_RED + "R" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case PURPLE:
                        line.append(ANSI_COLOR.ANSI_PURPLE + "P" + ANSI_COLOR.ANSI_RESET + " ");
                        break;
                    case NONE:
                        line.append("0" + " ");
                        break;
                }
            }
            if (i == 4 || i == 9 || i == 14){
                safePrinter.print(o,line.toString());
                line = new StringBuilder();
            }
        }
        safePrinter.print(o,line.toString());
        safePrinter.print(o,"\n");
    }

    private void printDraftPool(ArrayList<Dice> draft) {
        Object o = new Object();
        safePrinter.registry(o);
        safePrinter.print(o,"Draft Pool : \n");
        StringBuilder line = new StringBuilder();
        for (Dice d : draft) {
            line.append(d.getDiceColor().getAnsiColor()).append(ANSI_COLOR.BOLD).append("[")
                    .append(d.getValue()).append("]").append(ANSI_COLOR.ANSI_RESET)
                    .append("  ");
        }
        safePrinter.print(o,line.toString());
    }

    @Override
    public int getAmmountToChange(int ammountType){

        Object o = new Object();
        safePrinter.registry(o);
        int choice;
        if (ammountType == 0) {
            do {
                safePrinter.print(o,"Aumentare o Diminuire valore del dado => +1 , -1 : ");
                SpecialBoolean ok = GO;
                ReadObject r = safeRead();
                r.waitOb();
                if (!ok.equals(GO))
                    return 0;
                try {
                    choice = Integer.parseInt(r.getNotifica());
                }catch (NumberFormatException e){
                    choice = 0;
                }

                if (!(choice == -1 || choice == 1))
                    safePrinter.print(o,"Valore non valido , inserire 1 o  -1");
            } while (!(choice == -1 || choice == 1));
        }
        else {
            do {
                safePrinter.print(o,"Selezionare il valore del dado pescato: ");
                SpecialBoolean ok = GO;
                ReadObject r = safeRead();
                r.waitOb();
                if (!ok.equals(GO))
                    return -1;
                try {
                    choice = Integer.parseInt(r.getNotifica());
                }catch (NumberFormatException e){
                    choice = -1;
                }

                if (!((choice > 0) && (choice < 7)))
                    safePrinter.print(o,"Valore non valido , inserire un valore da 1 a 6");
            } while (!((choice > 0) && (choice < 7)));
        }
        return choice;
    }

    @Override
    public int getDraftPoolIndex(){
        boolean indexOK = false;
        Object o = new Object();
        safePrinter.registry(o);
        do {
            safePrinter.print(o,"Choose dice index from Draft Pool: ");

            SpecialBoolean ok = GO;
            ReadObject r = safeRead();
            r.waitOb();
            if (!ok.equals(GO))
                return -1;
            try {
                draftPoolIndex = Integer.parseInt(r.getNotifica());
            }catch (NumberFormatException e){
                draftPoolIndex = -1;
            }


            if (draftPoolIndex > 0 && draftPoolIndex <= gameStatus.getDraftPool().getDraft().size())
                indexOK = true;
        }while (!indexOK);
        draftPoolIndex = draftPoolIndex-1;
        return draftPoolIndex;
    }

    @Override
    public int getMatrixIndexFrom(){
        boolean cellIndexOK = false;
        Object o = new Object();
        safePrinter.registry(o);
        do {

            safePrinter.print(o,"Choose starting cell index: ");

            SpecialBoolean ok = GO;
            ReadObject r = safeRead();
            r.waitOb();
            if (!ok.equals(GO))
                return -1;
            try {
                cellIndexFrom = Integer.parseInt(r.getNotifica());
            }catch (NumberFormatException e){
                cellIndexFrom = -1;
            }


            if (cellIndexFrom > 0 && cellIndexFrom <= 20)
                cellIndexOK = true;
        }while (!cellIndexOK);
        cellIndexFrom = cellIndexFrom-1;
        return cellIndexFrom;
    }

    @Override
    public int getMatrixIndexTo(){
        boolean cellIndexOK = false;
        Object o = new Object();
        safePrinter.registry(o);
        do {
            safePrinter.print(o,"Choose ending cell index: ");

            SpecialBoolean ok = GO;
            ReadObject r = safeRead();
            r.waitOb();
            if (!ok.equals(GO))
                return -1;
            try {
                cellIndexTo = Integer.parseInt(r.getNotifica());
            }catch (NumberFormatException e){
                cellIndexTo = 0;
            }


            if (cellIndexTo > 0 && cellIndexTo <= 20)
                cellIndexOK = true;
        }while (!cellIndexOK);
        cellIndexTo = cellIndexTo-1;
        return cellIndexTo;
    }

    @Override
    public boolean askForAnotherDice() {
        Object o = new Object();
        safePrinter.registry(o);
        int choice = -1;
        do {
            safePrinter.print(o, "Do you want to place another dice?\nyes --> 1\nno --> 0\n");
            SpecialBoolean ok = GO;
            ReadObject r = safeRead();
            r.waitOb();
            if (!ok.equals(GO))
                return false;
            if (r.getNotifica().matches("[0-1]"))
                try {
                    choice = Integer.parseInt(r.getNotifica());
                }catch (NumberFormatException e){
                    choice=-1;
                }

            else
                System.out.println("Wrong value");
        } while (!(choice == 0 || choice == 1));
        return choice == 1;
    }

    @Override
    public void chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {
        Thread t = new Thread(() -> {
            Object o = new Object();
            safePrinter.registry(this);
            safePrinter.print(this,"Choose Window Pattern ");

            AbstractCardFactory factory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);

            Hashtable<String , Drawable> deck = null;
            try {
                deck = factory.getNewCardDeck();
            } catch (FileNotFoundException e) {
                safePrinter.print(this,"Catch chooseWP CLI");
                e.printStackTrace();
            }

            windowPatternCard1 = (WindowPatternCard) deck.get(wp1fronte); //setUpWindowPattern(pattern1);
            windowPatternCard2 = (WindowPatternCard) deck.get(wp2retro); //setUpWindowPattern(pattern2);
            windowPatternCard3 = (WindowPatternCard) deck.get(wp3fronte); //setUpWindowPattern(pattern3);
            windowPatternCard4 = (WindowPatternCard) deck.get(wp4retro); //setUpWindowPattern(pattern4);

            printBoard(windowPatternCard1);
            printBoard(windowPatternCard2);
            printBoard(windowPatternCard3);
            printBoard(windowPatternCard4);

            boolean correct = false;
            Object chooserObject = new Object();
            safePrinter.registry(chooserObject);
            String answer;
            do {

                safePrinter.print(chooserObject,"Choose id -> 0 \n Create Dynamic Card -> 1 ");
                ReadObject r = safeRead();
                r.waitOb();

                answer = r.getNotifica();
                if (answer.equals("0")|| answer.equals("1"))
                    correct =true;
                else
                    safePrinter.print(chooserObject,"Wrong choice");

            }while (!correct);

            SpecialBoolean ok = GO;
            switch (answer){
                case "0":
                    chooseIdWP(ok,chooserObject);
                    break;
                case "1":
                    DinamicCardCreator dc = createDynamicCard(ok,chooserObject);
                    try {
                        clientServerSender.choosenWindowPattern(dc.toPacket(),username);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }



        });
        t.start();
    }


    private DinamicCardCreator createDynamicCard(SpecialBoolean ok , Object o){

        String finish ="";
        boolean correct ;
        String cardName = "";
        String difficult = "";
        do {
            safePrinter.print(o,"Choose Name:");
            ReadObject r = safeRead();
            r.waitOb();
            String name = r.getNotifica();
            cardName= name;
            correct = name.matches("[A-Za-z0-9]+");
            if (!correct)
                safePrinter.print(o,"Insert a Valid Name");

        }while (!correct);

        do {
            safePrinter.print(o,"Choose difficult:");
            ReadObject r = safeRead();
            r.waitOb();
            String name = r.getNotifica();
            difficult= name;
            correct = name.matches("[0-9]+");
            if (!correct)
                safePrinter.print(o,"Insert a number ");

        }while (!correct);

        DinamicCardCreator dc = new DinamicCardCreator(cardName,Integer.parseInt(difficult));

        boolean finishEnter = false;
        String choice;
        do {
            safePrinter.print(o,"Add restriction? \n yes -> 1    no -> 0");
            ReadObject r = safeRead();
            r.waitOb();

            choice = r.getNotifica();
            if (!choice.matches("[0-1]")){
                safePrinter.print(o,"Wrong choice");
                finishEnter = false;
            }else {
                if (choice.equals("0")){
                    finishEnter = true;

                }
                else
                    generateDynamicCard(dc,o,ok);
            }
        }while (ok.isFlag()&&!finishEnter);
        return dc;
    }

    private void chooseIdWP(SpecialBoolean ok , Object o ){
        boolean choseOK = false;

        do {
            safePrinter.print(o,"Choose ID: ");
            ReadObject r = safeRead();
            r.waitOb();
            String choice = r.getNotifica();

            if (!ok.isFlag())
                return;

            int chose;
            try {
                chose = Integer.parseInt(choice);
            }catch (NumberFormatException e){
                chose = -1;
            }


            if (chose == Integer.parseInt(windowPatternCard1.getID()) || chose == Integer.parseInt(windowPatternCard2.getID()) ||
                    chose == Integer.parseInt(windowPatternCard3.getID()) || chose == Integer.parseInt(windowPatternCard4.getID())) {
                try {
                    clientServerSender.choosenWindowPattern(Integer.toString(chose), username);
                } catch (RemoteException e) {
                    generiClient.manageDisconnection(username, ip, Integer.parseInt(port));
                }
                choseOK = true;
            } else {
                safePrinter.print(o,"Wrong id");
            }
        }while (!choseOK);

    }
    public void generateDynamicCard(DinamicCardCreator dc , Object o , SpecialBoolean ok){
        String res="";
        String index="";
        boolean correct = false;
        do {
            if (!ok.isFlag())
                return;
            safePrinter.print(o,"Choose Restriction : R  B  Y  G  P  1  2  3  4  5  6");
            ReadObject r = safeRead();
            r.waitOb();

            res = r.getNotifica();
            if (res.equals("R")||res.equals("B")||res.equals("Y")||res.equals("G")||res.equals("P")||res.equals("1")||res.equals("2")||res.equals("3")||res.equals("4")||res.equals("5")||res.equals("6"))
                correct=true;
            if (!correct)
                safePrinter.print(o,"Invalid selection");
        }while (!correct);


        correct=false;
        do {
            if (!ok.isFlag())
                return;
            safePrinter.print(o,"Choose Index : 1  2  3  4  5  6  7  8  9  10  11  12  13  14  15  16  17  18  19  20");
            ReadObject r = safeRead();
            r.waitOb();

            index = r.getNotifica();
            if (index.equals("7")||index.equals("8")||index.equals("9")||index.equals("10")||index.equals("1")||index.equals("1")||index.equals("2")||index.equals("3")||index.equals("4")||index.equals("5")||index.equals("6")||index.equals("12")||index.equals("13")||index.equals("14")||index.equals("15")||index.equals("16")||index.equals("17")||index.equals("18")||index.equals("19")||index.equals("20"))
                correct=true;
            if (!correct)
                safePrinter.print(o,"Invalid selection");
        }while (!correct);


        dc.addRestriction(res,Integer.parseInt(index));

    }

    @Override
    public int getRoundIndex() {
        boolean chooseOK = false;
        Object o = new Object();
        safePrinter.registry(o);
        do {
            safePrinter.print(o,"Choose round index: ");

            SpecialBoolean ok = GO;
            ReadObject r = safeRead();
            r.waitOb();
            if (!ok.equals(GO))
                return -1;
            try {
                roundID = Integer.parseInt(r.getNotifica());
            }catch (NumberFormatException e){
                roundID = -1;
            }


            if (1 <= roundID && roundID <= gameStatus.getBoardRound().getDices().size())
                chooseOK = true;
        }while (!chooseOK);
        roundID = roundID-1;
        return roundID;
    }

    @Override
    public void updateGameStatus(GameStatus gameStat) {

        pingBack();

        gameStatus = gameStat;
        placeDiceAction = gameStatus.getPlayerByName(username).getPlaceDiceOfTheTurn();
        useToolCardBasic = gameStatus.getPlayerByName(username).getUseToolCardOfTheTurn();
        resetAllIndex();

        print(gameStatus);


    }



    private void resetAllIndex() {
        roundID = -1;
        draftPoolIndex = -1;
        cellIndexTo = -1;
        cellIndexFrom = -1;
        diceIndex = -1;
    }

    private SpecialBoolean GO = new SpecialBoolean(true);
    private void stopallRead(){
        GO.setFlag(false);
        GO=new SpecialBoolean(true);
    }


    private void useToolCard(){
        SpecialBoolean ok = GO;

            useToolCardBasic.useAction(this, gameStatus, username, ok );

            if (!ok.equals(GO))
                return;

            try {
                clientServerSender.sendAction(useToolCardBasic, username);
            } catch (RemoteException e) {
                generiClient.manageDisconnection(username, ip, Integer.parseInt(port));
            }


    }
    UI temp = this;
    public void placeDice(){
        SpecialBoolean ok = GO;
            placeDiceAction.useAction(this, gameStatus, username, ok);
            if (!ok.equals(GO))
                return;
            try {
                clientServerSender.sendAction(placeDiceAction, username);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

    }

    private void printToolCard(){
        Object o = new Object();
        safePrinter.registry(this);
        safePrinter.print(this,ANSI_COLOR.ANSI_RED + "TOOL CARDS: " + ANSI_COLOR.ANSI_RESET);
        for (ToolCard toolCard : gameStatus.getToolCards()){
            safePrinter.print(this,ANSI_COLOR.ANSI_RED +
                    toolCard.getName() + toolCard.getEffect()
                    + "\n" + ANSI_COLOR.ANSI_RESET);
        }
    }

    private void printPrivateCard(){
        Object o = new Object();
        safePrinter.registry(this);
        safePrinter.print(this,ANSI_COLOR.ANSI_BLUE + "PRIVATE CARD: "  +
                gameStatus.getPlayerPrivateObjectiveCards(username).getDiceColor()
                + ANSI_COLOR.ANSI_RESET
        );
    }

    private void printPublicCard(){
        Object o = new Object();
        safePrinter.registry(this);
        safePrinter.print(this,ANSI_COLOR.ANSI_GREEN + "PUBLIC CARDS: " + ANSI_COLOR.ANSI_RESET);
        for (PublicObjectiveCard publicObjectiveCard : gameStatus.getPublicObjectiveCards()){
            safePrinter.print(this,ANSI_COLOR.ANSI_GREEN + publicObjectiveCard.getName() +
                    publicObjectiveCard.getDescription() + "\n" + ANSI_COLOR.ANSI_RESET);
        }
    }

    private void printRoundTrack(){

        StringBuilder line = new StringBuilder();
        Object o = new Object();
        safePrinter.registry(this);
        for (int i = 0; i < 10; i++){
            if(gameStatus.getBoardRound().getDices().size()<=i)
                line.append(i + 1).append(" | ");
            else
                for (int j = 0; j < gameStatus.getBoardRound().getDices().get(i).size(); j++){
                    if (gameStatus.getBoardRound().getDices().get(i).get(j) != null) {
                        line.append(gameStatus.getBoardRound().getDices().get(i).get(j).getDiceColor().getAnsiColor()).
                                append(ANSI_COLOR.BOLD).append("[").append(gameStatus.getBoardRound().getDices().get(i).get(j).getValue()).
                                append("]").append(ANSI_COLOR.ANSI_RESET).append(" ");
                    }
                }


        }
        safePrinter.print(this,line.toString());
    }



    private boolean active = false;

    private SpecialBoolean FLAG = new SpecialBoolean(true);
    private void print(GameStatus gameStatus){
        stopallRead();
        printGameStatus();



        FLAG.setFlag(false);
        FLAG = new SpecialBoolean(true);

        System.out.println("PLAYER ENABLE: " + active );
        System.out.println("PLAYER NAME: " + username);


        if (active){

            Thread t = new Thread(()->{

                System.out.println("Quit game? -> 0 ");
                if (gameStatus.getPlayerByName(username).getPlaceDiceState()){
                    System.out.println("Place dice? --> 1");
                }
                if(gameStatus.getPlayerByName(username).getUseToolCardState()){
                    System.out.println("Use ToolCard --> 2");
                }
                boolean correct = true;
                String choose = "";
                do {

                    SpecialBoolean ok = GO ;
                    ReadObject r = safeRead();
                    r.waitOb();
                    choose = r.getNotifica();
                    if (!ok.equals(GO))
                        return;
                    if(choose.equals("1") || choose.equals("2") || choose.equals("0"))
                        correct =false;
                    else
                        System.out.println("Scelta non valida");


                }while (correct&&FLAG.isFlag());


                switch (choose){
                    case "0":
                        exit(0);
                    case "1":
                        placeDice();
                        break;
                    case "2":
                        useToolCard();
                        break;
                }
            });
            t.start();

        }

    }
    private void printGameStatus(){

        printRoundTrack();
        printPrivateCard();
        printPublicCard();
        printToolCard();
        printDraftPool(gameStatus.getDraftPool().getDraft());
        Object o = new Object();
        for (Player p : gameStatus.getPlayerCards().keySet()) {
            safePrinter.registry(o);
            safePrinter.print(o, p.getName());
            safePrinter.print(o, "Token ammount: " + p.getWallet().getTokenAmmount());
            printBoardGame((WindowPatternCard) gameStatus.getPlayerCards().get(p).get(0)); 
        }
    }

    @Override
    public void activate(){

        active = true;
        print(gameStatus);

    }

    @Override
    public void disable() {
        active = false;
        print(gameStatus);
        System.out.println("DISABLE");
    }

    @Override
    public void pingBack() {
            if(clientServerSender!=null) {
                try {
                    generiClient.getClientServerSender().pingBack(username);
                    //clientServerSender.pingBack(username);
                } catch (RemoteException e) {
                    Object o = new Object();
                    safePrinter.registry(this);
                    safePrinter.print(this, "Catch chooseWP CLI pingback");
                    e.printStackTrace();
                }catch(NullPointerException e){
                    System.out.println("PINGIBAG NULL POINTER EXCEPTIO");
                }
            }
    }

    @Override
    public void allCurrentPlayers(String players) {
        Thread t = new Thread(() -> {
            Object o = new Object();
            safePrinter.registry(this);
            String[] names = players.split("\\s*,\\s*");
            safePrinter.print(this,"\nPlayers currently connected:");
            for (String name : names) {
                playersNames.add(name);
                safePrinter.print(this,name);
            }
        });
        t.start();

    }

    @Override
    public ToolCard getChoosenToolCard() {
        int toolcardID;
        boolean chooseOK = false;

        SpecialBoolean ok = GO;
            do {
                Object o = new Object();
                safePrinter.registry(this);
                safePrinter.print(this,"Choose Tool Card : ");

                ReadObject read = safeRead();

                read.waitOb();
                String id = read.getNotifica();
                if (!ok.equals(GO))
                    return null;
                try {
                    toolcardID = Integer.parseInt(id);
                }catch (NumberFormatException e){
                    toolcardID = -1;
                }


                if (toolcardID > 0 && toolcardID <= 3)
                    chooseOK = true;
            }while (!chooseOK);
            toolcardID = toolcardID-1;


        return gameStatus.getToolCards().get(toolcardID);
    }

    @Override
    public int getDiceIndexFromRound() {
        boolean chooseOK = false;
        if (gameStatus.getBoardRound().getDices().size() >= (roundID + 1)) {
            do {
                Object o = new Object();
                safePrinter.registry(this);
                safePrinter.print(this,"Choose dice index: ");
                SpecialBoolean ok = GO;
                ReadObject r = safeRead();
                r.waitOb();
                if (!ok.equals(GO))
                    return -1;
                try {
                    diceIndex = Integer.parseInt(r.getNotifica());
                }catch (NumberFormatException e){
                    diceIndex = -1;
                }


                if (diceIndex > 0 && diceIndex <= gameStatus.getBoardRound().getDices().get(roundID).size())
                    chooseOK = true;
            } while (!chooseOK);
        }
        diceIndex = diceIndex - 1;
        return diceIndex;
    }

    @Override
    public void run() {
        Object o = new Object();
        safePrinter.registry(this);
        safePrinter.print(this,ANSI_COLOR.ANSI_RED + ANSI_COLOR.BOLD + "Welcome to Sagrada!" + ANSI_COLOR.ANSI_RESET);
        handleConnection();
        validateUsername(maxNameSize);
    }

    private void handleConnection(){

            boolean repeatInsertion = true;

            do{
                String choice;
                Object o = new Object();
                safePrinter.registry(this);
                safePrinter.print(this,"Choose connection type:\n0->RMI\n1->Socket");

                SpecialBoolean ok = GO;
                ReadObject r = safeRead();
                r.waitOb();
                choice = r.getNotifica();

                boolean correct = false;
                String ip ="";
                ReadObject r1;
                String port ="";

                switch(choice){
                    case "0":
                        rmi = true;
                        //get server ip from input
                        SpecialBoolean ok1;
                        do {
                            safePrinter.print(this,"Insert server IP: ");


                            ok1 = GO;

                            r1 = safeRead();
                            r1.waitOb();
                            ip = r1.getNotifica();
                            if (ip.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"))
                                correct=true;
                        }while (!correct);
                        setIp(ip);
                        //get server port from input
                        correct =false;
                        SpecialBoolean ok2;
                        do {
                            safePrinter.print(this,"Insert server port: ");


                            ok2 = GO;
                            ReadObject r2 = safeRead();
                            r2.waitOb();

                            try {
                                port = r2.getNotifica();
                            }catch (NumberFormatException e){
                                port = "-1";
                            }
                            if (port.matches("[0-9]+") && (Integer.parseInt(port)<65535 && (Integer.parseInt(port)>0)))
                                correct= true;
                        }while (!correct);

                        setPort(port);
                        repeatInsertion = false;
                        break;
                    case "1":
                        //get server ip from input
                        SpecialBoolean ok3;
                        correct = false;
                        do {
                            safePrinter.print(this,"Insert server IP: ");


                            ok3 = GO;

                            r1 = safeRead();
                            r1.waitOb();
                            ip = r1.getNotifica();
                            if (ip.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"))
                                correct=true;
                        }while (!correct);
                        setIp(ip);
                        //get server port from input
                        correct =false;
                        SpecialBoolean ok4;
                        do {
                            safePrinter.print(this,"Insert server port: ");


                            ok4 = GO;
                            ReadObject r2 = safeRead();
                            r2.waitOb();

                            try {
                                port = r2.getNotifica();
                            }catch (NumberFormatException e){
                                port = "-1";
                            }
                            if (port.matches("[0-9]+") && (Integer.parseInt(port)<65535 && (Integer.parseInt(port)>0)))
                                correct= true;
                        }while (!correct);

                        setPort(port);
                        repeatInsertion = false;
                        break;
                    default:
                        safePrinter.print(this,"Value typed is not valid");
                        break;
                }
            }while(repeatInsertion);
    }

    private void handleLogin() {
            if (rmi) {
                generiClient = new GeneriClient();
                generiClient.setLinkClientServerRMI();
                generiClient.setClientServerReciverRMI();
                clientServerReciver = generiClient.getClientServerReciver();
                try {
                    clientServerReciver.setUI(this);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                try {
                    generiClient.register(username, ip, Integer.parseInt(port));
                } catch (ConnectException e){
                    System.out.println("Connection refused");
                    handleConnection();
                    validateUsername(maxNameSize);
                    return;
                }catch (RemoteException e) {
                    System.out.println("Connection refused");
                    handleConnection();
                    validateUsername(maxNameSize);
                    return;
                }
            }
            else {
                generiClient = new GeneriClient();
                generiClient.setLinkClientServer(ip, Integer.parseInt(port));
                generiClient.setClientServerReciver();
                generiClient.setClientServerSender();
                clientServerReciver = generiClient.getClientServerReciver();
                try {
                    clientServerReciver.setUI(this);
                }catch (ConnectException e){
                    System.out.println("Connection refused");
                    handleConnection();
                    validateUsername(maxNameSize);
                    return;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                catch (NullPointerException e){
                    System.out.println("Connection refused");
                    handleConnection();
                    validateUsername(maxNameSize);
                    return;
                }
                generiClient.register(username);
            }
    }

    /**
     * @param max_Length Integer that represents the maximum length of a string
     * @return EventHandler used to validate a string to max_Length and to only digits and letters
     */
    private void validateUsername(final Integer max_Length) {

        Object o = new Object();
        safePrinter.registry(this);
            do {
                safePrinter.print(this,"Enter username:");

                SpecialBoolean ok = GO;
                ReadObject r = safeRead();
                r.waitOb();
                setUsername(r.getNotifica());
                if (username.contains("."))
                    safePrinter.print(this,"Your username can't contain char '.'");
                if (!username.matches("[A-Za-z0-9]+"))
                    safePrinter.print(this,"Username can contain only alphanumeric charachter");
                if (username.length() > max_Length)
                    safePrinter.print(this,"Your username is too long");
            } while (username.contains(".") || username.length() > max_Length||!username.matches("[A-Za-z0-9]+"));
        handleLogin();
    }

    @Override
    public void printMessage(String s){
        Thread t = new Thread(() -> {
            Object o = new Object();
            safePrinter.registry(this);
            safePrinter.print(this,s);
            switch (s) {
                case CONSTANT.usernameAlreadyUsed:
                    validateUsername(maxNameSize);
                    break;
                case CONSTANT.correctUsername:
                    clientServerSender = generiClient.getClientServerSender();
                    break;
            }
        });
        t.start();
    }


    @Override
    public void endGame(String winner){

        ScoreHandler scoreHandler = new ScoreHandler(gameStatus);

        System.out.println("Game Score\n");

        for (Player p : gameStatus.getPlayer()) {
            System.out.println(p.getName() + "scored: " +
                    scoreHandler.getFinalScore().get(gameStatus.getPlayerByName(p.getName()))
                    + " points\n");
        }
        System.out.println("The winner is: " + winner);
    }


    private ReadObject safeRead(){

        SpecialBoolean s = GO;
        ReadObject o = new ReadObject();
        serialReader.registry(o,s);
        return o;
    }

}


class SerialReader{

    Scanner s = new Scanner(System.in);
    Hashtable<ReadObject,SpecialBoolean> waitingList = new Hashtable<>();
    public synchronized void registry(ReadObject r , SpecialBoolean s ) {
        waitingList.put(r,s);
    }

    public synchronized void notifyAllWaitig(String string){
        for (ReadObject a : waitingList.keySet())
            a.notifica(string);
        waitingList = new Hashtable<>();
    }

}

class ReadObject{

    String notifica ="";
    Object o = new Object();

    public void notifica(String s){
        notifica = s;
        synchronized (o){
            o.notify();
        }

    }

    public String getNotifica() {
        return notifica;
    }

    public void waitOb(){
        synchronized (o){
            try {
                o.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

class SafePrinter{

    PrintStream out = System.out;

    Object o ;
    public void registry(Object o1){
        o = o1;
    }

    public void print(Object o1,String s){
        if (o.equals(o1))
            out.println(s);
    }

}

