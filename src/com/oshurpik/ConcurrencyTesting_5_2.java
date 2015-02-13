package com.oshurpik;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrencyTesting_5_2 {


    public static void main(String[] args) {
        
        new ConcurrencyTesting_5_2().readWriteLockExample();
        
    }
    
    
    public class Data
    {
        private List<String> names;

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public Data()
        {
            names = new ArrayList<>();
        }

        public List<String> getNames()
        {
            return names;
        }

        public void setNames(List<String> names)
        {
            this.names = names;
        }

        public void add(String str)
        {
            lock.writeLock().lock();
            try {
                System.out.println("Writer: Number of threads waiting : "
                    + lock.getQueueLength());

                // This will alwas be 1.
                System.out.println("Writer: Number of write locks waiting : "
                    + lock.getWriteHoldCount());
                names.add(str);
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        public void readData()
        {
            lock.readLock().lock();
            try{
                System.out.println("Reader: Number of threads waiting : "
                    + lock.getQueueLength());
                System.out.println("Reader: Number of read locks : "
                    + lock.getReadLockCount());
                Iterator<String> iter = names.iterator();
                while (iter.hasNext())
                {
                    iter.next();
                    // System.out.println(iter.next());
                }
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }finally{
                lock.readLock().unlock();
            }
        }
    }
    
    public class ListReader implements Runnable
    {
        Data myData;

        public ListReader(Data myData)
        {
            super();
            this.myData = myData;
        }

        public void run()
        {
            for (int i = 0; i < 10; i++)
            {
                myData.readData();
            }
        }
    }
    
    public class ListWriter implements Runnable
    {
        Data myData;

        public ListWriter(Data myData)
        {
            super();
            this.myData = myData;
        }

        public void run()
        {
            for (int i = 0; i < 10; i++)
            {
                myData.add(Thread.currentThread().getName() + " : " + i);
            }
        }
    }
    
    public void readWriteLockExample() 
    {
        final int THREADS = 4;
        
        ListReader[] readers = new ListReader[THREADS];
        ListWriter[] writers = new ListWriter[THREADS];
        Data data = new Data();
        Thread[] threads = new Thread[THREADS * 2];
        for (int i = 0; i < THREADS; i++)
        {
            readers[i] = new ListReader(data);
            writers[i] = new ListWriter(data);
            threads[i] = new Thread(readers[i], "" + i);
            threads[i + THREADS] = new Thread(writers[i], "" + i);
        }
 
        for (int i = 0; i < THREADS * 2; i++)
        {
            threads[i].start();
        }
 
        for (int i = 0; i < THREADS * 2; i++)
        {
            try
            {
                threads[i].join();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
 
    }
    
}
