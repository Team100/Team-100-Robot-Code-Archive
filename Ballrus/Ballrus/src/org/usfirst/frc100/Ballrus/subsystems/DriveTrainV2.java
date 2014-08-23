//ready, except for rangefinder and line reader code
package org.usfirst.frc100.Ballrus.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.PID;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.RobotMap;
import org.usfirst.frc100.Ballrus.commands.Drive;

/**
 * Controls the drive motors and shifters, and reads numerous sensors.
 */
public class DriveTrainV2 extends Subsystem {

    private final RobotDrive drive = RobotMap.driveTrainMainDrive;
    private final Encoder leftEncoder = RobotMap.driveTrainLeftEncoder; // positive = forward
    private final Encoder rightEncoder = RobotMap.driveTrainRightEncoder; // positive = forward
    private final Gyro gyro = RobotMap.driveTrainGyro; // positive = clockwise
    private final AnalogChannel rangeFinder = RobotMap.driveTrainRangeFinder; // higher = farther
    private final Solenoid shifter = RobotMap.driveTrainShifter; // true = low
    private final Counter leftCounter = RobotMap.driveTrainLeftCounter; // counts if the left line reader is over the line or not
    private final Counter rightCounter = RobotMap.driveTrainRightCounter; // counts if the right line reader is over the line or not
    private PID driveDistancePID = new PID("driveDistance");
    private PID driveAnglePID = new PID("driveAngle");
    private PID driveDistanceLowGearPID = new PID("driveDistanceLowGear");
    private PID driveAngleLowGearPID = new PID("driveAngleLowGear");
    
    private double distError = 0;
    private double angleError = 0;
    private double distOutput = 0;
    private double angleOutput = 0;
    private double lastRangeFinderValue = 0;
    private int rangeErrorCount = 0;

    // Sets the default command to Drive
    public void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

    // Adds preferences to dashboard during tuning mode
    public DriveTrainV2() {
        if (Preferences.driveTrainTuningMode) {
            SmartDashboard.putNumber("DriveDistance_kP", Preferences.driveDistance_kP);
            SmartDashboard.putNumber("DriveDistance_kI", Preferences.driveDistance_kI);
            SmartDashboard.putNumber("DriveDistance_kD", Preferences.driveDistance_kD);
            SmartDashboard.putNumber("DriveDistanceLowGear_kP", Preferences.driveDistanceLowGear_kP);
            SmartDashboard.putNumber("DriveDistanceLowGear_kI", Preferences.driveDistanceLowGear_kI);
            SmartDashboard.putNumber("DriveDistanceLowGear_kD", Preferences.driveDistanceLowGear_kD);
            SmartDashboard.putNumber("DriveAngle_kP", Preferences.driveAngle_kP);
            SmartDashboard.putNumber("DriveAngle_kI", Preferences.driveAngle_kI);
            SmartDashboard.putNumber("DriveAngle_kD", Preferences.driveAngle_kD);
            SmartDashboard.putNumber("DriveAngleLowGear_kP", Preferences.driveAngleLowGear_kP);
            SmartDashboard.putNumber("DriveAngleLowGear_kP", Preferences.driveAngleLowGear_kI);
            SmartDashboard.putNumber("DriveAngleLowGear_kP", Preferences.driveAngleLowGear_kD);
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
        if(Preferences.driveTrainTuningMode){
            drive.arcadeDrive(speed, turn);
        }
        else{
            drive.arcadeDrive(speed, turn);
        }
    }

    // Refreshes the values from the PID, returns true when in position
    public boolean updatePID(){
        if (shifter.get()) {//low gear
            distOutput = driveDistanceLowGearPID.getValue(getEncoderInches());
            angleOutput = driveAngleLowGearPID.getValue(getGyroDegrees());
        } else {//high gear
            distOutput = driveDistancePID.getValue(getEncoderInches());
            angleOutput = driveAnglePID.getValue(getGyroDegrees());
        }
        arcadeDrive(distOutput, angleOutput);
        return distOutput<Preferences.driveDistBuffer&&angleOutput<Preferences.driveAngleBuffer;
    }
    
    // Sets the target distance
    public void setDistanceSetPoint(double setpoint, boolean reset){
        if(reset){
            resetEncoders();
        }
        driveDistancePID.setSetpoint(setpoint);
        driveDistanceLowGearPID.setSetpoint(setpoint);
    }
    
    // Sets the target angle
    public void setAngleSetPoint(double setpoint, boolean reset){
        if(reset){
            resetGyro();
        }
        driveAnglePID.setSetpoint(setpoint);
        driveAngleLowGearPID.setSetpoint(setpoint);
    }
    
    // Returns robot angle relative to starting position, between -180 and 180
    public double getGyroDegrees() {
        double angle = gyro.getAngle() / Preferences.driveGyroToDegreeRatio;
        return angle;
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
        if(rangeErrorCount > 10) {
            resetRangefinder();
        }
        if (Math.abs(lastRangeFinderValue - currentValue) < Preferences.ultraAcceptableSpike) {
            lastRangeFinderValue = currentValue;
            return (currentValue);
        } else {
            rangeErrorCount++;
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
        lastRangeFinderValue = (rangeFinder.getVoltage()*1024/12.7); // if MB1220 ultrasonic sensor
    }
    
    // Resets the gyro so that the current angle is 0
    public void resetGyro() {
        gyro.reset();
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

    // Puts values on dashboard
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