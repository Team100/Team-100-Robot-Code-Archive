//ready
package org.usfirst.frc100.Robot2014.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Robot2014.Preferences;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Drives the robot, giving the choice of tank or arcade drive.
 */
public class  Drive extends Command {

    public Drive() {
	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Preferences.tankDriveMode){
            Robot.driveTrain.tankDrive(Robot.oi.getDriverLeft().getY(), Robot.oi.getDriverRight().getY());
        }
        else{
            Robot.driveTrain.arcadeDrive(Robot.oi.getDriverLeft().getY(), Robot.oi.getDriverRight().getX());
        }
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
