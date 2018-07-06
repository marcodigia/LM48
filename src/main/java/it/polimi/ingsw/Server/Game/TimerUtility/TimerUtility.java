package it.polimi.ingsw.Server.Game.TimerUtility;

import java.io.*;

public class TimerUtility {

    private File file;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public int readTimerFromFile(int delaySecond,String path){
        int delay = delaySecond*1000; //If timer.txt is not present create it with value 5s
        file = new File(path);
        try {
            if(file.createNewFile()) {
                fileWriter = new FileWriter(file);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(String.valueOf(delay));
                bufferedWriter.flush();
            }
            else{
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                delay = bufferedReader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(bufferedWriter!=null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileWriter!=null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufferedReader!=null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileReader!=null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(file!=null)
                file.setReadOnly();
        }
        return delay;
    }


}
