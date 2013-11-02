package org.usfirst.frc100.Mk3;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.Vector;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
//Digital I/O sensors
    //Drive Train
    public static final Encoder driveRightEncoder = new Encoder(3, 4);
    public static final Encoder driveLeftEncoder = new Encoder(5, 6, true); //The 'true' means the encoder is mounted backwards
    //Frisbee sensors
    public static final DigitalInput hopperFrisbeeSensor = new DigitalInput(7);
    public static final DigitalInput shooterFrisbeeSensor = new DigitalInput(8);
    public static final DigitalInput intakeFrisbeeSensor = new DigitalInput(9);
    //intake
    public static final DigitalInput intakeLimit = new DigitalInput(11);
//Analog sensors
    //Drive Train
    public static final Gyro driveGyro = new Gyro(1);
    //Intake
    //public static final AnalogChannel intakeTiltPotentiometer = new AnalogChannel(2);
//PWM Outputs
    //Vector of all motors
    public static final Vector motors = new Vector();
    //Drive Train
    public static final Talon driveLeftMotor = new Talon(2);
    public static final Talon driveRightMotor = new Talon(1);
    public static final RobotDrive driveRobotDrive = new RobotDrive(driveLeftMotor, driveRightMotor);
    //Shooter
    public static final Talon shooterFrontMotor = new Talon(3);
    public static final Talon shooterBackMotor = new Talon(4);
    //Intake
    public static final Talon intakeFrisbeeMotor = new Talon(5);
    public static final Talon intakeTiltMotor = new Talon(6);
//Solenoids
    //Shifter
    public static final DoubleSolenoid shifterGear = new DoubleSolenoid(1, 2);
    //Hanger
    public static final DoubleSolenoid hangerPistons = new DoubleSolenoid(3, 4);
    //Shooter
    public static final DoubleSolenoid tiltPistons = new DoubleSolenoid(5, 6);
    //Feeder
    public static final DoubleSolenoid feederPistons = new DoubleSolenoid(7, 8);
    //Intake
    public static final DoubleSolenoid intakeTiltPistons = new DoubleSolenoid(2, 1, 2);
    public static final DoubleSolenoid intakeDiscGripper1 = new DoubleSolenoid(2, 3, 4);
    public static final DoubleSolenoid intakeDiscGripper2 = new DoubleSolenoid(2, 5, 6);
//Relays
    public static final Compressor compressor = new Compressor(14, 3);
    public static final Relay cameraLights = new Relay(2);

    public static void init() {
        //Filling vector of motors
        motors.addElement(driveLeftMotor);
        motors.addElement(driveRightMotor);
        motors.addElement(driveRobotDrive);
        motors.addElement(shooterFrontMotor);
        motors.addElement(shooterBackMotor);

        //LiveWindow display
        //Drive Train
        LiveWindow.addSensor("DriveTrain", "RightEncoder", driveRightEncoder);
        LiveWindow.addSensor("DriveTrain", "LeftEncoder", driveLeftEncoder);
        LiveWindow.addActuator("DriveTrain", "LeftMotor2", driveLeftMotor);
        LiveWindow.addActuator("DriveTrain", "RightMotor1", driveRightMotor);
        LiveWindow.addSensor("DriveTrain", "Gyro", driveGyro);
        //Shooter
        LiveWindow.addActuator("Shooter", "FrontMotor3", shooterFrontMotor);
        LiveWindow.addActuator("Shooter", "BackMotor4", shooterBackMotor);
        LiveWindow.addActuator("Shooter", "TiltPistons", tiltPistons);
        //Shifter
        LiveWindow.addActuator("Shifter", "Gear", shifterGear);
        //Hanger
        LiveWindow.addActuator("Hanger", "HangerPistons", hangerPistons);
        //Feeder
        LiveWindow.addActuator("Feeder", "FeederPistons", feederPistons);
        //Intake
        LiveWindow.addActuator("Intake", "IntakeFrisbeeMotor", intakeFrisbeeMotor);
        LiveWindow.addActuator("Intake", "IntakeTiltMotor", intakeTiltMotor);
        //LiveWindow.addSensor("Intake", "IntakeTiltPotentiometer", intakeTiltPotentiometer);
        //Frisbee sensors
        LiveWindow.addSensor("FrisbeeSensors", "HopperFrisbeeSensor", hopperFrisbeeSensor);
        LiveWindow.addSensor("FrisbeeSensors", "ShooterFrisbeeSensor", shooterFrisbeeSensor);
        LiveWindow.addSensor("FrisbeeSensors", "IntakeFrisbeeSensor", intakeFrisbeeSensor);
        //Relays
        LiveWindow.addSensor("Relays", "Compressor", compressor);
        LiveWindow.addActuator("Relays", "CameraLights", cameraLights);

        Preferences p = Preferences.getInstance();
        if (!p.containsKey("RobotSafetyExpiration")) {
            p.putDouble("RobotSafetyExpiration", 0.5);
        }
    }//end init

    //puts safeties on all motors
    public static void safe() {
        Preferences p = Preferences.getInstance();
        final double expiration = p.getDouble("RobotSafetyExpiration", 0.0);
        for (int i = 0; i < motors.size(); i++) {
            ((MotorSafety) motors.elementAt(i)).setSafetyEnabled(true);
            ((MotorSafety) motors.elementAt(i)).setExpiration(expiration);
        }
    }//end safe

    //removes safeties from all motors (for LiveWindow)
    public static void unSafe() {
        for (int i = 0; i < motors.size(); i++) {
            ((MotorSafety) motors.elementAt(i)).setSafetyEnabled(false);
        }
    }//end unSafe
}//end RobotMap
