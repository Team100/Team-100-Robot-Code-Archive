//ready
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Gives manipulator left joystick control of tilter and manipulator right 
 * joystick control of shooter, never terminates
 */
public class FullManualControl extends Command {

    public FullManualControl() {
        requires(Robot.shooter);
        requires(Robot.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.tilter.manualControl(Robot.oi.getManipulator().getY());
        Robot.shooter.manualControl(Robot.oi.getManipulator().getThrottle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.tilter.manualControl(0);
        Robot.shooter.manualControl(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
