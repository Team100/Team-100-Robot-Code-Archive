//just needs gearshifting
package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import org.usfirst.frc100.Robot2014.Preferences;
import org.usfirst.frc100.Robot2014.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Robot2014.Robot;

/**
 * Controls the drive motors and shifters, and reads numerous sensors.
 */
public class DriveTrain extends Subsystem {

    SpeedController leftMotorMain = RobotMap.driveTrainLeftMotorMain; // positive = forward
    SpeedController leftMotorSlave = RobotMap.driveTrainLeftMotorSlave; // positive = forward
    SpeedController rightMotorMain = RobotMap.driveTrainRightMotorMain; // positive = forward
    SpeedController rightMotorSlave = RobotMap.driveTrainRightMotorSlave; // positive = forward
    RobotDrive mainDrive = RobotMap.driveTrainMainDrive;
    RobotDrive slaveDrive = RobotMap.driveTrainSlaveDrive;
    Encoder leftEncoder = RobotMap.driveTrainLeftEncoder; // positive = forward
    Encoder rightEncoder = RobotMap.driveTrainRightEncoder; // positive = forward
    Gyro gyro = RobotMap.driveTrainGyro; // positive = clockwise
    AnalogChannel rangeFinder = RobotMap.driveTrainRangeFinder; // higher = farther
    DoubleSolenoid shifter = RobotMap.driveTrainShifter; // forward = low
    AnalogChannel leftLineReader = RobotMap.driveTrainLeftLineReader; // true = line
    AnalogChannel rightLineReader = RobotMap.driveTrainRightLineReader; // true = line
    
    boolean slaveDriveEnabled = Preferences.slaveDriveDefaultEnabled;
    double distError = 0;
    double angleError = 0;
    double distOutput = 0;
    double angleOutput = 0;
    double direction = 0;
    double inches;

    // Sets the default command to Drive
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }
    
    // Adds kP to dashboard during tuning mode
    public DriveTrain(){
        if(Preferences.driveTrainTuningMode){
            SmartDashboard.putNumber("DriveStraight_kP", Preferences.driveStraight_kP);
            SmartDashboard.putNumber("AutoTurn_kP", Preferences.autoTurn_kP);
        }
    }

    // Sets the robot drives to tankdrive
    public void tankDrive(double left, double right) {
        mainDrive.tankDrive(left, right);
        if(slaveDriveEnabled){
            slaveDrive.tankDrive(left, right);
        }
        else{
            slaveDrive.stopMotor();
        }
        updateDashboard();
    }

    // Sets the robot drives to arcadedrive
    public void arcadeDrive(double speed, double turn) {
        mainDrive.arcadeDrive(speed, turn);
        if(slaveDriveEnabled){
            slaveDrive.arcadeDrive(speed, turn);
        }
        else{
            slaveDrive.stopMotor();
        }
        updateDashboard();
    }
    
    // Drives straight for a distance in inches, returns true when distance reached
    public boolean autoDriveStraight(double distance){
        // Distance output
        distError = getDistance()-distance;
        if (Math.abs(distError)>Preferences.driveDistBuffer){ // incorrect distance
            if(Preferences.driveTrainTuningMode){
                distOutput = distError*SmartDashboard.getNumber("DriveStraight_kP", 0);
            }
            else{
                distOutput = distError*Preferences.driveStraight_kP;
            }
        } else{ // correct distance
            distOutput = 0;
            if (Math.abs(angleError)<Preferences.driveAngleBuffer){
                stop();
                angleOutput=0;
                updateDashboard();
                return true;
            }
        }
        // Angle output
        angleError = direction-getAngle();
        while (angleError<0){
            angleError+=360;
        }
        angleError = (angleError+180)%360-180;
        if(Preferences.driveTrainTuningMode){
            angleOutput = angleError*SmartDashboard.getNumber("AutoTurn_kP", 0);
        }
        else{
            angleOutput = angleError*Preferences.autoTurn_kP;
        }
        // Setting motors
        arcadeDrive(distOutput, angleOutput);
        updateDashboard();
        return false;
    }
    
    // Rotates by an angle in degrees clockwise of straight, returns true when angle reached
    public boolean autoTurnByAngle(double angle){
        return autoTurnToAngle(direction+angle);
    }
    
    // Rotates to a specified angle in degrees relative to starting position, returns true when angle reached
    public boolean autoTurnToAngle(double angle){
        distOutput=distError=0;
        angleError = angle-getAngle();
        while (angleError<0){
            angleError+=360;
        }
        angleError = (angleError+180)%360-180;
        if (Math.abs(angleError)<Preferences.driveAngleBuffer){
            stop();
            angleOutput=0;
            updateDashboard();
            return true;
        }
        if(Preferences.driveTrainTuningMode){
            angleOutput = angleError*SmartDashboard.getNumber("AutoTurn_kP", 0);
        }
        else{
            angleOutput = angleError*Preferences.autoTurn_kP;
        }
        arcadeDrive(0, angleOutput);
        updateDashboard();
        return false;
    }
    
    // Returns robot angle relative to starting position
    public double getAngle(){
        return gyro.getAngle()/Preferences.driveGyroToDegreeRatio;
    }
        
    // Returns distance traveled in inches since last reset
    public double getDistance(){
        return (leftEncoder.get()+rightEncoder.get())/2/Preferences.driveEncoderToInchRatio;
    }
    
    // Resets both encoders to zero
    public void resetEncoders(){
        leftEncoder.reset();
        rightEncoder.reset();
    }
    
    // Call once before align to shoot
    public void resetRangefinder() {
        Robot.driveTrain.inches = (rangeFinder.getVoltage()/5*512/2.4);
    }
    
    // Call once before drive straight or turn by angle
    public void setDirection(){
        direction=getAngle();
    }
    
    // Stops the drive motors
    public void stop(){
        tankDrive(0,0);
    }
    
    // Puts values on dashboard if in tuning mode
    public void updateDashboard(){
        if(Preferences.driveTrainTuningMode){
            SmartDashboard.putNumber("AutoDriveDistOutput", distOutput);
            SmartDashboard.putNumber("AutoDriveAngleOutput", angleOutput);
            SmartDashboard.putNumber("AutoDriveRightEncoderValue", rightEncoder.get());
            SmartDashboard.putNumber("AutoDriveLeftEncoderValue", leftEncoder.get());
            SmartDashboard.putNumber("AutoDriveAverageEncoderValue", (leftEncoder.get()+rightEncoder.get())/2);
            SmartDashboard.putNumber("AutoDriveGyroValue", gyro.getAngle());
            SmartDashboard.putNumber("AutoDriveDistanceValue", getDistance());
            SmartDashboard.putNumber("AutoDriveAngleValue", getAngle());
            SmartDashboard.putNumber("AutoDriveDistError", distError);
            SmartDashboard.putNumber("AutoDriveAngleError", angleError);
            SmartDashboard.putNumber("RangeDistanceInches",getRangeInches() );
        }
    }
    
    //returns the inches away from where the MB1023 ultrasonic sensor is pointing
    //filters out upward spikes to the max voltage when the sensor is near its max distance
    public double getRangeInches()
    {
        double currentValue = rangeFinder.getVoltage()/5*512/2.4;
        
        if(Math.abs(inches - currentValue) < Preferences.ultraAcceptableSpike)
        {
            inches = currentValue;
            return (inches);
        }
        else
        {
            return -1;
        }
     
        
        
        
        
        
        
    }
    
}