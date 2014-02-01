/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threadpooltest;

/**
 *
 * @author Sam
 */
public class WorkerThread implements Runnable {
    private int workerNumber;

    WorkerThread(int number) {
        workerNumber = number;
    }

    public void run() {
        for (int i=0;i<=100;i+=20) {
        // Perform some work ...
            System.out.println("Worker number: " + workerNumber
                + ", percent complete: " + i );
            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
            }
        }
    }
}