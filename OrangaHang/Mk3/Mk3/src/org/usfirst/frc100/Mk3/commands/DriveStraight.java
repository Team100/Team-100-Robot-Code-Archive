package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;

/**
 * Will drive straight forward however many feet is listed under "AutoDist_0".
 */
public class DriveStraight extends CommandBase {

    double setPoint = 0;

    public DriveStraight(double d) {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
        setPoint = d;
    }
    
    public DriveStraight() {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
        setPoint = Preferences.getInstance().getDouble("AutoDist_0", 0.0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //driveTrain.enable();//temporary
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (driveTrain.driveStraight(setPoint)) {
            System.out.println("Distance reached");
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        driveTrain.arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
