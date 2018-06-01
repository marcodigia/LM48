package it.polimi.ingsw.Client.AbstractClient;

import it.polimi.ingsw.Client.CLI.CLI;
import it.polimi.ingsw.Client.GUI.GUIimpl;
import it.polimi.ingsw.Server.Game.Cards.CardManager;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClientRete {
    public static void main(String[] args){

        Boolean flag = false;
        for (String a : args){
            if (a.equals("-g"))
                flag=true;
        }
        String username;

        Unpacker.setUpUnpacker();
        try {
            CardManager.setWPCards(CONSTANT.windowPatternfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        GeneriClient generiClient = new GeneriClient();
        Scanner keyboard = new Scanner(System.in);
        boolean repeatInsertion = true;
        do{
            String choice;
            if (!flag){
                System.out.println("Type:\n0->GUI\n1->CLI");
                choice = keyboard.nextLine();
            } else
                choice = "0";



            switch(choice){
                case "0":
                    GUIimpl gui = new GUIimpl();
                    if (args.length>1){
                        GUIimpl.setIp(args[0]);
                        GUIimpl.setPort(args[1]);
                    }


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
