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
    //private double error;
    
   /**
    * @param degreeOfTurn
    * How many degrees to turn clockwise (negative if turning counter-clockwise)
    */
    public TurnInPlace(double degreeOfTurn)
    {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
        SmartDashboard.putString("test", "constructor");
        setPoint = (-1)*degreeOfTurn;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        driveTrain.resetGyro();
        //error = setPoint - driveTrain.getGyro();
        driveTrain.updatePID();
        driveTrain.turnController.setSetpoint(setPoint);
        driveTrain.turnController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
//        driveTrain.tankDrive(error*driveTrain.get_kP(), (-1)*error*driveTrain.get_kP());
//        error = setPoint - driveTrain.getGyro();
        SmartDashboard.putNumber("Error", driveTrain.turnController.getError());
        SmartDashboard.putString("test1", "");
        driveTrain.debugDriveTrain();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return Math.abs(setPoint - driveTrain.getGyro()) < 0.8;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        SmartDashboard.putString("test1", "done");
        driveTrain.turnController.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        SmartDashboard.putString("test1", "done");
        driveTrain.turnController.disable();
    }
}
