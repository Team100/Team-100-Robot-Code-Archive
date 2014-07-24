//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Moves arm to position for passing over the truss to the human player and 
 * pulls back the shooter to the correct distance. Command is when pressed.
 */
public class TiltToCameraAim extends Command {

    public TiltToCameraAim() {
        requires(Ballrus.shooter);
        requires(Ballrus.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.tilter.stop();
        Ballrus.shooter.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Ballrus.tilter.setPosition(Preferences.tilterCameraAngle);
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
