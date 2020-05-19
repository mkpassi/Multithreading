package com.company;

import java.util.Arrays;
import java.util.List;

public class MultiExecutorAssignment {

    private final List<Runnable> taskList ;

    public MultiExecutorAssignment (List<Runnable> taskList) {
        this.taskList = taskList;
    }


    public void executeAll(){
        System.out.println("Starting with the execution");
        taskList.parallelStream().forEach(task -> new Thread(task).start());
        System.out.println("execution completed");
    }

    public static void main(String args[]){
        Runnable task1 = () -> System.out.println("Thread starting with task1");
        Runnable task2 = () -> System.out.println("Thread starting with task2");
        Runnable task3 = () -> System.out.println("Thread starting with task3");
        Runnable task4 = () -> System.out.println("Thread starting with task4");
        MultiExecutorAssignment assignment =
                new MultiExecutorAssignment(Arrays.asList(task1,task2,task3,
                        task4));
        assignment.executeAll();
    }



}
