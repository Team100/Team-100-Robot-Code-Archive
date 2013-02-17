package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;
import java.io.IOException;

/**
 *
 * @author Sam
 */
public class Memorize extends CommandBase{
    
    //TODO add shooter to execute
    
    public Memorize(){
        requires(autoMemory);
    }

    protected void initialize() {
        System.out.println("Beginning Collection");
        autoMemory.beginCollection();
        SmartDashboard.putBoolean("Collecting", true);
    }

    /**
     * Gets the current joystick values and sends them the autoMemory.collectString
     * @see autoMemory
     */
    protected void execute() {
        //add shootButton and primeShootButton
        autoMemory.collectString(-OI.driverLeft.getY(), -OI.driverRight.getX());
        
    }
    

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        try {
            autoMemory.stopCollection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        SmartDashboard.putBoolean("Collecting", false);
    }

    protected void interrupted() {
        end();
    }
    
}
