//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Prepares the shooter to shoot by raising the upper intake roller. Command is
 * while held.
 */
public class RetractLowerRoller extends Command {

    public RetractLowerRoller() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.intake.setBottomPiston(false);
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