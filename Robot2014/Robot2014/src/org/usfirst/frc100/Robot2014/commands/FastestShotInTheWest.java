
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Preferences;
import org.usfirst.frc100.Robot2014.Robot;

/**
 *
 * @author Arkhan
 * 
 * This class allows us to shoot while moving! It's the fastest shot in the West!
 * It is called while held.
 * 
 */


public class FastestShotInTheWest extends Command {
    
    int state;
    
    public FastestShotInTheWest() {
        requires(Robot.driveTrain);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.resetRangefinder();
        state = 0;
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //rotate to face forward
        //drive straight
        //wait until the distance is apropos
        //shoot while still driving straight
        //end when button released
  
        if(state == 0)
        {
            Robot.driveTrain.autoTurnToAngle(0);
            state = 1;
        }
        
        Robot.driveTrain.
        
        
        
        
        
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
