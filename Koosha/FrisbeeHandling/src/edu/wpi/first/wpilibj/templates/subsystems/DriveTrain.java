/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.DriveWithJoysticks;

/**
 *
 * @author Koosha Jahani
 */
public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static Jaguar rightMotor1;
    private static Jaguar rightMotor2;
    private static Jaguar leftMotor1;
    private static Jaguar leftMotor2;
    
    private Encoder rightEncoder;
    private Encoder leftEncoder;
    private Gyro gyro;

    
    public DriveTrain()
    {
        rightMotor1 = new Jaguar(RobotMap.kRightMotor1);
        //rightMotor2 = new Jaguar(RobotMap.kRightMotor2);
        leftMotor1 = new Jaguar(RobotMap.kLeftMotor1);
        //leftMotor2 = new Jaguar(RobotMap.kLeftMotor2);
        
        rightEncoder = new Encoder(RobotMap.kRightEncoder1, RobotMap.kRightEncoder2);
        leftEncoder = new Encoder(RobotMap.kLeftEncoder1, RobotMap.kLeftEncoder2);
        gyro = new Gyro(1);
    }
    
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveWithJoysticks());
    }
    
    public void tankDrive(double leftSpeed, double rightSpeed)
    {
        leftMotor1.set(leftSpeed);
        //leftMotor2.set(leftSpeed);
        rightMotor1.set(rightSpeed);
        //rightMotor2.set(rightSpeed);
    }
    
    public double getLeftSpeed()
    {
        return leftEncoder.get();
    }
    
    public double getRightSpeed()
    {
        return rightEncoder.get();
    }
    
    public void resetGyro()
    {
        gyro.reset();
    }
    
    public double getGyro()
    {
        return gyro.getAngle();
    }
    
    public void debugDriveTrain()
    {
        SmartDashboard.putNumber("Left Value", this.getLeftSpeed());
        SmartDashboard.putNumber("Right Value", this.getRightSpeed());
        SmartDashboard.putNumber("Gyro Value", this.getGyro());
    }
}
