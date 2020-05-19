package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReadWriteLock {

    private static final int HIGHEST_PRICE = 1000;

    public static void main (String[] args) {
        InventoryDatabase database = new InventoryDatabase();
        Random randomGenerator = new Random();
        IntStream.rangeClosed(0, 100000).forEach(number -> database.addItem(randomGenerator.nextInt(number)));
        Thread writer = writerThread(database, randomGenerator);
        List<Thread> readerThreadList = new ArrayList<>();
                IntStream.rangeClosed(1, 7).forEach(num -> {
                    readerThreadList.add(readerThread(database,
                            randomGenerator));
                });
         writer.setDaemon(true);
         writer.start();
         readerThreadList.stream().forEach(thread -> {
             thread.setDaemon(true);
             thread.start();
         });
    }

    private static Thread readerThread(InventoryDatabase inventoryDatabase,
                                       Random randomGenerator){
        Thread readerThread = new Thread (() -> {
            IntStream.rangeClosed(0,100000).forEach( number -> {
                int upperBound = randomGenerator.nextInt(HIGHEST_PRICE);
                int lowerBound = upperBound >0 ?
                        randomGenerator.nextInt(upperBound):0;
                inventoryDatabase.getNumberOfItemsInPriceRange(lowerBound,
                        upperBound);
            });
        });
        return readerThread;
    }

    private static Thread writerThread (InventoryDatabase database,
                                   Random randomGenerator) {
        Thread writerThread = new Thread(() ->{
           while(true){
               database.addItem(randomGenerator.nextInt(HIGHEST_PRICE));
               database.removeItem(randomGenerator.nextInt(HIGHEST_PRICE));
                try{
                    Thread.sleep(10);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
           }
        });
        return writerThread;
    }

    private static class InventoryDatabase{

        // TreeMap is implementation of Red-Black Tree a type of Binary
        // Search Tree.
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        private ReentrantLock lock = new ReentrantLock();

        private int getNumberOfItemsInPriceRange(int lowerBound,
                                                 int upperBound){
            try{
                lock.lock();
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound);
                Integer toKey = priceToCountMap.floorKey(upperBound);

                if(fromKey == null || toKey == null){
                    return 0;
                }
                final NavigableMap<Integer, Integer> navigableMap = priceToCountMap.subMap(fromKey, true, toKey, true);
                return navigableMap.values().stream().mapToInt(Integer::intValue).sum();
            }finally {
                lock.unlock();
            }
        }

        private void addItem (int price){
            try {
                lock.lock();
                if (priceToCountMap.containsKey(price)) {
                    final Integer count = priceToCountMap.get(price);
                    priceToCountMap.put(price, count.intValue() + 1);
                } else {
                    priceToCountMap.put(price, 1);
                }
            }finally {
                lock.unlock();
            }
        }

        private void removeItem(int price) {
            try {
                lock.lock();
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.remove(price);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice - 1);
                }
            }finally {
                lock.unlock();
            }
        }
    }
}
