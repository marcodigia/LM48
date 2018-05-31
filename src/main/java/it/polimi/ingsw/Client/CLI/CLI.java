package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.Server.Game.Cards.*;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.GameRules.GameStatus;
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
public class CLI implements UI,Runnable{

    private static final int maxNameSize = 18;
    private static ArrayList<Player> players = new ArrayList<>();
    private static int height = 4;
    private static DraftPool draftPool;
    private ClientServerSender clientServerSender;


    public CLI(){
    }
    
    public void setClientServerSender(ClientServerSender cl){this.clientServerSender=cl;}

    public void print_boards() {
        System.out.println("Players Board :\n");
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < height; i++) {
            //System.out.println(i);
            for (Player p : players) {
                //ArrayList<Cell> cells = p.getRow(i);

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

        System.out.println("chose wp " + wp1fronte + " " + wp2retro + " " + wp3fronte +  " " + wp4retro);
        Scanner scanner = new Scanner(System.in);
        int chose = scanner.nextInt();
        AbstractCardFactory factory = new WindowPatternCardFactory(CONSTANT.windowPatternfile);
        Player p = new Player("aa", null);
        try {
            Hashtable<String , Drawable> deck =factory.getNewCardDeck();
            p.setGameContext(new GameContext(null,null,null, (WindowPatternCard) deck.get(Integer.toString(chose)),null));
            players.add(p);
            print_boards();
            try {
                clientServerSender.choosenWindowPattern(Integer.toString(chose), "aaa");
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

    }

    @Override
    public void allCurrentPlayers(String players) {

    }

    @Override
    public ToolCard getChoosenToolCard() {
        return null;
    }

    @Override
    public void run() {

    }
}
