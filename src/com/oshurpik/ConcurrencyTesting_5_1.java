package com.oshurpik;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyTesting_5_1 {


    public static void main(String[] args) {        
        new ConcurrencyTesting_5_1().concurrencyLockExample();        
    }
    
    class ReentrantLockResource {
 
        public void doSomething() {            
            System.out.println("doSomething:" + Thread.currentThread().getName());
            Random rn = new Random();
            try {
                Thread.sleep((1 + rn.nextInt(9)) * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        public void doLogging() {
            System.out.println("doLogging:" + Thread.currentThread().getName());
        }
    }
    
    class ReentrantLockExample implements Runnable {
 
        private ReentrantLockResource resource;
        private Lock lock;

        public ReentrantLockExample(ReentrantLockResource r) {
            this.resource = r;
        }

        @Override
        public void run() {
            
            this.lock = new ReentrantLock();            
            System.out.println("Try locking:" + Thread.currentThread().getName());
            try {
                if(lock.tryLock(10, TimeUnit.SECONDS)) {
                    System.out.println("Locked:" + Thread.currentThread().getName());
                    resource.doSomething();            
                    resource.doLogging();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally{
                lock.unlock();
                System.out.println("Unlocked:" + Thread.currentThread().getName());
            }
        }
 
    }
    
    public void concurrencyLockExample() {
        ReentrantLockResource resource = new ReentrantLockResource();
        ReentrantLockExample r = new ReentrantLockExample(resource);

        Thread t = new Thread(r, "My Thread");

        Thread t1 = new Thread(r, "My Thread2");
        t.start();
        t1.start();
    }
    
}
