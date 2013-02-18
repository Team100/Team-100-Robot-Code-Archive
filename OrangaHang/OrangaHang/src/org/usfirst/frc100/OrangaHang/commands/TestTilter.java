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
public class TestTilter extends CommandBase {
    public TestTilter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(tower);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        tower.disable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        tower.testTilter(OI.manipulator.getThrottle()/3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // runs until interrupted
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