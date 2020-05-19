package com.company;

import java.util.Random;

public class DeadlockExample {

    public static void main (String[] args) {
        Intersection intersection = new Intersection();

        Thread t1 = new Thread(() -> {
            Random random = new Random();
            while(true){
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersection.takeRoadA();
            }
        });
        Thread t2 = new Thread(() -> {
            Random random = new Random();
            while(true){
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersection.takeRoadB();
            }
        });

        t1.start();
        t2.start();

    }

    private static class Intersection{

        Object roadA = new Object();
        Object roadB = new Object();

        private void takeRoadA(){
            synchronized (roadA){
                System.out.println("Road A lock taken by thread : " + Thread.currentThread().getName());
                synchronized (roadB){
                    System.out.println("Train is Passing through Road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void takeRoadB(){
            synchronized (roadB){

                System.out.println("Road B lock taken by thread : " + Thread.currentThread().getName());

                synchronized (roadA){
                    System.out.println("Train passing through intersectiopn " +
                            "road A");

                    try{
                        Thread.sleep(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
