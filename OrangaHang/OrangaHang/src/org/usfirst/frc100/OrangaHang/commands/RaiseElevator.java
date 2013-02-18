
//RaiseElevator causes the elevating hooks to move to its upper limit.
//It stops when it gets there.

package org.usfirst.frc100.OrangaHang.commands;

import org.usfirst.frc100.OrangaHang.OI;

/**
 *
 * @author Team100
 */
public class RaiseElevator extends CommandBase {
    public RaiseElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        climber.raiseElevator();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (climber.getUpperLimit()|| (OI.manipulator.getY() > 0.5) || (OI.manipulator.getY() < -0.5));
    }

    // Called once after isFinished returns true
    protected void end() {
        climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
