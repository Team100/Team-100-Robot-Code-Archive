/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

/**
 *
 * @author Daniel
 */
public class WritePreferences extends CommandBase {    
    public WritePreferences() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        setRunWhenDisabled(true);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // TODO: should call writePreferences on all subsystems
        shooter.writePreferences();
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
