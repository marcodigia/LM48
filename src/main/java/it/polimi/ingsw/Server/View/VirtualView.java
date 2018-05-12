package it.polimi.ingsw.Server.View;


import it.polimi.ingsw.ClientServerCommonInterface.ServerClientSender;


//Virtual view is an Observer of the Model , and through ServerClientSender notify the changes to the ClientView
public class VirtualView  {

    ServerClientSender serverClientSender;


    public VirtualView(ServerClientSender serverClientSender) {
        this.serverClientSender = serverClientSender;
    }


    public String choseWindowPattern(){
        return null;
    }

   public void update(){

   }


}
