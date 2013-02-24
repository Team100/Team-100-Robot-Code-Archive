
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
    private boolean isFinished = false;
    private double setpoint;
    
    public QuickTurn()
    {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        driveTrain.resetGyro();
        // Joystick position determines direction of the turn
        if(Math.abs(OI.driverRight.getX()) < 0.1)
        {
            setpoint = 0.0;
            isFinished = true;
        }
        else
        {
            setpoint = 90.0 * (OI.driverRight.getX()>0.0 ? 1.0:-1.0);
            isFinished = false;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        if (isFinished) {
            return;
        }
        isFinished = driveTrain.quickTurn(setpoint);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        driveTrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        end();
    }
}
