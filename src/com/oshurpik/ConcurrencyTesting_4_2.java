package com.oshurpik;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTesting_4_2 {


    public static void main(String[] args) {
        new ConcurrencyTesting_4_2().scheduledThreadPoolExample();
        
    }
   
    class WorkerThread implements Runnable{
 
        private String command;

        public WorkerThread(String s){
            this.command=s;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" Start. Time = "+new Date());
            processCommand();
            System.out.println(Thread.currentThread().getName()+" End. Time = "+new Date());
        }

        private void processCommand() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString(){
            return this.command;
        }
    }
    
    public void scheduledThreadPoolExample() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
         
         
        //schedule to run after sometime
        System.out.println("Current Time = "+new Date());
        for(int i=0; i<3; i++){
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex) {
                ex.printStackTrace();
            }
            WorkerThread worker = new WorkerThread("do heavy processing");
            scheduledThreadPool.schedule(worker, 5, TimeUnit.SECONDS);
        }
        
        try {
            scheduledThreadPool.shutdown();
            scheduledThreadPool.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Finished all threads");
    }
    
}
