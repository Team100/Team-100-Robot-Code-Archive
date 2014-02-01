package org.usfirst.frc100.Mk3.commands;

/**
 * Raises shooter.
 */
public class TiltUp extends CommandBase {

    public TiltUp() {
        requires(tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        tilter.tiltUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
}//end TiltUp
