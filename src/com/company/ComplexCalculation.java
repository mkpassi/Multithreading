package com.company;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class ComplexCalculation {

    public static void main (String[] args) {
        ComplexCalculation complexCalculation = new ComplexCalculation();
        final BigInteger resultInteger = complexCalculation.calculateResult(BigInteger.valueOf(2),
                BigInteger.valueOf(3), BigInteger.valueOf(3),
                BigInteger.valueOf(3));
        System.out.println(resultInteger.longValueExact());
    }


    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result = BigInteger.ZERO;
        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1,
                power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2,
                power2);

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();



        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        final BigInteger result1 = thread1.getResult();

        final BigInteger result2 = thread2.getResult();


        result= result1.add(result2);
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {

            for(BigInteger x= BigInteger.ZERO; x.compareTo(power) != 0 ; x=
                    x.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
