
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
    private double angle=90;
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
        angle*= (OI.driverRight.getX() >= 0.0 ? 1.0 : -1.0);
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        SmartDashboard.putString("Is turning", "True");
        driveTrain.quickTurn(angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return Math.abs(angle - driveTrain.getGyro()) < 0.8;
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
