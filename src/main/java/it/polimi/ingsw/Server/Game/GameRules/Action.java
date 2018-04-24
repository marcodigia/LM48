package it.polimi.ingsw.Server.Game.GameRules;

public abstract class Action {

    @Override
    public boolean equals(Object obj) {
        if ( obj instanceof Action ){

            return  ((Action) obj).getActionName().equals( getActionName());

        }
        return super.equals(obj);
    }

    public abstract String getActionName();

    public abstract void doAction();
}
