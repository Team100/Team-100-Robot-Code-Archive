package org.usfirst.frc100.Ballrus.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Drives forward at half speed for a specified time.
 */
public class DeadReckoningDrive extends Command {
    
    Timer driveTime = new Timer();
    boolean isFinished = false;
    double time;
    
    /**
     * 
     * @param time Time in milliseconds
     */
    public DeadReckoningDrive(double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Ballrus.driveTrain);
        this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        driveTime.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(driveTime.get() < time) {
            Ballrus.driveTrain.tankDrive(-0.5, -0.5);
        } else {
            driveTime.stop();

    // Called once after isFinished returns true
            Ballrus.driveTrain.stop();
            isFinished = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
