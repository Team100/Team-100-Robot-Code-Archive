package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Moves arm to position for truss shot and pulls back the shooter to the
 * correct distance. Command is when pressed. Do not terminate until another
 * tilt command takes over the tilter subsystem.
 */
public class TiltToShootTruss extends Command {

    public TiltToShootTruss() {
        requires(Robot.shooter);
        requires(Robot.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
