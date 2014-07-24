//untested
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Tests whether the camera sees a target without shooting.
 */
public class CameraTest extends Command {

    private boolean targetDetected = false;
    private boolean done = false;
    private final double minTime;
    private final double maxTime;
    
    public CameraTest(double t1, double t2) {
        requires(Ballrus.camera);
        minTime = t1;
        maxTime = t2;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.camera.setCameraLights(true);
        done = false;
        targetDetected = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!Ballrus.camera.hasFreshImage()||timeSinceInitialized() < 1.0||done) { // wait for light ring to turn on
            System.out.println("Camera not ready");
            return;
        }
        if (Ballrus.camera.detectTarget()) { // hot target will change before we shoot
            targetDetected = true;
            done = true;
            System.out.println("Target Detected");
            SmartDashboard.putBoolean("TargetDetected", true);
        } else {
            targetDetected = false;
            done = true;
            System.out.println("No Target Detected");
            SmartDashboard.putBoolean("TargetDetected", false);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > maxTime || (targetDetected&&timeSinceInitialized()>minTime);
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("Target Detected: " + targetDetected);
        Ballrus.camera.setCameraLights(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
