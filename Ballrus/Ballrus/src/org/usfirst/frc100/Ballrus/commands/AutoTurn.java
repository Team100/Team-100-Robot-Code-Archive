//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Rotates the robot by the given angle in degrees, then stops the robot
 * and terminates. Parameter is the angle to turn to.
 */
public class AutoTurn extends Command {

    private int inPositionCounter;
    private double angle;
    private boolean toAngle = false;
    private double lastAngle;
    
    public AutoTurn(double angle) {
        requires(Ballrus.driveTrain);
        this.angle = angle;
    }

    public AutoTurn(double angle, boolean toAngle) {
        requires(Ballrus.driveTrain);
        this.angle = angle;
        this.toAngle = toAngle;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.driveTrain.setDirection();
        inPositionCounter = 0;
        lastAngle = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double realAngle = Ballrus.driveTrain.getGyroDegrees();
        if (toAngle) {
            if (Ballrus.driveTrain.autoTurnToAngle(angle)) {
                if (Math.abs(lastAngle - realAngle) < .5) {
                    inPositionCounter++;
                } else {
                    lastAngle = realAngle;
                    inPositionCounter = 0;
                }
            } else {
                inPositionCounter = 0;
            }
            return;
        }
        if(Ballrus.driveTrain.autoTurnByAngle(angle)){
            if(Math.abs(lastAngle - realAngle)<.5){
                inPositionCounter++;
            }
            else{
                lastAngle = realAngle;
                inPositionCounter = 0;
            }
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
        Ballrus.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
