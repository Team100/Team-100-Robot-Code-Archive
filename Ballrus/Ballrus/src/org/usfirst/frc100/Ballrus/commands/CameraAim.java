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

    private double targetAngle = 0;

    public CameraAim() {
        requires(Ballrus.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.camera.setCameraLights(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!Ballrus.camera.hasFreshImage()||timeSinceInitialized() < .2) { // wait for light ring to turn on
            System.out.println("Camera not ready");
            return;
        }
        if (Ballrus.camera.detectTarget()) { // hot target will change before we shoot
            targetAngle = Preferences.cameraAngle;
            System.out.println("Target Detected");
        } else {
            targetAngle = -Preferences.cameraAngle;
            System.out.println("No Target Detected");
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > .4;
    }

    // Called once after isFinished returns true
    protected void end() {
        new AutoTurn(targetAngle, true, 2).start();
        System.out.println("Target Angle: " + targetAngle);
        Ballrus.camera.setCameraLights(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
