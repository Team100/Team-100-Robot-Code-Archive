//ready, unless more prefs are needed
package org.usfirst.frc100.Ballrus;

/**
 * This class stores the values of the robot's preferences.
 */
public class Preferences {

    // Initial preference declarations for competition robot, many are used on all robots
    //<editor-fold>
    // General preferences
    public static boolean hammerHeadRobotMap  = false;
    public static boolean gwrathRobotMap = false;
    public static boolean practiceBot = false;
    public static boolean practiceBotCompShooter = false;
    public static boolean tankDriveMode = false; // false = arcadeDrive
    public static boolean displayIO = true; // puts all I/O on the SmartDashboard
    // DriveTrain PID
    public static boolean driveTrainTuningMode = true;
    public static double driveEncoderToInchRatio = -64.545; // (change in "DriveAverageEncoderValue")/(change in actual distance in inches)
    public static double driveGyroToDegreeRatio = 1.0; // (change in "DriveGyroValue")/(change in actual angle in degrees)
    public static double driveDistance_kP = 0.005; // error * kP = motor speed
    public static double driveAngle_kP = 0.002; // error * kP = motor speed
    public static double driveDistanceLowGear_kP = 0.0; // error * kP = motor speed
    public static double driveAngleLowGear_kP = 0.0; // error * kP = motor speed
    public static double driveDistance_kI = 0.0;
    public static double driveAngle_kI = 0.0;
    public static double driveDistanceLowGear_kI = 0.0;
    public static double driveAngleLowGear_kI = 0.0;
    public static double driveDistance_kD = 0.0;
    public static double driveAngle_kD = 0.0;
    public static double driveDistanceLowGear_kD = 0.0;
    public static double driveAngleLowGear_kD = 0.0;
    public static double driveDistBuffer = 2.0; // distance in inches autoDriveStraight can be off by
    public static double driveAngleBuffer = 2.0; // angle in degrees autoTurn can be off by
    public static double autoDriveDelay = 20.0; // code cycles to wait to make sure robot has stopped moving
    public static double driveMotorMinValue = 0.38; // the absolute value below which the motors cannot move the robot
    public static double driveLowGearMotorMinValue = 0.0; // the absolute value below which the motors cannot move the robot
    public static double driveJoystickDeadband = 0.1; // the absolute value below which the motors cannot move the robot
    public static double driveLeftOffset = 0.0; // bias towards left side of drivetrain, only used in arcade drive
    // Distance sensing
    public static double ultraInitialStopDistance = 112.0;
    public static double ultraActualStopDistance = 100.0; // inches away from target that we want to stop
    public static double ultraAcceptableSpike = 20.0;
    public static int inPositionCounter = 20;
    public static int ultraActualShootDistance = 120; //used by FastestShotInTheWest //Inches (from shooting from around 10 feet)
    // LineReader
    public static int lowerLimit = 895;
    public static int upperLimit = 920;
    public static double width = 24.5; // distance between line readers in inches
    
    // Tilter PID
    public static boolean tilterTuningMode = true;
    public static double tilterPotToDegreeRatio = 0.8333; // (change in "TilterSensorValue")/(change in actual angle in degrees)
    public static double tilterPot180DegreePosition = 554.0; // "TilterSensorValue" when tilter is straight up (180 degrees)
    public static double tilterAngleBuffer = 2.0; // angle in degrees tilter angle can be off by
    public static double tilter_kP = 0.02; // error * kP = motor speed
    public static double tilter_kI = 0.0;
    public static double tilter_kD = 0.0007;
    public static double tilterMinAngle = 70.0; // lowest angle in degrees that tilter can reach
    public static double tilterMaxAngle = 180.0; // highest angle in degrees that tilter can reach
    // Tilter angles (in degrees)
    public static double tilterShootHighAngle = 140.0; // tilter angle in degrees when shooting from close range (button 4)
    public static double tilterShootLowAngle = 128.0; // tilter angle in degrees when shooting from long range (button 1)
    public static double tilterShootTrussAngle = 155.0; // tilter angle in degrees when shooting over the truss (button 3)
    public static double tilterIntakeAngle = 70.0; // tilter angle in degrees when intaking (button 2)
    public static double tilterStowedAngle = 178.0; // tilter angle in degrees at start of match (button 9)
    public static double tilterTrussPassAngle = 155.0; // tilter angle in degrees when using quickshoot (AKA FastestShotInTheWest)
    public static double tilterCameraAngle = 105.0; // tilter angle in degrees when using the camera
    
    // Shooter tuning
    public static boolean shooterTuningMode = true;
    public static double shooterPotToInchRatio = 32.62; // (change in "ShooterSensorValue")/(change in actual distance pulled back in inches)
    public static double shooterPotZeroPosition = 272.0; // "ShooterSensorValue" when shooter is all the way forward
    public static double shooterDistanceBuffer = 0.1; // distance in inches shooter position can be off by
    public static double shooterPullBackSpeed = 1.0; // speed at which shooter pulls back
    public static double shooterPullForwardSpeed = 1.0; // speed at which shooter moves forward to home position (use absolute value)
    public static double shooterGrabDelay = 0.5; // time in seconds to wait after shooter reattaches before pulling back
    public static double shooterFullRange = 7.25; // distance in inches between all the way forward and all the way pulled back
    // Shooter positions (in inches)
    public static double shooterShootHighPullback = 4.0; // shooter pullback in inches when shooting from close range (button 4)
    public static double shooterShootLowPullback = 6.0; // shooter pullback in inches when shooting from long range (button 1)
    public static double shooterShootTrussPullback = 4.5; // shooter pullback in inches when shooting over the truss (button 3)
    public static double shooterIntakePullback = 6.5; // shooter pullback in inches when intaking (button 2)
    public static double shooterStowedPullback = 6.0; // shooter pullback in inches at start of match (button 9)
    public static double shooterTrussPassPullback = 5.5; // shooter pullback in inches when using quickshoot (AKA FastestShotInTheWest)
    
    // Intake
    public static boolean intakeTuningMode = true;
    public static double intakeInSpeed = 0.75; // roller speed when intaking the ball
    public static double intakeOutSpeed = 1.0; // roller speed when expelling the ball (use absolute value)
    
    // Camera
    public static boolean cameraEnabled = true;
    public static double cameraAngle = 40.0; // degrees to turn by for camera aim
    
    // RobotMap
    //</editor-fold>
    
    // Changes preferences to those for the practice bot, if practice bot has the competition shooter then tilter/shooter preferences will not be changed
    public static void setPracticeBotPrefs(){
        // Drivetrain PID
        driveEncoderToInchRatio = -64.11; // (change in "DriveAverageEncoderValue")/(change in actual distance in inches)
        driveGyroToDegreeRatio = 1.0; // (change in "DriveGyroValue")/(change in actual angle in degrees)
        driveDistance_kP = 0.005; // error * kP = motor speed
        driveAngle_kP = 0.001; // error * kP = motor speed
        driveAngleLowGear_kP = 0.0005; // error * kP = motor speed
        driveDistanceLowGear_kP = 0.003; // error * kP = motor speed
        driveDistBuffer = 2.0; // inches robot can be off by
        driveAngleBuffer = 2.0; // degrees robot can be off by
        autoDriveDelay = 20.0; // code cycles to wait to make sure robot has stopped moving
        driveMotorMinValue = 0.45; //the absolute value below which the motor cannot move the robot
        driveLowGearMotorMinValue = 0.4; //the absolute value below which the motor cannot move the robot
        
        if (!practiceBotCompShooter) {
            // Tilter PID
            tilterPotToDegreeRatio = 0.8778; // (change in "TilterSensorValue")/(change in actual angle in degrees)
            tilterPot180DegreePosition = 30.0; // "TilterSensorValue" when tilter is straight up (180 degrees)
            tilterAngleBuffer = 2.0; // angle in degrees tilter angle can be off by
            tilter_kP = 0.092; // error * kP = motor speed
            tilterShootHighAngle = 137.0; // tilter angle in degrees when shooting from close range (button 4)
            tilterShootLowAngle = 125.5; // tilter angle in degrees when shooting from long range (button 1)
            tilterShootTrussAngle = 155.0; // tilter angle in degrees when shooting over the truss (button 3)
            tilterIntakeAngle = 70.0; // tilter angle in degrees when intaking (button 2)
            tilterStowedAngle = 180.0; // tilter angle in degrees at start of match (button 9)
            tilterTrussPassAngle = 0.0; // tilter angle in degrees when using quickshoot (AKA FastestShotInTheWest)

            // Shooter tuning
            shooterPotToInchRatio = -1.0; // (change in "ShooterSensorValue")/(change in actual distance pulled back in inches)
            shooterPotZeroPosition = -600.0; // "ShooterSensorValue" when shooter is all the way forward
            shooterDistanceBuffer = 5.0; // distance in inches shooter position can be off by
            shooterPullBackSpeed = 1.0; // speed at which shooter pulls back
            shooterPullForwardSpeed = 1.0; // speed at which shooter moves forward to home position (use absolute value)
            shooterShootHighPullback = 220.0;
            shooterShootLowPullback = 220.0;
            shooterShootTrussPullback = 100.0;
            shooterIntakePullback = 220.0;
        }
    }
    
    // Changes preferences to those for hammerhead
    public static void setHammerhead(){
        driveEncoderToInchRatio = 38.2; // (change in "DriveAverageEncoderValue")/(change in actual distance in inches)
        driveGyroToDegreeRatio = 1.0; // (change in "DriveGyroValue")/(change in actual angle in degrees)
        driveDistance_kP = 0.15; // error * kP = motor speed
        driveAngle_kP = 0.15; // error * kP = motor speed
        driveDistBuffer = 2; // inches robot can be off by
        driveAngleBuffer = 2; // degrees robot can be off by
        autoDriveDelay = 10; // code cycles to wait to make sure robot has stopped moving
        driveMotorMinValue = 0; //the absolute value below which the motor cannot move the robot
        
    }

    // Changes preferences to those for gwrath
    public static void setGwrath() {
        driveEncoderToInchRatio = 21.72; // (change in "DriveAverageEncoderValue")/(change in actual distance in inches)
        driveGyroToDegreeRatio = 1.0; // (change in "DriveGyroValue")/(change in actual angle in degrees)
        driveDistance_kP = 0.1; // error * kP = motor speed
        driveAngle_kP = 0.1; // error * kP = motor speed
        driveDistBuffer = 5; // inches robot can be off by
        driveAngleBuffer = 5; // degrees robot can be off by
        autoDriveDelay = 10; // code cycles to wait to make sure robot has stopped moving
        driveMotorMinValue = 0; //the absolute value below which the motor cannot move the robot
        
    }
}