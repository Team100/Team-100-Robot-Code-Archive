//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Constantly refreshes the values on the dashboard.
 */
public class  UpdateDashboard extends Command {

    private int skip=0;
    public UpdateDashboard() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(++skip%10!=0)return;//added to fix cpu usage issues, change to only occur with preference
        Ballrus.driveTrain.updateDashboard();
        Ballrus.shooter.updateDashboard();
        Ballrus.intake.updateDashboard();
        Ballrus.tilter.updateDashboard();
        if(Preferences.displayIO){
            Ballrus.displayIO();
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
