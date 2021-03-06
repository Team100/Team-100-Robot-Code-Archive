/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

/**
 *
 * @author Team100
 */
public class ShiftGears extends CommandBase {
    
    public ShiftGears() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        //requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        driveTrain.shiftLowGear();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        driveTrain.shiftHighGear();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        driveTrain.shiftHighGear();
    }
}
