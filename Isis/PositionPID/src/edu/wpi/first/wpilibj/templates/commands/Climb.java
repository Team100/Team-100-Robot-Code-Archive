/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Isis
 */
public class Climb extends CommandBase {
    
    public Climb() {
        requires (climber);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        SmartDashboard.putNumber("Setpoint", 0.0);
        System.out.println("Setpoint added");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        climber.setSetpoint(SmartDashboard.getNumber("Setpoint", 0.0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        //climber.setSetpoint(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
