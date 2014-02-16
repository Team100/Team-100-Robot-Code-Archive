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
    private final Counter leftCounter = RobotMap.driveTrainLeftCounter; // counts if the left line reader is over the line or not
    private final Counter rightCounter = RobotMap.driveTrainRightCounter; // counts if the right line reader is over the line or not

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

    // Adds preferences to dashboard during tuning mode
    public DriveTrain() {
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putNumber("DriveStraight_kP", Preferences.driveStraight_kP);
            SmartDashboard.putNumber("DriveStraightLowGear_kP", Preferences.driveStraightLowGear_kP);
            SmartDashboard.putNumber("AutoTurn_kP", Preferences.autoTurn_kP);
            SmartDashboard.putNumber("AutoTurnLowGear_kP", Preferences.autoTurnLowGear_kP);
            SmartDashboard.putNumber("AutoDriveTestDistance", 0);
            SmartDashboard.putNumber("AutoDriveTestAngle", 0);
            SmartDashboard.putNumber("DriveMotorMinValue", Preferences.driveMotorMinValue);
            SmartDashboard.putNumber("DriveLowGearMotorMinValue", Preferences.driveLowGearMotorMinValue);
        }
    }

    // Sets the robot drives to tankdrive
    public void tankDrive(double left, double right) {
        double minVal;
        if(shifter.get()){//low gear
            if (Preferences.driveTrainTuningMode) {
                minVal = SmartDashboard.getNumber("DriveLowGearMotorMinValue");
            } else {
                minVal = Preferences.driveLowGearMotorMinValue;
            }
        }
        else if (Preferences.driveTrainTuningMode) {
            minVal = SmartDashboard.getNumber("DriveMotorMinValue");
        } else {
            minVal = Preferences.driveMotorMinValue;
        }
        if (left < 0) {
            left -= minVal;
        }
        if (left > 0) {
            left += minVal;
        }
        if (right < 0) {
            right -= minVal;
        }
        if (right > 0) {
            right += minVal;
        }
        drive.tankDrive(left, right);
    }

    // Sets the robot drives to arcadedrive
    public void arcadeDrive(double speed, double turn) {
        double minVal;
        if(shifter.get()) { //low gear
            if (Preferences.driveTrainTuningMode) {
                minVal = SmartDashboard.getNumber("DriveLowGearMotorMinValue");
            }
            else {
                minVal = Preferences.driveLowGearMotorMinValue;
            }
        }
        else if (Preferences.driveTrainTuningMode) {
            minVal = SmartDashboard.getNumber("DriveMotorMinValue");
        } else {
            minVal = Preferences.driveMotorMinValue;
        }
        if (speed < 0) {
            speed -= minVal;
        }
        if (speed > 0) {
            speed += minVal;
        }
        if (turn < 0) {
            turn -= minVal;
        }
        if (turn > 0) {
            turn += minVal;
        }
        drive.arcadeDrive(speed, turn);
    }

    // Drives straight for a distance in inches, returns true when distance reached
    public boolean autoDriveStraight(double distance) {
        // Distance output
        distError = getEncoderInches() - distance;
        if (Math.abs(distError) > Preferences.driveDistBuffer) { // incorrect distance
            if (shifter.get()) {//low gear
                if (Preferences.driveTrainTuningMode) {
                    distOutput = distError * SmartDashboard.getNumber("DriveStraightLowGear_kP", 0);
                } else {
                    distOutput = distError * Preferences.driveStraightLowGear_kP;
                }
            } else if (Preferences.driveTrainTuningMode) {
                distOutput = distError * SmartDashboard.getNumber("DriveStraight_kP", 0);
            } else {
                distOutput = distError * Preferences.driveStraight_kP;
            }
        } else { // correct distance
            distOutput = 0;
            if (Math.abs(angleError) < Preferences.driveAngleBuffer) {
                stop();
                angleOutput = 0;
                return true;
            }
        }
        // Angle output
        angleError = direction - getGyroDegrees();
        while (angleError < 0) {
            angleError += 360;
        }
        angleError = (angleError + 180) % 360 - 180;
        if (shifter.get()) {//low gear
            if (Preferences.driveTrainTuningMode) {
                angleOutput = angleError * SmartDashboard.getNumber("AutoTurnLowGear_kP", 0);
            } else {
                angleOutput = angleError * Preferences.autoTurnLowGear_kP;
            }
        } else if (Preferences.driveTrainTuningMode) {
            angleOutput = angleError * SmartDashboard.getNumber("AutoTurn_kP", 0);
        } else {
            angleOutput = angleError * Preferences.autoTurn_kP;
        }
        // Setting motors
        arcadeDrive(distOutput, angleOutput);
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
        if (shifter.get()) {//low gear
            if (Preferences.driveTrainTuningMode) {
                angleOutput = angleError * SmartDashboard.getNumber("AutoTurnLowGear_kP", 0);
            } else {
                angleOutput = angleError * Preferences.autoTurnLowGear_kP;
            }
        } else if (Preferences.driveTrainTuningMode) {
            angleOutput = angleError * SmartDashboard.getNumber("AutoTurn_kP", 0);
        } else {
            angleOutput = angleError * Preferences.autoTurn_kP;
        }
        // Setting motors
        arcadeDrive(speed, angleOutput);
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
            return true;
        }
        if (shifter.get()) {//low gear
            if (Preferences.driveTrainTuningMode) {
                angleOutput = angleError * SmartDashboard.getNumber("AutoTurnLowGear_kP", 0);
            } else {
                angleOutput = angleError * Preferences.autoTurnLowGear_kP;
            }
        } else if (Preferences.driveTrainTuningMode) {
            angleOutput = angleError * SmartDashboard.getNumber("AutoTurn_kP", 0);
        } else {
            angleOutput = angleError * Preferences.autoTurn_kP;
        }
        arcadeDrive(0, angleOutput);
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
    
//    //UNTESTED
//      // Returns rangefinder distance in inches. Spikes and out-of-range distances return -1
//    public double getRangeInchesIR() {
//        if(IRrange.getVoltage() <= 0.1)
//        {
//            return -1;
//        }
//        double currentValue = (1/((0.0073*IRrange.getVoltage())-0.0082))/2.54; // Sharp IR rangefinder (the huge one rated to 550cm)
//        /*if (Math.abs(lastRangeFinderValue - currentValue) < Preferences.ultraAcceptableSpike) {
//            lastRangeFinderValue = currentValue;
//            return (currentValue);
//        } else {
//            return -1;
//        }*/
//        return currentValue;
//    }
    
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

    // Shifts to low gear
    public void shiftLow() {
        shifter.set(true);
    }

    // Shifts to high gear
    public void shiftHigh() {
        shifter.set(false);
    }

    // Puts values on dashboard if in tuning mode
    public void updateDashboard() {
        SmartDashboard.putNumber("DriveAngleValue", getGyroDegrees());
        SmartDashboard.putNumber("DriveRangeValue", getRangeInches());
        SmartDashboard.putNumber("DriveDistanceValue", getEncoderInches());
        SmartDashboard.putBoolean("DriveShifterIsLow", shifter.get());
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putNumber("DriveDistOutput", distOutput);
            SmartDashboard.putNumber("DriveAngleOutput", angleOutput);
            SmartDashboard.putNumber("DriveRightEncoderValue", rightEncoder.get());
            SmartDashboard.putNumber("DriveLeftEncoderValue", leftEncoder.get());
            SmartDashboard.putNumber("DriveAverageEncoderValue", (leftEncoder.get() + rightEncoder.get()) / 2);
            SmartDashboard.putNumber("DriveGyroValue", gyro.getAngle());
            SmartDashboard.putNumber("DriveGyroVoltage", AnalogModule.getInstance(1).getVoltage(1));
            SmartDashboard.putNumber("DriveDistError", distError);
            SmartDashboard.putNumber("DriveAngleError", angleError);
            SmartDashboard.putNumber("DriveRangeSensorValue", rangeFinder.getVoltage());
        }
    }
}