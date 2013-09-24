package org.usfirst.frc100.Mk3.commands;

/**
 * Tilts the intake to high or low position. 1 = high, 2 = low.
 */
public class TiltIntake extends CommandBase {

    boolean done = false;

    public TiltIntake() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        intake.tiltToPosition();
        if (intake.inPosition) {
            done = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
