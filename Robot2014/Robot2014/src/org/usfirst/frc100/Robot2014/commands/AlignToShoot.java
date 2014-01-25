package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Preferences;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Uses the rangefinder to line up for shooting. Command is while held.
 */
public class AlignToShoot extends Command {

    boolean distReached;
    double distError;
    double storedDist;
    int inPositionCounter;
    int spikeDuration;

    public AlignToShoot() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.resetRangefinder();
        distReached = false;
        distError = 0;
        storedDist = 0;
        inPositionCounter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double currentDist = Robot.driveTrain.getRangeInches();
        if(currentDist <0)
        {
            spikeDuration++;
        }
        if(spikeDuration > 7)
        {
            Robot.driveTrain.resetRangefinder();
        }
        if ((currentDist > Preferences.ultraInitialStopDistance || currentDist < 0) && !distReached) {
            if (Preferences.tankDriveMode) {
                Robot.driveTrain.tankDrive(Robot.oi.getDriverLeft().getY(), Robot.oi.getDriverRight().getY());
            } else {
                Robot.driveTrain.arcadeDrive(Robot.oi.getDriverLeft().getY(), Robot.oi.getDriverRight().getX());
            }
        } else if (distReached == false) {
            if (Robot.driveTrain.autoTurnToAngle(0)) {
                if (Robot.driveTrain.getDistance() == storedDist) {
                    inPositionCounter++;
                }
                storedDist = Robot.driveTrain.getDistance();
                if (inPositionCounter >= Preferences.inPositionCounter) {
                    distReached = true;
                    distError = currentDist - Preferences.ultraActualStopDistance;
                    Robot.driveTrain.setDirection();
                    Robot.driveTrain.resetEncoders();
                }
            }
        } else {
            Robot.driveTrain.autoDriveStraight(distError);
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
