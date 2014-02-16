//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Ballrus;

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
        SmartDashboard.putBoolean("ManualControl", true);
        if(Ballrus.oi.manualShootButton.get()){
            Ballrus.shooter.setTrigger(true);
        }
        else{
            Ballrus.shooter.setTrigger(false);
        }
        Ballrus.tilter.manualControl(Ballrus.oi.getManipulator().getY());
        Ballrus.shooter.manualControl(Ballrus.oi.getManipulator().getThrottle());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.tilter.manualControl(0);
        Ballrus.shooter.manualControl(0);
        SmartDashboard.putBoolean("ManualControl", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
