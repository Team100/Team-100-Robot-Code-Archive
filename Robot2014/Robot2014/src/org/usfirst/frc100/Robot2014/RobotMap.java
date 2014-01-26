//change motor types
package org.usfirst.frc100.Robot2014;

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

    public static Vector motors = new Vector();

    public static RobotDrive driveTrainMainDrive;
    public static RobotDrive driveTrainSlaveDrive;
    public static SpeedController driveTrainLeftMotorMain;
    public static SpeedController driveTrainLeftMotorSlave;
    public static SpeedController driveTrainRightMotorMain;
    public static SpeedController driveTrainRightMotorSlave;
    public static Encoder driveTrainLeftEncoder;
    public static Encoder driveTrainRightEncoder;
    public static Gyro driveTrainGyro;
    public static AnalogChannel driveTrainRangeFinder;
    public static DoubleSolenoid driveTrainShifter;
    public static AnalogChannel driveTrainLeftLineReader;
    public static AnalogChannel driveTrainRightLineReader;
    public static AnalogTrigger driveTrainLeftLineTrigger;
    public static AnalogTrigger driveTrainRightLineTrigger;

    public static SpeedController shooterMotor;
    public static DigitalInput shooterHallEffectForward;
    public static DigitalInput shooterHallEffectBack;
    public static AnalogChannel shooterPotentiometer;
    public static DoubleSolenoid shooterRelease;
    public static Encoder shooterEncoder;
    public static Relay shooterReadyIndicator;

    public static SpeedController intakeTopMotor;
    public static SpeedController intakeBottomMotor;
    public static DoubleSolenoid intakeTopPiston;
    public static DoubleSolenoid intakeBottomPiston;
    public static DigitalInput intakeBallDetector;

    public static SpeedController tilterMotor;
    public static AnalogChannel tilterPotentiometer;

    public static Compressor compressor;
    public static Relay cameraLights;

    // Initializes actuators and sensors, adds them to livewindow
    public static void init() {
        if (Preferences.hammerHeadRobotMap) {
            initHammerHead();
        } else if (Preferences.gwrathRobotMap) {
            initGwrath();
        } else {
            driveTrainLeftMotorMain = new Victor(1, 1);
            LiveWindow.addActuator("DriveTrain", "LeftMotorMain", (Victor) driveTrainLeftMotorMain);

            driveTrainLeftMotorSlave = new Victor(1, 2);
            LiveWindow.addActuator("DriveTrain", "LeftMotorSlave", (Victor) driveTrainLeftMotorSlave);

            driveTrainRightMotorMain = new Victor(1, 3);
            LiveWindow.addActuator("DriveTrain", "RightMotorMain", (Victor) driveTrainRightMotorMain);

            driveTrainRightMotorSlave = new Victor(1, 4);
            LiveWindow.addActuator("DriveTrain", "RightMotorSlave", (Victor) driveTrainRightMotorSlave);

            driveTrainLeftEncoder = new Encoder(1, 1, 1, 2, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "LeftEncoder", driveTrainLeftEncoder);
            driveTrainLeftEncoder.setDistancePerPulse(1.0);
            driveTrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            driveTrainLeftEncoder.start();

            driveTrainRightEncoder = new Encoder(1, 3, 1, 4, false, EncodingType.k4X);
            LiveWindow.addSensor("DriveTrain", "RightEncoder", driveTrainRightEncoder);
            driveTrainRightEncoder.setDistancePerPulse(1.0);
            driveTrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            driveTrainRightEncoder.start();

            driveTrainGyro = new Gyro(1, 1);
            LiveWindow.addSensor("DriveTrain", "Gyro", driveTrainGyro);
            driveTrainGyro.setSensitivity(0.007);

            driveTrainRangeFinder = new AnalogChannel(1, 2);
            LiveWindow.addSensor("DriveTrain", "RangeFinder", driveTrainRangeFinder);

            driveTrainShifter = new DoubleSolenoid(1, 2);
            LiveWindow.addActuator("DriveTrain", "Shifter", driveTrainShifter);

            driveTrainLeftLineReader = new AnalogChannel(1, 5);
            LiveWindow.addSensor("DriveTrain", "LeftLineReader", driveTrainLeftLineReader);

            driveTrainRightLineReader = new AnalogChannel(1, 6);
            LiveWindow.addSensor("DriveTrain", "RightLineReader", driveTrainRightLineReader);
            
            driveTrainLeftLineTrigger = new AnalogTrigger(driveTrainLeftLineReader);

            driveTrainRightLineTrigger = new AnalogTrigger(driveTrainRightLineReader);
            
            shooterMotor = new Victor(1, 5);
            LiveWindow.addActuator("Shooter", "Motor", (Victor) shooterMotor);

            shooterHallEffectForward = new DigitalInput(1, 5);
            LiveWindow.addSensor("Shooter", "HallEffectForward", shooterHallEffectForward);

            shooterHallEffectBack = new DigitalInput(1, 6);
            LiveWindow.addSensor("Shooter", "HallEffectBack", shooterHallEffectBack);

            shooterPotentiometer = new AnalogChannel(1, 3);
            LiveWindow.addSensor("Shooter", "Potentiometer", shooterPotentiometer);

            shooterRelease = new DoubleSolenoid(7, 8);
            LiveWindow.addActuator("Shooter", "Release", shooterRelease);

            shooterEncoder = new Encoder(1, 7, 1, 8, false, EncodingType.k4X);
            LiveWindow.addSensor("Shooter", "Encoder", shooterEncoder);
            shooterEncoder.setDistancePerPulse(1.0);
            shooterEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
            shooterEncoder.start();
            intakeTopMotor = new Victor(1, 6);
            LiveWindow.addActuator("Intake", "TopMotor", (Victor) intakeTopMotor);

            intakeBottomMotor = new Victor(1, 7);
            LiveWindow.addActuator("Intake", "BottomMotor", (Victor) intakeBottomMotor);

            intakeTopPiston = new DoubleSolenoid(3, 4);
            LiveWindow.addActuator("Intake", "TopPiston", intakeTopPiston);

            intakeBottomPiston = new DoubleSolenoid(5, 6);
            LiveWindow.addActuator("Intake", "BottomPiston", intakeBottomPiston);

            intakeBallDetector = new DigitalInput(1, 9);
            LiveWindow.addSensor("Intake", "BallDetector", intakeBallDetector);

            tilterMotor = new Victor(1, 8);
            LiveWindow.addActuator("Tilter", "Motor", (Victor) tilterMotor);

            tilterPotentiometer = new AnalogChannel(1, 4);
            LiveWindow.addSensor("Tilter", "Potentiometer", tilterPotentiometer);

            compressor = new Compressor(1, 14, 1, 1);
            LiveWindow.addActuator("Compressor", "Compressor", compressor);

            cameraLights = new Relay(1, 2);
            LiveWindow.addActuator("Camera", "LightRing", compressor);

            shooterReadyIndicator = new Relay(1, 3);
            LiveWindow.addActuator("Shooter", "readyIndicator", shooterReadyIndicator);

            driveTrainMainDrive = new RobotDrive(driveTrainLeftMotorMain, driveTrainRightMotorMain);
            driveTrainSlaveDrive = new RobotDrive(driveTrainLeftMotorSlave, driveTrainRightMotorSlave);

            motors.addElement(driveTrainLeftMotorMain);
            motors.addElement(driveTrainLeftMotorSlave);
            motors.addElement(driveTrainRightMotorMain);
            motors.addElement(driveTrainRightMotorSlave);
            motors.addElement(shooterMotor);
            motors.addElement(intakeTopMotor);
            motors.addElement(intakeBottomMotor);
            motors.addElement(tilterMotor);
        }
    }

    // Initializes for hammerhead
    public static void initHammerHead() {
        Preferences.setHammerhead();

        driveTrainLeftMotorMain = new Victor(1, 4);
        LiveWindow.addActuator("DriveTrain", "LeftMotorMain", (Victor) driveTrainLeftMotorMain);

        driveTrainLeftMotorSlave = new Victor(1, 5);
        LiveWindow.addActuator("DriveTrain", "LeftMotorSlave", (Victor) driveTrainLeftMotorSlave);

        driveTrainRightMotorMain = new Victor(1, 2);
        LiveWindow.addActuator("DriveTrain", "RightMotorMain", (Victor) driveTrainRightMotorMain);

        driveTrainRightMotorSlave = new Victor(1, 3);
        LiveWindow.addActuator("DriveTrain", "RightMotorSlave", (Victor) driveTrainRightMotorSlave);

        driveTrainLeftEncoder = new Encoder(1, 8, 1, 9, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "LeftEncoder", driveTrainLeftEncoder);
        driveTrainLeftEncoder.setDistancePerPulse(1.0);
        driveTrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainLeftEncoder.start();
        
        driveTrainRightEncoder = new Encoder(1, 6, 1, 7, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "RightEncoder", driveTrainRightEncoder);
        driveTrainRightEncoder.setDistancePerPulse(1.0);
        driveTrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainRightEncoder.start();
        
        driveTrainGyro = new Gyro(1, 1);
        LiveWindow.addSensor("DriveTrain", "Gyro", driveTrainGyro);
        driveTrainGyro.setSensitivity(0.007);
        
        driveTrainRangeFinder = new AnalogChannel(1, 6);
        LiveWindow.addSensor("DriveTrain", "RangeFinder", driveTrainRangeFinder);

        driveTrainShifter = new DoubleSolenoid(1, 2);
        LiveWindow.addActuator("DriveTrain", "Shifter", driveTrainShifter);

        driveTrainLeftLineReader = new AnalogChannel(1, 5);
        LiveWindow.addSensor("DriveTrain", "LeftLineReader", driveTrainLeftLineReader);

        driveTrainRightLineReader = new AnalogChannel(1, 2);
        LiveWindow.addSensor("DriveTrain", "RightLineReader", driveTrainRightLineReader);
        
        driveTrainLeftLineTrigger = new AnalogTrigger(driveTrainLeftLineReader);

        driveTrainRightLineTrigger = new AnalogTrigger(driveTrainRightLineReader);
        
        shooterMotor = new Victor(1, 9);
        LiveWindow.addActuator("Shooter", "Motor", (Victor) shooterMotor);

        shooterHallEffectForward = new DigitalInput(1, 5);
        LiveWindow.addSensor("Shooter", "HallEffectForward", shooterHallEffectForward);

        shooterHallEffectBack = new DigitalInput(1, 1);
        LiveWindow.addSensor("Shooter", "HallEffectBack", shooterHallEffectBack);

        shooterPotentiometer = new AnalogChannel(1, 3);
        LiveWindow.addSensor("Shooter", "Potentiometer", shooterPotentiometer);

        shooterRelease = new DoubleSolenoid(7, 8);
        LiveWindow.addActuator("Shooter", "Release", shooterRelease);

        shooterEncoder = new Encoder(1, 2, 1, 3, false, EncodingType.k4X);
        LiveWindow.addSensor("Shooter", "Encoder", shooterEncoder);
        shooterEncoder.setDistancePerPulse(1.0);
        shooterEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        shooterEncoder.start();
        
        intakeTopMotor = new Victor(1, 6);
        LiveWindow.addActuator("Intake", "TopMotor", (Victor) intakeTopMotor);

        intakeBottomMotor = new Victor(1, 7);
        LiveWindow.addActuator("Intake", "BottomMotor", (Victor) intakeBottomMotor);

        intakeTopPiston = new DoubleSolenoid(3, 4);
        LiveWindow.addActuator("Intake", "TopPiston", intakeTopPiston);

        intakeBottomPiston = new DoubleSolenoid(5, 6);
        LiveWindow.addActuator("Intake", "BottomPiston", intakeBottomPiston);

        intakeBallDetector = new DigitalInput(1, 4);
        LiveWindow.addSensor("Intake", "BallDetector", intakeBallDetector);

        tilterMotor = new Victor(1, 8);
        LiveWindow.addActuator("Tilter", "Motor", (Victor) tilterMotor);

        tilterPotentiometer = new AnalogChannel(1, 4);
        LiveWindow.addSensor("Tilter", "Potentiometer", tilterPotentiometer);

        compressor = new Compressor(1, 14, 1, 1);
        LiveWindow.addActuator("Compressor", "Compressor", compressor);

        cameraLights = new Relay(1, 2);
        LiveWindow.addActuator("Camera", "LightRing", compressor);

        shooterReadyIndicator = new Relay(1, 3);
        LiveWindow.addActuator("Shooter", "readyIndicator", shooterReadyIndicator);

        driveTrainMainDrive = new RobotDrive(driveTrainLeftMotorMain, driveTrainRightMotorMain);
        driveTrainSlaveDrive = new RobotDrive(driveTrainLeftMotorSlave, driveTrainRightMotorSlave);
        driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        driveTrainMainDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        driveTrainSlaveDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        driveTrainSlaveDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);

        motors.addElement(driveTrainLeftMotorMain);
        motors.addElement(driveTrainLeftMotorSlave);
        motors.addElement(driveTrainRightMotorMain);
        motors.addElement(driveTrainRightMotorSlave);
        motors.addElement(shooterMotor);
        motors.addElement(intakeTopMotor);
        motors.addElement(intakeBottomMotor);
        motors.addElement(tilterMotor);
    }

    // Initializes for gwrath
    public static void initGwrath() {
        Preferences.setGwrath();
        
        driveTrainLeftEncoder = new Encoder(1, 4, 1, 2, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "lEncoder", driveTrainLeftEncoder);
        driveTrainLeftEncoder.setDistancePerPulse(1.0);
        driveTrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainLeftEncoder.start();
        
        driveTrainRightEncoder = new Encoder(1, 5, 1, 6, false, EncodingType.k4X);
        LiveWindow.addSensor("DriveTrain", "rEncoder", driveTrainRightEncoder);
        driveTrainRightEncoder.setDistancePerPulse(1.0);
        driveTrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainRightEncoder.start();
        
        driveTrainShifter = new DoubleSolenoid(1, 2);
        LiveWindow.addActuator("DriveTrain", "Shifter", driveTrainShifter);
        
        driveTrainGyro = new Gyro(1, 1);
        LiveWindow.addSensor("DriveTrain", "gyro", driveTrainGyro);
        driveTrainGyro.setSensitivity(0.007);
        
        driveTrainRangeFinder = new AnalogChannel(1, 2);
        LiveWindow.addSensor("DriveTrain", "RangeFinder", driveTrainRangeFinder);

        driveTrainLeftLineReader = new AnalogChannel(1, 6);
        LiveWindow.addSensor("DriveTrain", "lReader", driveTrainLeftLineReader);

        driveTrainRightLineReader = new AnalogChannel(1, 7);
        LiveWindow.addSensor("DriveTrain", "rReader", driveTrainRightLineReader);

        driveTrainLeftLineTrigger = new AnalogTrigger(driveTrainLeftLineReader);

        driveTrainRightLineTrigger = new AnalogTrigger(driveTrainRightLineReader);

        driveTrainLeftMotorMain = new Jaguar(1, 6);
        LiveWindow.addActuator("DriveTrain", "leftA", (Jaguar) driveTrainLeftMotorMain);

        driveTrainLeftMotorSlave = new Jaguar(1, 7);
        LiveWindow.addActuator("DriveTrain", "leftB", (Jaguar) driveTrainLeftMotorSlave);

        driveTrainRightMotorMain = new Jaguar(1, 8);
        LiveWindow.addActuator("DriveTrain", "rightA", (Jaguar) driveTrainRightMotorMain);

        driveTrainRightMotorSlave = new Jaguar(1, 9);
        LiveWindow.addActuator("DriveTrain", "rightB", (Jaguar) driveTrainRightMotorSlave);

        driveTrainMainDrive = new RobotDrive(driveTrainLeftMotorMain, driveTrainRightMotorMain);
        
        driveTrainMainDrive.setSafetyEnabled(true);
        driveTrainMainDrive.setExpiration(0.1);
        driveTrainMainDrive.setSensitivity(0.5);
        driveTrainMainDrive.setMaxOutput(1.0);
        
        driveTrainSlaveDrive = new RobotDrive(driveTrainLeftMotorSlave, driveTrainRightMotorSlave);

        driveTrainSlaveDrive.setSafetyEnabled(true);
        driveTrainSlaveDrive.setExpiration(0.1);
        driveTrainSlaveDrive.setSensitivity(0.5);
        driveTrainSlaveDrive.setMaxOutput(1.0);

        shooterMotor = new Victor(1, 5);
        LiveWindow.addActuator("Shooter", "Motor", (Victor) shooterMotor);

        shooterHallEffectForward = new DigitalInput(1, 5);
        LiveWindow.addSensor("Shooter", "HallEffectForward", shooterHallEffectForward);

        shooterHallEffectBack = new DigitalInput(1, 6);
        LiveWindow.addSensor("Shooter", "HallEffectBack", shooterHallEffectBack);

        shooterPotentiometer = new AnalogChannel(1, 3);
        LiveWindow.addSensor("Shooter", "Potentiometer", shooterPotentiometer);

        shooterRelease = new DoubleSolenoid(7, 8);
        LiveWindow.addActuator("Shooter", "Release", shooterRelease);

        shooterEncoder = new Encoder(1, 7, 1, 8, false, EncodingType.k4X);
        LiveWindow.addSensor("Shooter", "Encoder", shooterEncoder);
        shooterEncoder.setDistancePerPulse(1.0);
        shooterEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        shooterEncoder.start();
        
        intakeTopMotor = new Victor(1, 6);
        LiveWindow.addActuator("Intake", "TopMotor", (Victor) intakeTopMotor);

        intakeBottomMotor = new Victor(1, 7);
        LiveWindow.addActuator("Intake", "BottomMotor", (Victor) intakeBottomMotor);

        intakeTopPiston = new DoubleSolenoid(3, 4);
        LiveWindow.addActuator("Intake", "TopPiston", intakeTopPiston);

        intakeBottomPiston = new DoubleSolenoid(5, 6);
        LiveWindow.addActuator("Intake", "BottomPiston", intakeBottomPiston);

        intakeBallDetector = new DigitalInput(1, 9);
        LiveWindow.addSensor("Intake", "BallDetector", intakeBallDetector);

        tilterMotor = new Victor(1, 8);
        LiveWindow.addActuator("Tilter", "Motor", (Victor) tilterMotor);

        tilterPotentiometer = new AnalogChannel(1, 4);
        LiveWindow.addSensor("Tilter", "Potentiometer", tilterPotentiometer);

        compressor = new Compressor(1, 14, 1, 1);
        LiveWindow.addActuator("Compressor", "Compressor", compressor);

        cameraLights = new Relay(1, 2);
        LiveWindow.addActuator("Camera", "LightRing", compressor);

        shooterReadyIndicator = new Relay(1, 3);
        LiveWindow.addActuator("Shooter", "readyIndicator", shooterReadyIndicator);

        motors.addElement(driveTrainLeftMotorMain);
        motors.addElement(driveTrainLeftMotorSlave);
        motors.addElement(driveTrainRightMotorMain);
        motors.addElement(driveTrainRightMotorSlave);
        motors.addElement(shooterMotor);
        motors.addElement(intakeTopMotor);
        motors.addElement(intakeBottomMotor);
        motors.addElement(tilterMotor);
    }

    // Sets all motors on the robot to zero
    public static void stopAllMotors() {
        for (int i = 0; i < motors.size(); i++) {
            ((SpeedController) motors.elementAt(i)).set(0);
            driveTrainMainDrive.stopMotor();
            driveTrainSlaveDrive.stopMotor();
        }
    }

    // Makes all motors safety enabled
    public static void safeAllMotors() {
        for (int i = 0; i < motors.size(); i++) {
            ((MotorSafety) motors.elementAt(i)).setSafetyEnabled(true);
            driveTrainMainDrive.setSafetyEnabled(true);
            driveTrainSlaveDrive.setSafetyEnabled(true);
        }
    }

    // Makes all motors not safety enabled (for test mode?)
    public static void unsafeAllMotors() {
        for (int i = 0; i < motors.size(); i++) {
            ((MotorSafety) motors.elementAt(i)).setSafetyEnabled(false);
            driveTrainMainDrive.setSafetyEnabled(false);
            driveTrainSlaveDrive.setSafetyEnabled(false);
        }
    }
}
