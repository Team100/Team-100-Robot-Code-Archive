package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Views the value of a preference on the dashboard.
 */
public class ViewPreference extends Command {
    
    public ViewPreference() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        SmartDashboard.putString("PreferenceValue", Preferences.getPref(SmartDashboard.getString("PreferenceName")));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
