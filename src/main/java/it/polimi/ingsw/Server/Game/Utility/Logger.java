package it.polimi.ingsw.Server.Game.Utility;

public class Logger {

    private static String log ="";
    private static String lastLog ="";
    public static void log(String s){
        log += s;
        lastLog = s;

    }

    public static String getLog(){
        return log;
    }

    public static String getLastLog(){return lastLog;}
}
