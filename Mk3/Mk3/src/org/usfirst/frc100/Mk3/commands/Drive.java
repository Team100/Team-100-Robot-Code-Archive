package org.usfirst.frc100.Mk3.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc100.Mk3.OI;

public class Drive extends CommandBase {

    boolean isTurning = false; //Uses variable so that it doesn't interfere with shift button
    Preferences p = Preferences.getInstance();
    Timer t = new Timer();

    public Drive() {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //Because our robot only has 4 wheels we have to shift down whenever we turn
        
//    For ArcadeDrive:
//        if (Math.abs(OI.driverRight.getX()) > p.getDouble("ShiftWhenTurningThreshold", 0.5) && !isTurning) {
//            shifter.shiftLowGear(); //Shifts down when the driver starts to turn
//            t.reset();
//            t.start();
//            isTurning = true;
//        }
//        if (Math.abs(OI.driverRight.getX()) <= p.getDouble("ShiftWhenTurningThreshold", 0.5) && isTurning && t.get() > p.getDouble("ShiftWhenTurningDelay", 1.5)) {
//            shifter.shiftHighGear(); //Shifts back up when the driver stops turning
//            isTurning = false;
//        }

        driveTrain.tankDrive(OI.driverLeft.getY(), OI.driverRight.getY()); //Double joystick arcade drive
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //default command
    }

    // Called once after isFinished returns true
    protected void end() {
        // We don't stop the drivetrain motors here in order to smoothly
        // transition to AlignToShoot
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
