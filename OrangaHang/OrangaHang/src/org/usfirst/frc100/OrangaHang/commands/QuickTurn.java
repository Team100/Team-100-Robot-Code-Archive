
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
    private double setpoint;
    private double angle;
    private double error;
    private double kP = 0.011;
    private double kDB = 0.19;
    
    public QuickTurn()
    {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
        SmartDashboard.putNumber("QuickTurnP", kP);
        SmartDashboard.putNumber("QuickTurnDeadband", kDB);
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
            setpoint = 90 * (OI.driverRight.getX()>0.0 ? 1.0:-1.0);
        }
        
        kP = SmartDashboard.getNumber("QuickTurnP");
        kDB = SmartDashboard.getNumber("QuickTurnDeadband");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        angle = (-driveTrain.getGyro()); // -driveTrain.getGyro() b/c the gyro is upsidedown
        error = setpoint - angle;
        if(isFinished())
        {
            return;
        }
        double twist = error * kP;
        
        if(Math.abs(twist) < kDB)
        {
            twist = kDB * (twist>0.0 ? 1.0:-1.0);
        }
        driveTrain.quickTurn(twist);
        
        SmartDashboard.putNumber("QuickTurnTwist", twist);
        SmartDashboard.putNumber("QuickTurnGyro", angle);
        SmartDashboard.putNumber("QuickTurnError", error);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return Math.abs(angle) >= Math.abs(setpoint);
    }

    // Called once after isFinished returns true
    protected void end()
    {
        driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        end();
    }
}
