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
    
    public LowerElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //only lower elevator partway to get from level 2 to level 3 of pyramid
        if (climber.getLevel()!=2){
            climber.lowerElevator();//leave the rest commented out if stationary hooks are on the bottom
        }
        else {
            climber.lowerElevatorPartway();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((climber.getLevel()==2&&climber.getPartwayLimit())||climber.getLowerLimit());
    }

    // Called once after isFinished returns true
    protected void end() {
        //this is only in LowerElevator, NOT RaiseElevator
        climber.nextLevel();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
