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

    public AlignToShoot() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
         Robot.driveTrain.resetRangefinder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

       double tempInches = Robot.driveTrain.getRangeInches();
       if(tempInches<0)
       {
           Robot.driveTrain.stop();
           return;
       }

       if (tempInches > Preferences.ultraStopDistance) {
            if (Preferences.tankDriveMode) {
                Robot.driveTrain.tankDrive(Robot.oi.getDriverLeft().getY(), Robot.oi.getDriverRight().getY());
            } else {
                Robot.driveTrain.arcadeDrive(Robot.oi.getDriverLeft().getY(), Robot.oi.getDriverRight().getX());
            }
        } else if(complete == false) {
            if(Robot.driveTrain.autoTurnToAngle(0)){
                complete = true;
                Robot.driveTrain.setDirection();
                Robot.driveTrain.resetEncoders();
            }
        }

        if (complete) {
            //Robot.driveTrain.autoTurnToAngle(0);
            Robot.driveTrain.autoDriveStraight(tempInches - Preferences.ultraStopDistance);
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
