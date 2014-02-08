//ready
package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives a distance obtained from SmartDashboard by calling AutoDriveStraight,
 * or turns to an angle obtained from SmartDashboard by calling AutoTurn
 */
public class AutoDriveTest extends Command {

    private boolean distance;
    
    public AutoDriveTest(boolean distance) {
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
        if(distance){
            new AutoDriveStraight(SmartDashboard.getNumber("AutoDriveTestDistance", 0)).start();
        }
        else{
            new AutoTurn(SmartDashboard.getNumber("AutoDriveTestAngle", 0)).start();
        }
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}