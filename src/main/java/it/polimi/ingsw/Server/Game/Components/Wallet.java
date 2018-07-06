package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Packetable;

import javax.lang.model.element.PackageElement;

public class Wallet implements Packetable {

    private int tokenNumber = 100 ;

    public void setUpWallet(int i){
        tokenNumber = i ;
    }

    public Boolean enoughToken(int tokensRequired){
        System.out.println("token number: " + tokenNumber
        + "tokenRequired: " + tokensRequired);
        return tokenNumber>=tokensRequired;
    }

    public void useToken(int tokenRequired){
        if (enoughToken(tokenRequired))
            tokenNumber -= tokenRequired;
    }

    public int getTokenAmmount(){
        return tokenNumber;
    }

    @Override
    public String toPacket() {
        return ""+tokenNumber;
    }
}
