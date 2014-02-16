//ready
package org.usfirst.frc100.Ballrus;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.Vector;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    // Initial declarations of sensors/actuators
    //<editor-fold>
    public static Vector motors = new Vector();
    
    public static RobotDrive driveTrainMainDrive;
    public static SpeedController driveTrainLeftMotor;
    public static SpeedController driveTrainLeftMotor2;//for other robots
    public static SpeedController driveTrainRightMotor;
    public static SpeedController driveTrainRightMotor2;//for other robots
    public static Encoder driveTrainLeftEncoder;
    public static Encoder driveTrainRightEncoder;
    public static Gyro driveTrainGyro;
    public static AnalogChannel driveTrainRangeFinder;
    public static Solenoid driveTrainShifter;
    public static AnalogChannel driveTrainLeftLineReader;
    public static AnalogChannel driveTrainRightLineReader;
    public static AnalogTrigger driveTrainLeftLineTrigger;
    public static AnalogTrigger driveTrainRightLineTrigger;
    public static Counter driveTrainLeftCounter;
    public static Counter driveTrainRightCounter;
    
    public static SpeedController shooterMotor;
    public static DigitalInput shooterForwardLimit;
    public static DigitalInput shooterBackLimit;
    public static AnalogChannel shooterPotentiometer;
    public static Solenoid shooterRelease;
    public static Encoder shooterEncoder;
    public static Relay shooterReadyIndicator;
    
    public static SpeedController intakeTopMotor;
    public static SpeedController intakeSideMotor;
    public static Solenoid intakeTopPiston;
    public static Solenoid intakeSparePiston;
    public static DigitalInput intakeBallDetector;
    
    public static SpeedController tilterMotor;
    public static DigitalInput tilterTopLimit;
    public static DigitalInput tilterBottomLimit;
    public static AnalogChannel tilterPotentiometer;
    
    public static Compressor compressor;
    public static Relay cameraLights;
    //</editor-fold>

    // Initializes actuators and sensors, adds them to livewindow
    public static void init() {
       if (Preferences.practiceBot) {
           Preferences.setPracticeBotPrefs();
       }
       if (Preferences.hammerHeadRobotMap) {
           initHammerHead();
       } else if (Preferences.gwrathRobotMap) {
           initGwrath();
       } else {
           driveTrainLeftMotor = new Talon(1, 1);
           LiveWindow.addActuator("DriveTrain", "LeftMotorMain", (Talon) driveTrainLeftMotor);

            driveTrainRightMotor = new Talon(1, 2);
            LiveWindow.addActuator("DriveTrain", "RightMotorMain", (Talon) driveTrainRightMotor);

            driveTrainLeftEncoder = new Encoder(1, 1, 1, 2, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "LeftEncoder", driveTrainLeftEncoder);
            driveTrainLeftEncoder.setDistancePerPulse(1/Preferences.driveEncoderToInchRatio);
            driveTrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            driveTrainLeftEncoder.start();

            driveTrainRightEncoder = new Encoder(1, 3, 1, 4, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "RightEncoder", driveTrainRightEncoder);
            driveTrainRightEncoder.setDistancePerPulse(1/Preferences.driveEncoderToInchRatio);
            driveTrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            driveTrainRightEncoder.start();

            driveTrainGyro = new Gyro(1, 1);
            LiveWindow.addSensor("DriveTrain", "Gyro", driveTrainGyro);
            driveTrainGyro.setSensitivity(0.007);

            driveTrainRangeFinder = new AnalogChannel(1, 2);
            LiveWindow.addSensor("DriveTrain", "RangeFinder", driveTrainRangeFinder);

            driveTrainShifter = new Solenoid(1);
            LiveWindow.addActuator("DriveTrain", "Shifter", driveTrainShifter);

            driveTrainLeftLineReader = new AnalogChannel(1, 5);
            LiveWindow.addSensor("DriveTrain", "LeftLineReader", driveTrainLeftLineReader);

            driveTrainRightLineReader = new AnalogChannel(1, 6);
            LiveWindow.addSensor("DriveTrain", "RightLineReader", driveTrainRightLineReader);
            
            driveTrainLeftLineTrigger = new AnalogTrigger(driveTrainLeftLineReader);
            driveTrainLeftLineTrigger.setLimitsRaw(Preferences.lowerLimit, Preferences.upperLimit);

            driveTrainRightLineTrigger = new AnalogTrigger(driveTrainRightLineReader);
            driveTrainRightLineTrigger.setLimitsRaw(Preferences.lowerLimit, Preferences.upperLimit);
            
            driveTrainLeftCounter = new Counter(driveTrainLeftLineTrigger);
            driveTrainLeftCounter.setUpSourceEdge(true, true);
            driveTrainLeftCounter.start();
            
            driveTrainRightCounter = new Counter(driveTrainRightLineTrigger);
            driveTrainRightCounter.setUpSourceEdge(true, true);
            driveTrainRightCounter.start();
            
            shooterMotor = new Talon(1, 3);
            LiveWindow.addActuator("Shooter", "Motor", (Talon) shooterMotor);

            shooterForwardLimit = new DigitalInput(1, 5);
            LiveWindow.addSensor("Shooter", "HallEffectForward", shooterForwardLimit);

            shooterBackLimit = new DigitalInput(1, 6);
            LiveWindow.addSensor("Shooter", "HallEffectBack", shooterBackLimit);

            shooterPotentiometer = new AnalogChannel(1, 3);
            LiveWindow.addSensor("Shooter", "Potentiometer", shooterPotentiometer);

            shooterRelease = new Solenoid(2);
            LiveWindow.addActuator("Shooter", "Release", shooterRelease);

            shooterEncoder = new Encoder(1, 7, 1, 8, false, EncodingType.k4X);
            LiveWindow.addSensor("Shooter", "Encoder", shooterEncoder);
            shooterEncoder.setDistancePerPulse(1.0);
            shooterEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            shooterEncoder.start();
            
            intakeTopMotor = new Talon(1, 4);
            LiveWindow.addActuator("Intake", "TopMotor", (Talon) intakeTopMotor);

            intakeSideMotor = new Talon(1, 5);
            LiveWindow.addActuator("Intake", "BottomMotor", (Talon) intakeSideMotor);

            intakeTopPiston = new Solenoid(3);
            LiveWindow.addActuator("Intake", "TopPiston", intakeTopPiston);

            intakeSparePiston = new Solenoid(4);
            LiveWindow.addActuator("Intake", "SparePiston", intakeSparePiston);

            intakeBallDetector = new DigitalInput(1, 9);
            LiveWindow.addSensor("Intake", "BallDetector", intakeBallDetector);

            tilterMotor = new Talon(1, 6);
            LiveWindow.addActuator("Tilter", "Motor", (Talon) tilterMotor);
            
            tilterTopLimit = new DigitalInput(1, 10);
            LiveWindow.addSensor("Tilter", "TopLimit", tilterTopLimit);

            tilterBottomLimit = new DigitalInput(1, 11);
            LiveWindow.addSensor("Tilter", "BottomLimit", tilterBottomLimit);

            tilterPotentiometer = new AnalogChannel(1, 4);
            LiveWindow.addSensor("Tilter", "Potentiometer", tilterPotentiometer);

            compressor = new Compressor(1, 14, 1, 1);
            LiveWindow.addActuator("Compressor", "Compressor", compressor);

            cameraLights = new Relay(1, 2);
            LiveWindow.addActuator("Camera", "LightRing", compressor);

            shooterReadyIndicator = new Relay(1, 3);
            LiveWindow.addActuator("Shooter", "readyIndicator", shooterReadyIndicator);

            driveTrainMainDrive = new RobotDrive(driveTrainLeftMotor, driveTrainRightMotor);

            motors.addElement(driveTrainLeftMotor);
            motors.addElement(driveTrainRightMotor);
            motors.addElement(shooterMotor);
            motors.addElement(intakeTopMotor);
            motors.addElement(intakeSideMotor);
            motors.addElement(tilterMotor);
        }
    }

    // Initializes for hammerhead
    public static void initHammerHead() {
        Preferences.setHammerhead();

        driveTrainLeftMotor = new Victor(1, 4);
        LiveWindow.addActuator("DriveTrain", "LeftMotorMain", (Victor) driveTrainLeftMotor);

        driveTrainLeftMotor2 = new Victor(1, 5);
        LiveWindow.addActuator("DriveTrain", "LeftMotorSlave", (Victor) driveTrainLeftMotor2);

        driveTrainRightMotor = new Victor(1, 2);
        LiveWindow.addActuator("DriveTrain", "RightMotorMain", (Victor) driveTrainRightMotor);

        driveTrainRightMotor2 = new Victor(1, 3);
        LiveWindow.addActuator("DriveTrain", "RightMotorSlave", (Victor) driveTrainRightMotor2);

        driveTrainLeftEncoder = new Encoder(1, 8, 1, 9, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "LeftEncoder", driveTrainLeftEncoder);
        driveTrainLeftEncoder.setDistancePerPulse(1/Preferences.driveEncoderToInchRatio);
        driveTrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainLeftEncoder.start();
        
        driveTrainRightEncoder = new Encoder(1, 6, 1, 7, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "RightEncoder", driveTrainRightEncoder);
        driveTrainRightEncoder.setDistancePerPulse(1/Preferences.driveEncoderToInchRatio);
        driveTrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainRightEncoder.start();
        
        driveTrainGyro = new Gyro(1, 1);
        LiveWindow.addSensor("DriveTrain", "Gyro", driveTrainGyro);
        driveTrainGyro.setSensitivity(0.007);
        
        driveTrainRangeFinder = new AnalogChannel(1, 6);
        LiveWindow.addSensor("DriveTrain", "RangeFinder", driveTrainRangeFinder);

        driveTrainShifter = new Solenoid(1);
        LiveWindow.addActuator("DriveTrain", "Shifter", driveTrainShifter);

        driveTrainLeftLineReader = new AnalogChannel(1, 5);
        LiveWindow.addSensor("DriveTrain", "LeftLineReader", driveTrainLeftLineReader);

        driveTrainRightLineReader = new AnalogChannel(1, 2);
        LiveWindow.addSensor("DriveTrain", "RightLineReader", driveTrainRightLineReader);
        
        driveTrainLeftLineTrigger = new AnalogTrigger(driveTrainLeftLineReader);
        driveTrainLeftLineTrigger.setLimitsRaw(Preferences.lowerLimit, Preferences.upperLimit);

        driveTrainRightLineTrigger = new AnalogTrigger(driveTrainRightLineReader);
        driveTrainRightLineTrigger.setLimitsRaw(Preferences.lowerLimit, Preferences.upperLimit);
        
        shooterMotor = new Victor(1, 9);
        LiveWindow.addActuator("Shooter", "Motor", (Victor) shooterMotor);

        shooterForwardLimit = new DigitalInput(1, 5);
        LiveWindow.addSensor("Shooter", "HallEffectForward", shooterForwardLimit);

        shooterBackLimit = new DigitalInput(1, 1);
        LiveWindow.addSensor("Shooter", "HallEffectBack", shooterBackLimit);

        shooterPotentiometer = new AnalogChannel(1, 3);
        LiveWindow.addSensor("Shooter", "Potentiometer", shooterPotentiometer);

        shooterRelease = new Solenoid(4);
        LiveWindow.addActuator("Shooter", "Release", shooterRelease);

        shooterEncoder = new Encoder(1, 2, 1, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("Shooter", "Encoder", shooterEncoder);
        shooterEncoder.setDistancePerPulse(1.0);
        shooterEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        shooterEncoder.start();
        
        intakeTopMotor = new Victor(1, 6);
        LiveWindow.addActuator("Intake", "TopMotor", (Victor) intakeTopMotor);

        intakeSideMotor = new Victor(1, 7);
        LiveWindow.addActuator("Intake", "BottomMotor", (Victor) intakeSideMotor);

        intakeTopPiston = new Solenoid(2);
        LiveWindow.addActuator("Intake", "TopPiston", intakeTopPiston);

        intakeSparePiston = new Solenoid(3);
        LiveWindow.addActuator("Intake", "BottomPiston", intakeSparePiston);

        intakeBallDetector = new DigitalInput(1, 4);
        LiveWindow.addSensor("Intake", "BallDetector", intakeBallDetector);

        tilterMotor = new Victor(1, 8);
        LiveWindow.addActuator("Tilter", "Motor", (Victor) tilterMotor);
        
        tilterTopLimit = new DigitalInput(1, 10);
        LiveWindow.addSensor("Tilter", "TopLimit", tilterTopLimit);
        
        tilterBottomLimit = new DigitalInput(1, 11);
        LiveWindow.addSensor("Tilter", "BottomLimit", tilterBottomLimit);

        tilterPotentiometer = new AnalogChannel(1, 4);
        LiveWindow.addSensor("Tilter", "Potentiometer", tilterPotentiometer);

        compressor = new Compressor(1, 14, 1, 1);
        LiveWindow.addActuator("Compressor", "Compressor", compressor);

        cameraLights = new Relay(1, 2);
        LiveWindow.addActuator("Camera", "LightRing", compressor);

        shooterReadyIndicator = new Relay(1, 3);
        LiveWindow.addActuator("Shooter", "readyIndicator", shooterReadyIndicator);
        
        driveTrainLeftCounter = new Counter(driveTrainLeftLineTrigger);
        driveTrainLeftCounter.setUpSourceEdge(true, true);
        driveTrainLeftCounter.start();

        driveTrainRightCounter = new Counter(driveTrainRightLineTrigger);
        driveTrainRightCounter.setUpSourceEdge(true, true);
        driveTrainRightCounter.start();

        driveTrainMainDrive = new RobotDrive(driveTrainLeftMotor, driveTrainLeftMotor2, driveTrainRightMotor, driveTrainRightMotor2);
        driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);

        motors.addElement(driveTrainLeftMotor);
        motors.addElement(driveTrainLeftMotor2);
        motors.addElement(driveTrainRightMotor);
        motors.addElement(driveTrainRightMotor2);
        motors.addElement(shooterMotor);
        motors.addElement(intakeTopMotor);
        motors.addElement(intakeSideMotor);
        motors.addElement(tilterMotor);
    }

    // Initializes for gwrath
    public static void initGwrath() {
        Preferences.setGwrath();
        
        driveTrainLeftMotor = new Victor(1, 6);
            LiveWindow.addActuator("DriveTrain", "LeftMotorMain", (Victor) driveTrainLeftMotor);

            driveTrainLeftMotor2 = new Victor(1, 7);
            LiveWindow.addActuator("DriveTrain", "LeftMotorSlave", (Victor) driveTrainLeftMotor2);

            driveTrainRightMotor = new Victor(1, 8);
            LiveWindow.addActuator("DriveTrain", "RightMotorMain", (Victor) driveTrainRightMotor);

            driveTrainRightMotor2 = new Victor(1, 9);
            LiveWindow.addActuator("DriveTrain", "RightMotorSlave", (Victor) driveTrainRightMotor2);

            driveTrainLeftEncoder = new Encoder(1, 4, 1, 3, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "LeftEncoder", driveTrainLeftEncoder);
            driveTrainLeftEncoder.setDistancePerPulse(1/Preferences.driveEncoderToInchRatio);
            driveTrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            driveTrainLeftEncoder.setReverseDirection(true);
            driveTrainLeftEncoder.start();

            driveTrainRightEncoder = new Encoder(1, 1, 1, 2, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "RightEncoder", driveTrainRightEncoder);
            driveTrainRightEncoder.setDistancePerPulse(1/Preferences.driveEncoderToInchRatio);
            driveTrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            driveTrainRightEncoder.setReverseDirection(true);
            driveTrainRightEncoder.start();

            driveTrainGyro = new Gyro(1, 1);
            LiveWindow.addSensor("DriveTrain", "Gyro", driveTrainGyro);
            driveTrainGyro.setSensitivity(0.007);

            driveTrainRangeFinder = new AnalogChannel(1, 2);
            LiveWindow.addSensor("DriveTrain", "RangeFinder", driveTrainRangeFinder);

            driveTrainShifter = new Solenoid(1);
            LiveWindow.addActuator("DriveTrain", "Shifter", driveTrainShifter);

            driveTrainLeftLineReader = new AnalogChannel(1, 6);
            LiveWindow.addSensor("DriveTrain", "LeftLineReader", driveTrainLeftLineReader);

            driveTrainRightLineReader = new AnalogChannel(1, 7);
            LiveWindow.addSensor("DriveTrain", "RightLineReader", driveTrainRightLineReader);
            
            driveTrainLeftLineTrigger = new AnalogTrigger(driveTrainLeftLineReader);
            driveTrainLeftLineTrigger.setLimitsRaw(Preferences.lowerLimit, Preferences.upperLimit);

            driveTrainRightLineTrigger = new AnalogTrigger(driveTrainRightLineReader);
            driveTrainRightLineTrigger.setLimitsRaw(Preferences.lowerLimit, Preferences.upperLimit);
            
            shooterMotor = new Victor(1, 2);
            LiveWindow.addActuator("Shooter", "Motor", (Victor) shooterMotor);

            shooterForwardLimit = new DigitalInput(1, 5);
            LiveWindow.addSensor("Shooter", "HallEffectForward", shooterForwardLimit);

            shooterBackLimit = new DigitalInput(1, 6);
            LiveWindow.addSensor("Shooter", "HallEffectBack", shooterBackLimit);

            shooterPotentiometer = new AnalogChannel(1, 3);
            LiveWindow.addSensor("Shooter", "Potentiometer", shooterPotentiometer);

            shooterRelease = new Solenoid(4);
            LiveWindow.addActuator("Shooter", "Release", shooterRelease);

            shooterEncoder = new Encoder(1, 7, 1, 8, false, EncodingType.k4X);
            LiveWindow.addSensor("Shooter", "Encoder", shooterEncoder);
            shooterEncoder.setDistancePerPulse(1.0);
            shooterEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            shooterEncoder.start();
            intakeTopMotor = new Victor(1, 4);
            LiveWindow.addActuator("Intake", "TopMotor", (Victor) intakeTopMotor);

            intakeSideMotor = new Victor(1, 3);
            LiveWindow.addActuator("Intake", "SideMotor", (Victor) intakeSideMotor);

            intakeTopPiston = new Solenoid(2);
            LiveWindow.addActuator("Intake", "TopPiston", intakeTopPiston);

            intakeSparePiston = new Solenoid(3);
            LiveWindow.addActuator("Intake", "BottomPiston", intakeSparePiston);

            intakeBallDetector = new DigitalInput(1, 9);
            LiveWindow.addSensor("Intake", "BallDetector", intakeBallDetector);

            tilterMotor = new Victor(1, 1);
            LiveWindow.addActuator("Tilter", "Motor", (Victor) tilterMotor);

            tilterPotentiometer = new AnalogChannel(1, 4);
            LiveWindow.addSensor("Tilter", "Potentiometer", tilterPotentiometer);
           
            tilterTopLimit = new DigitalInput(1, 10);
            LiveWindow.addSensor("Tilter", "TopLimit", tilterTopLimit);

            tilterBottomLimit = new DigitalInput(1, 11);
            LiveWindow.addSensor("Tilter", "BottomLimit", tilterBottomLimit);

            compressor = new Compressor(1, 14, 1, 1);
            LiveWindow.addActuator("Compressor", "Compressor", compressor);

            cameraLights = new Relay(1, 2);
            LiveWindow.addActuator("Camera", "LightRing", compressor);

            shooterReadyIndicator = new Relay(1, 3);
            LiveWindow.addActuator("Shooter", "readyIndicator", shooterReadyIndicator);
            
            driveTrainLeftCounter = new Counter(driveTrainLeftLineTrigger);
            driveTrainLeftCounter.setUpSourceEdge(true, true);
            driveTrainLeftCounter.start();

            driveTrainRightCounter = new Counter(driveTrainRightLineTrigger);
            driveTrainRightCounter.setUpSourceEdge(true, true);
            driveTrainRightCounter.start();

            driveTrainMainDrive = new RobotDrive(driveTrainLeftMotor, driveTrainLeftMotor2, driveTrainRightMotor, driveTrainRightMotor2);
            driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
            driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
            driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
            driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);

            motors.addElement(driveTrainLeftMotor);
            motors.addElement(driveTrainLeftMotor2);
            motors.addElement(driveTrainRightMotor);
            motors.addElement(driveTrainRightMotor2);
            motors.addElement(shooterMotor);
            motors.addElement(intakeTopMotor);
            motors.addElement(intakeSideMotor);
            motors.addElement(tilterMotor);
    }

    // Sets all motors on the robot to zero
    public static void stopAllMotors() {
        for (int i = 0; i < motors.size(); i++) {
            ((SpeedController) motors.elementAt(i)).set(0);
            driveTrainMainDrive.stopMotor();
        }
    }

    // Makes all motors safety enabled
    public static void safeAllMotors() {
        for (int i = 0; i < motors.size(); i++) {
            ((MotorSafety) motors.elementAt(i)).setSafetyEnabled(true);
            driveTrainMainDrive.setSafetyEnabled(true);
        }
    }

    // Makes all motors not safety enabled (for test mode?)
    public static void unsafeAllMotors() {
        for (int i = 0; i < motors.size(); i++) {
            ((MotorSafety) motors.elementAt(i)).setSafetyEnabled(false);
            driveTrainMainDrive.setSafetyEnabled(false);
        }
    }
}
