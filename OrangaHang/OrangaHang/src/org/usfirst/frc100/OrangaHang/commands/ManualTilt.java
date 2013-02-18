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
    double position;
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
        position=tower.getPotentiometer();
        if (Math.abs(OI.manipulator.getThrottle())>.5){
            tower.setSetpoint(position+(OI.manipulator.getThrottle()/100));
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
