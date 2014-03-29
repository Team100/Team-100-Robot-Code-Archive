
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Compensates for gyro drift.
 */
public class CalibrateGyro extends Command {
    
    private double duration = 0.0;
    
    public CalibrateGyro(double duration) {
        this.duration = duration;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.driveTrain.setGyroDrift(0.0);
        Ballrus.driveTrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > duration;
    }

    // Called once after isFinished returns true
    protected void end() {
        double drift = Ballrus.driveTrain.getGyroDegrees()/timeSinceInitialized();
        Ballrus.driveTrain.setGyroDrift(drift);
        System.out.println("Gyro Drift Per Second: "+drift);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
