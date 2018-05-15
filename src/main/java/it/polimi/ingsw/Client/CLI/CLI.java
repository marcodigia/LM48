package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.ClientServerCommonInterface.ClientServerSender;
import it.polimi.ingsw.Server.Game.Cards.AbstractCardFactory;
import it.polimi.ingsw.Server.Game.Cards.Drawable;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCardFactory;
import it.polimi.ingsw.Server.Game.GameRules.GameContext;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.UI;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

//Decided to make CLI static , because it just need to print stuff
public class CLI extends UI implements Runnable{

    private static final int maxNameSize = 18;
    private static ArrayList<Player> players = new ArrayList<>();
    private static int height = 4;
    private static DraftPool draftPool;
    private ClientServerSender clientServerSender;
    private PrintStream ps;

    public CLI(ClientServerSender clientServerSender, PrintStream ps){
        this.clientServerSender = clientServerSender;
        this.ps = ps;

    }

    public void print_boards() {
        ps.println("Players Board :\n");
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < height; i++) {
            //ps.println(i);
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
        ps.println(line);
    }

    public void print_draftboard(ArrayList<Dice> draft) {

        ps.println("Draft Pool : \n");
        StringBuilder line = new StringBuilder();
        for (Dice d : draft) {
            line.append(d.getDiceColor().getAnsiColor()).append(ANSI_COLOR.BOLD).append("[")
                    .append(d.getValue()).append("]").append(ANSI_COLOR.ANSI_RESET)
                    .append("  ");
        }
        ps.println(line);
        ps.println();
    }

    @Override
    public void printMessage(String s){
        ps.println(s);
    }

    @Override
    public int getAmmountToChange(){

        InputStream is = System.in;
        Scanner scanner = new Scanner(is);

        int choice;
        do {

            ps.println("Aumentare o Diminuire valore del dado => +1 , -1 : ");
            choice = scanner.nextInt();
            if (!(choice == -1 || choice == 1))
                ps.println("Valore non valido , inserire +1 o  -1");
        } while (!(choice == -1 || choice == 1));
        return choice;
    }

    @Override
    public int getDraftPoolIndex(){
        ps.println("Indice dado Draft pool : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public int getMatrixIndexFrom(){
        ps.println("Indice cella Window Pattern partenza : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public int getMatrixIndexTo(){
        ps.println("Indice cella window Pattern destinazione : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public String chooseWP(String wp1fronte, String wp2retro, String wp3fronte, String wp4retro) {

        ps.println("chose wp " + wp1fronte + " " + wp2retro + " " + wp3fronte +  " " + wp4retro);
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
                clientServerSender.choosenWindowPattern(Integer.toString(chose));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int getRoundTrackIndex() {
        ps.println("Indice cella round Track : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public void UpdateDraftPol() {

    }

    @Override
    public void updateWindowPattern(Player player) {

    }

    @Override
    public void updateRoundTrack() {

    }

    @Override
    public void run() {

    }
}
