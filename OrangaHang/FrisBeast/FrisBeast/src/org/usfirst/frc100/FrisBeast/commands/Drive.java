/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.FrisBeast.commands;

import org.usfirst.frc100.FrisBeast.OI;

/**
 *
 * @author Paul
 */
public class Drive extends CommandBase {
    
    public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //NOTE: change AlignToShoot if you change this
        driveTrain.tankDrive(OI.driverRight.getY(), OI.driverLeft.getY());//put inverted for both back in for comp bot
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;//default command
    }

    // Called once after isFinished returns true
    protected void end() {
        // We don't stop the drivetrain motors here in order to smoothly
        // transition to AlignToShoot
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
