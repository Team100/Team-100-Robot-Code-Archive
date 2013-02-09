
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author bradmiller
 */
public class DriveWithJoysticks extends CommandBase
{
    private double left;
    private double right;

    public DriveWithJoysticks()
    {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        left = 0.0;
        right = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        left = oi.logitech.getY();
        right = oi.logitech.getThrottle();
        
        if(oi.button8.get())
        {
            driveTrain.tankDrive(0.5*left, 0.5*right);
        }
        else
        {
            driveTrain.tankDrive(left, right);
        }
        
        if(oi.button4.get())
        {
            driveTrain.resetGyro();
        }
        
        driveTrain.debugDriveTrain();
        SmartDashboard.putNumber("Left Joystick", left);
        SmartDashboard.putNumber("Right Joystick", right);
        SmartDashboard.putBoolean("Button 8", oi.button8.get());
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
