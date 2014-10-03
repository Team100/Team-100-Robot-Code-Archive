//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Pulls shooter all the way back without moving tilter. Command is when
 * pressed.
 */
public class PullBackFull extends Command {

    public PullBackFull() {
        requires(Ballrus.shooter);
        requires(Ballrus.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Ballrus.tilter.stop();
        Ballrus.shooter.setPosition(Preferences.shooterIntakePullback);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > 5||(timeSinceInitialized()>.1&&Ballrus.shooter.inPosition());
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.tilter.stop();
        Ballrus.shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
