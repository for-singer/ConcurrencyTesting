package com.oshurpik;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrencyTesting_2_1 {


    public static void main(String[] args) {
        
        new ConcurrencyTesting_2_1().concurrentLinkedQueueExample();
        
    }
    
    class ConcurrentLinkedQueueProducer implements Runnable {

       ConcurrentLinkedQueue<String> queue;
       ConcurrentLinkedQueueProducer(ConcurrentLinkedQueue<String> queue){
          this.queue = queue;
       }
       public void run() {
          System.out.println("Producer Started");
          try {
             for (int i = 1; i < 10; i++) {
                queue.add("String" + i);
                System.out.println("Added: String" + i);
                Thread.currentThread().sleep(200);
             }
          } catch (Exception ex) {
             ex.printStackTrace();
          }
       }
    }
    
    class ConcurrentLinkedQueueConsumer implements Runnable {

       ConcurrentLinkedQueue<String> queue;
       ConcurrentLinkedQueueConsumer(ConcurrentLinkedQueue<String> queue){
          this.queue = queue;
       }
       public void run() {
          String str;
          System.out.println("Consumer Started");
          for (int x = 0; x < 10; x++) {
             while ((str = queue.poll()) != null) {
                System.out.println("Removed: " + str);
             }
             try {
                Thread.currentThread().sleep(500);
             } catch (Exception ex) {
                ex.printStackTrace();
             }
          }
       }
    }
    
    public void concurrentLinkedQueueExample() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
        Thread producer = new Thread(new ConcurrentLinkedQueueProducer(queue));
        Thread consumer = new Thread(new ConcurrentLinkedQueueConsumer(queue));
        producer.start();
        consumer.start();
        
    }
    
}
