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
    private Jaguar rightMotor1;
    private Jaguar rightMotor2;
    private Jaguar leftMotor1;
    private Jaguar leftMotor2;
    
    private Encoder rightEncoder;
    private Encoder leftEncoder;
    private Gyro gyro;
    
    public PIDController turnController;

    private PIDSource s_Gyro = new PIDSource()
    {
        public double pidGet()
        {
            return returnPIDInput();
        }
    };
    
    private PIDOutput output = new PIDOutput()
    {
        public void pidWrite(double output)
        {
            usePIDOutput(output);
        }
    };
    
    private double kP = (1/180);
    private double kI = 0.0;
    private double kD = 0.0;

    
    public DriveTrain()
    {
        rightMotor1 = new Jaguar(RobotMap.kRightMotor1);
        //rightMotor2 = new Jaguar(RobotMap.kRightMotor2);
        leftMotor1 = new Jaguar(RobotMap.kLeftMotor1);
        //leftMotor2 = new Jaguar(RobotMap.kLeftMotor2);
        
        rightEncoder = new Encoder(RobotMap.kRightEncoder1, RobotMap.kRightEncoder2);
        leftEncoder = new Encoder(RobotMap.kLeftEncoder1, RobotMap.kLeftEncoder2);
        gyro = new Gyro(1);
        
        turnController = new PIDController(kP, kI, kD, s_Gyro, output);
        
        SmartDashboard.putNumber("P constant", kP);
        SmartDashboard.putNumber("I constant", kI);
        SmartDashboard.putNumber("D constant", kD);
    }
    
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveWithJoysticks());
    }
    
    public void setLeftDrive(double d)
    {
        leftMotor1.set(d);
        //leftMotor2.set(d);
    }
    
    public void setRightDrive(double d)
    {
        rightMotor1.set(d);
        //rightMotor2.set(d);
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
    
    public double get_kP()
    {
        return kP;
    }
    
    public double get_kI()
    {
        return kI;
    }
    
    public double get_kD()
    {
        return kD;
    }
    
    public void updatePID()
    {
        kP = SmartDashboard.getNumber("P constant");
        kI = SmartDashboard.getNumber("I constant");
        kD = SmartDashboard.getNumber("D constant");
    }
    
    protected double returnPIDInput()
    {
        return gyro.getAngle();
    }
    
    protected void usePIDOutput(double output)
    {
        tankDrive(output, (-1)*output);
    }
    
    public void debugDriveTrain()
    {
        SmartDashboard.putNumber("Left Value", this.getLeftSpeed());
        SmartDashboard.putNumber("Right Value", this.getRightSpeed());
        SmartDashboard.putNumber("Gyro Value", this.getGyro());
    }
}
