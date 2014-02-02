//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Drives the robot, giving the choice of tank or arcade drive.
 */
public class  Drive extends Command {

    public Drive() {
	requires(Ballrus.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Preferences.tankDriveMode){
            Ballrus.driveTrain.tankDrive(Ballrus.oi.getDriverLeft().getY(), Ballrus.oi.getDriverRight().getY());
        }
        else{
            Ballrus.driveTrain.arcadeDrive(Ballrus.oi.getDriverLeft().getY(), Ballrus.oi.getDriverRight().getX());
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
