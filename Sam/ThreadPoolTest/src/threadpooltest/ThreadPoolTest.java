/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threadpooltest;

import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;

/**
 *
 * @author Sam
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        int numWorkers = 2;         // This is the number of things starting in the queue
        int threadPoolSize = 2;   // Used only if the number of threads is limited
    
         
        ExecutorService tpes =
            
            //Executors.newCachedThreadPool();                //Thread pool with unlimited threads it will use existing
                                                            //threads if they are avaliable.
        
            //Executors.newCachedThreadPool(ThreadFactory)  //Thread pool with unlimited size, it uses a 
                                                            //ThreadFactory to create the threads.
        
            Executors.newFixedThreadPool(threadPoolSize); //Thread pool with a limited number of threads.
        
            //Executors.newFixedThreadPool(threadPoolSize, ThreadFactory) //Limited numbers of threads created
                                                            //by a thread factory
        

        
    
        WorkerThread[] workers = new WorkerThread[numWorkers];
        for (int i = 0; i < numWorkers; i++)  {
            workers[i] = new WorkerThread(i);
            tpes.execute(workers[i]);//This executes workers(1) in an availiable thread
        }
        
        
        CallableWorker callworker = new CallableWorker();
        
        
        for (int i = 0; i < 5; i++)  {
            tpes.submit(callworker);
        }
        
        //tpes.shutdown();           //Ends the thread pool
    }
}