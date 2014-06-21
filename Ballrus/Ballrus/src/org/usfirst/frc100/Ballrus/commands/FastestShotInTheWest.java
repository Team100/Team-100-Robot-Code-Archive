//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * This class allows us to shoot while moving (using the ultrasonic sensor to
 * tell us when to shoot)! It's the fastest shot in the West! It is called while
 * held.
 */
public class FastestShotInTheWest extends Command {

    private boolean distanceReached;
    private double speed;

    public FastestShotInTheWest() {
        requires(Ballrus.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.driveTrain.resetRangefinder();
        distanceReached = false;
        speed = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!distanceReached) {
            speed = Ballrus.driveTrain.getEncoderSpeed();
            if (Ballrus.driveTrain.getRangeInches() <= (Preferences.ultraActualShootDistance + (0.12 * speed)) && Ballrus.driveTrain.getRangeInches() > 0) {  //if MB1220
                distanceReached = true;
                new TriggerShootReload().start();
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return distanceReached;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
