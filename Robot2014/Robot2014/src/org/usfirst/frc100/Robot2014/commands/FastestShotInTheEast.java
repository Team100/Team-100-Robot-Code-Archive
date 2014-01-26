
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Preferences;
import org.usfirst.frc100.Robot2014.Robot;
import org.usfirst.frc100.Robot2014.RobotMap;

/**
 *
 * @author Arkhan/Koosha-Man
 * 
 * This class allows us to shoot while moving (using the line sensor to tell us when to shoot)! It's the fastest shot in the East!
 * It is called while held.
 * 
 */


public class FastestShotInTheEast extends Command {
    AnalogTrigger reader = RobotMap.driveTrainLeftLineTrigger;
    Counter counter = new Counter(reader);
    int state;
    boolean distApropos;
    double speed;
    boolean triggered = false;
    
    public FastestShotInTheEast() {
        requires(Robot.driveTrain);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.resetRangefinder();
        state = 0;
        distApropos = false;
        reader.setLimitsRaw(Preferences.lowerLimit, Preferences.upperLimit);
        counter.setUpSourceEdge(true, true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //rotate to face forward CHECK
        //drive straight CHECK
        //wait until the distance is apropos CHECK  
        //shoot while still driving straight CHECK
        //end when button released CHECK
        
        triggered = (1 == counter.get()%2);
  
        if(state == 0)
        {
            Robot.driveTrain.autoTurnToAngle(0);
            state = 1;
            //System.out.println("STATE now equals 1");
        }
        
        if(state == 1 && distApropos == false)
        {
            Robot.driveTrain.driveStraight(Robot.oi.getDriverRight().getY());
  
            if(triggered)
            {
                distApropos = true;
            }
            
        }
        
        if(distApropos == true)
        {
            new TriggerShootReload().start();
            //Robot.driveTrain.driveStraight(Robot.oi.getDriverRight().getY());
            Robot.driveTrain.stop();
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
