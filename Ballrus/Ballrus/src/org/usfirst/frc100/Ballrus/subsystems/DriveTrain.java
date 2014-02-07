//ready, except for rangefinder and line reader code
package org.usfirst.frc100.Ballrus.subsystems;

import org.usfirst.frc100.Ballrus.commands.Drive;
import org.usfirst.frc100.Ballrus.RobotMap;
import org.usfirst.frc100.Ballrus.Preferences;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Ballrus;

/**
 * Controls the drive motors and shifters, and reads numerous sensors.
 */
public class DriveTrain extends Subsystem {

    private final RobotDrive drive = RobotMap.driveTrainMainDrive;
    private final Encoder leftEncoder = RobotMap.driveTrainLeftEncoder; // positive = forward
    private final Encoder rightEncoder = RobotMap.driveTrainRightEncoder; // positive = forward
    private final Gyro gyro = RobotMap.driveTrainGyro; // positive = clockwise
    private final AnalogChannel rangeFinder = RobotMap.driveTrainRangeFinder; // higher = farther
    private final Solenoid shifter = RobotMap.driveTrainShifter; // true = low
    private final AnalogChannel leftLineReader = RobotMap.driveTrainLeftLineReader; // true = line
    private final AnalogChannel rightLineReader = RobotMap.driveTrainRightLineReader; // true = line
    private final Counter leftCounter = RobotMap.driveTrainLeftCounter;
    private final Counter rightCounter = RobotMap.driveTrainRightCounter;

    private double distError = 0;
    private double angleError = 0;
    private double distOutput = 0;
    private double angleOutput = 0;
    private double direction = 0;
    private double lastRangeFinderValue = 0;

    // Sets the default command to Drive
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

    // Adds kP to dashboard during tuning mode
    public DriveTrain() {
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putNumber("DriveStraight_kP", Preferences.driveStraight_kP);
            SmartDashboard.putNumber("AutoTurn_kP", Preferences.autoTurn_kP);
            SmartDashboard.putNumber("AutoDriveTestDistance", 0);
            SmartDashboard.putNumber("AutoDriveTestAngle", 0);
        }
    }

    // Sets the robot drives to tankdrive
    public void tankDrive(double left, double right) {
        if(Math.abs(left)<Preferences.driveMotorMinValue){
            if(left<0){
                left = -Preferences.driveMotorMinValue;
            }
            if(left>0){
                left = Preferences.driveMotorMinValue;
            }
        }
        if(Math.abs(right)<Preferences.driveMotorMinValue){
            if(right<0){
                right = -Preferences.driveMotorMinValue;
            }
            if(right>0){
                right = Preferences.driveMotorMinValue;
            }
        }
        drive.tankDrive(left, right);
        updateDashboard();
    }

    // Sets the robot drives to arcadedrive
    public void arcadeDrive(double speed, double turn) {
        if(Math.abs(speed)<Preferences.driveMotorMinValue){
            if(speed<0){
                speed = -Preferences.driveMotorMinValue;
            }
            if(speed>0){
                speed = Preferences.driveMotorMinValue;
            }
        }
        if(Math.abs(turn)<Preferences.driveMotorMinValue){
            if(turn<0){
                turn = -Preferences.driveMotorMinValue;
            }
            if(turn>0){
                turn = Preferences.driveMotorMinValue;
            }
        }
        drive.arcadeDrive(speed, turn);
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
            SmartDashboard.putNumber("gyro voltage raw " , AnalogModule.getInstance(1).getVoltage(1));
            System.out.println("gyro voltage raw " + AnalogModule.getInstance(1).getVoltage(1));
            SmartDashboard.putNumber("gyro heading", gyro.getAngle());
            System.out.println("gyro heading" + gyro.getAngle());
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
        //double currentValue = rangeFinder.getVoltage() / 5 * 512 / 2.4; //if MB1023 ultrasonic sensor
        //double currentValue = rangeFinder.getVoltage()*84.252 +3.664 ; //if MB1220 ultrasonic sensor, equation from testing, works OK but not great
        double currentValue = rangeFinder.getVoltage()*1024/12.7; //if if MB1220 ultrasonic sensor, equation from datasheet, works pretty good, better than the other one
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
        //Ballrus.driveTrain.lastRangeFinderValue = (rangeFinder.getVoltage() / 5 * 512 / 2.4); //if MB1023 ultrsonic sensor
        Ballrus.driveTrain.lastRangeFinderValue = (rangeFinder.getVoltage()*1024/12.7); // if MB1220 ultrasonic sensor
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

    // Start the line reader counters =P
    public void startLineReaders() {
        leftCounter.start();
        rightCounter.start();
    }

    // Stops the line reader counters =P
    public void stopLineReaders() {
        leftCounter.stop();
        rightCounter.stop();
    }

    // Returns true if the left trigger is over the line
    public boolean getLeftTriggerState() {
        return (1 == leftCounter.get());
    }

    // Returns true if the right trigger is over the line
    public boolean getRightTriggerState() {
        return (1 == rightCounter.get());
    }

    // Puts values on dashboard if in tuning mode
    public void updateDashboard() {
        SmartDashboard.putNumber("AutoDriveAngleValue", getGyroDegrees());
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putNumber("AutoDriveDistOutput", distOutput);
            SmartDashboard.putNumber("AutoDriveAngleOutput", angleOutput);
            SmartDashboard.putNumber("AutoDriveRightEncoderValue", rightEncoder.get());
            SmartDashboard.putNumber("AutoDriveLeftEncoderValue", leftEncoder.get());
            SmartDashboard.putNumber("AutoDriveAverageEncoderValue", (leftEncoder.get() + rightEncoder.get()) / 2);
            SmartDashboard.putNumber("AutoDriveGyroValue", gyro.getAngle());
            SmartDashboard.putNumber("AutoDriveDistanceValue", getEncoderInches());
            SmartDashboard.putNumber("AutoDriveDistError", distError);
            SmartDashboard.putNumber("AutoDriveAngleError", angleError);
            SmartDashboard.putNumber("RangeDistanceInches", getRangeInches());
        }
    }

    // Shifts to low gear
    public void shiftLow() {
        shifter.set(true);
    }

    // Shifts to high gear
    public void shiftHigh() {
        shifter.set(false);
    }
}
