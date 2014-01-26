
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
    Counter counter = RobotMap.counter;
    
    private static final int STATE_INIT = 0;
    private static final int STATE_DONETURN = 1;

    int state;
    boolean isFin;
    double speed;
    boolean triggered = false;
    
    public FastestShotInTheEast() {
        requires(Robot.driveTrain);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.resetRangefinder();
        state = STATE_INIT;
        isFin = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
        //rotate to face forward CHECK
        //drive straight CHECK
        //wait until the distance is apropos CHECK  
        //shoot while still driving straight CHECK
        //end when button released CHECK
        
        triggered = (1 == counter.get()%2);
  
        if(state == STATE_INIT)
        {
            if(Robot.driveTrain.autoTurnToAngle(0))
            {
                state = STATE_DONETURN;
            }
            //System.out.println("STATE now equals 1");
        }
        
        if(state == STATE_DONETURN && !triggered)
        {
            Robot.driveTrain.driveStraight(Robot.oi.getDriverRight().getY());
        }
        
        if(triggered)
        {
            new TriggerShootReload().start();
            //Robot.driveTrain.driveStraight(Robot.oi.getDriverRight().getY());
            Robot.driveTrain.stop();
            //System.out.println("SHOT...DEAD");
            isFin = true;
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFin;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
