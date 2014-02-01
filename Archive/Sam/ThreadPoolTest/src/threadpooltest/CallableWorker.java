/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threadpooltest;

import edu.emory.mathcs.backport.java.util.concurrent.Callable;

/**
 *
 * @author Sam
 */
public class CallableWorker implements Callable{
    private int number;
    
    CallableWorker(){
    }

    @Override
    public Object call() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        number++;
        for (int i=0;i<=100;i+=20) {
        // Perform some work ...
            System.out.println("Called Worker #" + number
                + ", percent complete: " + i );
            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
            }
        }
        
        return null;
    }
    
}
