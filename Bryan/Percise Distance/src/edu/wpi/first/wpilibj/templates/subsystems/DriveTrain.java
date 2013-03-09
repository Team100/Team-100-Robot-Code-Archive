/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.DriveWithJoysticks;



/**
 *
 * @author Bryan
 */
public class DriveTrain extends Subsystem {
    RobotDrive drive;
    Encoder leftEncoder;
    Encoder rightEncoder;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveWithJoysticks());
    }
    public DriveTrain() {
        leftEncoder = new Encoder (RobotMap.leftAEncoder,RobotMap.leftBEncoder);
        rightEncoder = new Encoder (RobotMap.rightAEncoder,RobotMap.rightBEncoder);
        drive = new RobotDrive(RobotMap.leftMotor,RobotMap.rightMotor);
    }
    public void SendToSmartDashboard(){
        leftEncoder.start();
        rightEncoder.start();
        //SmartDashboard.putDouble("Left Encoder", leftEncoder.getDistance());
        SmartDashboard.putDouble("Left Encoder Count", leftEncoder.get());
        SmartDashboard.putDouble("Right Encoder Count",rightEncoder.get());
        //setDistancePerPulse()
    }
    public void tankdrive(double left, double right) {
        drive.tankDrive(left, right);
    }
}
