package org.usfirst.frc100.Robot2014;

/**
 * This class stores the values of the robot's preferences.
 */
public class Preferences {

    // DriveTrain modes
    public static boolean tankDriveMode = true; // false = arcadeDrive
    public static boolean slaveDriveDefaultEnabled = true; // whether slave drive is activated by default
    // DriveTrain PID
    public static boolean driveTrainTuningMode = true;
    public static double driveEncoderToInchRatio = 1; // encoder / ratio = inches
    public static double driveGyroToDegreeRatio = 1; // gyro / ratio = degrees
    public static double driveStraight_kP = 0; // error * kP = motor speed
    public static double autoTurn_kP = 0; // error * kP = motor speed
    public static double driveDistBuffer = 0; // inches robot can be off by
    public static double driveAngleBuffer = 0; // degrees robot can be off by
    public static double autoDriveDelay = 10; // time to wait to make sure robot has stopped moving
    // Distance sensing
    public static double ultraStopDistance = 60; // inches away from target that we want to stop
    public static double ultraAcceptableSpike = 20;
            
    // Tilter PID
    public static boolean tilterTuningMode = false;
    public static double tilterPotToDegreeRatio = .27; // pot / ratio = degrees
    public static double tilterPotOffsetDegrees = -1481.5; // degrees + offset = angle
    public static double tilterAngleBuffer = 8; // degrees tilter can be off by
    public static double tilter_kP = -0.02; // error * kP = motor speed, neg if tilter motor reversed (positive=down)
    // Tilter angles (in degrees)
    public static double shootHighAngle = 180;
    public static double shootLowAngle = 90;
    public static double shootTrussAngle = 0;
    public static double intakeAngle = 270;
    
    // Shooter tuning
    public static boolean shooterTuningMode = false;
    public static double shooterPotToInchRatio = 1; // pot / ratio = inches
    public static double shooterPotOffsetInches = 0; // inches + offset = position
    public static double shooterEncoderToInchRatio = 1; // encoder / ratio = inches
    public static double shooterDistanceBuffer = 0; // inches shooter can be off by
    public static double shooterPullBackSpeed = .5;
    public static double shooterPullForwardSpeed = .2;
    // Shooter positions (in inches)
    public static double shootHighPosition = 0;
    public static double shootLowPosition = 0;
    public static double shootTrussPosition = 0;
    public static double intakePosition = 0;
}
