package it.polimi.ingsw.Server.Game.Utility;

public class Logger {

    private static String log ="";

    public static void log(String s){
        log += s;

    }

    public static String getLog(){
        return log;
    }
}
