package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Mk3.RobotMap;
import org.usfirst.frc100.Mk3.commands.Drive;

/**
 *
 * @author Team100
 */
public class DriveTrain extends Subsystem implements SubsystemControl {
    //Robot parts
    private final Talon rightMotor = RobotMap.driveRightMotor;
    private final Talon leftMotor = RobotMap.driveLeftMotor;
    private final Encoder rightEncoder = RobotMap.driveRightEncoder;
    private final Encoder leftEncoder = RobotMap.driveLeftEncoder;
    private final Gyro gyro = RobotMap.driveGyro;
    private final RobotDrive robotDrive=RobotMap.driveRobotDrive;
    //Constants
    private final double kUltraDistRatio = 0.009794921875;
    //Preferences defaults
    private final boolean kDefaultReverseDirection = false;//need to change this for comp bot b/c now driving "backward"
    private final double kDefaultShootLimitVoltage = 1.12;//was 1.2 for comp bot
    private final double kDefaultQuickTurnProportion = 0.011;
    private final double kDefaultQuickTurnDeadband = 0.19;
    //Other
    private boolean hasBeenAchieved = false;
    
    //starts encoders
    public DriveTrain(){
        leftEncoder.start();
        rightEncoder.start();
        gyro.reset();
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("DriveTrainShootLimitVoltage")) {
            p.putDouble("DriveTrainShootLimitVoltage", kDefaultShootLimitVoltage);
        }
        if (!p.containsKey("DriveTrainQuickTurnP")) {
            p.putDouble("DriveTrainQuickTurnP", kDefaultQuickTurnProportion);
        }
        if (!p.containsKey("DriveTrainQuickTurnDeadband")) {
            p.putDouble("DriveTrainQuickTurnDeadband", kDefaultQuickTurnDeadband);
        }
        if (!p.containsKey("DriveTrainReverseDirection")) {
            p.putBoolean("DriveTrainReverseDirection", kDefaultReverseDirection);
        }
    }//end constructor
    
    //creates a new Drive
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }//end initDefaultCommand
    
    public void tankDrive(double leftSpeed, double rightSpeed){
        Preferences p = Preferences.getInstance();
        final boolean kReverseDirection = p.getBoolean("DriveTrainReverseDirection", false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, kReverseDirection);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, kReverseDirection);
        robotDrive.tankDrive(leftSpeed, rightSpeed);
    }//end tankDrive
    
    //basic arcadeDrive: y=forward/backward speed, x=left/right speed
    public void arcadeDrive(double y, double x){
        Preferences p = Preferences.getInstance();
        final boolean kReverseDirection = p.getBoolean("DriveTrainReverseDirection", false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, kReverseDirection);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, kReverseDirection);
        robotDrive.arcadeDrive(y, x+0.05);
        SmartDashboard.putNumber("DriveTrainGyro", -gyro.getAngle());//upside down
    }// end arcadeDrive
    
    public void resetGyro() {
        gyro.reset();
    }//end resetGyro

    public double getGyro() {
        return gyro.getAngle();
    }//end getGyro
    
    public void resetRangefinder(){
        hasBeenAchieved = false;
    }//end resetRangefinder
    
    public boolean quickTurn(double setpoint) {
        // Proportional quickturn algorithm
        Preferences p = Preferences.getInstance();
        final double kP = p.getDouble("DriveTrainQuickTurnP", 0.0);
        final double kDB = p.getDouble("DriveTrainQuickTurnDeadband", 0.0);
        double angle = -getGyro(); // -driveTrain.getGyro() b/c the gyro is upsidedown
        double error = setpoint - angle;
        double twist = error * kP;
        double magnitude = Math.abs(twist);
        if(magnitude < kDB) {
            twist = kDB * (twist>0.0 ? 1.0:-1.0);
        } else if (magnitude > 1.0) {
            twist = (twist>0.0 ? 1.0:-1.0);
        }
        
        SmartDashboard.putNumber("DriveTrainGyro", angle);
        
        leftMotor.set(-twist);
        rightMotor.set(twist);
 
        // Done once we pass the setpoint
        return Math.abs(angle) >= Math.abs(setpoint);
    }//end quickTurn
    
    public void disable(){
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
    }//end enable
    
}//end DriveTrain
