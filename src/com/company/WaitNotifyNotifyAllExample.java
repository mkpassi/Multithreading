package com.company;

public class WaitNotifyNotifyAllExample {

    public class MySharedClass{
        private boolean isComplete = false;


        public void waitUntilComplete(){
            synchronized (this){
                while(isComplete == false){
                    try {
                        this.wait();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }

        public void complete(){
            synchronized (this){
                isComplete = true;
                this.notify();
            }
        }
    }

}
