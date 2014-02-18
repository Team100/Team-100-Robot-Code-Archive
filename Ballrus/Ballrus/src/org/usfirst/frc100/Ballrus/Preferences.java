//ready, unless more prefs are needed
package org.usfirst.frc100.Ballrus;

/**
 * This class stores the values of the robot's preferences.
 */
public class Preferences {

    // Initial preference declarations for competition robot
    //<editor-fold>
    // DriveTrain modes
    public static boolean tankDriveMode = false; // false = arcadeDrive
    public static boolean displayIO = true;
    // DriveTrain PID
    public static boolean driveTrainTuningMode = true;
    public static double driveEncoderToInchRatio = -64.545; // encoder / ratio = inches
    public static double driveGyroToDegreeRatio = 1.0; // gyro / ratio = degrees
    public static double driveStraight_kP = 0.005; // error * kP = motor speed
    public static double autoTurn_kP = 0.002; // error * kP = motor speed
    public static double driveStraightLowGear_kP = 0.0;
    public static double autoTurnLowGear_kP = 0.0; // error * kP = motor speed
    public static double driveDistBuffer = 2.0; // inches robot can be off by
    public static double driveAngleBuffer = 2.0; // degrees robot can be off by
    public static double autoDriveDelay = 20.0; // time to wait to make sure robot has stopped moving
    public static double driveMotorMinValue = 0.38; //the absolute value below which the motor cannot move the robot
    public static double driveLowGearMotorMinValue = 0.0; //the absolute value below which the motor cannot move the robot
    public static double driveJoystickDeadband = 0.15; //the absolute value below which the motor cannot move the robot
    public static double driveLeftOffset = 0.0;
    // Distance sensing
    public static double ultraInitialStopDistance = 112.0;
    public static double ultraActualStopDistance = 100.0; // inches away from target that we want to stop
    public static double ultraAcceptableSpike = 20.0;
    public static int inPositionCounter = 20;
    public static int ultraActualShootDistance = 150;
    // LineReader
    public static int lowerLimit = 895;
    public static int upperLimit = 920;
    public static double width = 24.5; // length between line readers in inches
    
    // Tilter PID
    public static boolean tilterTuningMode = true;
    public static double tilterPotToDegreeRatio = 0.8667; // pot / ratio = degrees
    public static double tilterPotOffsetDegrees = -416.0; // degrees + offset = angle
    public static double tilterAngleBuffer = 2.0; // degrees tilter can be off by
    public static double tilter_kP = 0.09; // error * kP = motor speed, neg if tilter motor reversed (positive=down)
    // Tilter angles (in degrees)
    public static double shootHighAngle = 137.0; // button 4
    public static double shootLowAngle = 125.5; // button 1
    public static double shootTrussAngle = 155.0; // button 3
    public static double intakeAngle = 65.0; // button 2
    public static double stowedAngle = 180.0; // button 9
    public static double shootQuickAngle = 0.0; // used by quickshoot
    
    // Shooter tuning
    public static boolean shooterTuningMode = true;
    public static double shooterPotToInchRatio = -1.0; // pot / ratio = inches
    public static double shooterPotZeroPosition = -407.0; // inches - offset = position, offset = -1 * "zero position" (all the way forward)
    public static double shooterDistanceBuffer = 3.0; // inches shooter can be off by
    public static double shooterPullBackSpeed = 1.0;
    public static double shooterPullForwardSpeed = 1.0;
    // Shooter positions (in inches)
    public static double shootHighPosition = 215.0;
    public static double shootLowPosition = 215.0;
    public static double shootTrussPosition = 100.0;
    public static double intakePosition = 215.0;
    public static double stowedPosition = 215.0;
    public static double shootQuickPosition = 215.0;
    public static double shootGrabDelay = .5;
    
    // Intake
    public static boolean intakeTuningMode = true;
    public static double intakeInSpeed = 0.75;
    public static double intakeOutSpeed = 1.0; // absolute value
    
    // Camera
    public static boolean cameraEnabled = false;
    public static double cameraAngle = 30.0; // degrees to turn by for camera aim
    
    // RobotMap
    public static boolean hammerHeadRobotMap  = false;
    public static boolean gwrathRobotMap = false;
    public static boolean practiceBot = false;
    //</editor-fold>
    
    // Changes preferences to those for the practice bot
    public static void setPracticeBotPrefs(){
        // Drivetrain PID
        driveEncoderToInchRatio = -64.11; // encoder / ratio = inches
        driveGyroToDegreeRatio = 1.0; // gyro / ratio = degrees
        driveStraight_kP = 0.005; // error * kP = motor speed
        autoTurn_kP = 0.001; // error * kP = motor speed
        autoTurnLowGear_kP = 0.0005; // error * kP = motor speed
        driveStraightLowGear_kP = 0.003; // error * kP = motor speed
        driveDistBuffer = 2.0; // inches robot can be off by
        driveAngleBuffer = 2.0; // degrees robot can be off by
        autoDriveDelay = 20.0; // time to wait to make sure robot has stopped moving
        driveMotorMinValue = 0.45; //the absolute value below which the motor cannot move the robot
        driveLowGearMotorMinValue = 0.4; //the absolute value below which the motor cannot move the robot
        
        // Tilter PID
        tilterPotToDegreeRatio = 0.8778; // pot / ratio = degrees
        tilterPotOffsetDegrees = 30.0; // degrees + offset = angle
        tilterAngleBuffer = 2.0; // degrees tilter can be off by
        tilter_kP = 0.092; // error * kP = motor speed, neg if tilter motor reversed (positive=down)
        shootHighAngle = 137.0; // button 4
        shootLowAngle = 125.5; // button 1
        shootTrussAngle = 155.0; // button 3
        intakeAngle = 70.0; // button 2
        stowedAngle = 180.0; // button 9
        shootQuickAngle = 0.0; // used by quickshoot
        
        // Shooter tuning
        shooterPotToInchRatio = -1.0; // pot / ratio = inches1
        shooterPotZeroPosition = -600.0; // inches + offset = position
        shooterDistanceBuffer = 5.0; // inches shooter can be off by
        shooterPullBackSpeed = 1.0;
        shooterPullForwardSpeed = 1.0;
        shootHighPosition = 220.0;
        shootLowPosition = 220.0;
        shootTrussPosition = 100.0;
        intakePosition = 220.0;
    }
    
    // Changes preferences to those for hammerhead
    public static void setHammerhead(){
        driveEncoderToInchRatio = 38.2; // encoder / ratio = inches
        driveGyroToDegreeRatio = 1.0; // gyro / ratio = degrees
        driveStraight_kP = 0.15; // error * kP = motor speed
        autoTurn_kP = 0.15; // error * kP = motor speed
        driveDistBuffer = 2; // inches robot can be off by
        driveAngleBuffer = 2; // degrees robot can be off by
        autoDriveDelay = 10; // time to wait to make sure robot has stopped moving
        driveMotorMinValue = 0; //the absolute value below which the motor cannot move the robot
        
    }

    // Changes preferences to those for gwrath
    public static void setGwrath() {
        driveEncoderToInchRatio = 21.72; // encoder / ratio = inches
        driveGyroToDegreeRatio = 1.0; // gyro / ratio = degrees
        driveStraight_kP = 0.1; // error * kP = motor speed
        autoTurn_kP = 0.1; // error * kP = motor speed
        driveDistBuffer = 5; // inches robot can be off by
        driveAngleBuffer = 5; // degrees robot can be off by
        autoDriveDelay = 10; // time to wait to make sure robot has stopped moving
        driveMotorMinValue = 0; //the absolute value below which the motor cannot move the robot
        
    }
}