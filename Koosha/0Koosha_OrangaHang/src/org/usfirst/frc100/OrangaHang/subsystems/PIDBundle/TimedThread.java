/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems.PIDBundle;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Isis
 */
public class TimedThread implements Runnable {

    Callable m_callable;
    int m_period_ms = 10;

    public TimedThread(Callable callable) {
        this.m_callable = callable;
    }

    public void run() {
        while (true) {
            m_callable.call();
            int period;
            synchronized (this) {
                period = m_period_ms;
            }
            Timer.delay((double) period / 1000.0);
        }
    }

    public void start() {
        new Thread(this).start();
    }
    
    public synchronized void setPeriod(int period){
        m_period_ms = period;
    }
}//end TimedThread
