//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Shifts to low gear, then shifts back when command terminates. Command is
 * while held.
 */
public class ShiftLow extends Command {
   
    public ShiftLow() {
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.driveTrain.shiftLow();
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
        Ballrus.driveTrain.shiftHigh();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
