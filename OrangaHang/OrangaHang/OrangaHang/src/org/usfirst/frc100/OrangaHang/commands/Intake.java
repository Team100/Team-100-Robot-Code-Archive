
//Intake moves the frisbee belt hook down to the bottom in order to make room for frisbees.
//It also makes the shooter spin backwards at a speed that we can choose. 

package org.usfirst.frc100.OrangaHang.commands;

/**
 *
 * @author Team100
 */
public class Intake extends CommandBase {
    
    public Intake() {
        requires(frisbeeTransport);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        // Reset can't be in initialize, because the transport overshoots, so the hall-effect is triggered again
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        frisbeeTransport.takeFrisbees();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//whileHeld
    }

    // Called once after isFinished returns true
    protected void end() {
        frisbeeTransport.disable();
        //Reset top so we can move up
        frisbeeTransport.resetTop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
