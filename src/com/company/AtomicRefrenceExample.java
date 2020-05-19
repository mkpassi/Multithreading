package com.company;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicRefrenceExample {

    public static void main (String[] args) {
        String oldValue ="oldValue";
        String newValue = "newValue";

        AtomicReference<String> atomicReference = new AtomicReference(oldValue);
        atomicReference.set("this is changed value");
        atomicReference.compareAndSet(oldValue, "this is new Value");
        System.out.println(atomicReference.get());
    }
}
