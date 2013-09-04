/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;
import org.usfirst.frc100.Mk3.OI;

/**
 *
 * @author Paul
 */
public class Drive extends CommandBase {
    boolean inLowGear=false;//uses variable so that it doesn't interfere with shift button
    Preferences p=Preferences.getInstance();
    
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
        if(Math.abs(OI.driverRight.getX())>p.getDouble("ShiftWhenTurningThreshold", 0.5) &&!inLowGear){
            shifter.shiftLowGear();
            inLowGear=true;
        }
        if(Math.abs(OI.driverRight.getX())<=p.getDouble("ShiftWhenTurningThreshold", 0.5)&&inLowGear){
            shifter.shiftHighGear();
            inLowGear=false;
        }
        driveTrain.arcadeDrive(OI.driverLeft.getY(), OI.driverRight.getX());//put inverted for both back in for comp bot
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
