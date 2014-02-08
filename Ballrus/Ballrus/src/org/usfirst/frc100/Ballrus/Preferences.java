//ready, unless more prefs are needed
package org.usfirst.frc100.Ballrus;

/**
 * This class stores the values of the robot's preferences.
 */
public class Preferences {

    // DriveTrain modes
    public static boolean tankDriveMode = true; // false = arcadeDrive
    // DriveTrain PID
    public static boolean driveTrainTuningMode = false;
    public static double driveEncoderToInchRatio = 38.2; // encoder / ratio = inches
    public static double driveGyroToDegreeRatio = 1; // gyro / ratio = degrees
    public static double driveStraight_kP = 0; // error * kP = motor speed
    public static double autoTurn_kP = 0; // error * kP = motor speed
    public static double driveDistBuffer = 0; // inches robot can be off by
    public static double driveAngleBuffer = 0; // degrees robot can be off by
    public static double autoDriveDelay = 20; // time to wait to make sure robot has stopped moving
    public static double driveMotorMinValue = 0; //the absolute value below which the motor cannot move the robot
    // Distance sensing
    public static double ultraInitialStopDistance = 112; 
    public static double ultraActualStopDistance = 100; // inches away from target that we want to stop
    public static double ultraAcceptableSpike = 20;
    public static int inPositionCounter = 3;
    public static int ultraActualShootDistance = 80;
    // LineReader
    public static int lowerLimit = 895;
    public static int upperLimit = 920;
    public static double width = 24.5; // length between line readers in inches
    
    // Tilter PID
    public static boolean tilterTuningMode = true;
    public static double tilterPotToDegreeRatio = .8778; // pot / ratio = degrees
    public static double tilterPotOffsetDegrees = 60; // degrees + offset = angle
    public static double tilterAngleBuffer = 2.0; // degrees tilter can be off by
    public static double tilter_kP = 0.092; // error * kP = motor speed, neg if tilter motor reversed (positive=down)
    // Tilter angles (in degrees)
    public static double shootHighAngle = 174; // button 4
    public static double shootLowAngle = 135; // button 1
    public static double shootTrussAngle = 135; // button 3
    public static double intakeAngle = 90; // button 2
    public static double stowedAngle = 0;
    public static double shootQuickAngle = 0;
    
    // Shooter tuning
    public static boolean shooterTuningMode = false;
    public static double shooterPotToInchRatio = 20; // pot / ratio = inches
    public static double shooterPotOffsetInches = 0; // inches + offset = position
//    public static double shooterEncoderToInchRatio = 1; // encoder / ratio = inches
    public static double shooterDistanceBuffer = 2; // inches shooter can be off by
    public static double shooterPullBackSpeed = 0;
    public static double shooterPullForwardSpeed = 0;
    // Shooter positions (in inches)
    public static double shootHighPosition = 0;
    public static double shootLowPosition = 10;
    public static double shootTrussPosition = 20;
    public static double intakePosition = 30;
    public static double stowedPosition = 40;
    public static double shootQuickPosition = 0;
    
    // RobotMap
    public static boolean hammerHeadRobotMap  = false;
    public static boolean gwrathRobotMap = false;
    
    // Changes preferences to those for hammerhead
    public static void setHammerhead(){
        driveEncoderToInchRatio = 38.2; // encoder / ratio = inches
        driveGyroToDegreeRatio = 1; // gyro / ratio = degrees
        driveStraight_kP = 0.15; // error * kP = motor speed
        autoTurn_kP = 0.15; // error * kP = motor speed
        driveDistBuffer = 2; // inches robot can be off by
        driveAngleBuffer = 2; // degrees robot can be off by
        autoDriveDelay = 10; // time to wait to make sure robot has stopped moving
        driveMotorMinValue = 0; //the absolute value below which the motor cannot move the robot
        
        ultraInitialStopDistance = 120; // inches away from target that we want to stop
        ultraActualStopDistance = 100;
        ultraAcceptableSpike = 20;
        inPositionCounter = 7;
    }

    // Changes preferences to those for gwrath
    public static void setGwrath() {
        driveEncoderToInchRatio = 21.72; // encoder / ratio = inches
        driveGyroToDegreeRatio = 1; // gyro / ratio = degrees
        driveStraight_kP = 0.1; // error * kP = motor speed
        autoTurn_kP = 0.1; // error * kP = motor speed
        driveDistBuffer = 5; // inches robot can be off by
        driveAngleBuffer = 5; // degrees robot can be off by
        autoDriveDelay = 10; // time to wait to make sure robot has stopped moving
        driveMotorMinValue = 0; //the absolute value below which the motor cannot move the robot
        
        ultraInitialStopDistance = 120; // inches away from target that we want to stop
        ultraActualStopDistance = 100;
        ultraAcceptableSpike = 20;
        inPositionCounter = 7;
    }
}
//93.75