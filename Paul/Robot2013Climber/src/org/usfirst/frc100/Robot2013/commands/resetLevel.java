/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Robot2013.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2013.Robot;

/**
 *
 * @author Student
 */
public class resetLevel extends Command {
    
    public resetLevel() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires (Robot.climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.climber.resetLevel();
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
