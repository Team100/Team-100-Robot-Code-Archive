// RobotBuilder Version: 0.0.2
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in th future.
package org.usfirst.frc100.Robot2013;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder.PIDSourceParameter;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Encoder driveTrainLeftEncoder;
    public static Encoder driveTrainRightEncoder;
    public static Accelerometer driveTrainAccelerometer;
    public static Gyro driveTrainGyro;
    public static DoubleSolenoid driveTrainLeftShifter;
    public static DoubleSolenoid driveTrainRightShifter;
    public static SpeedController driveTrainLeft;
    public static SpeedController driveTrainRight;
    public static RobotDrive driveTrainRobotDrive;
    public static SpeedController intakeIntakeMotor;
    public static SpeedController intakeFeedMotor;
    public static DigitalInput intakeFeedGate;
    public static Encoder climberClimberEncoder;
    public static SpeedController climberClimberMotor;
    public static DigitalInput climberTopClimberSwitch;
    public static DigitalInput climberBottomClimberSwitch;
    public static SpeedController climberClimberTilter;
    public static AnalogChannel climberClimberAnglePot;
    public static Encoder climberClimberAngleEncoder;
    public static Compressor climberCompressor;
    public static SpeedController shooterShooterMotor;
    public static DigitalInput shooterDigitalInput1;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainLeftEncoder = new Encoder(1, 3, 1, 4, false, EncodingType.k4X);
	LiveWindow.addSensor("DriveTrain", "LeftEncoder", driveTrainLeftEncoder);
        driveTrainLeftEncoder.setDistancePerPulse(1.0);
        driveTrainLeftEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainLeftEncoder.start();
        driveTrainRightEncoder = new Encoder(1, 5, 1, 6, false, EncodingType.k4X);
	LiveWindow.addSensor("DriveTrain", "RightEncoder", driveTrainRightEncoder);
        driveTrainRightEncoder.setDistancePerPulse(1.0);
        driveTrainRightEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainRightEncoder.start();
        driveTrainAccelerometer = new Accelerometer(1, 1);
	LiveWindow.addSensor("DriveTrain", "Accelerometer", driveTrainAccelerometer);
        driveTrainAccelerometer.setSensitivity(1.0);
        driveTrainAccelerometer.setZero(2.5);
        driveTrainGyro = new Gyro(1, 2);
	LiveWindow.addSensor("DriveTrain", "Gyro", driveTrainGyro);
        driveTrainGyro.setSensitivity(1.25);
        driveTrainLeftShifter = new DoubleSolenoid(1, 1, 2);      
	
        
        driveTrainRightShifter = new DoubleSolenoid(1, 3, 4);      
	
        
        driveTrainLeft = new Talon(1, 3);
	LiveWindow.addActuator("DriveTrain", "Left", (Talon) driveTrainLeft);
        
        driveTrainRight = new Talon(1, 5);
	LiveWindow.addActuator("DriveTrain", "Right", (Talon) driveTrainRight);
        
        driveTrainRobotDrive = new RobotDrive(driveTrainLeft, driveTrainRight);
	
        driveTrainRobotDrive.setSafetyEnabled(true);
        driveTrainRobotDrive.setExpiration(0.1);
        driveTrainRobotDrive.setSensitivity(0.5);
        driveTrainRobotDrive.setMaxOutput(1.0);
        
        intakeIntakeMotor = new Talon(1, 7);
	LiveWindow.addActuator("Intake", "IntakeMotor", (Talon) intakeIntakeMotor);
        
        intakeFeedMotor = new Victor(1, 4);
	LiveWindow.addActuator("Intake", "FeedMotor", (Victor) intakeFeedMotor);
        
        intakeFeedGate = new DigitalInput(1, 13);
	LiveWindow.addSensor("Intake", "FeedGate", intakeFeedGate);
        
        climberClimberEncoder = new Encoder(1, 9, 1, 10, false, EncodingType.k4X);
	LiveWindow.addSensor("Climber", "ClimberEncoder", climberClimberEncoder);
        climberClimberEncoder.setDistancePerPulse(1.0);
        climberClimberEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        climberClimberEncoder.start();
        climberClimberMotor = new Talon(1, 1);
	LiveWindow.addActuator("Climber", "ClimberMotor", (Talon) climberClimberMotor);
        
        climberTopClimberSwitch = new DigitalInput(1, 1);
	LiveWindow.addSensor("Climber", "TopClimberSwitch", climberTopClimberSwitch);
        
        climberBottomClimberSwitch = new DigitalInput(1, 2);
	LiveWindow.addSensor("Climber", "BottomClimberSwitch", climberBottomClimberSwitch);
        
        climberClimberTilter = new Talon(1, 2);
	LiveWindow.addActuator("Climber", "ClimberTilter", (Talon) climberClimberTilter);
        
        climberClimberAnglePot = new AnalogChannel(1, 3);
	LiveWindow.addSensor("Climber", "ClimberAnglePot", climberClimberAnglePot);
        
        climberClimberAngleEncoder = new Encoder(1, 11, 1, 12, false, EncodingType.k4X);
	LiveWindow.addSensor("Climber", "ClimberAngleEncoder", climberClimberAngleEncoder);
        climberClimberAngleEncoder.setDistancePerPulse(1.0);
        climberClimberAngleEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        climberClimberAngleEncoder.start();
        climberCompressor = new Compressor(1, 14, 1, 1);
	
        
        shooterShooterMotor = new Talon(1, 6);
	LiveWindow.addActuator("Shooter", "ShooterMotor", (Talon) shooterShooterMotor);
        
        shooterDigitalInput1 = new DigitalInput(1, 7);
	LiveWindow.addSensor("Shooter", "Digital Input 1", shooterDigitalInput1);
        
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
