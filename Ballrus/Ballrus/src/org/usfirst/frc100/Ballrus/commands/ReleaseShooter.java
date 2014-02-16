/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 *
 * @author Team100
 */
public class ReleaseShooter extends Command {

    private boolean isFinished;

    public ReleaseShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Ballrus.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Ballrus.shooter.reload()) {
            Ballrus.shooter.setTrigger(true);
            isFinished = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
