package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
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
    //circumference/ticks
    private final double kRightDistRatio = ((4.0/12.0*3.14159))/1440;
    private final double kLeftDistRatio = ((4.0/12.0*3.14159))/1440;
    private final double ultraDistRatio = 0.009794921875;
    
    //starts encoders
    public DriveTrain(){
        leftEncoder.start();
        rightEncoder.start();
        gyro.reset();
    }//end constructor
    
    //removes safeties from the robotDrive
    public void unSafe(){
        robotDrive.setSafetyEnabled(false);
    }//end unSafe
    
    //puts safeties on the robotDrive
    public void safe(){
        robotDrive.setSafetyEnabled(true);
    }//end safe
    
    //creates a new Drive
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        //setDefaultCommand(new Drive());
    }//end initDefaultCommand
    
    //basic tankDrive
    public void tankDrive(double leftSpeed, double rightSpeed){
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }//end tankDrive
    
    //basic arcadeDrive: y=forward/backward speed, x=left/right speed
    public void arcadeDrive(double y, double x){
        robotDrive.arcadeDrive(y, x);
        SmartDashboard.putNumber("LEFT ENCODER", leftEncoder.get());
        SmartDashboard.putNumber("RIGHT ENCODER", rightEncoder.get());
        SmartDashboard.putNumber("GYRO", gyro.getAngle());
    }// end arcadeDrive
    
    //toggles gear
//    public void shiftGear(){
//        //high=forward
//        if(shifter.get().equals(DoubleSolenoid.Value.kForward)){
//            shifter.set(DoubleSolenoid.Value.kReverse);
//        }
//        else{
//            shifter.set(DoubleSolenoid.Value.kForward);
//        }
//    }//end shiftGear
    
    public void shiftLowGear(){
        shifter.set(DoubleSolenoid.Value.kReverse);
    }
    //shifts to high gear, regardless of current gear
    public void shiftHighGear(){
        shifter.set(DoubleSolenoid.Value.kForward);
    }//end shiftHighGear
    
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
