package it.polimi.ingsw.Server.Game.Utility;

public class Logger {

    private static String log ="";

    public static void log(String s){
        log += s;

        System.out.println("\\033[2J");
        System.out.println(log);
    }

    public static String getLog(){
        return log;
    }
}
