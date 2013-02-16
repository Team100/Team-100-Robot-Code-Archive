package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.RobotDrive;
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
    //Robot parts
    private final SpeedController rightMotor = RobotMap.driveRightMotor;
    private final SpeedController leftMotor = RobotMap.driveLeftMotor;
    private final Encoder rightEncoder = RobotMap.driveRightEncoder;
    private final Encoder leftEncoder = RobotMap.driveLeftEncoder;
    private final Gyro gyro = RobotMap.driveGyro;
    private final AnalogChannel ultraDist = RobotMap.driveUltrasonic;
    private final DoubleSolenoid shifter = RobotMap.driveGear;
    private final RobotDrive robotDrive=new RobotDrive(leftMotor, rightMotor);//add to robotMap?
    //Constants
    private final double kRightDistRatio = 1440 / ((4.0/12.0*3.14159));
    private final double kLeftDistRatio = 1440 / ((4.0/12.0*3.14159));
    private final double ultraDistRatio = 0.009794921875;
    //encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet  
    private boolean highGear = shifter.equals(DoubleSolenoid.Value.kForward);
    private boolean lowGear = shifter.equals(DoubleSolenoid.Value.kReverse);
    
    public DriveTrain(){
        leftEncoder.start();
        rightEncoder.start();
    }
    
    //creates a new Drive
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Drive());
    }//end initDefaultCommand
    
    //basic tankDrive
    public void tankDrive(double leftSpeed, double rightSpeed){
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }//end tankDrive
    
    //basic arcadeDrive: y=forward/backward speed, x=left/right speed
    public void arcadeDrive(double y, double x){
        robotDrive.arcadeDrive(y, x);
    }// end arcadeDrive

    public void shiftHighGear()
    {
        if(!isHighGear())
        {
            //shifts gears to opposite position
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
        return highGear;
    }
    
    //aligns robot for shooting
    public void alignToShoot(double left, double right){
        
        if(ultraDist.getVoltage() < 1.2) {
            if(left > 0) {
                leftMotor.set(0);
                rightMotor.set(0);
            } else {
                arcadeDrive(left, right);
            }
        } else {
            arcadeDrive(left, right);
        }
        
//        if(Math.abs(gyro.getAngle()) > 2) {
//            leftMotor.set(gyro.getAngle() / 20);
//            rightMotor.set(gyro.getAngle() / -20);
//        } else {
//            if(ultraDist.getVoltage() > 1.0) {
//                leftMotor.set(1.0);
//                rightMotor.set(-1.0);
//            } else {
//                leftMotor.set(0);
//                rightMotor.set(0);
//            }
//        }
        
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
