//ready, unless more prefs are needed
package org.usfirst.frc100.Ballrus;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;

/**
 * This class stores the values of the robot's preferences.
 *
 * To add a new preference: 1. Add its declaration to the class. 2. Assign its
 * value in the readFromFile method. 3. Add it to the preferences file on the
 * cRIO. 4. Add it to the preferences file backup in the repository.
 */
public class Preferences {

    private static String generalFile = "";
    private static String fastFile = "";
    private static String combinedFile = "";
    // Initial preference declarations for competition robot, many are used on all robots
    //<editor-fold>
    // General preferences
    public static boolean hammerHeadRobotMap = false;
    public static boolean gwrathRobotMap = false;
    public static boolean practiceBot = false;
    public static boolean practiceBotCompShooter = false;
    public static boolean tankDriveMode = false; // false = arcadeDrive
    public static boolean guestMode = false; // 1/3 speed
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
    public static double tilterPot180DegreePosition = 653.0; // "TilterSensorValue" when tilter is straight up (180 degrees)
    public static double tilterPot90DegreePosition = 579.0; // "TilterSensorValue" when tilter is straight forwards (90 degrees)
    public static double tilterPotToDegreeRatio = (tilterPot180DegreePosition - tilterPot90DegreePosition) / 90; // (change in "TilterSensorValue")/(change in actual angle in degrees)
//    public static double tilterPotToDegreeRatio = 0.8222; // (change in "TilterSensorValue")/(change in actual angle in degrees)
    public static double tilterAngleBuffer = 2.0; // angle in degrees tilter angle can be off by
    public static double tilter_kP = 0.02; // error * kP = motor speed
    public static double tilter_kI = 0.0;
    public static double tilter_kD = 0.0007;
    public static double tilterMinAngle = 70.0; // lowest angle in degrees that tilter can reach
    public static double tilterMaxAngle = 180.0; // highest angle in degrees that tilter can reach
    // Tilter angles (in degrees)
    public static double tilterShootHighAngle = 144.0; // tilter angle in degrees when shooting from close range (button 4)
    public static double tilterShootLowAngle = 128.0; // tilter angle in degrees when shooting from long range (button 1)
    public static double tilterShootTrussAngle = 153.0; // tilter angle in degrees when shooting over the truss (button 3)
    public static double tilterIntakeAngle = 67.0; // tilter angle in degrees when intaking (button 2)
    public static double tilterStowedAngle = 178.0; // tilter angle in degrees at start of match (button 9)
    public static double tilterTrussPassAngle = 155.0; // tilter angle in degrees when using quickshoot (AKA FastestShotInTheWest)
    public static double tilterCameraAngle = 105.0; // tilter angle in degrees when using the camera
    // Shooter tuning
    public static boolean shooterTuningMode = true;
    public static boolean shooterDisabled = false;
    public static double shooterPotZeroPosition = 275.0; // "ShooterSensorValue" when shooter is all the way forward
    public static double shooterPotBackPosition = 477.0; // "ShooterSensorValue" when shooter is all the way back
    public static double shooterFullRange = 7.25; // distance in inches between all the way forward and all the way pulled back
    public static double shooterPotToInchRatio = (shooterPotBackPosition - shooterPotZeroPosition) / shooterFullRange; // (change in "ShooterSensorValue")/(change in actual distance pulled back in inches)
//    public static double shooterPotToInchRatio = 27.86; // (change in "ShooterSensorValue")/(change in actual distance pulled back in inches)
    public static double shooterDistanceBuffer = 0.1; // distance in inches shooter position can be off by
    public static double shooterPullBackSpeed = 1.0; // speed at which shooter pulls back
    public static double shooterPullForwardSpeed = 1.0; // speed at which shooter moves forward to home position (use absolute value)
    public static double shooterGrabDelay = 0.5; // time in seconds to wait after shooter reattaches before pulling back
    // Shooter positions (in inches)
    public static double shooterShootHighPullback = 4.35; // shooter pullback in inches when shooting from close range (button 4)
    public static double shooterShootLowPullback = 6.0; // shooter pullback in inches when shooting from long range (button 1)
    public static double shooterShootTrussPullback = 4.7; // shooter pullback in inches when shooting over the truss (button 3)
    public static double shooterIntakePullback = 6.5; // shooter pullback in inches when intaking (button 2)
    public static double shooterStowedPullback = 6.0; // shooter pullback in inches at start of match (button 9)
    public static double shooterReloadPullback = -0.2; // shooter pullback in inches to reload after shooting
    public static double shooterTrussPassPullback = 5.5; // shooter pullback in inches when using quickshoot (AKA FastestShotInTheWest)
    // Intake
    public static boolean intakeTuningMode = true;
    public static double intakeInSpeed = 0.75; // roller speed when intaking the ball
    public static double intakeOutSpeed = 1.0; // roller speed when expelling the ball (use absolute value)
    // Camera
    public static boolean cameraEnabled = false;
    public static double cameraAngle = 40.0; // degrees to turn by for camera aim

    // RobotMap
    //</editor-fold>
    // Changes preferences to those for the practice bot, if practice bot has the competition shooter then tilter/shooter preferences will not be changed
    public static void setPracticeBotPrefs() {
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
    public static void setHammerhead() {
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
        driveMotorMinValue = 0; // the absolute value below which the motor cannot move the robot
    }

    // Updates the values of the preferences to match the file
    public static void readFromFile(boolean all) {
        DataInputStream actualFile;
        FileConnection fc;
        String file = "";
        try {
            if (all) {
                fc = (FileConnection) Connector.open("file:///generalPreferences.txt", Connector.READ);
                actualFile = fc.openDataInputStream();
                for (int i = 0; i < 10000; i++) {
                    RobotMap.stopAllMotors();
                    int nextChar = actualFile.read();
                    if (nextChar == -1) {
                        break;
                    }
                    file = file + (char) nextChar;
                }
                generalFile = file;
                file="";
            }
            fc = (FileConnection) Connector.open("file:///fastPreferences.txt", Connector.READ);
            actualFile = fc.openDataInputStream();
            for (int i = 0; i < 10000; i++) {
                RobotMap.stopAllMotors();
                int nextChar = actualFile.read();
                if (nextChar == -1) {
                    break;
                }
                file = file + (char) nextChar;
            }
            fastFile = file;
        } catch (IOException e) {
            System.out.println("File Reading Failed");
            return;
        }
        combinedFile = generalFile + fastFile;
        hammerHeadRobotMap = "true".equals(getPref("hammerHeadRobotMap"));
        gwrathRobotMap = "true".equals(getPref("gwrathRobotMap"));
        practiceBot = "true".equals(getPref("practiceBot"));
        practiceBotCompShooter = "true".equals(getPref("practiceBotCompShooter"));

        if (hammerHeadRobotMap || gwrathRobotMap || practiceBot) {
            return;
        }
        try {
            if (!practiceBotCompShooter) {
                tankDriveMode = "true".equals(getPref("tankDriveMode"));
                displayIO = "true".equals(getPref("displayIO"));
                driveTrainTuningMode = "true".equals(getPref("driveTrainTuningMode"));
                driveEncoderToInchRatio = Double.parseDouble(getPref("driveEncoderToInchRatio"));
                driveGyroToDegreeRatio = Double.parseDouble(getPref("driveGyroToDegreeRatio"));
                driveDistance_kP = Double.parseDouble(getPref("driveDistance_kP"));
                driveAngle_kP = Double.parseDouble(getPref("driveAngle_kP"));
                driveDistanceLowGear_kP = Double.parseDouble(getPref("driveDistanceLowGear_kP"));
                driveAngleLowGear_kP = Double.parseDouble(getPref("driveAngleLowGear_kP"));
                driveDistance_kI = Double.parseDouble(getPref("driveDistance_kI"));
                driveAngle_kI = Double.parseDouble(getPref("driveAngle_kI"));
                driveDistanceLowGear_kI = Double.parseDouble(getPref("driveDistanceLowGear_kI"));
                driveAngleLowGear_kI = Double.parseDouble(getPref("driveAngleLowGear_kI"));
                driveDistance_kD = Double.parseDouble(getPref("driveDistance_kD"));
                driveAngle_kD = Double.parseDouble(getPref("driveAngle_kD"));
                driveDistanceLowGear_kD = Double.parseDouble(getPref("driveDistanceLowGear_kD"));
                driveAngleLowGear_kD = Double.parseDouble(getPref("driveAngleLowGear_kD"));
                driveDistBuffer = Double.parseDouble(getPref("driveDistBuffer"));
                driveAngleBuffer = Double.parseDouble(getPref("driveAngleBuffer"));
                autoDriveDelay = Double.parseDouble(getPref("autoDriveDelay"));
                driveMotorMinValue = Double.parseDouble(getPref("driveMotorMinValue"));
                driveLowGearMotorMinValue = Double.parseDouble(getPref("driveLowGearMotorMinValue"));
                driveJoystickDeadband = Double.parseDouble(getPref("driveJoystickDeadband"));
                driveLeftOffset = Double.parseDouble(getPref("driveLeftOffset"));
                ultraInitialStopDistance = Double.parseDouble(getPref("ultraInitialStopDistance"));
                ultraActualStopDistance = Double.parseDouble(getPref("ultraActualStopDistance"));
                ultraAcceptableSpike = Double.parseDouble(getPref("ultraAcceptableSpike"));
                inPositionCounter = Integer.parseInt(getPref("inPositionCounter"));
                ultraActualShootDistance = Integer.parseInt(getPref("ultraActualShootDistance"));
                lowerLimit = Integer.parseInt(getPref("lowerLimit"));
                upperLimit = Integer.parseInt(getPref("upperLimit"));
                width = Double.parseDouble(getPref("width"));
            }
            tilterTuningMode = "true".equals(getPref("tilterTuningMode"));
            tilterPotToDegreeRatio = Double.parseDouble(getPref("tilterPotToDegreeRatio"));
            tilterPot180DegreePosition = Double.parseDouble(getPref("tilterPot180DegreePosition"));
            tilterPot90DegreePosition = Double.parseDouble(getPref("tilterPot90DegreePosition"));
            tilterAngleBuffer = Double.parseDouble(getPref("tilterAngleBuffer"));
            tilter_kP = Double.parseDouble(getPref("tilter_kP"));
            tilter_kI = Double.parseDouble(getPref("tilter_kI"));
            tilter_kD = Double.parseDouble(getPref("tilter_kD"));
            tilterMinAngle = Double.parseDouble(getPref("tilterMinAngle"));
            tilterMaxAngle = Double.parseDouble(getPref("tilterMaxAngle"));
            tilterShootHighAngle = Double.parseDouble(getPref("tilterShootHighAngle"));
            tilterShootLowAngle = Double.parseDouble(getPref("tilterShootLowAngle"));
            tilterShootTrussAngle = Double.parseDouble(getPref("tilterShootTrussAngle"));
            tilterIntakeAngle = Double.parseDouble(getPref("tilterIntakeAngle"));
            tilterStowedAngle = Double.parseDouble(getPref("tilterStowedAngle"));
            tilterTrussPassAngle = Double.parseDouble(getPref("tilterTrussPassAngle"));
            tilterCameraAngle = Double.parseDouble(getPref("tilterCameraAngle"));
            shooterTuningMode = "true".equals(getPref("shooterTuningMode"));
            shooterDisabled = "true".equals(getPref("shooterDisabled"));
            shooterPotToInchRatio = Double.parseDouble(getPref("shooterPotToInchRatio"));
            shooterPotZeroPosition = Double.parseDouble(getPref("shooterPotZeroPosition"));
            shooterPotBackPosition = Double.parseDouble(getPref("shooterPotBackPosition"));
            shooterDistanceBuffer = Double.parseDouble(getPref("shooterDistanceBuffer"));
            shooterPullBackSpeed = Double.parseDouble(getPref("shooterPullBackSpeed"));
            shooterPullForwardSpeed = Double.parseDouble(getPref("shooterPullForwardSpeed"));
            shooterGrabDelay = Double.parseDouble(getPref("shooterGrabDelay"));
            shooterFullRange = Double.parseDouble(getPref("shooterFullRange"));
            shooterShootHighPullback = Double.parseDouble(getPref("shooterShootHighPullback"));
            shooterShootLowPullback = Double.parseDouble(getPref("shooterShootLowPullback"));
            shooterShootTrussPullback = Double.parseDouble(getPref("shooterShootTrussPullback"));
            shooterIntakePullback = Double.parseDouble(getPref("shooterIntakePullback"));
            shooterStowedPullback = Double.parseDouble(getPref("shooterStowedPullback"));
            shooterReloadPullback = Double.parseDouble(getPref("shooterReloadPullback"));
            shooterTrussPassPullback = Double.parseDouble(getPref("shooterTrussPassPullback"));
            intakeTuningMode = "true".equals(getPref("intakeTuningMode"));
            intakeInSpeed = Double.parseDouble(getPref("intakeInSpeed"));
            intakeOutSpeed = Double.parseDouble(getPref("intakeOutSpeed"));
            cameraEnabled = "true".equals(getPref("cameraEnabled"));
            cameraAngle = Double.parseDouble(getPref("cameraAngle"));
            guestMode = "true".equals(getPref("guestMode"));
            
            if (shooterFullRange != 0) {
                shooterPotToInchRatio = (shooterPotBackPosition - shooterPotZeroPosition) / shooterFullRange;
            }
            tilterPotToDegreeRatio = (tilterPot180DegreePosition - tilterPot90DegreePosition) / 90;
            changePreferenceInFastFile("shooterPotToInchRatio", shooterPotToInchRatio + "");
            changePreferenceInFastFile("tilterPotToDegreeRatio", tilterPotToDegreeRatio + "");
            writeToFile();
        } catch (java.lang.NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Changes the contents of the preferences file to the current values in the code
    public static void writeToFile() {
        if (hammerHeadRobotMap || gwrathRobotMap || practiceBot) {
            return;
        }
        DataOutputStream actualFile;
        FileConnection fc;
        if ("".equals(generalFile) || "".equals(fastFile)) {
            System.out.println("File is empty");
            return;
        }
        try {
            fc = (FileConnection) Connector.open("file:///generalPreferences.txt", Connector.WRITE);
            fc.create();
            actualFile = fc.openDataOutputStream();
            for (int i = 0; i < generalFile.length(); i++) {
                actualFile.write(generalFile.charAt(i));
            }
            fc = (FileConnection) Connector.open("file:///fastPreferences.txt", Connector.WRITE);
            fc.create();
            actualFile = fc.openDataOutputStream();
            for (int i = 0; i < fastFile.length(); i++) {
                actualFile.write(fastFile.charAt(i));
            }
        } catch (IOException e) {
            System.out.println("File Writing Failed");
        }
    }

    // Changes the value of a single preference in the code file, but NOT the value of the actual preference or in the actual file
    public static void changePreferenceInGeneralFile(String name, String value) {
        if (hammerHeadRobotMap || gwrathRobotMap || practiceBot) {
            return;
        }
        if ("".equals(generalFile)) {
            System.out.println("File is empty");
            return;
        }
        int startIndex = generalFile.indexOf("\n" + name + " ", 0) + 1;
        String subString = generalFile.substring(startIndex);
        int endIndex = startIndex + subString.indexOf("\n") - 1;
        if (startIndex < 0 || endIndex < 0) {
            System.out.println(name + " not found");
            return;
        }
        generalFile = generalFile.substring(0, startIndex) + name + " " + value + generalFile.substring(endIndex, generalFile.length());
        combinedFile = generalFile + fastFile;
    }

    // Changes the value of a single preference in the code file, but NOT the value of the actual preference or in the actual file
    public static void changePreferenceInFastFile(String name, String value) {
        if (hammerHeadRobotMap || gwrathRobotMap || practiceBot) {
            return;
        }
        if ("".equals(fastFile)) {
            System.out.println("File is empty");
            return;
        }
        int startIndex = fastFile.indexOf("\n" + name + " ", 0) + 1;
        String subString = fastFile.substring(startIndex);
        int endIndex = startIndex + subString.indexOf("\n") - 1;
        if (startIndex < 0 || endIndex < 0) {
            System.out.println(name + " not found");
            return;
        }
        fastFile = fastFile.substring(0, startIndex) + name + " " + value + fastFile.substring(endIndex, fastFile.length());
        combinedFile = generalFile + fastFile;
    }

    // Finds a preference in the file
    public static String getPref(String name) {
        if ("".equals(combinedFile)) {
            System.out.println("File is empty");
            return "0";
        }
        int startIndex = combinedFile.indexOf("\n" + name + " ", 0) + 1;
        String subString = combinedFile.substring(startIndex);
        int endIndex = startIndex + subString.indexOf("\n") - 1;
        if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex) {
            System.out.println(name + " not found");
            return "0";
        }
        return combinedFile.substring(startIndex + name.length() + 1, endIndex);
    }
}