package org.usfirst.frc100.Mk3.commands;

/**
 * Activates the pneumatics in order to shift the gears down.
 */
public class ShiftDown extends CommandBase {

    public ShiftDown() {
        // Use requires() here to declare subsystem dependencies
        //does NOT require drivetrain so doesn't interrupt drive command
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        shifter.shiftLowGear();
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
}
