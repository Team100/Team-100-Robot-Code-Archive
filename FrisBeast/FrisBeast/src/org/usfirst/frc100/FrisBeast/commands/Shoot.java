//Launches frisbee after held for half a second or until the button is released.
package org.usfirst.frc100.FrisBeast.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Team100
 */
public class Shoot extends CommandBase {
    private final Timer timer = new Timer();
    
    public Shoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(feeder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        timer.reset();
        timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        feeder.pullBack();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timer.get() >= 0.5);
    }

    // Called once after isFinished returns true
    protected void end() {
        feeder.pushForward();
        timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
