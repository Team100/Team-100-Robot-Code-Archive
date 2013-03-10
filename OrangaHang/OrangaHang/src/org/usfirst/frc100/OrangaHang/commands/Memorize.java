/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.IOException;
import org.usfirst.frc100.OrangaHang.OI;

/**
 *
 * @author Sam
 */
public class Memorize extends CommandBase{
    
    Timer timer = new Timer();
    
    public Memorize(){
        requires(autoMemory);
    }

    protected void initialize() {
        timer.reset();
        timer.start();
        autoMemory.beginCollection();
    }

    /**
     * Gets the current joystick values and sends them the autoMemory.collectString
     * @see autoMemory
     */
    protected void execute() {
        autoMemory.collectString(OI.driverLeft.getY(), OI.driverRight.getX(),
                OI.shootButton.get(), OI.primeShootButton.get(),timer.get());
        if(timer.get() > 15000){
            end();
        }
        
    } 

    protected boolean isFinished() {
        //TODO: run for 15 seconds
        return false;
    }

    protected void end() {
        try {
            autoMemory.stopCollection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }      
        timer.stop();
    }
    
    protected void interrupted() {
        end();
    }    
}
