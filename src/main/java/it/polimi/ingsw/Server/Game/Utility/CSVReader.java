package it.polimi.ingsw.Server.Game.Utility;

import it.polimi.ingsw.Server.Game.GameRules.Restriction;

import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {

    public static ArrayList<String> parseLine(String line) {
        Scanner data;
        data = new Scanner(line);
        data.useDelimiter(",");
        ArrayList<String> pattern = new ArrayList<>();
        while (data.hasNext()) {
            String s = data.next();
            pattern.add(s);


        }
        data.close();
        return pattern;
    }

}
