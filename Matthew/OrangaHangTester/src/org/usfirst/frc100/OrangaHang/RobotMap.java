package org.usfirst.frc100.OrangaHang;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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
    public static final DigitalInput climberPoleSwitch = new DigitalInput(13);  
    //Shooter
    public static final DigitalInput shooterFrontHallEffect = new DigitalInput(1);
    public static final DigitalInput shooterBackHallEffect = new DigitalInput(2);
    //Intake
    public static final DigitalInput intakeTopSwitch = new DigitalInput(11);
    public static final DigitalInput intakeBottomSwitch = new DigitalInput(12);
    //add pressure switch?
    
    //Analog sensors
    //Drive Train
    public static final Gyro driveGyro = new Gyro(1);
    public static final AnalogChannel driveUltrasonic = new AnalogChannel(2);
    //Tower
    public static final AnalogChannel towerPotent = new AnalogChannel(3);
    //Climber
    public static final AnalogChannel climberRightFixedIR = new AnalogChannel(4);
    public static final AnalogChannel climberLeftFixedIR = new AnalogChannel(5);
    public static final AnalogChannel climberRightMovingIR = new AnalogChannel(6);
    public static final AnalogChannel climberLeftMovingIR = new AnalogChannel(7);
    
    
    //PWM Outputs
    //Drive Train
    public static final Talon driveLeftMotor = new Talon(2);
    public static final Talon driveRightMotor = new Talon(1);
    //Climber
    public static final Victor climberTopMotor = new Victor(3);
    public static final Victor climberBottomMotor = new Victor(4);
    //Shooter
    public static final Victor shooterFrontMotor = new Victor(5);
    public static final Victor shooterBackMotor = new Victor(6);
    //Intake
    public static final Victor intakeMotor = new Victor(8);
    //Tower
    public static final Victor towerMotor = new Victor(7);
    
    
    //Solenoids
    //Drive Train
    public static final DoubleSolenoid driveGear = new DoubleSolenoid(1,2);
    //Tower
    public static final DoubleSolenoid towerArmPistons = new DoubleSolenoid(3,4);
    //Relays
    public static final Compressor compressor = new Compressor(14,1);
    public static final Relay cameraLights = new Relay(2);
    
    public static void init(){
        //Safety measures & set-up
        //climberTopMotor.setExpiration(1.0);
        //climberBottomMotor.setExpiration(1.0);
        //climberTopMotor.setSafetyEnabled(true);//This cannot be run using liveWindow if safetyEnabled
        //climberBottomMotor.setSafetyEnabled(true);//This cannot be run using liveWindow if safetyEnabled
        //LiveWindow display
        LiveWindow.addActuator("DriveTrain", "Tester",  climberTopMotor);
        //Drive Train
        LiveWindow.addSensor("DriveTrain", "RightEncoder", driveRightEncoder);
        LiveWindow.addSensor("DriveTrain", "LeftEncoder", driveLeftEncoder);
        LiveWindow.addActuator("DriveTrain", "LeftMotor2", driveLeftMotor);
        LiveWindow.addActuator("DriveTrain", "RightMotor1", driveRightMotor);
        LiveWindow.addSensor("DriveTrain", "Gyro" , driveGyro);
        LiveWindow.addSensor("DriveTrain", "Ultrasonic" , driveUltrasonic);
        LiveWindow.addSensor("DriveTrain", "Gear" , driveGear);
        
        //Climber
        LiveWindow.addSensor("Climber", "ClimberEncoder" , climberEncoder);
        LiveWindow.addSensor("Climber", "TopSwitch" , climberTopSwitch);
        LiveWindow.addSensor("Climber", "BottomSwitch" , climberBottomSwitch);
        LiveWindow.addSensor("Climber", "PoleSwitch" , climberPoleSwitch);
        LiveWindow.addActuator("Climber", "TopMotor3", climberTopMotor);
        LiveWindow.addActuator("Climber", "BottomMotor4", climberBottomMotor);
        LiveWindow.addSensor("Climber", "RightFixedIR" , climberRightFixedIR);
        LiveWindow.addSensor("Climber", "RightMovingIR" , climberRightMovingIR);
        LiveWindow.addSensor("Climber", "LeftFixedIR" , climberLeftFixedIR);
        LiveWindow.addSensor("Climber", "LeftMovingIR" , climberLeftMovingIR);
        
        //Shooter
        LiveWindow.addActuator("Shooter", "FrontMotor5", shooterFrontMotor);
        LiveWindow.addActuator("Shooter", "BackMotor6", shooterBackMotor);
        LiveWindow.addSensor("Shooter", "FrontHallEffect" , shooterFrontHallEffect);
        LiveWindow.addSensor("Shooter", "BackHallEffect" , shooterBackHallEffect);
        
        //Intake
        LiveWindow.addActuator("Intake", "Motor8", intakeMotor);
        LiveWindow.addSensor("Intake", "TopSwitch" , intakeTopSwitch);
        LiveWindow.addSensor("Intake", "BottomSwitch" , intakeBottomSwitch);
        
        //Tower
        LiveWindow.addActuator("Tower", "Motor7", towerMotor);
        LiveWindow.addSensor("Tower", "Potent" , towerPotent);
        LiveWindow.addSensor("Tower", "ArmPistons" , towerArmPistons);
        
        //Relays
        LiveWindow.addActuator("Relays", "Compressor" , compressor);
        LiveWindow.addActuator("Relays", "CameraLights" , cameraLights);        
        
    }//end init
    
}//end RobotMap
