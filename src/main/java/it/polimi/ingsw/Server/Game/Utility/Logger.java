package it.polimi.ingsw.Server.Game.Utility;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Logger {

    private static String log ="";
    private static String lastLog ="";
    private static Text text;
    public static void log(String s){
        log += s;
        lastLog = s;
        System.out.println(lastLog);
        if (text!=null)
            Platform.runLater(()-> {

                    text.setText(lastLog);
                    text.setStyle("-fx-font-weight: bold");

            });


    }

    public static String getLog(){
        return log;
    }

    public static String getLastLog(){return lastLog;}

    public static void setTextfx(Text textfx){
       text = textfx;
    }
}
