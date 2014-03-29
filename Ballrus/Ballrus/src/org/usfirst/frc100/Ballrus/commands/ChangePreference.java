package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Changes the value of a preference from the dashboard.
 */
public class ChangePreference extends Command {
    
    public ChangePreference() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Preferences.changePreferenceInFile(SmartDashboard.getString("PreferenceName"), SmartDashboard.getString("PreferenceValue"));
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
