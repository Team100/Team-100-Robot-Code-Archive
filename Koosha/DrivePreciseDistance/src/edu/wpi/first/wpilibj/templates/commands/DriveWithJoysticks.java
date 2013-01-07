/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *
 * @author Student
 */
public class DriveWithJoysticks extends CommandBase {

    public DriveWithJoysticks() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        driveTrain.Drive(oi.getYAxis(), oi.getThrottleAxis());
        SmartDashboard.putDouble("Left Drive Count: ", driveTrain.getLeftEncode());
        SmartDashboard.putDouble("Right Drive Count", driveTrain.getRightEncode());
        SmartDashboard.putDouble("Logitech X-Axis", oi.getXAxis());
        SmartDashboard.putDouble("Logitech Y-Axis", oi.getYAxis());
        SmartDashboard.putDouble("Logitech Throttle", oi.getThrottleAxis());
        SmartDashboard.putDouble("Logitech Twist", oi.getTwistAxis());
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