package it.polimi.ingsw.Server.Game.Components;

import it.polimi.ingsw.Packetable;

import javax.lang.model.element.PackageElement;

public class Wallet implements Packetable {

    private int tokenNumber = 100 ;

    /**
     * @param i the ammout of token for the wallet
     */
    public void setUpWallet(int i){
        tokenNumber = i ;
    }

    /**
     * @param tokensRequired number of token required to be tested
     * @return true if wallet contains enough tokens
     */
    public Boolean enoughToken(int tokensRequired){
        return tokenNumber>=tokensRequired;
    }

    /**
     * decrease the value of token of the wallet by the given ammount
     * @param tokenRequired token  number to be tested
     */
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
