package com.oshurpik;

import java.util.concurrent.Semaphore;

public class ConcurrencyTesting_3_1 {


    public static void main(String[] args) {
        
        new ConcurrencyTesting_3_1().semaphoreExample();
        
    } 
    
    
    Semaphore binary = new Semaphore(1);
    
    private void semaphoreMutualExclusion() {
        try {
            binary.acquire();

            //mutual exclusive region
            System.out.println(Thread.currentThread().getName() + " inside mutual exclusive region");
            Thread.sleep(1000);

        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } finally {
            binary.release();
            System.out.println(Thread.currentThread().getName() + " outside of mutual exclusive region");
        }
    }
    
    public void semaphoreExample() {
        new Thread(){
            @Override
            public void run(){
                semaphoreMutualExclusion(); 
            }
        }.start();
      
        new Thread(){
            @Override
            public void run(){
              semaphoreMutualExclusion(); 
            }
        }.start();
      
    }
    
}
