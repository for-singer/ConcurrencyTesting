package com.oshurpik;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrencyTesting_1_2 {

    Map<String,String> myMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        
        new ConcurrencyTesting_1_2().concurrentHashMapExample();
                
    }
    
    public void concurrentHashMapExample() {
        new Thread(new Thread1()).start();
        new Thread(new Thread2()).start();
    }
    
    class Thread1 implements Runnable {
        
        @Override
        public void run() {
            myMap.put("1", "1");
            myMap.put("2", "1");
            myMap.put("3", "1");
            myMap.put("4", "1");
            myMap.put("5", "1");
            myMap.put("6", "1");
            System.out.println("concurrentHashMapExample before iterator from thread 1: "+myMap);
            Iterator<String> it = myMap.keySet().iterator();

            while(it.hasNext()){
                String key = it.next();
                if(key.equals("3")) myMap.put(key+"new", "new3");
            }
            System.out.println("concurrentHashMapExample after iterator from thread 1: "+myMap);
        }
    }
    
    class Thread2 implements Runnable {
        
        @Override
        public void run() {            
            myMap.put("7", "1");
            myMap.put("8", "1");
            System.out.println("concurrentHashMapExample before iterator from thread 2: "+myMap);
            Iterator<String> it = myMap.keySet().iterator();

            while(it.hasNext()){
                String key = it.next();
                if(key.equals("3")) myMap.put(key+"new", "new3");
            }
            System.out.println("concurrentHashMapExample after iterator from thread 2: "+myMap);
        }
    }
    
}
