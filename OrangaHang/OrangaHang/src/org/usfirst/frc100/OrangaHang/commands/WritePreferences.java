/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.Preferences;

/**
 *
 * @author Daniel
 */
public class WritePreferences extends CommandBase {
    
    Preferences p;
    
    public WritePreferences() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        p = Preferences.getInstance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //The syntax for naming keys of PID Widgets is      name + (kP | kI | kD | kMaxOutput | kMinOutput | kMax_Veloc)
        p.putDouble("key", 1.0);
        
        
        //Save the Preferences
        p.save();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
