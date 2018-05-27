package it.polimi.ingsw.Client.AbstractClient;

import it.polimi.ingsw.Client.CLI.CLI;
import it.polimi.ingsw.Client.GUI.GUIimpl;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;

import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClientRete {
    public static void main(String[] args){

        String username;

        Unpacker.setUpUnpacker();

        GeneriClient generiClient = new GeneriClient();
        Scanner keyboard = new Scanner(System.in);
        boolean repeatInsertion = true;
        do{
            System.out.println("Type:\n0->GUI\n1->CLI");
            String choice = keyboard.nextLine();
            switch(choice){
                case "0":
                    GUIimpl gui = new GUIimpl();
                    gui.setGeneriClient(generiClient);
                    Thread tu = new Thread(gui);
                    tu.start();
                    repeatInsertion = false;
                    break;
                case "1":
                    CLI cli = new CLI();
                    Thread t = new Thread(cli);
                    t.start();
                    repeatInsertion = false;
                    break;
                default:
                    System.out.println("Value typed is not valid");
                    break;
            }
        }while(repeatInsertion);

    }
}
