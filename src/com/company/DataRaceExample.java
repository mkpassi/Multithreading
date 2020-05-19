package com.company;

import java.util.stream.IntStream;

public class DataRaceExample {

    public static void main (String[] args) {
        DataRace dataRace = new DataRace();

        Thread t1 = new Thread( () ->{
            IntStream.rangeClosed(0, 1000).forEach(integer -> {
                dataRace.incrementVariables();
            });
        });


        Thread t2 = new Thread( () ->{
            IntStream.rangeClosed(0, 1000).forEach(integer -> {
                dataRace.checkIfDataRace();
            });
        });
        t1.start();
        t2.start();

    }


    private static class DataRace{
        private int x=0;
        private int y=0;

        private void incrementVariables(){
            x++;
            y++;
        }

        private void checkIfDataRace(){
          //  System.out.println("value of x and y outer:" + x +"=="+y);
            if(x != y){

                System.out.println("value of x and y:" + x +"=="+y);

            }

        }


    }
}
