//ready
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Preferences;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Drives the robot a given distance in a straight line, then stops the robot
 * and terminates. Parameter is the distance in inches to drive.
 */
public class AutoTurn extends Command {

    int inPositionCounter = 0;
    double angle;
    
    public AutoTurn(double angle) {
        requires(Robot.driveTrain);
        this.angle=angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Robot.driveTrain.autoTurnByAngle(angle)){
            inPositionCounter++;
        }
        else{
            inPositionCounter = 0;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return inPositionCounter>Preferences.autoDriveDelay;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
