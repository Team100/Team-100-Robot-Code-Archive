//ready
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Prepares the shooter to shoot by raising the upper intake roller. Command is
 * while held.
 */
public class ArmShooter extends Command {

    public ArmShooter() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.intake.setTopPiston(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.intake.setTopPiston(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}