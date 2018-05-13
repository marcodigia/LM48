package it.polimi.ingsw.Server.Game.WaitingRoom;

import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;
import it.polimi.ingsw.Server.Game.GameRules.Player;
import it.polimi.ingsw.Server.Game.ServerRete.ServerRete;

import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//Al momento fatta per la singola partita
public class WaitingRoom {

    public ArrayList<Player> clientList = new ArrayList<>();
    //TODO alla fine di una partita fare clear di questo array per consentire username uguali in diverse partite
    private ArrayList<Player> clientWhoAreGaming = new ArrayList<>();
    private ServerRete serverRete;
    private Timer timer = null;
    private File file;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public void setServerRete(ServerRete serverRete){
        this.serverRete = serverRete;
    }

    public synchronized  boolean addClient(String username, ServerClientSender clientRef){
        if(scanForSameUsername(username)){
            try {
                clientRef.sendMessage("Nome utente già in uso");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        }
        //Crea giocatore
        Player newPlayer = new Player(username,clientRef);
        clientList.add(newPlayer);
        System.out.println(username);
        waitForGame();
        return true;
    }
    //True if there is another player with same name
    //False if there is not  a player with same name
    private boolean scanForSameUsername(String username){
        Player playerSameUsername = null;
        if(clientList.size()>0){
            for(Player p : clientList)
                if(p.getName().equals(username))
                    playerSameUsername = p;
            if(playerSameUsername!=null)
                return true;
        }
        return false;
    }
    //Rimuovi client dalla clientList se non è ancora iniziato il game. Se game è
    //già iniziato occorre settare il client a disconnesso così che si possa riconnettere
    //in seguito
    public synchronized  boolean removeClient(String username){
        Player playerToRemove = null;
        //Cerca tra i client in attesa di giocare
        for(Player p : clientList)
            if(p.getName().equals(username))
                 playerToRemove = p;
        //Se trovi il player resetta il timer
        if(playerToRemove!=null) {
            clientList.remove(playerToRemove);
            waitForGame();
            return true;
        }
        //Cerca tra i client che stanno giocando
        for(Player p : clientWhoAreGaming)
            if(p.getName().equals(username)) {
                p.setIsNotConnected();
                return true;
            }
        return false;
    }

    private void waitForGame(){
        if(clientList.size()>=2){
            if(clientList.size() == 4){
                //TODO classe partita già implementata???
                //TODO bloccare ulteriri utenti se patrtita in corso ma permettere riconnessioni
            }
            else{
                if(timer==null) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("inizia una nuova partita");
                            serverRete.createNewGame(clientList);
                            clientWhoAreGaming.addAll(clientList);
                            clientList.clear();
                        }
                    }, readTimerFromFile("timer.txt"));
                }
            }
        }
        else{
            if(timer!=null){
             timer.cancel();
             timer = null;
            }
        }
    }

    private int readTimerFromFile(String path){
        int delay = 5*1000; //If timer.txt is not present create it with value 30s
        file = new File(path);
        try {
            if(file.createNewFile()) {
                fileWriter = new FileWriter(file);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(delay);
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

    public void clearClientWhoAreGaming(){
        clientWhoAreGaming.clear();
    }

}
