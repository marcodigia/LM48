package it.polimi.ingsw;

import it.polimi.ingsw.Server.Game.Cards.CardsUtility.DinamicCardCreator;
import it.polimi.ingsw.Server.Game.Cards.WindowPatternCard;
import it.polimi.ingsw.Server.Game.Utility.CONSTANT;
import it.polimi.ingsw.Server.Game.Utility.Unpacker;

public class t {


    public static void main(String args[]) {


       /* CountRow columCount = new CountRow(10);

        CountSets countSets = new CountSets(1);

        DiagonalEffect diagonalEffect = new DiagonalEffect();
        Unpacker.setUpUnpacker();
        WindowPatternCard windowPatternCard = (WindowPatternCard) Unpacker.WPDeck.get("1");

        windowPatternCard.placeDice(new Dice("P"+13),13,true,true,true);
        for (int i = 5 ; i < 20 ; i ++){

            windowPatternCard.placeDice(new Dice("G"+i),i,true,true,true);

        }






       /* ArrayList<Dice>d = diagonalEffect.getDiagDx(0,windowPatternCard);
        for (Dice dd : d){
            System.out.println("D: " +dd);
        }
        System.out.println("--------") ;

        d = diagonalEffect.getDiagDx(1,windowPatternCard);
        for (Dice dd : d){
            System.out.println("D: " +dd);
        }

        System.out.println("--------") ;

        d = diagonalEffect.getDiagDx(2,windowPatternCard);
        for (Dice dd : d){
            System.out.println("D: " +dd);
        }

        System.out.println("--------") ;

        d = diagonalEffect.getDiagDx(3,windowPatternCard);
        for (Dice dd : d){
            System.out.println("D: " +dd);
        }

        System.out.println("--------") ;

        d = diagonalEffect.getDiagDx(5,windowPatternCard);
        for (Dice dd : d){
            System.out.println("D: " +dd);
        }*/
       /* for (int i = 5 ; i < 10 ; i ++){

            windowPatternCard.placeDice(new Dice("Y2"),i,true,true,true);

        }
        windowPatternCard.placeDice(new Dice("R2"),14,true,true,true);
        windowPatternCard.placeDice(new Dice("Y3"),13,true,true,true);
        windowPatternCard.placeDice(new Dice("B4"),12,true,true,true);
        windowPatternCard.placeDice(new Dice("G1"),11,true,true,true);
        windowPatternCard.placeDice(new Dice("B1"),10,true,true,true);

        windowPatternCard.placeDice(new Dice("R2"),16,true,true,true);
        windowPatternCard.placeDice(new Dice("Y3"),17,true,true,true);
        windowPatternCard.placeDice(new Dice("B4"),18,true,true,true);
        windowPatternCard.placeDice(new Dice("G5"),19,true,true,true);
        windowPatternCard.placeDice(new Dice("B6"),15,true,true,true);
        int score  = columCount.getScoreColor(windowPatternCard);


        ArrayList<Restriction> restrictions = new ArrayList<>();
        restrictions.add(Restriction.ONE);
        restrictions.add(Restriction.TWO);
        restrictions.add(Restriction.THREE);
        restrictions.add(Restriction.FOUR);
        restrictions.add(Restriction.FIVE);
        restrictions.add(Restriction.SIX);
        System.out.println("SCORE " + diagonalEffect.getPoints(windowPatternCard));

    }


       ArrayList<String> a = new ArrayList<>();
       for (int i  = 0 ; i < 3 ; i++)
           a.add(String.valueOf(i));


       a.remove(0);
       a.add(0,"A");

       for ( String s: a)
           System.out.println(s);

        try {

            Enumeration en = NetworkInterface.getNetworkInterfaces();
            while(en.hasMoreElements()){
                NetworkInterface ni=(NetworkInterface) en.nextElement();
                Enumeration ee = ni.getInetAddresses();
                while(ee.hasMoreElements()) {
                    InetAddress ia= (InetAddress) ee.nextElement();
                    System.out.println(ia.getHostAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    } */



       Unpacker.setUpUnpacker();
      WindowPatternCard wp =  Unpacker.WP_fromPacket("Dynamic_1.Industria.5.1.R.3.0.6.5.4.R.2.0.0.0.5.R.1.0.0.0.3.R","null.null.null.null.null.null.null.null.null.null.null.null.null.null.null.null.null.null.null.null");

      System.out.println((new DinamicCardCreator("Matt",6)).toPacket());
      System.out.println((Unpacker.WP_fromPacket(new DinamicCardCreator("Matt",6).toPacket(),CONSTANT.emptyWp)).toPacket());

    }
}
