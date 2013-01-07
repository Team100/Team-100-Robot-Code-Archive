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
        int numWorkers = 5;
        int threadPoolSize = 2;
    
        ExecutorService tpes =
            Executors.newFixedThreadPool(threadPoolSize);
    
        WorkerThread[] workers = new WorkerThread[numWorkers];
        for (int i = 0; i < numWorkers; i++)  {
            workers[i] = new WorkerThread(i);
            tpes.execute(workers[i]);
        }
        tpes.shutdown();
    }
}