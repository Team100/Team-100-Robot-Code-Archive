package org.usfirst.frc100.Robot2014;

/**
 * This class stores the values of the robot's preferences.
 */
public class Preferences {

    // DriveTrain modes
    public static boolean tankDriveMode = true; // false = arcadeDrive
    public static boolean slaveDriveDefaultEnabled = true; // whether slave drive is activated by default

    // Tilter PID
    public static double tilterPotToDegreeRatio = .27; // pot / ratio = degrees
    public static double tilterPotOffsetDegrees = -1481.5; // degrees + offset = angle
    public static double tilterAngleBuffer = 8; // degrees tilter can be off by
    public static double tilter_kP = -0.02; // error * kP = motor speed, neg if tilter motor reversed (positive=down)
    public static boolean tilterTuningMode = true;
    // Tilter angles (in degrees)
    public static double shootHighAngle = 180;
    public static double shootLowAngle = 90;
    public static double shootTrussAngle = 0;
    public static double intakeAngle = 270;
    
    // Shooter tuning
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
