// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.GeeWrathwithLineReader;
    
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType; import edu.wpi.first.wpilibj.PIDSource.PIDSourceParameter;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import java.util.Vector;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Encoder driveTrainlEncoder;
    public static Encoder driveTrainrEncoder;
    public static Gyro driveTraingyro;
    public static AnalogChannel driveTrainlReader;
    public static AnalogChannel driveTrainrReader;
    public static AnalogTrigger driveTrainlTrigger;
    public static AnalogTrigger driveTrainrTrigger;
    public static SpeedController driveTrainleftA;
    public static SpeedController driveTrainleftB;
    public static SpeedController driveTrainrightA;
    public static SpeedController driveTrainrightB;
    public static RobotDrive driveTraindrive;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrainlEncoder = new Encoder(1, 4, 1, 3, false, EncodingType.k4X);
	LiveWindow.addSensor("DriveTrain", "lEncoder", driveTrainlEncoder);
        driveTrainlEncoder.setDistancePerPulse(0.005);
        driveTrainlEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainlEncoder.start();
        driveTrainrEncoder = new Encoder(1, 1, 1, 2, false, EncodingType.k4X);
	LiveWindow.addSensor("DriveTrain", "rEncoder", driveTrainrEncoder);
        driveTrainrEncoder.setDistancePerPulse(0.005);
        driveTrainrEncoder.setPIDSourceParameter(PIDSourceParameter.kRate);
        driveTrainrEncoder.start();
        driveTraingyro = new Gyro(1, 1);
	LiveWindow.addSensor("DriveTrain", "gyro", driveTraingyro);
        driveTraingyro.setSensitivity(0.007);
        driveTrainlReader = new AnalogChannel(1, 6);
	LiveWindow.addSensor("DriveTrain", "lReader", driveTrainlReader);
        
        driveTrainrReader = new AnalogChannel(1, 7);
	LiveWindow.addSensor("DriveTrain", "rReader", driveTrainrReader);
        
        driveTrainlTrigger = new AnalogTrigger(driveTrainlReader);
//        LiveWindow.addSensor("DriveTrain", "lTrigger", (LiveWindowSendable) driveTrainlTrigger);
        
        driveTrainrTrigger = new AnalogTrigger(driveTrainrReader);
//        LiveWindow.addSensor("DriveTrain", "rTrigger", (LiveWindowSendable) driveTrainrTrigger);
        
        driveTrainleftA = new Jaguar(1, 6);
	LiveWindow.addActuator("DriveTrain", "leftA", (Jaguar) driveTrainleftA);
        
        driveTrainleftB = new Jaguar(1, 7);
	LiveWindow.addActuator("DriveTrain", "leftB", (Jaguar) driveTrainleftB);
        
        driveTrainrightA = new Jaguar(1, 8);
	LiveWindow.addActuator("DriveTrain", "rightA", (Jaguar) driveTrainrightA);
        
        driveTrainrightB = new Jaguar(1, 9);
	LiveWindow.addActuator("DriveTrain", "rightB", (Jaguar) driveTrainrightB);
        
        driveTraindrive = new RobotDrive(driveTrainleftA, driveTrainleftB,
              driveTrainrightA, driveTrainrightB);
	
        driveTraindrive.setSafetyEnabled(true);
        driveTraindrive.setExpiration(0.1);
        driveTraindrive.setSensitivity(0.5);
        driveTraindrive.setMaxOutput(1.0);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
