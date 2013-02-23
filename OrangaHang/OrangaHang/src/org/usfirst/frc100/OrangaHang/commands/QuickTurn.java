
//QuickTurn quickly turns OrangaHang a variable amount of degrees. 
//we will probably set it to 90 degrees.

package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.OI;

/**
 *
 * @author Team100
 */
public class QuickTurn extends CommandBase
{
    private double setpoint=90;
    private double error;
    
    public QuickTurn()
    {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
//        driveTrain.resetValues();
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        driveTrain.resetGyro();
        
        if(Math.abs(OI.driverRight.getX()) < 0.1)
        {
            setpoint = 0.0;
        }
        else
        {
            setpoint *= (OI.driverRight.getX() > 0.0 ? 1.0 : -1.0);
        }
        
        SmartDashboard.putString("Is turning", "True");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        double angle = (-driveTrain.getGyro()); // -driveTrain.getGyro() b/c the gyro is upsidedown
        error = setpoint - angle;
        driveTrain.quickTurn(error);
        
        SmartDashboard.putNumber("Twist Value", error/90.0);
        SmartDashboard.putNumber("Gyro Angle", angle);
        SmartDashboard.putNumber("Error", error);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return Math.abs(error) < 1.5;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        SmartDashboard.putString("Is turning", "False");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        end();
    }
}
