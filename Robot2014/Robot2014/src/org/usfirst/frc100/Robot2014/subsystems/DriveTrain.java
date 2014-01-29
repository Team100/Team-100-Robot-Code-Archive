//just needs rangefinding
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
    double lastRangeFinderValue = 0;

    // Sets the default command to Drive
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

    // Adds kP to dashboard during tuning mode
    public DriveTrain() {
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putNumber("DriveStraight_kP", Preferences.driveStraight_kP);
            SmartDashboard.putNumber("AutoTurn_kP", Preferences.autoTurn_kP);
        }
    }

    // Sets the robot drives to tankdrive
    public void tankDrive(double left, double right) {
        mainDrive.tankDrive(left, right);
        if (slaveDriveEnabled) {
            slaveDrive.tankDrive(left, right);
        } else {
            slaveDrive.stopMotor();
        }
        updateDashboard();
    }

    // Sets the robot drives to arcadedrive
    public void arcadeDrive(double speed, double turn) {
        mainDrive.arcadeDrive(speed, turn);
        if (slaveDriveEnabled) {
            slaveDrive.arcadeDrive(speed, turn);
        } else {
            slaveDrive.stopMotor();
        }
        updateDashboard();
    }

    // Drives straight for a distance in inches, returns true when distance reached
    public boolean autoDriveStraight(double distance) {
        // Distance output
        distError = getEncoderInches() - distance;
        if (Math.abs(distError) > Preferences.driveDistBuffer) { // incorrect distance
            if (Preferences.driveTrainTuningMode) {
                distOutput = distError * SmartDashboard.getNumber("DriveStraight_kP", 0);
            } else {
                distOutput = distError * Preferences.driveStraight_kP;
            }
        } else { // correct distance
            distOutput = 0;
            if (Math.abs(angleError) < Preferences.driveAngleBuffer) {
                stop();
                angleOutput = 0;
                updateDashboard();
                return true;
            }
        }
        // Angle output
        angleError = direction - getGyroDegrees();
        while (angleError < 0) {
            angleError += 360;
        }
        angleError = (angleError + 180) % 360 - 180;
        if (Preferences.driveTrainTuningMode) {
            angleOutput = angleError * SmartDashboard.getNumber("AutoTurn_kP", 0);
        } else {
            angleOutput = angleError * Preferences.autoTurn_kP;
        }
        // Setting motors
        arcadeDrive(distOutput, angleOutput);
        updateDashboard();
        return false;
    }
    
    // Drives straight forever at a given speed
    public void driveStraight(double speed) {
        // Angle output
        angleError = direction - getGyroDegrees();
        while (angleError < 0) {
            angleError += 360;
        }
        angleError = (angleError + 180) % 360 - 180;
        if (Preferences.driveTrainTuningMode) {
            angleOutput = angleError * SmartDashboard.getNumber("AutoTurn_kP", 0);
        } else {
            angleOutput = angleError * Preferences.autoTurn_kP;
        }
        // Setting motors
        arcadeDrive(speed, angleOutput);
        updateDashboard();
    }

    // Rotates by an angle in degrees clockwise of straight, returns true when angle reached
    public boolean autoTurnByAngle(double angle) {
        return autoTurnToAngle(direction + angle);
    }

    // Rotates to a specified angle in degrees relative to starting position, returns true when angle reached
    public boolean autoTurnToAngle(double angle) {
        distOutput = distError = 0;
        angleError = angle - getGyroDegrees();
        while (angleError < 0) {
            angleError += 360;
        }
        angleError = (angleError + 180) % 360 - 180;
        if (Math.abs(angleError) < Preferences.driveAngleBuffer) {
            stop();
            angleOutput = 0;
            updateDashboard();
            return true;
        }
        if (Preferences.driveTrainTuningMode) {
            angleOutput = angleError * SmartDashboard.getNumber("AutoTurn_kP", 0);
        } else {
            angleOutput = angleError * Preferences.autoTurn_kP;
        }
        arcadeDrive(0, angleOutput);
        updateDashboard();
        return false;
    }

    // Returns robot angle relative to starting position
    public double getGyroDegrees() {
        return gyro.getAngle() / Preferences.driveGyroToDegreeRatio;
    }

    // Returns distance traveled in inches since last reset
    public double getEncoderInches() {
        return (leftEncoder.getDistance()+ rightEncoder.getDistance()) / 2;
    }

    // Returns the current speed in inches per second
    public double getEncoderSpeed() {
        double speed = (leftEncoder.getRate() + rightEncoder.getRate())/2;
        return speed;
    }
    
    // Returns rangefinder distance in inches. Spikes return -1
    public double getRangeInches() {
        double currentValue = rangeFinder.getVoltage() / 5 * 512 / 2.4;
        if (Math.abs(lastRangeFinderValue - currentValue) < Preferences.ultraAcceptableSpike) {
            lastRangeFinderValue = currentValue;
            return (currentValue);
        } else {
            return -1;
        }
    }

    // Resets both encoders to zero
    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    // Call once before align to shoot
    public void resetRangefinder() {
        Robot.driveTrain.lastRangeFinderValue = (rangeFinder.getVoltage() / 5 * 512 / 2.4);
    }
    
    // Resets the gyro so that the current angle is 0
    public void resetGyro() {
        gyro.reset();
    }

    // Call once before drive straight or turn by angle
    public void setDirection() {
        direction = getGyroDegrees();
    }

    // Stops the drive motors
    public void stop() {
        tankDrive(0, 0);
    }

    // Puts values on dashboard if in tuning mode
    public void updateDashboard() {
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putNumber("AutoDriveDistOutput", distOutput);
            SmartDashboard.putNumber("AutoDriveAngleOutput", angleOutput);
            SmartDashboard.putNumber("AutoDriveRightEncoderValue", rightEncoder.get());
            SmartDashboard.putNumber("AutoDriveLeftEncoderValue", leftEncoder.get());
            SmartDashboard.putNumber("AutoDriveAverageEncoderValue", (leftEncoder.get() + rightEncoder.get()) / 2);
            SmartDashboard.putNumber("AutoDriveGyroValue", gyro.getAngle());
            SmartDashboard.putNumber("AutoDriveDistanceValue", getEncoderInches());
            SmartDashboard.putNumber("AutoDriveAngleValue", getGyroDegrees());
            SmartDashboard.putNumber("AutoDriveDistError", distError);
            SmartDashboard.putNumber("AutoDriveAngleError", angleError);
            SmartDashboard.putNumber("RangeDistanceInches", getRangeInches());
        }
    }

    // Shifts to low gear
    public void shiftLow() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }

    // Shifts to high gear
    public void shiftHigh() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }
}
