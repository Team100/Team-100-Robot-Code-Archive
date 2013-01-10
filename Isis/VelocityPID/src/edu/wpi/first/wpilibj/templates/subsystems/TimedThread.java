/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Student
 */
public class TimedThread implements Runnable {

    Callable m_callable;
    int m_period_ms;

    public TimedThread(Callable callable, int period_ms)  {
        this.m_callable = callable;
        this.m_period_ms = period_ms;
    }

    public void run(){
         while (true) {
            m_callable.call();
            Timer.delay((double) m_period_ms / 1000.0);
        }
    }
    
    
    public void start() {
        new Thread(this).start(); 
    }
}//end TimedThread
