package org.usfirst.frc100.OrangaHang;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Solenoid;
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
    public static final Encoder driveRightEncoder = new Encoder(1,2);
    public static final Encoder driveLeftEncoder = new Encoder(3,4);
    //Climber
    public static final Encoder climberEncoder = new Encoder(5,6);
    public static final DigitalInput climberTopSwitch;
    public static final DigitalInput climberBottomSwitch;
    public static final DigitalInput climberPoleSwitch;
//    public static final DigitalInput climberRightFixedMetal;
//    public static final DigitalInput climberLeftFixedMetal;
//    public static final DigitalInput climberRightMovingMetal;
//    public static final DigitalInput climberLeftMovingMetal;
    //Shooter
    public static final DigitalInput shooterFrontOptical = new DigitalInput(7);
    public static final DigitalInput shooterBackOptical = new DigitalInput(8);
    //Intake
    public static final DigitalInput intakeTopSwitch;
    public static final DigitalInput intakeBottomSwitch;
    
    
    //Analog sensors
    //Drive Train
    public static final Gyro driveGyro = new Gyro(1);
    public static final AnalogChannel driveRangefinder = new AnalogChannel(2);
    //Tower
    public static final AnalogChannel towerPotent = new AnalogChannel(3);
    
    
    //PWM Outputs
    //Drive Train
    public static final Talon driveLeftMotor = new Talon(1);
    public static final Talon driveRightMotor = new Talon(2);
    //Climber
    public static final Victor climberTopMotor = new Victor(3);
    public static final Victor climberBottomMotor = new Victor(4);
    //Shooter
    public static final Victor shooterFrontMotor = new Victor(5);
    public static final Victor shooterBottomMotor = new Victor(6);
    //Intake
    public static final Victor intakeMotor = new Victor(7);
    //Tower
    public static final Victor towerMotor = new Victor(8);
    
    
    //Solenoids
    //Drive Train
    public static final Solenoid driveHighGear = new Solenoid(1);
    public static final Solenoid driveLowGear = new Solenoid(2);
    //Tower or Pneumatics, not sure which yet
    public static final Solenoid towerRightArmPiston = new Solenoid(3);
    public static final Solenoid towerLeftArmPiston = new Solenoid(4);
    
    public static void init(){
        //Drive Train
        LiveWindow.addSensor("DriveTrain", "RightEncoder", driveRightEncoder);
        LiveWindow.addSensor("Drive Train", "LeftEncoder", driveLeftEncoder);
        //Climber
        LiveWindow.addSensor("Climber", "ClimberEncoder" , climberEncoder);
    }//end init
    
}//end RobotMap
