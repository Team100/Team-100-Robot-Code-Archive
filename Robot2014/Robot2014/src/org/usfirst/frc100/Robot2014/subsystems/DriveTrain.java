package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import org.usfirst.frc100.Robot2014.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the drive motors and shifters, and reads numerous sensors.
 */
public class DriveTrain extends Subsystem {

    SpeedController leftMotorMain = RobotMap.driveTrainDriveLeftMotorMain;
    SpeedController leftMotorSlave = RobotMap.driveTrainDriveLeftMotorSlave;
    SpeedController rightMotorMain = RobotMap.driveTrainDriveRightMotorMain;
    SpeedController rightMotorSlave = RobotMap.driveTrainDriveRightMotorSlave;
    RobotDrive mainDrive = RobotMap.driveTrainMainDrive;
    RobotDrive slaveDrive = RobotMap.driveTrainSlaveDrive;
    Encoder leftEncoder = RobotMap.driveTrainDriveLeftEncoder;
    Encoder rightEncoder = RobotMap.driveTrainDriveRightEncoder;
    Gyro gyro = RobotMap.driveTrainGyro;
    AnalogChannel rangeFinder = RobotMap.driveTrainRangeFinder;
    Relay shifter = RobotMap.driveTrainShifter;
    AnalogChannel leftLineReader = RobotMap.driveTrainLeftLineReader;
    AnalogChannel rightLineReader = RobotMap.driveTrainRightLineReader;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }
    
    public void tankDrive(double left, double right){
        mainDrive.tankDrive(left, right);
        slaveDrive.tankDrive(left, right);
    }
    
    public void arcadeDrive(double speed, double turn){
        mainDrive.arcadeDrive(speed, turn);
        slaveDrive.arcadeDrive(speed, turn);
    }
}
