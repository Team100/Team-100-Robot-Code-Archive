/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Team100
 */
public class QuickTurn extends CommandBase
{
    private double setPoint;
    public QuickTurn(double setPoint)
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
        this.setPoint = setPoint;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        driveTrain.setSetpoint(setPoint);
        driveTrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        SmartDashboard.putString("Is turning", "True");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return Math.abs(driveTrain.getSetpoint() - driveTrain.getGyro()) < 0.8;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        driveTrain.disable();
        SmartDashboard.putString("Is turning", "False");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        driveTrain.disable();
        SmartDashboard.putString("Is turning", "False");
    }
}
