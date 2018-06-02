package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.AbstractClient.GeneriClient;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerReciver;
import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
import it.polimi.ingsw.Server.Game.GameRules.Restriction;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

//Decided to make CLI static , because it just need to print stuff
public class CLI implements UI, Runnable{

    private static final int maxNameSize = 20;
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<String> playersNames = new ArrayList<>();
    private static int height = 4;
    private static DraftPool draftPool;
    private static String ip;
    private static String port;
    private GameStatus gameStatus;
    private ClientServerSender clientServerSender;
    private ClientServerReciver clientServerReciver;
    private GeneriClient generiClient;
    private String username;
    private boolean rmi = false;

    public CLI(){
    }

    /**
     * @param ip String which represents server ip
     */
    public static void setIp(String ip) {
        CLI.ip = ip;
    }

    /**
     * @param port String which represents server port
     */
    public static void setPort(String port) {
        CLI.port = port;
    }

    /**
     * @param generiClient GeneriClient which represents an RMI or Socket client
     */
    public void setGeneriClient(GeneriClient generiClient){
        this.generiClient = generiClient;
    }

    /**
     * @return GeneriClient getter
     */
    public GeneriClient getGeneriClient(){return generiClient;}

    /**
     * @param u String which represents username's name
     */
    public void setUsername(String u){
        username = u;
    }

    public void print_boards() {
        System.out.println(ANSI_COLOR.BOLD + "Players Board:" + ANSI_COLOR.ANSI_RESET);
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < height; i++) {
            //System.out.println(i);
            for (Player p : players) {
                ArrayList<Restriction> cells = p.getWindowPatternCard().getRow(i);
                for (Restriction r : cells
                     ) {
                    System.out.println(r.name());
                }
                line.append("   ");
            }
            line.append("\n");

        }
        //TODO Max name size

        for (Player player : players) {

            int space = maxNameSize - player.getName().length();
            String spaces = new String(new char[space]).replace("\0", " ");


            line.append(player.getName()).append(spaces);
        }
        System.out.println(line);
    }

    private void printBoard(WindowPatternCard windowPatternCard){
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < height; i++) {
                ArrayList<Restriction> cells = windowPatternCard.getRow(i);
                for (Restriction r : cells
                        ) {
                    System.out.println(r.name());
                }
                line.append("   ");
            }
            line.append("\n");
    }

    public void print_draftboard(ArrayList<Dice> draft) {

        System.out.println("Draft Pool : \n");
        StringBuilder line = new StringBuilder();
        for (Dice d : draft) {
            line.append(d.getDiceColor().getAnsiColor()).append(ANSI_COLOR.BOLD).append("[")
                    .append(d.getValue()).append("]").append(ANSI_COLOR.ANSI_RESET)
                    .append("  ");
        }
        System.out.println(line);
        System.out.println();
    }

    @Override
    public void printMessage(String s){
        if(s.equals((CONSTANT.correctUsername))){
            clientServerSender = generiClient.getClientServerSender();
            pingBack();
        }
        System.out.println(s);
    }

    @Override
    public int getAmmountToChange(){

        InputStream is = System.in;
        Scanner scanner = new Scanner(is);

        int choice;
        do {

            System.out.println("Aumentare o Diminuire valore del dado => +1 , -1 : ");
            choice = scanner.nextInt();
            if (!(choice == -1 || choice == 1))
                System.out.println("Valore non valido , inserire +1 o  -1");
        } while (!(choice == -1 || choice == 1));
        return choice;
    }

    @Override
    public int getDraftPoolIndex(){
        System.out.println("Indice dado Draft pool : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public int getMatrixIndexFrom(){
        System.out.println("Indice cella Window Pattern partenza : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public int getMatrixIndexTo(){
        System.out.println("Indice cella window Pattern destinazione : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public void chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {

    /*    AbstractCardFactory factory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);
        try {
            Hashtable<String , Drawable> deck =factory.getNewCardDeck();
            WindowPatternCard wp1 = (WindowPatternCard) deck.get(Integer.parseInt(wp1fronte));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    */

        System.out.println("Choose Window Pattern: " + wp1fronte + " " + wp2retro + " " + wp3fronte + " " + wp4retro);

        Scanner scanner = new Scanner(System.in);
        int chose = scanner.nextInt();

        AbstractCardFactory factory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);

        Player p = new Player(username, null);
        try {
            Hashtable<String , Drawable> deck =factory.getNewCardDeck();
            deck.get(Integer.parseInt(wp1fronte));
            //printBoard();
            deck.get(Integer.parseInt(wp2retro));
            deck.get(Integer.parseInt(wp3fronte));
            deck.get(Integer.parseInt(wp4retro));

            p.setGameContext(new GameContext(null,null,null, (WindowPatternCard) deck.get(Integer.toString(chose)),null));
            players.add(p);
            print_boards();
            try {
                clientServerSender.choosenWindowPattern(Integer.toString(chose), username);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    @Override
    public int getRoundTrackIndex() {
        System.out.println("Indice cella round Track : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public void updateGameStatus(GameStatus gameStatus) {

    }


    @Override
    public void activate() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void pingBack() {
        if(clientServerSender!=null){
            try {
                clientServerSender.pingBack(username);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void allCurrentPlayers(String players) {
        String[] names = players.split("\\s*,\\s*");
        System.out.println("\nPlayers currently connected:");
        for (int i = 0 ; i < names.length ; i++) {
            playersNames.add(new String(names[i]));
            System.out.println(names[i]);
        }
    }

    @Override
    public ToolCard getChoosenToolCard() {
        return null;
    }

    @Override
    public void run() {
        System.out.println(ANSI_COLOR.ANSI_RED + ANSI_COLOR.BOLD + "Welcome to Sagrada!" + ANSI_COLOR.ANSI_RESET);
        handleConnection();
        username_Validation(maxNameSize);
        handleLogin();
        handleLobby();
    }

    private void handleLobby() {
    }

    private void handleConnection(){
        Scanner input = new Scanner(System.in);
        boolean repeatInsertion = true;

        do{
            String choice;
            System.out.println("Choose connection type:\n0->RMI\n1->Socket");
            choice = input.nextLine();

            switch(choice){
                case "0":
                    rmi = true;
                    //get server ip from input
                    System.out.println("Insert server IP: ");
                    input = new Scanner(System.in);
                    setIp(input.nextLine());
                    //get server port from input
                    System.out.println("Insert server port: ");
                    input = new Scanner(System.in);
                    setPort(input.nextLine());repeatInsertion = false;
                    break;
                case "1":
                    //get server ip from input
                    System.out.println("Insert server IP: ");
                    input = new Scanner(System.in);
                    setIp(input.nextLine());
                    //get server port from input
                    System.out.println("Insert server port: ");
                    input = new Scanner(System.in);
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
    public void username_Validation(final Integer max_Length) {
        //return new
        Scanner keyboard = new Scanner(System.in);
        do {
            System.out.println("Enter username:");
            setUsername(keyboard.nextLine());
            if (username.contains("."))
                System.out.println("Your username can't contain char '.'");
            if (username.length() > max_Length)
                System.out.println("Your username is too long");
        } while (username.contains(".") || username.length()>max_Length);
    }

}
