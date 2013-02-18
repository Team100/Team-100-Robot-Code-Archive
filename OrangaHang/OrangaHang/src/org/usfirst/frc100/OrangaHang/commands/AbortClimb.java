/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

/**
 *
 * @author Team100
 */
public class AbortClimb extends CommandBase {
    
    public AbortClimb() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        climber.disable();
        climber.stop();
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
