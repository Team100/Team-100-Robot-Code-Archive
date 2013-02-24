package org.usfirst.frc100.OrangaHang;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.ADXL345_I2C.DataFormat_Range;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.Vector;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    
    //Digital I/O sensors
    //Drive Train
    public static final Encoder driveRightEncoder = new Encoder(3,4);
    public static final Encoder driveLeftEncoder = new Encoder(5,6);
    //Climber
    public static final Encoder climberEncoder = new Encoder(7,8);
    public static final DigitalInput climberTopSwitch = new DigitalInput(9);
    public static final DigitalInput climberBottomSwitch = new DigitalInput(10);
    //Shooter
    public static final DigitalInput shooterFrontHallEffect = new DigitalInput(1);
    public static final DigitalInput shooterBackHallEffect = new DigitalInput(2);
    public static final Counter shooterCounterFront = new Counter(shooterFrontHallEffect);
    public static final Counter shooterCounterBack = new Counter(shooterBackHallEffect);
    //Intake
    public static final DigitalInput frisbeeTransportTopSwitch = new DigitalInput(11);
    public static final DigitalInput frisbeeTransportBottomSwitch = new DigitalInput(12);
    //add pressure switch?
    
    //Analog sensors
    //Drive Train
    public static final Gyro driveGyro = new Gyro(1);
    public static final AnalogChannel driveUltrasonic = new AnalogChannel(2);
    public static final ADXL345_I2C driveAccelerometer = new ADXL345_I2C(1, DataFormat_Range.k2G);
    //Tower
    public static final AnalogChannel towerPotent = new AnalogChannel(3);
    
    
    //PWM Outputs
    //Vector of all motors
    public static final Vector motors = new Vector();
    //Drive Train
    public static final Talon driveLeftMotor = new Talon(2);
    public static final Talon driveRightMotor = new Talon(1);
    public static final RobotDrive driveRobotDrive = new RobotDrive(driveLeftMotor, driveRightMotor);
    //Climber
    public static final Victor climberTopMotor = new Victor(3);
    public static final Victor climberBottomMotor = new Victor(4);
    //Shooter
    public static final Victor shooterFrontMotor = new Victor(5);
    public static final Victor shooterBackMotor = new Victor(6);
    //FrisbeeTransport
    public static final Victor frisbeeTransportMotor = new Victor(8);
    //Tower
    public static final Victor towerMotor = new Victor(7);
    
    
    //Solenoids
    //Shifter
    public static final DoubleSolenoid shifterGear = new DoubleSolenoid(1,2);
    //Fixed Arms
    public static final DoubleSolenoid armPistons = new DoubleSolenoid(3,4);
    //Relays
    public static final Compressor compressor = new Compressor(14,1);
    public static final Relay cameraLights = new Relay(2);
    
    //LiveWindow code
    public static void init(){
        //Filling vector of motors
        motors.addElement(driveLeftMotor);
        motors.addElement(driveRightMotor);
        motors.addElement(driveRobotDrive);
        motors.addElement(climberTopMotor);
        motors.addElement(climberBottomMotor);
        motors.addElement(shooterFrontMotor);
        motors.addElement(shooterBackMotor);
        motors.addElement(frisbeeTransportMotor);
        motors.addElement(towerMotor);
        
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
        //Climber
        LiveWindow.addSensor("Climber", "ClimberEncoder" , climberEncoder);
        LiveWindow.addSensor("Climber", "TopSwitch" , climberTopSwitch);
        LiveWindow.addSensor("Climber", "BottomSwitch" , climberBottomSwitch);
        LiveWindow.addActuator("Climber", "TopMotor3", climberTopMotor);
        LiveWindow.addActuator("Climber", "BottomMotor4", climberBottomMotor);
        
        //Shooter
        LiveWindow.addActuator("Shooter", "FrontMotor5", shooterFrontMotor);
        LiveWindow.addActuator("Shooter", "BackMotor6", shooterBackMotor);
        LiveWindow.addSensor("Shooter", "FrontHallEffect" , shooterFrontHallEffect);
        LiveWindow.addSensor("Shooter", "BackHallEffect" , shooterBackHallEffect);
        LiveWindow.addSensor("Shooter", "FrontCounter", shooterCounterFront);
        LiveWindow.addSensor("Shooter", "BackCounter", shooterCounterBack);
        
        //Frisbee Transport
        LiveWindow.addActuator("FrisbeeTransport", "Motor8", frisbeeTransportMotor);
        LiveWindow.addSensor("FrisbeeTransport", "TopSwitch" , frisbeeTransportTopSwitch);
        LiveWindow.addSensor("FrisbeeTransport", "BottomSwitch" , frisbeeTransportBottomSwitch);
        
        //Tower
        LiveWindow.addActuator("Tower", "Motor7", towerMotor);
        LiveWindow.addSensor("Tower", "Potent" , towerPotent);
        
        //Shifter
        LiveWindow.addActuator("Shifter", "Gear" , shifterGear);
        
        //Fixed Arms
        LiveWindow.addActuator("FixedArms", "ArmPistons" , armPistons);
        
        //Relays
        LiveWindow.addActuator("Relays", "Compressor" , compressor);
        LiveWindow.addActuator("Relays", "CameraLights" , cameraLights);        
        
    }//end init

    //puts safeties on all motors
    public static void safe() {
        for (int i=0; i<motors.size(); i++){
            ((MotorSafety)motors.elementAt(i)).setSafetyEnabled(true);
            ((MotorSafety)motors.elementAt(i)).setExpiration(0.5);
        }
    }//end safe

    //removes safeties from all motors (for LiveWindow)
    public static void unSafe() {
        for (int i=0; i<motors.size(); i++){
            ((MotorSafety)motors.elementAt(i)).setSafetyEnabled(false);
        }
    }//end unSafe
}//end RobotMap
