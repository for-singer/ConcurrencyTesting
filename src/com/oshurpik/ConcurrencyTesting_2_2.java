package com.oshurpik;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTesting_2_2 {

    public static void main(String[] args) {        
        new ConcurrencyTesting_2_2().arrayBlockingQueueExample();        
    }
    
    private static BlockingQueue<String> bq = new ArrayBlockingQueue<>(2);
    
    class ArrayBlockingQueuePutThread implements Runnable {
 
        @Override
        public void run() {
            try {
                int i = 0;
                for (int j = 0 ; j < 10; j++) {
                    bq.put("A" + i);
                    System.out.println("Producer has put: A" + i);
                    i++;
                    Thread.sleep(700);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    class ArrayBlockingQueueTakeThread implements Runnable {
 
        @Override
        public void run() {
            try {
                while(true) {
                    Thread.sleep(800);
                    final String data = bq.poll(3, TimeUnit.SECONDS);
                    if (data == null) break;
                    System.out.println("Consumer has consumed.." + data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void arrayBlockingQueueExample() {
        final Thread pt = new Thread(new ArrayBlockingQueuePutThread());
        pt.start();
 
        final Thread tt = new Thread(new ArrayBlockingQueueTakeThread());
        tt.start();
    }
    
}
