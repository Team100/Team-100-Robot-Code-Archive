package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.Drive;

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
    private final double kRightDistRatio = ((4.0/12.0*Math.PI))/1440;
    private final double kLeftDistRatio = ((4.0/12.0*Math.PI))/1440;
    private final double kUltraDistRatio = 0.009794921875;
    //Preferences defaults
    private final boolean kDefaultReverseDirection = false;
    private final double kDefaultShootLimitVoltage = 1.12;//was 1.2 for comp bot
    private final double kDefaultQuickTurnProportion = 0.011;
    private final double kDefaultQuickTurnDeadband = 0.19;
    //Tuneables
    private double setpoint;
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
        leftMotor.set(leftSpeed);
        rightMotor.set(rightSpeed);
        SmartDashboard.putNumber("DriveTrainGyro", -gyro.getAngle());//upside down
    }//end tankDrive
    
    //basic arcadeDrive: y=forward/backward speed, x=left/right speed
    public void arcadeDrive(double y, double x){
        Preferences p = Preferences.getInstance();
        final boolean kReverseDirection = p.getBoolean("DriveTrainReverseDirection", false);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, kReverseDirection);
        robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, kReverseDirection);
        robotDrive.arcadeDrive(y, x);
        SmartDashboard.putNumber("DriveTrainGyro", -gyro.getAngle());//upside down
    }// end arcadeDrive
    
    public void resetGyro() {
        gyro.reset();
    }//end resetGyro

    public double getGyro() {
        return gyro.getAngle();
    }//end getGyro
    
    //aligns robot for shooting
    public void alignToShoot(double left, double right) {
        Preferences p = Preferences.getInstance();
        final double kShootLimitVoltage = p.getDouble("DriveTrainShootLimitVoltage", 0.0);
        double maxVoltage =  ultraDist.getVoltage();
        for (int i=0; i<10; i++){
            double voltage = ultraDist.getVoltage();
            if (voltage>maxVoltage){
                maxVoltage = voltage;
            }
        }
        SmartDashboard.putNumber("RangefinderVoltage", maxVoltage);//want to see on competition dashboard!!
        System.out.println("RangefinderVoltage: " + maxVoltage + " kShootLimitVoltage: "  + kShootLimitVoltage);
        if (maxVoltage <= kShootLimitVoltage ) {
            hasBeenAchieved = true;
        }
        if (left <= 0.0 && hasBeenAchieved){
            left = 0.0;
        }
        arcadeDrive(left, right);
    }//end alignToShoot
    
    public void resetRangefinder(){
        hasBeenAchieved = false;
    }
    
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
    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //FIXME: remove PID code if we don't use it
    //PID Control
    //driveRight
    PIDSource sourceRight = new PIDSource(){
        public double pidGet(){
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
    
    public double getSetpoint() {
        return this.setpoint;
    }//end getSetpoint
    
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

    public void writePreferences() {
        
    }//end writePreferences
    
}//end DriveTrain
