package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
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
    private final double kRightDistRatio = 1000 / ((18.0/30.0)*(7.5/12.0*3.14159));
    private final double kLeftDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));
    //encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet  
    private boolean highGear = true;
    private boolean lowGear = false;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Drive());
    }//end initDefaultCommand
    
    public void tankDrive(double leftSpeed, double rightSpeed){
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }//end tankDrive
    
    public void shift(){
        if(highGear){
            lowGear = true;
            highGear = false;
            shifter.set(DoubleSolenoid.Value.kReverse);
        } else if (lowGear){
            highGear = true;
            lowGear = false;
            shifter.set(DoubleSolenoid.Value.kForward);
        }
    }//end shift
    
    public boolean isHighGear()
    {
        return highGear;
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
    
    public void setSetpoint(double setpoint){
        pidRight.setSetpoint(setpoint);
        pidLeft.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void disable(){
        setSetpoint(0.0);
        pidRight.disable();
        pidLeft.disable();
        rightEncoder.reset();
        leftEncoder.reset();
    }//end disable
    
    public void enable(){
        rightEncoder.reset();
        leftEncoder.reset();
        rightEncoder.start();
        leftEncoder.start();
        pidRight.enable();
        pidLeft.enable();
    }//end enable
    
}//end DriveTrain
