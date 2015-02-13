package com.oshurpik;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConcurrencyTesting_4_1 {


    public static void main(String[] args) {
        
        new ConcurrencyTesting_4_1().futureTaskExample();
        
    }
    
    class FutureTaskCallable implements Callable<String> {

	private long waitTime;

	public FutureTaskCallable(int timeInMillis){
		this.waitTime=timeInMillis;
	}
	@Override
	public String call() throws Exception {
		Thread.sleep(waitTime);
        //return the thread name executing this callable task
        return Thread.currentThread().getName();
	}
    }
   
    public void futureTaskExample() {
        FutureTaskCallable callable1 = new FutureTaskCallable(1000);
        FutureTaskCallable callable2 = new FutureTaskCallable(2000);

        FutureTask<String> futureTask1 = new FutureTask<>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                if(futureTask1.isDone() && futureTask2.isDone()){
                    System.out.println("Done");
                    //shut down executor service
                    executor.shutdown();
                    return;
                }

                if(!futureTask1.isDone()){
                    //wait indefinitely for future task to complete
                    System.out.println("FutureTask1 output="+futureTask1.get());
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if(s !=null){
                    System.out.println("FutureTask2 output="+s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }catch(TimeoutException e){
                    //do nothing
            }
        }
    }
    
}
