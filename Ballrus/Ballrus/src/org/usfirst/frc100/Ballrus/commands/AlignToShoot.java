//needs more testing
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Uses the rangefinder to line up for shooting. Command is while held.
 */
public class AlignToShoot extends Command {

    private boolean distReached;
    private double distError;
    private double storedDist;
    private int inPositionCounter;
    private int spikeDuration;

    public AlignToShoot() {
        requires(Ballrus.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.driveTrain.resetRangefinder();
        distReached = false;
        distError = 0.0;
        storedDist = 0.0;
        inPositionCounter = 0;
        spikeDuration = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double currentDist = Ballrus.driveTrain.getRangeInches();
        if (currentDist < 0) {
            spikeDuration++;
        }
        if (spikeDuration > 7) {
            Ballrus.driveTrain.resetRangefinder();
        }
        if ((currentDist > Preferences.ultraInitialStopDistance || currentDist < 0) && !distReached) {
            if (Preferences.tankDriveMode) {
                Ballrus.driveTrain.tankDrive(Ballrus.oi.getDriverLeft().getY(), Ballrus.oi.getDriverRight().getY());
            } else {
                Ballrus.driveTrain.arcadeDrive(Ballrus.oi.getDriverLeft().getY(), Ballrus.oi.getDriverRight().getX());
            }
        } else if (distReached == false) {
            if (Ballrus.driveTrain.autoTurnToAngle(0)) {
                if (Ballrus.driveTrain.getEncoderInches() == storedDist) {
                    inPositionCounter++;
                }
                storedDist = Ballrus.driveTrain.getEncoderInches();
                if (inPositionCounter >= Preferences.inPositionCounter) {
                    distReached = true;
                    distError = currentDist - Preferences.ultraActualStopDistance;
                    Ballrus.driveTrain.setDirection();
                    Ballrus.driveTrain.resetEncoders();
                }
            }
        } else {
            Ballrus.driveTrain.autoDriveStraight(distError);
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
