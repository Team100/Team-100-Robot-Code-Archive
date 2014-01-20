//ready
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Shoots the ball and resets shooter, then starts a TiltToIntake command.
 * Command is when pressed.
 */
public class TriggerShootReload extends Command {
    private boolean isFinished = false;
        
    public TriggerShootReload() {
        requires(Robot.shooter);
        requires(Robot.tilter);
        setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.tilter.stop();
        Robot.shooter.stop();
        Robot.shooter.setTrigger(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        isFinished = Robot.shooter.reload();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.shooter.setTrigger(false);
        new TiltToIntake().start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
