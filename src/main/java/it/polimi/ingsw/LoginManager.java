package it.polimi.ingsw;

import java.util.ArrayList;

public class LoginManager {
    private ArrayList<String> nicknames;

    private void allocateLazy() {
        if (nicknames == null) {
            nicknames = new ArrayList<String>();
        }
    }

    Boolean login(String nick) {
        allocateLazy();
        int count = nicknames.size(); if (count >= 4)
            return false;
        if (nicknames.contains(nick))
            return false;
        nicknames.add(nick);
        return true;
    }
}
