package org.usfirst.frc100.Mk3.commands;

/**
 * Runs the rollers on the intake.
 */
public class RunIntake extends CommandBase {

    public RunIntake() {
        // Use requires() here to declare subsystem dependencies
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        intake.runIntake();
        tilter.tiltUp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //whileHeld
    }

    // Called once after isFinished returns true
    protected void end() {
        intake.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
