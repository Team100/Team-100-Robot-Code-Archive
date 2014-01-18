package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import org.usfirst.frc100.Robot2014.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

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
    
    boolean slaveDriveEnabled = org.usfirst.frc100.Robot2014.Preferences.slaveDriveDefaultEnabled;

    // Sets the default command to Drive
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
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
    }
}