package it.polimi.ingsw.Client.AbstractClient;

import it.polimi.ingsw.Client.CLI.CLI;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClientRete {
    public static void main(String[] args){

        final int PORTSERVER = 2000;

        Unpacker.setUpUnpacker();


        GeneriClient generiClient = null;
        Scanner keyboard = new Scanner(System.in);
        boolean repeatInsertion = true;
        do{
            System.out.println("Type connection you want to use:\n0->Socket\n1->RMI");
            String choice = keyboard.nextLine();
            switch(choice){
                case "0":
                    System.out.println("Type server ip");
                    String ipServer = keyboard.next();
                    generiClient = new GeneriClient(ipServer,PORTSERVER);
                    repeatInsertion = false;
                    break;
                case "1":
                    generiClient = new GeneriClient();
                    repeatInsertion = false;
                    break;
                default:
                    System.out.println("Value typed is not valid");
                    break;
            }
        }while(repeatInsertion);

        //scegli tra
        PrintStream ps = System.out;
        CLI cli = new CLI();
        //GUI.clientServerSender = generiClient.getClientServerSender();
        //GUI gui = new GUI(generiClient.getClientServerSender());
        Thread t = new Thread(cli);
        t.start();

        System.out.println("Type your username");
        String command = keyboard.next();
        generiClient.register(command);
        try {
            cli.setClientServerSender(generiClient.getClientServerSender());
            generiClient.getClientServerReciver().setUI(cli);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
