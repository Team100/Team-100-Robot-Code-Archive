/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.FrisBeast.subsystems.PIDBundle;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Isis
 */
public class TimedThread implements Runnable {

    private Callable m_callable;
    private String m_periodKey;
    private final double kDefaultPeriod=.05;

    public TimedThread(Callable callable, String periodKey) {
        this.m_callable = callable;
        this.m_periodKey = periodKey;
        Preferences p =Preferences.getInstance();
        if (!p.containsKey(m_periodKey)){
            p.putDouble(m_periodKey, kDefaultPeriod);
        }
    }

    public void run() {
        while (true) {
            m_callable.call();
            double period= Preferences.getInstance().getDouble(m_periodKey, kDefaultPeriod);
            Timer.delay(period);
        }
    }

    public void start() {
        new Thread(this).start();
    }
    
}//end TimedThread
