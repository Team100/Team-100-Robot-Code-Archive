//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Stops the intake rollers. Command is when pressed.
 */
public class StopIntake extends Command {

    public StopIntake() {
        requires(Ballrus.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.intake.stop();
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
    }
}