package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.CommandBase;
import org.usfirst.frc100.OrangaHang.commands.Drive;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.PositionSendablePID;

/**
 *
 * @author Team100
 */
public class DriveTrain extends Subsystem {
    
    private final SpeedController rightMotor = RobotMap.driveRightMotor;
    private final SpeedController leftMotor = RobotMap.driveLeftMotor;
    private final Encoder rightEncoder = RobotMap.driveRightEncoder;
    private final Encoder leftEncoder = RobotMap.driveLeftEncoder;
    private final Gyro gyro = RobotMap.driveGyro;
    private final AnalogChannel ultraDist = RobotMap.driveUltrasonic;
    private final DoubleSolenoid shifter = RobotMap.driveGear;
    //Constants
    private final double kRightDistRatio = 1000 / ((18.0/30.0)*(7.5/12.0*Math.PI));
    private final double kLeftDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*Math.PI));
    //encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet
    private final double kLeftRightRatio = 1000 / 1440;
    private double setpoint;    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Drive());
    }//end initDefaultCommand
    
    public void setLeftMotor(double s)
    {
        leftMotor.set(s);
    }
    
    public void setRightMotor(double s)
    {
        rightMotor.set(s);
    }
    
    public void tankDrive(double leftSpeed, double rightSpeed){
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }//end tankDrive
    
    public void shiftHighGear()
    {
        if(!isHighGear())
        {
            shifter.set(DoubleSolenoid.Value.kForward);
        }
    }
    
    public void shiftLowGear()
    {
        if(isHighGear())
        {
            shifter.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public boolean isHighGear()
    {
        return shifter.equals(DoubleSolenoid.Value.kForward);
    }
    
    public void resetGyro()
    {
        gyro.reset();
    }
    
    public double getGyro()
    {
        return gyro.getAngle();
    }
    
    public void alignToShoot(){
        
    }//end alignToShoot
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //PID Control
    //driveRight
    PIDSource sourceRight = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderRight_raw", rightEncoder.getRaw());
            return rightEncoder.getRaw();
        }
    }; //end anonym class PIDSource
    PIDOutput outputRight = new PIDOutput(){
        public void pidWrite(double output){
            rightMotor.set(output);
        }
    }; //end anonym class PIDOutput
    private PositionSendablePID pidRight = new PositionSendablePID("right",sourceRight, outputRight, kRightDistRatio);
    
    //driveLeft
    PIDSource sourceLeft = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderLeft_raw", leftEncoder.get());
            return leftEncoder.get();
        }
    }; //end anonym class PIDSource
    PIDOutput outputLeft = new PIDOutput(){
        public void pidWrite(double output){
             leftMotor.set(output);
        }
    }; //end anonym class PIDOutput
    private PositionSendablePID pidLeft = new PositionSendablePID("left",sourceLeft, outputLeft, kLeftDistRatio);
    
    // drive turn
    PIDSource sourceGyro = new PIDSource()
    {
        public double pidGet()
        {
            return gyro.getAngle();
        }
    };
    PIDOutput outputTurn = new PIDOutput()
    {
        public void pidWrite(double d)
        {
            rightMotor.set(+d);
            leftMotor.set(-d * kLeftRightRatio);
        }
    };
    private PositionSendablePID pidTurn = new PositionSendablePID("turn", sourceGyro, outputTurn, kRightDistRatio);
    
    public void setSetpoint(double setpoint)
    {
        this.setpoint = setpoint;
        pidRight.setSetpoint(setpoint);
        pidLeft.setSetpoint(setpoint);
        pidTurn.setSetpoint(setpoint);
    }//end setSetpoint
    
    public double getSetpoint()
    {
        return setpoint;
    }
    
    public void disable(){
        setSetpoint(0.0);
        
        pidRight.disable();
        rightEncoder.reset();
        
        pidLeft.disable();
        leftEncoder.reset();
        
        pidTurn.disable();
        gyro.reset();
    }//end disable
    
    public void enable()
    {
        pidRight.enable();
        rightEncoder.reset();
        
        pidLeft.enable();
        leftEncoder.reset();
        
        pidTurn.enable();
        gyro.reset();
    }//end enable

}//end DriveTrain
