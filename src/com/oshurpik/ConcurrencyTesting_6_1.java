package com.oshurpik;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyTesting_6_1 {


    public static void main(String[] args) {
        
        new ConcurrencyTesting_6_1().atomicIntegerExample();
        
    }
    
    class ProcessingThread implements Runnable {
        private final AtomicInteger count = new AtomicInteger();


        @Override
        public void run() {
            for (int i = 1; i < 5; i++) {
                processSomething(i);
                count.incrementAndGet();
            }
        }


        public int getCount() {
            return this.count.get();
        }


        private void processSomething(int i) {
            // processing some job
            try {
                Thread.sleep(i * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    
    public void atomicIntegerExample() {
 
        try {
            ProcessingThread pt = new ProcessingThread();
            Thread t1 = new Thread(pt, "t1");
            t1.start();
            Thread t2 = new Thread(pt, "t2");
            t2.start();
            t1.join();
            t2.join();
            System.out.println("Processing count=" + pt.getCount());
        }
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
}
