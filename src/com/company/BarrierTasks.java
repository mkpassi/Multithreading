package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarrierTasks {

    public static void main (String[] args) {
        int numberOfThreads = 200;
        List<Thread> threads = new ArrayList<>();
        Barrier barrier = new Barrier(numberOfThreads);

        for(int i=0;i<numberOfThreads;i++){
            threads.add(new Thread(new CoordinatedWorkRunner(barrier)));
        }

        for(Thread thred : threads){
            thred.start();
        }
    }




   public static class CoordinatedWorkRunner implements Runnable{

       private Barrier barrier;

       public CoordinatedWorkRunner(Barrier barrier){
           this.barrier = barrier;
       }

       @Override
       public void run () {
            try{
                task();
            }catch (InterruptedException ex){
                ex.getMessage();
            }
       }

       private void task() throws InterruptedException{
           System.out.println(Thread.currentThread().getName() + " Part 1 of " +
                   "the task");
           barrier.barrier();
           System.out.println(Thread.currentThread().getName() + "Part 2 of " +
                   "the task");

       }
   }

    public static class Barrier{

        private final int numberOfWorkers;
        private final Semaphore semaphore = new Semaphore(0);
        private int counter = 0;
        private Lock lock = new ReentrantLock();

        public Barrier(int numberOfWorkers){
            this.numberOfWorkers = numberOfWorkers;
        }

        public void barrier(){
            lock.lock();
            boolean isLastWorker = false;
            try{
                counter++;
                if(counter == numberOfWorkers){
                    isLastWorker = true;
                }
            }finally {
                lock.unlock();
            }

            if(isLastWorker){
                semaphore.release(numberOfWorkers-1);
            }else{
                try{
                    semaphore.acquire();
                }catch (InterruptedException exception){
                    exception.getMessage();
                }
            }
        }
    }
}
