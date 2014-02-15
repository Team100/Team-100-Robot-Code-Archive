//AlignToShoot stops the robot at the ideal distance from the wall.
//The distance is measured by the MaxBotix ultrasonic sensor.
//You drive with the joysticks while holding the button down. It will let you
//drive but stop you when you get to the right distance from the wall.
package org.usfirst.frc100.FrisBeast.commands;

import org.usfirst.frc100.FrisBeast.OI;

/**
 *
 * @author Team100
 */
public class AlignToShoot extends CommandBase {
    
 
    
    public AlignToShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        
        requires(driveTrain);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        driveTrain.resetRangefinder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        driveTrain.alignToShoot(OI.driverLeft.getY(), OI.driverRight.getX());//inverted for left
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//whileHeld
    }

    // Called once after isFinished returns true
    protected void end() {
        //Drive will resume
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
