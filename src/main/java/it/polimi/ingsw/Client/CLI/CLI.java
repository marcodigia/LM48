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

    private int roundID = -1;
    private int draftPoolIndex = -1;
    private int cellIndexTo = -1;
    private int cellIndexFrom = -1;
    private int diceIndex = -1;



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

            System.out.println(windowPatternCard.getID());
            ArrayList<Restriction> cells = new ArrayList<>();
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                cells.add(windowPatternCard.getRestrictionAtIndex(i));
                if (i == 5 || i == 10 || i == 15){
                    System.out.println(line);
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
            System.out.println(line);
            System.out.println("\n");

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
                System.out.println(line);
                line = new StringBuilder();
            }
        }
        System.out.println(line);
        System.out.println("\n");
    }

    private void printDraftPool(ArrayList<Dice> draft) {

        System.out.println("Draft Pool : \n");
        StringBuilder line = new StringBuilder();
        for (Dice d : draft) {
            line.append(d.getDiceColor().getAnsiColor()).append(ANSI_COLOR.BOLD).append("[")
                    .append(d.getValue()).append("]").append(ANSI_COLOR.ANSI_RESET)
                    .append("  ");
        }
        System.out.println(line);
    }

    @Override
    public int getAmmountToChange(int ammountType){

        Thread t = new Thread(()->{});
        t.start();

        InputStream is = System.in;
        Scanner scanner = new Scanner(is);

        int choice;
        do {

            System.out.println("Aumentare o Diminuire valore del dado => +1 , -1 : ");
            System.out.print("\r");
            choice = scanner.nextInt();
            if (!(choice == -1 || choice == 1))
                System.out.println("Valore non valido , inserire +1 o  -1");
        } while (!(choice == -1 || choice == 1));
        return choice;
    }

    @Override
    public int getDraftPoolIndex(){
        boolean indexOK = false;
        do {
            System.out.println("Choose dice index from Draft Pool: ");
            Scanner keyboard = new Scanner(System.in);
            System.out.print("\r");
            draftPoolIndex = keyboard.nextInt();
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
            System.out.println("Choose starting cell index: ");
            Scanner keyboard = new Scanner(System.in);
            System.out.print("\r");
            cellIndexFrom = keyboard.nextInt();
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
            System.out.println("Choose ending cell index: ");
            Scanner keyboard = new Scanner(System.in);
            System.out.print("\r");
            cellIndexTo = keyboard.nextInt();
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
            System.out.println("Choose Window Pattern: ");

            AbstractCardFactory factory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);

            Hashtable<String , Drawable> deck = null;
            try {
                deck = factory.getNewCardDeck();
            } catch (FileNotFoundException e) {
                System.out.println("Catch chooseWP CLI");
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

            System.out.println("Choose ID: ");
            Scanner scanner = new Scanner(System.in);
            System.out.print("\r");
            int chose = scanner.nextInt();

            if (chose>=1 && chose <=24) {
                try {
                    clientServerSender.choosenWindowPattern(Integer.toString(chose), username);
                } catch (RemoteException e) {
                    generiClient.manageDisconnection(username, ip, Integer.parseInt(port));
                }
            }
            else {
                System.out.println("Wrong id");
            }
        });
        t.start();
    }

    @Override
    public int getRoundIndex() {
        boolean chooseOK = false;
        do {
            System.out.println("Choose round index: ");
            Scanner keyboard = new Scanner(System.in);
            System.out.print("\r");
            roundID = keyboard.nextInt();
            if (1 <= roundID && roundID <= 10)
                chooseOK = true;
        }while (!chooseOK);
        roundID = roundID-1;
        return roundID;
    }

    @Override
    public void updateGameStatus(GameStatus gameStat) {
        Thread t = new Thread(() -> {
            gameStatus = gameStat;
            printGameStatus();
            placeDiceAction = gameStatus.getPlayerByName(username).getPlaceDiceOfTheTurn();
            useToolCardBasic = gameStatus.getPlayerByName(username).getUseToolCardOfTheTurn();
            resetAllIndex();
        });
        t.start();
    }

    private void resetAllIndex() {
        roundID = -1;
        draftPoolIndex = -1;
        cellIndexTo = -1;
        cellIndexFrom = -1;
        diceIndex = -1;
    }

    private void makeMove(){
        int answer, answer2;
        Scanner keyboard = new Scanner(System.in);
        do {
            System.out.print("\r");
            System.out.println(
                    "Skip Tourn? --> 0\n" +
                    "Do you want to use a Tool Card? --> 1\n" +
                    "Do you want to place a dice from Draft Pool? --> 2\n");
            answer = keyboard.nextInt();
        }
        while (answer!=0 && answer!=1 && answer!=2);
        if (answer == 1){
            useToolCard();
            do {
                System.out.print("\r");
                System.out.println(
                        "Skip? --> 0\n" + "Do you want to place a dice from Draft Pool? --> 1\n");
                answer2 = keyboard.nextInt();
            }
            while (answer2!=0 && answer2!=1);
            if (answer2 == 1)
                placeDice();
        }
        if (answer == 2){
            placeDice();
            do {
                System.out.print("\r");
                System.out.println(
                        "Skip? --> 0\n" + "Do you want to use a Tool Card? --> 1\n");
                answer2 = keyboard.nextInt();
            }
            while (answer2!=0 && answer2!=1);
            if (answer2==1)
                useToolCard();
        }
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
            System.out.println("PlaceDiceAction CLI: " + placeDiceAction.toPacket());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void printToolCard(){
        System.out.println(ANSI_COLOR.ANSI_RED + "TOOL CARDS: " + ANSI_COLOR.ANSI_RESET);
        for (ToolCard toolCard : gameStatus.getToolCards()){
            System.out.println(ANSI_COLOR.ANSI_RED + toolCard.getID() +
                    toolCard.getName() + toolCard.getEffect()
                    + "\n" + ANSI_COLOR.ANSI_RESET);
        }
    }

    private void printPrivateCard(){
        System.out.println(ANSI_COLOR.ANSI_BLUE + "PRIVATE CARD: " + ANSI_COLOR.ANSI_RESET);
        System.out.println(ANSI_COLOR.ANSI_BLUE +
                gameStatus.getPlayerPrivateObjectiveCards(username).getDiceColor()
                + ANSI_COLOR.ANSI_RESET
        );
    }

    private void printPublicCard(){
        System.out.println(ANSI_COLOR.ANSI_GREEN + "PUBLIC CARDS: " + ANSI_COLOR.ANSI_RESET);
        for (PublicObjectiveCard publicObjectiveCard : gameStatus.getPublicObjectiveCards()){
            System.out.println(ANSI_COLOR.ANSI_GREEN + publicObjectiveCard.getName() +
                    publicObjectiveCard.getDescription() + "\n" + ANSI_COLOR.ANSI_RESET);
        }
    }

    private void printRoundTrack(){

        StringBuilder line = new StringBuilder();

        for (int i = 0; i < 10; i++){
            if(gameStatus.getBoardRound().getDices().size()==0)
                line.append(i + 1).append(" | ");
            else
                for (int j = 0; j < gameStatus.getBoardRound().getDices().get(i).size() + 1; j++){
                    if (gameStatus.getBoardRound().getDices().get(i).get(j) != null) {
                        line.append(gameStatus.getBoardRound().getDices().get(i).get(j).getDiceColor().getAnsiColor()).
                                append(ANSI_COLOR.BOLD).append("[").append(gameStatus.getBoardRound().getDices().get(i).get(j).getValue()).
                                append("]").append(ANSI_COLOR.ANSI_RESET).append(" ");
                    }
                }

        }
        System.out.println(line);
    }

    private void printGameStatus(){
        printRoundTrack();
        printPrivateCard();
        printPublicCard();
        printToolCard();
        printDraftPool(gameStatus.getDraftPool().getDraft());
        for (Player p : gameStatus.getPlayerCards().keySet()){
            System.out.println(p.getName());
            printBoardGame((WindowPatternCard) gameStatus.getPlayerCards().get(p).get(0));
        }
    }

    @Override
    public void activate(){
        Thread t = new Thread(this::makeMove);
        t.start();
    }

    @Override
    public void disable() {

    }

    @Override
    public void pingBack() {
        Thread t = new Thread(() -> {
            if(clientServerSender!=null){
                try {
                    clientServerSender.pingBack(username);
                } catch (RemoteException e) {
                    System.out.println("Catch chooseWP CLI pingback");
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
            System.out.println("\nPlayers currently connected:");
            for (String name : names) {
                playersNames.add(name);
                System.out.println(name);
            }
        });
        t.start();
    }

    @Override
    public ToolCard getChoosenToolCard() {
        int toolcardID;
        boolean chooseOK = false;
        do {
            System.out.println("Choose Tool Card ID: ");
            Scanner keyboard = new Scanner(System.in);
            System.out.print("\r");
            toolcardID = keyboard.nextInt();
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
                System.out.println("Choose dice index: ");
                Scanner keyboard = new Scanner(System.in);
                System.out.print("\r");
                diceIndex = keyboard.nextInt();
                if (diceIndex > 0 && diceIndex <= gameStatus.getBoardRound().getDices().get(roundID).size())
                    chooseOK = true;
            } while (!chooseOK);
        }
        diceIndex = diceIndex - 1;
        return diceIndex;
    }

    @Override
    public void run() {
        System.out.println(ANSI_COLOR.ANSI_RED + ANSI_COLOR.BOLD + "Welcome to Sagrada!" + ANSI_COLOR.ANSI_RESET);
        handleConnection();
        validateUsername(maxNameSize);
    }

    private void handleConnection(){
            Scanner input = new Scanner(System.in);
            boolean repeatInsertion = true;

            do{
                String choice;
                System.out.println("Choose connection type:\n0->RMI\n1->Socket");
                System.out.print("\r");
                choice = input.nextLine();

                switch(choice){
                    case "0":
                        rmi = true;
                        //get server ip from input
                        System.out.println("Insert server IP: ");
                        input = new Scanner(System.in);
                        System.out.print("\r");
                        setIp(input.nextLine());
                        //get server port from input
                        System.out.println("Insert server port: ");
                        input = new Scanner(System.in);
                        System.out.print("\r");
                        setPort(input.nextLine());
                        repeatInsertion = false;
                        break;
                    case "1":
                        //get server ip from input
                        System.out.println("Insert server IP: ");
                        input = new Scanner(System.in);
                        System.out.print("\r");
                        setIp(input.nextLine());
                        //get server port from input
                        System.out.println("Insert server port: ");
                        input = new Scanner(System.in);
                        System.out.print("\r");
                        setPort(input.nextLine());
                        repeatInsertion = false;
                        break;
                    default:
                        System.out.println("Value typed is not valid");
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
            Scanner keyboard = new Scanner(System.in);
            do {
                System.out.println("Enter username:");
                System.out.print("\r");
                setUsername(keyboard.nextLine());
                if (username.contains("."))
                    System.out.println("Your username can't contain char '.'");
                if (username.length() > max_Length)
                    System.out.println("Your username is too long");
            } while (username.contains(".") || username.length() > max_Length);
            handleLogin();
    }

    @Override
    public void printMessage(String s){
        Thread t = new Thread(() -> {
            System.out.println(s);
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

}
