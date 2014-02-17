//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Moves arm to low shooting position and pulls back the shooter to the correct
 * distance. Command is when pressed.
 */
public class TiltToShootLow extends Command {

    public TiltToShootLow() {
        requires(Ballrus.shooter);
        requires(Ballrus.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Ballrus.tilter.setPosition(Preferences.shootLowAngle);
        Ballrus.shooter.setPosition(Preferences.shootLowPosition);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > 5||(timeSinceInitialized()>.1&&Ballrus.tilter.inPosition()&&Ballrus.shooter.inPosition());
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.tilter.stop();
        Ballrus.shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
