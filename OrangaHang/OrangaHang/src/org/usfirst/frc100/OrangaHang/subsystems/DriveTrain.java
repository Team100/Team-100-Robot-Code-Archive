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
public class DriveTrain extends Subsystem implements SubsystemControl {
    //Robot parts
    private final SpeedController rightMotor = RobotMap.driveRightMotor;
    private final SpeedController leftMotor = RobotMap.driveLeftMotor;
    private final Encoder rightEncoder = RobotMap.driveRightEncoder;
    private final Encoder leftEncoder = RobotMap.driveLeftEncoder;
    private final Gyro gyro = RobotMap.driveGyro;
    private final AnalogChannel ultraDist = RobotMap.driveUltrasonic;
    private final RobotDrive robotDrive=RobotMap.driveRobotDrive;
    //Constants
    //circumference/ticks
    private final double kRightDistRatio = ((4.0/12.0*Math.PI))/1440;
    private final double kLeftDistRatio = ((4.0/12.0*Math.PI))/1440;
    private final double ultraDistRatio = 0.009794921875;
    private double setpoint;
    
    //starts encoders
    public DriveTrain(){
        leftEncoder.start();
        rightEncoder.start();
        gyro.reset();
        // FIXME: put into preferences
        SmartDashboard.putNumber("DriveTrainQuickTurnP", 0.011);
        SmartDashboard.putNumber("DriveTrainQuickTurnDeadband", 0.19);
    }//end constructor
    
    //creates a new Drive
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Drive());
    }//end initDefaultCommand
    
    public void tankDrive(double leftSpeed, double rightSpeed){
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
    }//end tankDrive
    
    //basic arcadeDrive: y=forward/backward speed, x=left/right speed
    public void arcadeDrive(double y, double x){
        robotDrive.arcadeDrive(y, x);
        SmartDashboard.putNumber("DriveTrainEncoderL", leftEncoder.get());
        SmartDashboard.putNumber("DriveTrainEncoderR", rightEncoder.get());
        SmartDashboard.putNumber("DriveTrainGyro", -gyro.getAngle());//upside down
    }// end arcadeDrive
    
    public void resetGyro()
    {
        gyro.reset();
    }

    public double getGyro()
    {
        return gyro.getAngle();
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
        
    }//end alignToShoot
    
    public boolean quickTurn(double setpoint) {
        // Proportional quickturn algorithm
        double angle = -getGyro(); // -driveTrain.getGyro() b/c the gyro is upsidedown
        double error = setpoint - angle;
        double kP = SmartDashboard.getNumber("DriveTrainQuickTurnP", 0.011);
        double kDB = SmartDashboard.getNumber("DriveTrainQuickTurnDeadband", 0.19);
        double twist = error * kP;
        double magnitude = Math.abs(twist);
        if(magnitude < kDB) {
            twist = kDB * (twist>0.0 ? 1.0:-1.0);
        } else if (magnitude > 1.0) {
            twist = (twist>0.0 ? 1.0:-1.0);
        }
        
        SmartDashboard.putNumber("DriveTrainQuickTurnTwist", twist);
        SmartDashboard.putNumber("DriveTrainQuickTurnError", error);
        SmartDashboard.putNumber("DriveTrainEncoderL", leftEncoder.get());
        SmartDashboard.putNumber("DriveTrainEncoderR", rightEncoder.get());
        SmartDashboard.putNumber("DriveTrainGyro", angle);
        
        leftMotor.set(-twist);
        rightMotor.set(twist);
 
        // Done once we pass the setpoint
        return Math.abs(angle) >= Math.abs(setpoint);
    }
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIXME: remove PID code if we don't use it
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
//    private PositionSendablePID pidRight = new PositionSendablePID("right",sourceRight, outputRight, kRightDistRatio);
    
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
//    private PositionSendablePID pidLeft = new PositionSendablePID("left",sourceLeft, outputLeft, kLeftDistRatio);
    
    public void setSetpoint(double setpoint){
        this.setpoint = setpoint;
//        pidRight.setSetpoint(setpoint);
//        pidLeft.setSetpoint(setpoint);
    }//end setSetpoint
    
    public double getSetpoint()
    {
        return this.setpoint;
    }
    
    public void disable(){
        setSetpoint(0.0);
 //       pidRight.disable();
 //       pidLeft.disable();
        leftMotor.set(0.0);
        rightMotor.set(0.0);
        rightEncoder.reset();
        leftEncoder.reset();
    }//end disable
    
    public void enable(){
        rightEncoder.reset();
        leftEncoder.reset();
        rightEncoder.start();
        leftEncoder.start();
 //       pidRight.enable();
 //       pidLeft.enable();
    }//end enable
    public void resetValues()
    {
//        pidRight.getValues(); // Extremly misleading name; doesn't return anything. Resets all constants
//        pidLeft.getValues();
        //pidTurn.getValues();
    }

    public void writePreferences() {
    }
    
}//end DriveTrain
