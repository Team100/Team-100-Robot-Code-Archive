//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Gives manipulator left joystick control of tilter and manipulator right 
 * joystick control of shooter. Command gets toggled.
 */
public class FullManualControl extends Command {

    public FullManualControl() {
        requires(Ballrus.shooter);
        requires(Ballrus.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//        SmartDashboard.putBoolean("ManualControl", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(Ballrus.oi.manualShootButton.get()){
            Ballrus.shooter.setTrigger(true);
        }
        else{
            Ballrus.shooter.setTrigger(false);
        }
        if(!(Math.abs(Ballrus.oi.getManipulator().getY()) < Preferences.driveJoystickDeadband)) {
            Ballrus.tilter.manualControl(Ballrus.oi.getManipulator().getY());
        }
        else {
            Ballrus.tilter.manualControl(0.0);
        }
        if(!(Math.abs(Ballrus.oi.getManipulator().getThrottle()) < Preferences.driveJoystickDeadband)) {
            Ballrus.shooter.manualControl(Ballrus.oi.getManipulator().getThrottle());
        }
        else {
            Ballrus.shooter.manualControl(0.0);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.tilter.manualControl(0);
        Ballrus.shooter.manualControl(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
