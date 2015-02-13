package com.oshurpik;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class ConcurrencyTesting_6_2 {


    public static void main(String[] args) {
        
        new ConcurrencyTesting_6_2().atomicMarkableReferenceeExample();
        
    }
    
    AtomicMarkableReference<String> amr= new AtomicMarkableReference<>("ref",false);
	
    class AddThread implements Runnable{

            @Override
            public void run() {
                System.out.println("\n" + Thread.currentThread().getName()

                            + " ---> " + amr.getReference()

                            + "\nMark: " + amr.isMarked());

                //update the value of mark as true if "ref" matches to current reference
                amr.attemptMark("ref",true);

                //if current reference is "ref" and current mark is "true" then it change as newref and false respectivelly
                amr.compareAndSet("ref","newref",true,false);

                // sets the new refernce and new mark without any check  
                amr.set("reference",true);
                
                System.out.println("\n" + Thread.currentThread().getName()

                            + " ---> " + amr.getReference()

                            + "\nMark: " + amr.isMarked());

            }

    }
	
   public void atomicMarkableReferenceeExample() {
	   new Thread(new AddThread()).start();
           try {
                Thread.sleep(100);
           }
           catch(InterruptedException ex) {
               ex.printStackTrace();
           }
	   new Thread(new AddThread()).start();
   }
    
}
