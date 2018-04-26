package it.polimi.ingsw.Server.Game.GameRules;

import java.util.ArrayList;

public class Restriction {


    //TODO add addiacency restriction ??

    private ArrayList<String> restrictions;

    public Restriction() {
        this.restrictions = new ArrayList<String>();
    }

    public boolean verifyRestrictions(String res_to_test ){
        Boolean result = true ;
        for( String s : restrictions ) {

            if(s.equals(res_to_test))
                result = false ;
            //result = restrictions.contains(res_to_test);//
        }
        return result;

    }

    public void addRestriction(String s ){
        restrictions.add(s);
    }

    public void removeRestricion(String s){
        restrictions.remove(s);
    }

    // return a copy of the private state
    public ArrayList<String> getRestrictions(){
        ArrayList<String> res = new ArrayList<String>(restrictions);
        return res;
    }
}
