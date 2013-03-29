package org.usfirst.frc100.FrisBeast;

import edu.wpi.first.wpilibj.*;
//import edu.wpi.first.wpilibj.ADXL345_I2C.DataFormat_Range;
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
    public static final Encoder driveRightEncoder = new Encoder(3,4);
    public static final Encoder driveLeftEncoder = new Encoder(5,6);
    //Shooter
    public static final DigitalInput shooterFrontHalfEncoder = new DigitalInput(1);
    public static final DigitalInput shooterBackHalfEncoder = new DigitalInput(2);
    public static final Counter shooterCounterFront = new Counter(shooterFrontHalfEncoder);
    public static final Counter shooterCounterBack = new Counter(shooterBackHalfEncoder);
    //Pressure switch for compressor
    
    //Analog sensors
    //Drive Train
    public static final Gyro driveGyro = new Gyro(1);
    public static final AnalogChannel driveUltrasonic = new AnalogChannel(2);
    //03/04/13: Accelerometer constructor is hanging for some reason on practice bot
    //public static final ADXL345_I2C driveAccelerometer;
    
    //PWM Outputs
    //Vector of all motors
    public static final Vector motors = new Vector();
    //Drive Train
    public static final Talon driveLeftMotor = new Talon(2);
    public static final Talon driveRightMotor = new Talon(1);
    public static final RobotDrive driveRobotDrive = new RobotDrive(driveLeftMotor, driveRightMotor);
    //Shooter
    public static final Victor shooterFrontMotor = new Victor(3);
    public static final Victor shooterBackMotor = new Victor(4);
    
    //Solenoids
    //Shifter
    public static final DoubleSolenoid shifterGear = new DoubleSolenoid(1,2);
    //Hanger
    public static final DoubleSolenoid hangerPistons = new DoubleSolenoid(3,4);
    //Shooter tilt
    public static final DoubleSolenoid tiltPistons = new DoubleSolenoid(5,6);
    //Feeder
    public static final DoubleSolenoid feederPistons = new DoubleSolenoid(7,8);
    
    //Relays
    public static final Compressor compressor = new Compressor(14,1);
    public static final Relay cameraLights = new Relay(2);
    
    public static void init(){
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
        LiveWindow.addSensor("DriveTrain", "Gyro" , driveGyro);
        //ADXL345_I2C doesn't implement LiveWindowSendable
        //LiveWindow.addSensor("DriveTrain", "Accelerometer", driveAccelerometer);
        LiveWindow.addSensor("DriveTrain", "Ultrasonic" , driveUltrasonic);        
        //Shooter
        LiveWindow.addActuator("Shooter", "FrontMotor5", shooterFrontMotor);
        LiveWindow.addActuator("Shooter", "BackMotor6", shooterBackMotor);
        LiveWindow.addSensor("Shooter", "FrontHallEffect" , shooterFrontHalfEncoder);
        LiveWindow.addSensor("Shooter", "BackHallEffect" , shooterBackHalfEncoder);
        LiveWindow.addSensor("Shooter", "FrontCounter", shooterCounterFront);
        LiveWindow.addSensor("Shooter", "BackCounter", shooterCounterBack);
        //Shifter
        LiveWindow.addActuator("Shifter", "Gear" , shifterGear);
        //Hanger
        LiveWindow.addActuator("Hanger", "HangerPistons" , hangerPistons);
        //Shooter tilt
        LiveWindow.addActuator("ShooterTilt", "TiltPistons" , tiltPistons);
        //Feeder
        LiveWindow.addActuator("Feeder", "FeederPistons" , feederPistons);
        //Relays
        LiveWindow.addSensor("Relays", "Compressor" , compressor);
        LiveWindow.addActuator("Relays", "CameraLights" , cameraLights);        
        
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("RobotSafetyExpiration")) {
            p.putDouble("RobotSafetyExpiration", 0.5);
        }
    }//end init

    //puts safeties on all motors
    public static void safe() {
        Preferences p = Preferences.getInstance();
        final double expiration = p.getDouble("RobotSafetyExpiration", 0.0);
        for (int i=0; i<motors.size(); i++){
            ((MotorSafety)motors.elementAt(i)).setSafetyEnabled(true);
            ((MotorSafety)motors.elementAt(i)).setExpiration(expiration);
        }
    }//end safe

    //removes safeties from all motors (for LiveWindow)
    public static void unSafe() {
        for (int i=0; i<motors.size(); i++){
            ((MotorSafety)motors.elementAt(i)).setSafetyEnabled(false);
        }
    }//end unSafe
    
}//end RobotMap
