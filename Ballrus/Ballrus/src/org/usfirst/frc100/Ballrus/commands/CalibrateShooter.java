//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Sets the ratio and offset preferences of the shooter
 */
public class CalibrateShooter extends Command {
    
    boolean forwardTriggered = false; // whether the forward limit has been triggered yet
    boolean isFinished = false;
    double forwardValue = 0;
    double backValue = 0;
    
    public CalibrateShooter() {
        requires(Ballrus.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Ballrus.shooter.setTrigger(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!forwardTriggered&&!Ballrus.shooter.getForwardLimit()){ // moving forward
            Ballrus.shooter.manualControl(-0.5);
        }
        else if(!forwardTriggered&&Ballrus.shooter.getForwardLimit()){ // all the way forward
            Ballrus.shooter.manualControl(0);
            forwardValue = Ballrus.shooter.getSensorValue();
            forwardTriggered = true;
        }
        else if(forwardTriggered&&!Ballrus.shooter.getBackLimit()){ // pulling backward
            Ballrus.shooter.manualControl(0.5);
        }
        else if(forwardTriggered&&Ballrus.shooter.getBackLimit()){ // all the way pulled back
            Ballrus.shooter.manualControl(0);
            backValue = Ballrus.shooter.getSensorValue();
            Preferences.shooterPotZeroPosition = forwardValue;
            Preferences.shooterPotToInchRatio = (backValue - forwardValue)/Preferences.shooterFullRange;
            System.out.println("ShooterPotZeroPosition = " + Preferences.shooterPotZeroPosition);
            System.out.println("ShooterPotToInchRatio = " + Preferences.shooterPotToInchRatio);
            isFinished = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished||timeSinceInitialized()>10;
    }

    // Called once after isFinished returns true
    protected void end() {
        Ballrus.shooter.setTrigger(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}