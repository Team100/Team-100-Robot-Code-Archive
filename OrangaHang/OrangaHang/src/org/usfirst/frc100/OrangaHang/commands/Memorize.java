/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import java.io.IOException;
import org.usfirst.frc100.OrangaHang.OI;

/**
 *
 * @author Sam
 */
public class Memorize extends CommandBase{
    
    public Memorize(){
        requires(autoMemory);
    }

    protected void initialize() {
        autoMemory.beginCollection();
    }

    /**
     * Gets the current joystick values and sends them the autoMemory.collectString
     * @see autoMemory
     */
    protected void execute() {
        autoMemory.collectString(OI.driverLeft.getY(), OI.driverRight.getX(),
                OI.shootButton.get(), OI.primeShootButton.get());
        
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
    }
    
    protected void interrupted() {
        end();
    }    
}
