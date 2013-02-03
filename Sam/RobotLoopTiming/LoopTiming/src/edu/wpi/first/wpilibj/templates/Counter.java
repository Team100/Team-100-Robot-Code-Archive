/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sam
 */
public class Counter {
    
    private long C_time = 0;//Current time
    private long O_time = 0;//Original time
    
    public Counter(long times){//Contructor expects the current time of the robot
        C_time = time(times); // Sets the current time to the time given, this number often changes
        O_time = time(times); // Sets the original time to the time given, this number never changes
    }
    
    public void update(){ //Called at the end of a loop
        long delay = time(System.currentTimeMillis()) - C_time;//gets the time between the C_time and the
            //what the robots actual current time is
        
        SmartDashboard.putNumber("Delay", delay);
        SmartDashboard.putNumber("Current Time", time(System.currentTimeMillis()));
        SmartDashboard.putNumber("Last Time", C_time);
        
        
        C_time  = time(System.currentTimeMillis());//Resets the current time to what it actually is
    }
    
    public long time(long N_time){//Returns the time since the robot was started.
        return N_time - O_time;
    }
    
}
