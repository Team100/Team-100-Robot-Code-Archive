//ready
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Preferences;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Rotates the robot by the given angle in degrees, then stops the robot
 * and terminates. Parameter is the angle to turn to.
 */
public class AutoTurn extends Command {

    int inPositionCounter = 0;
    double angle;
    boolean toAngle = false;
    
    public AutoTurn(double angle) {
        requires(Robot.driveTrain);
        this.angle=angle;
    }

    public AutoTurn(double angle, boolean toAngle) {
        requires(Robot.driveTrain);
        this.angle=angle;
        this.toAngle=toAngle;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.setDirection();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(toAngle){
            if(Robot.driveTrain.autoTurnToAngle(angle)){
                inPositionCounter++;
            }
            else{
                inPositionCounter = 0;
            }
            return;
        }
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
