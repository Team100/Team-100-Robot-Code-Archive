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
public class LowerElevator extends CommandBase {
    
    boolean isFinished=false;
    public LowerElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        climber.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        climber.setSetpoint(SmartDashboard.getNumber("Setpoint", 0.0));
        //only lower elevator partway to get from level 2 to level 3 of pyramid
        if (climber.getLevel()!=2){
            climber.lowerElevator();
        }
        else {
            climber.lowerElevatorPartway();
        }
        if (climber.getLowerLimit()){
            isFinished=true;
        }
        if (climber.getLevel()==2&&climber.getPartwayLimit()){
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
