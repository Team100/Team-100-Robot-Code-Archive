//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Drives the robot a given distance in a straight line, then stops the robot
 * and terminates. Parameter is the distance in inches to drive.
 */
public class AutoDriveStraight extends Command {

    private int inPositionCounter;
    private final double distance;
    private final double timeOut;
    
    public AutoDriveStraight(double distance, double timeOut) {
        requires(Ballrus.driveTrain);
        this.distance=distance;
        this.timeOut = timeOut;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.driveTrain.setDirection();
        Ballrus.driveTrain.resetEncoders();
        inPositionCounter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Ballrus.driveTrain.autoDriveStraight(distance)){
            inPositionCounter++;
        }
        else{
            inPositionCounter = 0;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return inPositionCounter>Preferences.autoDriveDelay||timeSinceInitialized()>timeOut;
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
