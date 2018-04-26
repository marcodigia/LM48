package it.polimi.ingsw.Server.Game.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {

    public static ArrayList<String> parseLine(String line) {
        Scanner data;
        data = new Scanner(line);
        data.useDelimiter(",");
        ArrayList<String> pattern = new ArrayList<String>();
        while (data.hasNext()) {
            String s = data.next();
            pattern.add(s);
        }
        data.close();
        return pattern;
    }

}
