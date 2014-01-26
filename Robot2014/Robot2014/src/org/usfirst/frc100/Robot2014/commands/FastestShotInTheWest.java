
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
    boolean distApropos;
    double speed;
    
    public FastestShotInTheWest() {
        requires(Robot.driveTrain);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.resetRangefinder();
        state = 0;
        distApropos = false;
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //rotate to face forward CHECK
        //drive straight CHECK
        //wait until the distance is apropos CHECK  
        //shoot while still driving straight CHECK
        //end when button released CHECK
  
        if(state == 0)
        {
            Robot.driveTrain.autoTurnToAngle(0);
            state = 1;
            //System.out.println("STATE now equals 1");
        }
        
        if(state == 1 && distApropos == false)
        {
            Robot.driveTrain.driveStraight(Robot.oi.getDriverRight().getY());
            
            speed = Robot.driveTrain.getCurrentSpeed();
            //System.out.println("........speed " + speed);
            
            if(Robot.driveTrain.getRangeInches() <= (Preferences.ultraActualShootDistance + (0.5*speed)))
            {
                distApropos = true;
               // System.out.println("DIST is apropos " + Robot.driveTrain.getRangeInches());
            }
            
        }
        
        if(distApropos == true)
        {
            new TriggerShootReload().start();
            Robot.driveTrain.driveStraight(Robot.oi.getDriverRight().getY());
            //System.out.println("SHOT...DEAD");
        }
        
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
