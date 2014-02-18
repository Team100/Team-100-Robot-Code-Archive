//untested
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Starts an autoTurn command to an angle determined by the camera to be the hot
 * goal. Command is whenPressed, and terminates after the angle is calculated.
 */
public class CameraAim extends Command {

    private double targetAngle = Preferences.cameraAngle;

    public CameraAim() {
        requires(Ballrus.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.camera.setCameraLights(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (timeSinceInitialized() < .2||!Ballrus.camera.hasFreshImage()) { // wait for light ring to turn on
            return;
        }
        if (Ballrus.camera.leftTargetHot()) { // hot target will change before we shoot
            targetAngle = Preferences.cameraAngle;
        } else {
            targetAngle = -Preferences.cameraAngle;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > .4;
        // return timeSinceInitialized() > .2;
    }

    // Called once after isFinished returns true
    protected void end() {
        new AutoTurn(targetAngle,true, 2).start();
        Ballrus.camera.setCameraLights(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
