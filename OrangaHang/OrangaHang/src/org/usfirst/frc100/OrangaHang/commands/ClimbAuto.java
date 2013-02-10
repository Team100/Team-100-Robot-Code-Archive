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
public class ClimbAuto extends CommandBase {
    
    public ClimbAuto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        SmartDashboard.putNumber("Setpoint", 0.0);
        climber.resetLevel();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        climber.setSetpoint(SmartDashboard.getNumber("Setpoint", 0.0));
        //Warning: this code might not work
        climber.raiseElevator();
        climber.lowerElevator();
        climber.nextLevel();
        climber.raiseElevator();
        climber.lowerElevator();
        climber.nextLevel();
        climber.raiseElevator();
        climber.lowerElevatorPartway();
        climber.nextLevel();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (climber.getLevel()>2);
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
