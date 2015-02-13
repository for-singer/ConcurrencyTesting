package com.oshurpik;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrencyTesting_1_1 {
    List<String> list = new CopyOnWriteArrayList<>();

    
    public static void main(String[] args) {
        
        new ConcurrencyTesting_1_1().copyOnWriteArrayListExample();
        
    }
    
    public void copyOnWriteArrayListExample() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                list.add("5");

                Iterator<String> it = list.iterator();

                System.out.println("copyOnWriteArrayListExample:");
                while(it.hasNext()){
                    System.out.println("List for thread 1 is:"+list);
                    String str = it.next();
                    System.out.println("Element from thread 1 is:" + str);
                    try {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                Iterator<String> it = list.iterator();

                while(it.hasNext()){
                    System.out.println("List for thread 2 is:"+list);
                    String str = it.next();
                    System.out.println("Element from thread 2 is:" + str);
                    if(str.equals("2"))list.remove("5");
                    if(str.equals("3"))list.add("3 found");

                    if(str.equals("4")) list.set(1, "4");
                    try {
                        Thread.sleep(1005);
                    }
                    catch(InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
}
