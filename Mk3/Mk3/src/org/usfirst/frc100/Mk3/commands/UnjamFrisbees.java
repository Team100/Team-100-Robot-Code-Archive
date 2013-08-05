package org.usfirst.frc100.Mk3.commands;

/**
 *
 * @author Team100
 */
public class UnjamFrisbees extends CommandBase {
    
    public UnjamFrisbees() {
        requires(feeder);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        feeder.pullBack();
        shooter.runBackwards();
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
        end();
    }
}//end UnjamFrisbees
