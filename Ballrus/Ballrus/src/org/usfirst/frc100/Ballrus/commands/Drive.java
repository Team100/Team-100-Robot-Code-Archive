//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        double leftY=Ballrus.oi.getDriverLeft().getY();
        double rightY=Ballrus.oi.getDriverRight().getY();
        double rightX=Ballrus.oi.getDriverRight().getX();
        if(Math.abs(leftY)<Preferences.driveJoystickDeadband){
            leftY = 0.0;
        }
        if(Math.abs(rightY)<Preferences.driveJoystickDeadband){
            rightY = 0.0;
        }
        if(Math.abs(rightX)<Preferences.driveJoystickDeadband){
            rightX = 0.0;
        }
        if(Preferences.tankDriveMode){
            Ballrus.driveTrain.tankDrive(leftY, rightY);
        }
        else{
            Ballrus.driveTrain.arcadeDrive(leftY, rightX);
        }
    SmartDashboard.putNumber("Left Joystick", Ballrus.oi.getDriverLeft().getY());
    SmartDashboard.putNumber("Right Joystick", Ballrus.oi.getDriverRight().getX());
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
