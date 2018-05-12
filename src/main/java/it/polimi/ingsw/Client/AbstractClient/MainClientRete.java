package it.polimi.ingsw.Client.AbstractClient;

import java.util.Scanner;

public class MainClientRete {
    public static void main(String[] args){
        final int PORTSERVER = 2000;
        GeneriClient generiClient = null;
        Scanner keyboard = new Scanner(System.in);
        boolean repeatInsertion = true;
        do{
            System.out.println("Scegli la connessione che vuoi usare:\n0->Socket\n1->RMI");
            String choice = keyboard.nextLine();
            switch(choice){
                case "0":
                    System.out.println("Inserisci l'ip del server");
                    String ipServer = keyboard.next();

                    generiClient = new GeneriClient(ipServer,PORTSERVER);
                    repeatInsertion = false;
                    break;
                case "1":
                    generiClient = new GeneriClient();
                    repeatInsertion = false;
                    break;
                default:
                    System.out.println("Valore inserito non è valido");
                    break;
            }
        }while(repeatInsertion);
        //scegli tra CLI e GUI new CLI(genirClient.getClientServerSender) o GUI(genirClient.getClientServerSender)
        System.out.println("Inserisci il tuo username");
        String username = keyboard.next();
        generiClient.register(username);

        System.out.println("Digita per disconnettere");
        username = keyboard.next();
        generiClient.unregister();

        System.out.println("Ricontetti");
        username = keyboard.next();
        generiClient.register(username);

        username = keyboard.next(); //blocca client
    }
}
