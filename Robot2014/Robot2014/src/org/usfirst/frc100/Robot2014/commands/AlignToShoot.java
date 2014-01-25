package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Preferences;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Uses either the line follower or the rangefinder to line up for shooting.
 * Command is while held.
 */
public class AlignToShoot extends Command {

    boolean complete = false;
    double findub;
    double findub2;
    double storedDist;
//    boolean take2trigger;
    boolean take2complete;
    int counter;

    public AlignToShoot() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.resetRangefinder();
        complete = false;
//        take2trigger = false;
        counter = 0;
        storedDist = 0;
        //take2 = true;

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        double tempInches = Robot.driveTrain.getRangeInches();
//            System.out.println("tempInches " + tempInches);
//        if (tempInches < 0) {
//            Robot.driveTrain.stop();
//            return;
//        }

        if ((tempInches > Preferences.ultraInitialStopDistance||tempInches<0) &&!complete) {
            if (Preferences.tankDriveMode) {
                Robot.driveTrain.tankDrive(Robot.oi.getDriverLeft().getY(), Robot.oi.getDriverRight().getY());
            } else {
                Robot.driveTrain.arcadeDrive(Robot.oi.getDriverLeft().getY(), Robot.oi.getDriverRight().getX());
            }
        } else if (complete == false) {
            if (Robot.driveTrain.autoTurnToAngle(0)) {
                //Robot.driveTrain.stop();
                
                if(Robot.driveTrain.getDistance() == storedDist)
                {
                    counter++;
                }
                
                storedDist = Robot.driveTrain.getDistance();
                
                if(counter >= Preferences.inQuisitionCounter)
                {
                    complete = true;
                    findub = tempInches - Preferences.ultraActualStopDistance;
                    Robot.driveTrain.setDirection();
                    Robot.driveTrain.resetEncoders();
                    System.out.println("INITfindub " + findub);
                System.out.println("INITtempInches " + tempInches);
                System.out.println("INITpref " + Preferences.ultraActualStopDistance);
                }
                
//                System.out.println("INITfindub " + findub);
//                System.out.println("INITtempInches " + tempInches);
//                System.out.println("INITpref " + Preferences.ultraActualStopDistance);
                
                

            }
        } else {
            
//            System.out.println("findub " + findub);
//            System.out.println("tempInches " + tempInches);
            
            Robot.driveTrain.autoDriveStraight(findub);

            
           /* if(Robot.driveTrain.autoDriveStraight(findub))
            {
                take2trigger = true;
                Robot.driveTrain.stop(); 
            }*/
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
