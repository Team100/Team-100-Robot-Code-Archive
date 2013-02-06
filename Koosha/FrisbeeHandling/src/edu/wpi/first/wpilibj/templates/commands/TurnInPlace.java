/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Student
 */
public class TurnInPlace extends CommandBase
{
    private double setPoint;
    private double error;
    
   /**
    * @param degreeOfTurn
    * How many degrees to turn clockwise (negative if turning counter-clockwise)
    */
    public TurnInPlace(double degreeOfTurn)
    {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
        SmartDashboard.putString("test", "constructor");
        setPoint = (-1.0)*degreeOfTurn;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        driveTrain.resetGyro();
        error = setPoint - driveTrain.getGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        driveTrain.tankDrive(error/180, (-1)*error/180);
        error = setPoint - driveTrain.getGyro();
        SmartDashboard.putNumber("Error", error);
        SmartDashboard.putString("test1", "");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return Math.abs(error)<6.0;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        SmartDashboard.putString("test1", "done");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        SmartDashboard.putString("test1", "done");
    }
}
