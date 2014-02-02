/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.emory.mathcs.backport.java.util.concurrent.Callable;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sam
 */
public class HoldSpeed implements Callable{
    
    private Jaguar jag;
    
    HoldSpeed(Jaguar jaguar){
        jag = jaguar;
    }

    public Object call() throws Exception {
        jag.set(1);
        SmartDashboard.putBoolean("HoldSpeed thread", true);
        return null;
    }
    
}
