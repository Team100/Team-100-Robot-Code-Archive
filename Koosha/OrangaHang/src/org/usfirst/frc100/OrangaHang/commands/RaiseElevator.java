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
public class RaiseElevator extends CommandBase {
    boolean isFinished=false;
    public RaiseElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        SmartDashboard.putNumber("Setpoint", 0.0);
        climber.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        climber.setSetpoint(SmartDashboard.getNumber("Setpoint", 0.0));
        climber.raiseElevator();
        if (climber.getUpperLimit()){
            isFinished=true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
        climber.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
