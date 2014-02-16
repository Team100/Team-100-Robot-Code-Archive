package org.usfirst.frc100.Mk3.commands;

/**
 * Called during the final second of the match. Will lower the hanging arms just
 * in case we're under the pyramid.
 */
public class LastSecondHang extends CommandBase {

    public LastSecondHang() {
        // Use requires() here to declare subsystem dependencies
        requires(hanger);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        hanger.retractHanger();
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
