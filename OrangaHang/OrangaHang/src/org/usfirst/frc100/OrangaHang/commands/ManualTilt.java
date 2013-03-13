/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import org.usfirst.frc100.OrangaHang.OI;

/**
 *
 * @author Team100
 */
public class ManualTilt extends CommandBase {
    public ManualTilt() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(tower);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        tower.manualTilt((OI.manipulator.getThrottle())/5.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // runs until interrupted
        return false;//default command
    }

    // Called once after isFinished returns true
    protected void end() {
        tower.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
