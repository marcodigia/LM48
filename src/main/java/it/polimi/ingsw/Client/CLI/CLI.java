package it.polimi.ingsw.Client.CLI;

import com.sun.org.apache.bcel.internal.generic.FADD;
import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Actions;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.PlaceDiceAction;
import it.polimi.ingsw.Server.Game.GameRules.Actions.Basic.UseToolCardBasic;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;
import javafx.application.Platform;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

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
    private boolean primaMossa = false;
    private int answer;
    
    public CLI(){
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

            safePrint(windowPatternCard.getID());
            ArrayList<Restriction> cells = new ArrayList<>();
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                cells.add(windowPatternCard.getRestrictionAtIndex(i));
                if (i == 5 || i == 10 || i == 15){
                    safePrint(line);
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
            safePrint(line);
            safePrint("\n");

    }


    private void printBoardGame(WindowPatternCard windowPatternCard) {

        StringBuilder line = new StringBuilder();

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
                safePrint(line);
                line = new StringBuilder();
            }
        }
        safePrint(line);
        safePrint("\n");
    }

    private void printDraftPool(ArrayList<Dice> draft) {

        safePrint("Draft Pool : \n");
        StringBuilder line = new StringBuilder();
        for (Dice d : draft) {
            line.append(d.getDiceColor().getAnsiColor()).append(ANSI_COLOR.BOLD).append("[")
                    .append(d.getValue()).append("]").append(ANSI_COLOR.ANSI_RESET)
                    .append("  ");
        }
        safePrint(line);
    }

    @Override
    public int getAmmountToChange(int ammountType){

        InputStream is = System.in;

        int choice;
        do {

            safePrint("Aumentare o Diminuire valore del dado => +1 , -1 : ");
             
            choice = Integer.parseInt(safeRead());
            if (!(choice == -1 || choice == 1))
                safePrint("Valore non valido , inserire +1 o  -1");
        } while (!(choice == -1 || choice == 1));
        return choice;
    }

    @Override
    public int getDraftPoolIndex(){
        boolean indexOK = false;
        do {
            safePrint("Choose dice index from Draft Pool: ");

            // 
            draftPoolIndex = Integer.parseInt(safeRead());

            if (draftPoolIndex > 0 && draftPoolIndex <= gameStatus.getDraftPool().getDraft().size())
                indexOK = true;
        }while (!indexOK);
        draftPoolIndex = draftPoolIndex-1;
        return draftPoolIndex;
    }

    @Override
    public int getMatrixIndexFrom(){
        boolean cellIndexOK = false;
        do {
            safePrint("Choose starting cell index: ");


            cellIndexFrom = Integer.parseInt(safeRead());

            if (cellIndexFrom > 0 && cellIndexFrom <= 20)
                cellIndexOK = true;
        }while (!cellIndexOK);
        cellIndexFrom = cellIndexFrom-1;
        return cellIndexFrom;
    }

    @Override
    public int getMatrixIndexTo(){
        boolean cellIndexOK = false;
        do {
            safePrint("Choose ending cell index: ");

             
            cellIndexTo = Integer.parseInt(safeRead());

            if (cellIndexTo > 0 && cellIndexTo <= 20)
                cellIndexOK = true;
        }while (!cellIndexOK);
        cellIndexTo = cellIndexTo-1;
        return cellIndexTo;
    }

    @Override
    public boolean askForAnotherDice() {
        //TODO implementare
        return false;
    }

    @Override
    public void chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {
        Thread t = new Thread(() -> {
            safePrint("Choose Window Pattern: ");

            AbstractCardFactory factory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);

            Hashtable<String , Drawable> deck = null;
            try {
                deck = factory.getNewCardDeck();
            } catch (FileNotFoundException e) {
                safePrint("Catch chooseWP CLI");
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

            safePrint("Choose ID: ");
            String choice = safeRead();
            int chose = Integer.parseInt(choice);

            if (chose>=1 && chose <=24) {
                try {
                    clientServerSender.choosenWindowPattern(Integer.toString(chose), username);
                } catch (RemoteException e) {
                    generiClient.manageDisconnection(username, ip, Integer.parseInt(port));
                }
            }
            else {
                safePrint("Wrong id");
            }
        });
        t.start();
    }

    @Override
    public int getRoundIndex() {
        boolean chooseOK = false;
        do {
            safePrint("Choose round index: ");

             
            roundID = Integer.parseInt(safeRead());

            if (1 <= roundID && roundID <= 10)
                chooseOK = true;
        }while (!chooseOK);
        roundID = roundID-1;
        return roundID;
    }

    @Override
    public void updateGameStatus(GameStatus gameStat) {


        gameStatus = gameStat;
        placeDiceAction = gameStatus.getPlayerByName(username).getPlaceDiceOfTheTurn();
        useToolCardBasic = gameStatus.getPlayerByName(username).getUseToolCardOfTheTurn();
        resetAllIndex();
        synchronized (Sam){
            printGameStatus();
            semaforo=0;
            if(primaMossa)
                makeSecondMove();
            Sam.notify();
        }

    }

    private void makeSecondMove(){

        int answer2 = -1;

        if(answer==1){
            do {

                safePrint(
                        "Skip? --> 0\n" + "Do you want to place a dice from Draft Pool? --> 1\n");

                answer2 = Integer.parseInt(safeRead());

            }
            while (answer2!=0 && answer2!=1);
            if (answer2 == 1)
                placeDice();
        }

        else{
            do {

                safePrint(
                        "Skip? --> 0\n" + "Do you want to use a Tool Card? --> 1\n");
                answer2 = Integer.parseInt(safeRead());

            }
            while (answer2!=0 && answer2!=1);
            if (answer2==1)
                useToolCard();
        }
        primaMossa = false;
        semaforo = 1;

    }

    private void resetAllIndex() {
        roundID = -1;
        draftPoolIndex = -1;
        cellIndexTo = -1;
        cellIndexFrom = -1;
        diceIndex = -1;
    }

    private void makeMove(){
        while (semaforo==1) {
            try {
                synchronized (Sam) {
                    Sam.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        do {
             

            safePrint(
                    "Skip Turn? --> 0\n" +
                    "Do you want to use a Tool Card? --> 1\n" +
                    "Do you want to place a dice from Draft Pool? --> 2\n");
            answer = Integer.parseInt(safeRead());


        }
        while (answer!=0 && answer!=1 && answer!=2);
        if (answer == 1){
            useToolCard();
        }
        if (answer == 2){
            placeDice();
        }
        primaMossa = true;
        semaforo=1;

    }

    private void useToolCard(){
        useToolCardBasic.useAction(this, gameStatus, username);
        try {
            clientServerSender.sendAction(useToolCardBasic, username);
        } catch (RemoteException e) {
            generiClient.manageDisconnection(username, ip, Integer.parseInt(port));
        }
    }

    public void placeDice(){
        placeDiceAction.useAction(this, gameStatus, username);
        try {
            clientServerSender.sendAction(placeDiceAction, username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void printToolCard(){
        safePrint(ANSI_COLOR.ANSI_RED + "TOOL CARDS: " + ANSI_COLOR.ANSI_RESET);
        for (ToolCard toolCard : gameStatus.getToolCards()){
            safePrint(ANSI_COLOR.ANSI_RED + toolCard.getID() +
                    toolCard.getName() + toolCard.getEffect()
                    + "\n" + ANSI_COLOR.ANSI_RESET);
        }
    }

    private void printPrivateCard(){
        safePrint(ANSI_COLOR.ANSI_BLUE + "PRIVATE CARD: "  +
                gameStatus.getPlayerPrivateObjectiveCards(username).getDiceColor()
                + ANSI_COLOR.ANSI_RESET
        );
    }

    private void printPublicCard(){
        safePrint(ANSI_COLOR.ANSI_GREEN + "PUBLIC CARDS: " + ANSI_COLOR.ANSI_RESET);
        for (PublicObjectiveCard publicObjectiveCard : gameStatus.getPublicObjectiveCards()){
            safePrint(ANSI_COLOR.ANSI_GREEN + publicObjectiveCard.getName() +
                    publicObjectiveCard.getDescription() + "\n" + ANSI_COLOR.ANSI_RESET);
        }
    }

    private void printRoundTrack(){

        StringBuilder line = new StringBuilder();

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
        safePrint(line);
    }


    private boolean active = false;
    private void print(GameStatus gameStatus){

        System.out.println("Games status");

        if (active){
            if (gameStatus.getPlayerByName(username).getPlaceDiceState())
                System.out.println("Place dice?");

        }


    }
    private void printGameStatus(){
        
        printRoundTrack();
        printPrivateCard();
        printPublicCard();
        printToolCard();
        printDraftPool(gameStatus.getDraftPool().getDraft());
        for (Player p : gameStatus.getPlayerCards().keySet()) {
            safePrint(p.getName());
            printBoardGame((WindowPatternCard) gameStatus.getPlayerCards().get(p).get(0)); 
        }
    }

    @Override
    public void activate(){

        Thread t1 = new Thread(this::makeMove);
        t1.start();
        active = true;

    }

    @Override
    public void disable() {
        semaforo=1;
        active = false;
    }

    @Override
    public void pingBack() {
        Thread t = new Thread(() -> {
            if(clientServerSender!=null){
                try {
                    clientServerSender.pingBack(username);
                } catch (RemoteException e) {
                    safePrint("Catch chooseWP CLI pingback");
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override
    public void allCurrentPlayers(String players) {
        Thread t = new Thread(() -> {
            String[] names = players.split("\\s*,\\s*");
            safePrint("\nPlayers currently connected:");
            for (String name : names) {
                playersNames.add(name);
                safePrint(name);
            }
        });
        t.start();

    }

    @Override
    public ToolCard getChoosenToolCard() {
        int toolcardID;
        boolean chooseOK = false;
        do {
            safePrint("Choose Tool Card : ");

             
            toolcardID = Integer.parseInt(safeRead());

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
                safePrint("Choose dice index: ");

                 
                diceIndex = Integer.parseInt(safeRead());

                if (diceIndex > 0 && diceIndex <= gameStatus.getBoardRound().getDices().get(roundID).size())
                    chooseOK = true;
            } while (!chooseOK);
        }
        diceIndex = diceIndex - 1;
        return diceIndex;
    }

    @Override
    public void run() {
        safePrint(ANSI_COLOR.ANSI_RED + ANSI_COLOR.BOLD + "Welcome to Sagrada!" + ANSI_COLOR.ANSI_RESET);
        handleConnection();
        validateUsername(maxNameSize);
    }

    private void handleConnection(){

            boolean repeatInsertion = true;

            do{
                String choice;
                safePrint("Choose connection type:\n0->RMI\n1->Socket");
                 
                choice = safeRead();

                switch(choice){
                    case "0":
                        rmi = true;
                        //get server ip from input
                        safePrint("Insert server IP: ");

                         
                        setIp(safeRead());
                        //get server port from input
                        safePrint("Insert server port: ");

                         
                        setPort(safeRead());
                        repeatInsertion = false;
                        break;
                    case "1":
                        //get server ip from input
                        safePrint("Insert server IP: ");

                         
                        setIp(safeRead());
                        //get server port from input
                        safePrint("Insert server port: ");

                         
                        setPort(safeRead());
                        repeatInsertion = false;
                        break;
                    default:
                        safePrint("Value typed is not valid");
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
                generiClient.register(username, ip, Integer.parseInt(port));
            }
            else {
                generiClient = new GeneriClient();
                generiClient.setLinkClientServer(ip, Integer.parseInt(port));
                generiClient.setClientServerReciver();
                generiClient.setClientServerSender();
                clientServerReciver = generiClient.getClientServerReciver();
                try {
                    clientServerReciver.setUI(this);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                generiClient.register(username);
            }
    }

    /**
     * @param max_Length Integer that represents the maximum length of a string
     * @return EventHandler used to validate a string to max_Length and to only digits and letters
     */
    private void validateUsername(final Integer max_Length) {

            do {
                safePrint("Enter username:");
                 
                setUsername(safeRead());
                if (username.contains("."))
                    safePrint("Your username can't contain char '.'");
                if (username.length() > max_Length)
                    safePrint("Your username is too long");
            } while (username.contains(".") || username.length() > max_Length);
        handleLogin();
    }

    @Override
    public void printMessage(String s){
        Thread t = new Thread(() -> {
            safePrint(s);
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

    }

    private String safeRead(){
        synchronized (System.in){
            message = s.next();
            return message;
        }
    }

    private void safePrint(String s){
        synchronized (System.out){
            System.out.println(s);
        }
    }

    private void safePrint(StringBuilder line) {
        synchronized (System.out){
            System.out.println(line);
        }
    }


}
