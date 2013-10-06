package org.usfirst.frc100.Mk3.commands;

import org.usfirst.frc100.Mk3.OI;

public class ManualTilt extends CommandBase {

    public ManualTilt() {
        // Use requires() here to declare subsystem dependencies
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!OI.overideIntakeButton.get()){
            intake.tiltToPosition(OI.manipulator.getY());
        }
        else{
            intake.manualTilt(OI.manipulator.getY());
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
