package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;


public class ProducerConsumerUsingSemaphore {

    public static void main (String[] args) {
        ProducerConsumerProblem producerConsumer =
                new ProducerConsumerProblem();
        Thread t1 = new Thread(() -> {
            try {
                for (int i = 1; i < 2000; i++) {
                 //   System.out.println("Producing task");
                    producerConsumer.produceTask(new Task(i, "Description " +
                            "for task Number : " + i));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {

                for (int i = 1; i < 20; i++) {
                 //   System.out.println("Consuming task");
                    producerConsumer.consumerTask();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }


    public static class ProducerConsumerProblem {

        Queue<Task> taskQueue = new ArrayDeque();
        Semaphore full = new Semaphore(0);
        Semaphore empty = new Semaphore(8);
        ReentrantLock lock = new ReentrantLock();


        void produceTask (Task task) throws InterruptedException {
            empty.acquire();
            lock.lock();
            // System.out.println("taskQueue Before :" + taskQueue);
            taskQueue.offer(task);
            System.out.println("taskQueue After :" + taskQueue);
            lock.unlock();
            full.release();
        }

        void consumerTask () throws InterruptedException {

            full.acquire();
            lock.lock();
          //  System.out.println("taskQueue Before :" + taskQueue);
            final Task task = taskQueue.poll();
            System.out.println("Got task:");
           //  System.out.println("taskQueue After :" + taskQueue);
            lock.unlock();
            task.executeTask();
            empty.release();
        }
    }

    public static class Task {

        int taskId;
        String taskDescription;

        Task (int taskId, String taskDescription) {
            this.taskId = taskId;
            this.taskDescription = taskDescription;
        }

        public void executeTask () {
            System.out.println("Executing task with Id : " + taskId + " " +
                    "having descripiton : " + taskDescription + ".");
        }

        @Override
        public String toString () {
            final StringBuilder sb = new StringBuilder("Task{");
            sb.append("taskId=").append(taskId);
            sb.append(", taskDescription='").append(taskDescription).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
