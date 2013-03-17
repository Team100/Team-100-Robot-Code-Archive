
//FrisbeesToShoot activates the frisbee belt at a speed that we can change.
//It feeds the frisbees into the shooter wheels.

package org.usfirst.frc100.OrangaHang.commands;

/**
 *
 * @author Team100
 */
public class FrisbeesToShoot extends CommandBase {
    
    public FrisbeesToShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(frisbeeTransport);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        // Reset can't be in initialize, because the transport overshoots, so the hall-effect is triggered again
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        frisbeeTransport.shootFrisbees();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//whileHeld
    }

    // Called once after isFinished returns true
    protected void end() {
        frisbeeTransport.disable();
        //Reset bottom counter so we can move back down
        frisbeeTransport.resetBottom();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
