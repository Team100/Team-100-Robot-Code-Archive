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

    SpeedController leftMotorMain = RobotMap.driveTrainLeftMotorMain;
    SpeedController leftMotorSlave = RobotMap.driveTrainLeftMotorSlave;
    SpeedController rightMotorMain = RobotMap.driveTrainRightMotorMain;
    SpeedController rightMotorSlave = RobotMap.driveTrainRightMotorSlave;
    RobotDrive mainDrive = RobotMap.driveTrainMainDrive;
    RobotDrive slaveDrive = RobotMap.driveTrainSlaveDrive;
    Encoder leftEncoder = RobotMap.driveTrainLeftEncoder;
    Encoder rightEncoder = RobotMap.driveTrainRightEncoder;
    Gyro gyro = RobotMap.driveTrainGyro;
    AnalogChannel rangeFinder = RobotMap.driveTrainRangeFinder;
    DoubleSolenoid shifter = RobotMap.driveTrainShifter;
    AnalogChannel leftLineReader = RobotMap.driveTrainLeftLineReader;
    AnalogChannel rightLineReader = RobotMap.driveTrainRightLineReader;

    // Sets the default command to Drive
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

    // Sets the robot drives to tankdrive
    public void tankDrive(double left, double right) {
        mainDrive.tankDrive(left, right);
        slaveDrive.tankDrive(left, right);
    }

    // Sets the robot drives to arcadedrive
    public void arcadeDrive(double speed, double turn) {
        mainDrive.arcadeDrive(speed, turn);
        slaveDrive.arcadeDrive(speed, turn);
    }
}
