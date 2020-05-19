package com.company;

public class MinMaxMetrics {

    // Add all necessary member variables
    private volatile long MIN_METRICS ;
    private volatile long MAX_METRICS ;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        // Add code here
        this.MIN_METRICS =0;
        this.MAX_METRICS =0;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public synchronized void addSample(long newSample) {
        if(MIN_METRICS == 0 && MAX_METRICS == 0){
            MIN_METRICS = newSample;
        }
        if(newSample < MIN_METRICS){
            MIN_METRICS = newSample;
        }else{
            if(newSample > MAX_METRICS){
                MAX_METRICS = newSample;
            }
        }
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return MIN_METRICS;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        // Add code here
        return MAX_METRICS;
    }
}
