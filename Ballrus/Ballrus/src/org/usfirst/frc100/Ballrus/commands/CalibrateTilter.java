//incomplete
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Sets the ratio and offset preferences of the shooter
 */
public class CalibrateTilter extends Command {
    
    boolean forwardTriggered = false; // whether the forward limit has been triggered yet
    boolean isFinished = false;
    double topValue = 0;
    double bottomValue = 0;
    
    public CalibrateTilter() {
        requires(Ballrus.tilter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!forwardTriggered&&!Ballrus.tilter.getTopLimit()){ // moving up
            Ballrus.tilter.manualControl(0.3);
        }
        else if(!forwardTriggered&&Ballrus.tilter.getTopLimit()){ // all the way up
            Ballrus.tilter.manualControl(0);
            topValue = Ballrus.tilter.getSensorValue();
            forwardTriggered = true;
        }
        else if(forwardTriggered&&!Ballrus.tilter.getBottomLimit()){ // pulling down
            Ballrus.tilter.manualControl(-0.3);
        }
        else if(forwardTriggered&&Ballrus.tilter.getBottomLimit()){ // all the way down
            Ballrus.tilter.manualControl(0);
            bottomValue = Ballrus.tilter.getSensorValue();
            Preferences.tilterPotToDegreeRatio = (bottomValue - topValue)/(Preferences.tilterMinAngle-Preferences.tilterMaxAngle);
            Preferences.tilterPot180DegreePosition = ((bottomValue/Preferences.tilterPotToDegreeRatio)+(180-Preferences.tilterMinAngle))*Preferences.tilterPotToDegreeRatio;
            System.out.println("TilterPot180DegreePosition = " + Preferences.tilterPot180DegreePosition);
            System.out.println("TilterPotToDegreeRatio = " + Preferences.tilterPotToDegreeRatio);
            isFinished = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished||timeSinceInitialized()>10;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}