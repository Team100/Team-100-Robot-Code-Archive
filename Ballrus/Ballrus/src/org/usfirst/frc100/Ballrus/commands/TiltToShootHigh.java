//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Moves arm to high shooting position and pulls back the shooter to the correct
 * distance. Command is when pressed. Do not terminate until another tilt
 * command takes over the tilter subsystem.
 */
public class TiltToShootHigh extends Command {

    public TiltToShootHigh() {
        requires(Ballrus.shooter);
        requires(Ballrus.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Ballrus.tilter.setPosition(Preferences.shootHighAngle);
        Ballrus.shooter.setPosition(Preferences.shootHighPosition);
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