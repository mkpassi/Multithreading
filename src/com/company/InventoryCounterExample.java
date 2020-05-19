package com.company;

public class InventoryCounterExample {


    public static void main (String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementThread incrementThread = new IncrementThread(inventoryCounter);
        DecrementThread decrementThread = new DecrementThread(inventoryCounter);

        incrementThread.start();
        decrementThread.start();
        incrementThread.join();
        decrementThread.join();

        System.out.println("Inventory Total Count : " + inventoryCounter.getItems());

    }

    private static class IncrementThread extends Thread{

        final InventoryCounter inventoryCounter ;

        public IncrementThread (InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }


        @Override
        public void run () {
            for(int i=1;i<=10000;i++){
                inventoryCounter.increment();
            }
        }
    }

    private static class DecrementThread extends Thread{

        final InventoryCounter inventoryCounter ;

        public DecrementThread (InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }


        @Override
        public void run () {
            for(int i=1;i<=10000;i++){
                inventoryCounter.decrement();
            }
        }
    }



    private static class InventoryCounter{

        private int items =0;

        public void increment(){
            synchronized (this){
                items++;
            }
        }

        public void decrement(){
           synchronized (this) {
               items--;
           }
        }

        public int getItems(){
            synchronized (this){

                return items;
            }
        }
    }
}
