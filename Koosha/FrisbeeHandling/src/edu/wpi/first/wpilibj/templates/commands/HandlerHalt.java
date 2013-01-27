/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.*;

/**
 *
 * @author Student
 */
public class HandlerHalt extends CommandBase
{
    boolean isHalted;
    
    public HandlerHalt() {
        // Use requires() here to declare subsystem dependencies
        requires(frisbeeHandler);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        isHalted=false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        frisbeeHandler.setFeedMotor(0.0);
        frisbeeHandler.setIntakeMotor(0.0);
        isHalted=true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isHalted;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        SmartDashboard.putString("Is Halted=", "True");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        SmartDashboard.putString("Is Halted=", isHalted ? "True" : "False"); // returns value of isHalted
    }
}
