package it.polimi.ingsw.Client.CLI;

import it.polimi.ingsw.Client.View.UI;
import it.polimi.ingsw.Exceptions.PlayersNumbersException;
import it.polimi.ingsw.Server.Game.Cards.CardsComponents.Cell;
import it.polimi.ingsw.Server.Game.Components.Boards.DraftPool;
import it.polimi.ingsw.Server.Game.Components.Dice;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.Utility.ANSI_COLOR;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

//Decided to make CLI static , because it just need to print stuff
public class CLI extends UI {

    private static final int maxNameSize = 18;
    private static ArrayList<Player> players = new ArrayList<>();
    private static int height = 4;
    private static DraftPool draftPool;


    public static void addPlayer(Player player) throws PlayersNumbersException {
        if (players.size() >= 4)
            throw new PlayersNumbersException();
        players.add(player);
    }

    public static void print_boards() {
        System.out.println("Players Board :\n");
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < height; i++) {
            //System.out.println(i);
            for (Player p : players) {
                ArrayList<Cell> cells = p.getRow(i);
                for (Cell cell : cells) {
                    if (cell.isEmpty())
                        line.append(cell.getRestriction().toString());
                    else {
                        Dice d = cell.getDice();
                        line.append(d.getDiceColor().getAnsiColor()).append(ANSI_COLOR.BOLD).append("[").append(d.getValue()).append("]").append(ANSI_COLOR.ANSI_RESET);
                    }
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

    public static void print_draftborad(ArrayList<Dice> draft) {

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
    public int getDraftPoolIndex() {
        System.out.println("Indice dado Draft pool : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public int getMatrixIndexFrom() {
        System.out.println("Indice cella Window Pattern partenza : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
    }

    @Override
    public int getMatrixIndexTo() {
        System.out.println("Indice cella window Pattern destinazione : ");
        InputStream is = System.in;
        Scanner scanner = new Scanner(is);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < draftPool.getDraft().size())
            return choice;
        return -1;
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
    public void UpdateDraftPol() {

    }

    @Override
    public void updateWindowPattern(Player player) {

    }

    @Override
    public void updateRoundTrack() {

    }
}
