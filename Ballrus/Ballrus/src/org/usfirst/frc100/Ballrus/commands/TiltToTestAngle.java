//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Moves arm to a position from the dashboard. Command is when pressed.
 */
public class TiltToTestAngle extends Command {

    public TiltToTestAngle() {
        requires(Ballrus.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.shooter.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Ballrus.tilter.setPosition(SmartDashboard.getNumber("TilterTestAngle"));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized() > 5 || (timeSinceInitialized() > .1 && Ballrus.tilter.inPosition());
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.tilter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
