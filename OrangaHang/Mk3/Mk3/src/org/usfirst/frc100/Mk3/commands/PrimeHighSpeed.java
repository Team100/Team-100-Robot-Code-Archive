package org.usfirst.frc100.Mk3.commands;

/**
 * Runs the shooter wheels at high speed.
 */
public class PrimeHighSpeed extends CommandBase {

    public PrimeHighSpeed() {
        // Use requires() here to declare subsystem dependencies
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shooter.primeHighSpeed();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.timeSinceInitialized() > 10.0; //whileHeld
    }

    // Called once after isFinished returns true
    protected void end() {
        shooter.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
