package com.oshurpik;

import java.util.concurrent.Exchanger;

public class ConcurrencyTesting_3_2 {


    public static void main(String[] args) {
        new ConcurrencyTesting_3_2().exchangerExample();        
    }
    
    
    class ExchangerThread extends Thread {
 
        Exchanger<String> exchanger;
        String message;

        ExchangerThread(Exchanger<String> exchanger, String message) {
           this.exchanger = exchanger;
           this.message = message;
        }

        public void run() {
           try {
              System.out.println(this.getName() + " message: " + message);

              // exchange messages
              message = exchanger.exchange(message);

              System.out.println(this.getName() + " message: " + message);
           } catch (Exception e) {
           }
        }
    }
    
    public void exchangerExample() {
 
      Exchanger<String> exchanger = new Exchanger<>();
 
      Thread t1 = new ExchangerThread(exchanger, "I like coffee.");
      Thread t2 = new ExchangerThread(exchanger, "I like tea");
      t1.start();
      t2.start();
   }
    
}
