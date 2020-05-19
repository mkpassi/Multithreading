package com.company;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private class PricesUpdater extends Thread{
        private final PricesContainer pricesContainer ;
        private Random randomGenerator = new Random();

        private PricesUpdater (PricesContainer pricesContainer) {
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run () {
            while(true){
                pricesContainer.getLockObject().lock();
                try{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pricesContainer.setBitCoinCashPrice(randomGenerator.nextInt(1000));
                    pricesContainer.setBitCoinPrice(randomGenerator.nextInt(2000));
                    pricesContainer.setEtherCoinPrice(randomGenerator.nextInt(1234));
                    pricesContainer.setLiteCoinPrice(randomGenerator.nextInt(11221));
                    pricesContainer.setRippleCoinPrice(randomGenerator.nextInt(2242));
                }finally {
                    pricesContainer.getLockObject().unlock();
                }

                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private class PricesContainer{

        private Lock lockObject = new ReentrantLock();

        private double bitCoinPrice;
        private double etherCoinPrice;
        private double liteCoinPrice;
        private double bitCoinCashPrice;
        private double rippleCoinPrice;

        public Lock getLockObject () {
            return lockObject;
        }

        public void setLockObject (Lock lockObject) {
            this.lockObject = lockObject;
        }

        public double getBitCoinPrice () {
            return bitCoinPrice;
        }

        public void setBitCoinPrice (double bitCoinPrice) {
            this.bitCoinPrice = bitCoinPrice;
        }

        public double getEtherCoinPrice () {
            return etherCoinPrice;
        }

        public void setEtherCoinPrice (double etherCoinPrice) {
            this.etherCoinPrice = etherCoinPrice;
        }

        public double getLiteCoinPrice () {
            return liteCoinPrice;
        }

        public void setLiteCoinPrice (double liteCoinPrice) {
            this.liteCoinPrice = liteCoinPrice;
        }

        public double getBitCoinCashPrice () {
            return bitCoinCashPrice;
        }

        public void setBitCoinCashPrice (double bitCoinCashPrice) {
            this.bitCoinCashPrice = bitCoinCashPrice;
        }

        public double getRippleCoinPrice () {
            return rippleCoinPrice;
        }

        public void setRippleCoinPrice (double rippleCoinPrice) {
            this.rippleCoinPrice = rippleCoinPrice;
        }
    }

}
