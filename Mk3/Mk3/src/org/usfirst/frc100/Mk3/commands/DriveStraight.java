/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;

/**
 *
 * @author Student
 */
public class DriveStraight extends CommandBase {
    boolean isFinished=false;
    public DriveStraight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        driveTrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (driveTrain.driveStraight(Preferences.getInstance().getDouble("AutoDist_0", 0.0))) {
            isFinished=true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
