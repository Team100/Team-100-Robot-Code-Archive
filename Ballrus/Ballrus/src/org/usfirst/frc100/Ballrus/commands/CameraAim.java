//not yet fully implemented
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Starts an autoTurn command to an angle determined by the camera to be the hot
 * goal. Command is whenPressed, and terminates after the angle is calculated.
 */
public class CameraAim extends Command {
    
    double targetAngle;
    
    public CameraAim() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //put code here
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        new AutoTurn(targetAngle).start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}