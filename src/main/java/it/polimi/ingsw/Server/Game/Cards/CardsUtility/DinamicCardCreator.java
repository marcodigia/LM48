package it.polimi.ingsw.Server.Game.Cards.CardsUtility;

import it.polimi.ingsw.Packetable;
import it.polimi.ingsw.Server.Game.Cards.CardManager;

import java.util.ArrayList;

public class DinamicCardCreator implements Packetable {


    ArrayList<String> cardPattern = new ArrayList<>();


    String  username;
    public DinamicCardCreator(String username,int difficult) {

        this.username = username;
        cardPattern.add(String.valueOf(difficult));
        for (int i = 0 ; i < 20 ; i++){
            cardPattern.add("0");
        }
    }

    public void addRestriction(String res , int index){
        cardPattern.remove(index+1);
        cardPattern.add(index+1,res);
    }


    @Override
    public String toPacket() {
        StringBuilder packet = new StringBuilder();

        for (String s : cardPattern)
            packet.append(s).append(".");
        packet.deleteCharAt(packet.length()-1);
        return CardManager.createDynamicCard(username,packet.toString());
    }
}
