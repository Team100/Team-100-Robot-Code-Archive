//ready
package org.usfirst.frc100.Ballrus;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.commands.*;
import org.usfirst.frc100.Ballrus.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Ballrus extends IterativeRobot {

    public static OI oi;
    public static DriveTrain driveTrain;
    public static Shooter shooter;
    public static Intake intake;
    public static Tilter tilter;
    public static Compressor compressor;
    public static Camera camera;

    // This function is run when the robot is first started up and should be
    // used for any initialization code.
    public void robotInit() {
        Preferences.readFromFile();
        RobotMap.init();
        driveTrain = new DriveTrain();
        shooter = new Shooter();
        intake = new Intake();
        tilter = new Tilter();
        compressor = new Compressor();
        if(Preferences.cameraEnabled) {
            camera = new Camera();
        }
        oi = new OI();
        
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(intake);
        SmartDashboard.putData(tilter);
        SmartDashboard.putData(compressor);
    }

    // This function is called at the beginning of autonomous
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
        RobotMap.stopAllMotors();
        new Autonomous().start();
        new UpdateDashboard().start();
        compressor.startCompressor();
    }

    // This function is called periodically during autonomous
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    // This function is called at the beginning of operator control
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        RobotMap.stopAllMotors();
        new Drive().start();
        new UpdateDashboard().start();
        compressor.startCompressor();
    }

    // This function is called periodically during operator control
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    // This function is called periodically during test mode
    public void testPeriodic() {
        compressor.startCompressor();
        LiveWindow.run();
    }
    
    // Puts all of the I/O on smartDashboard, but causes lag
    public static void displayIO(){
        for(int i = 1; i < 15; i++){
            SmartDashboard.putBoolean("DIO_"+i, DigitalModule.getInstance(1).getDIO(i));
        }
        for(int i = 1; i < 9; i++){
            SmartDashboard.putNumber("Analog_"+i, AnalogModule.getInstance(1).getVoltage(i));
        }
        for(int i = 1; i < 11; i++){
            SmartDashboard.putNumber("PWM_"+i, DigitalModule.getInstance(1).getPWM(i));
        }
        for(int i = 1; i < 9; i++){
            SmartDashboard.putBoolean("Relay_"+i, DigitalModule.getInstance(1).getRelayForward(i));
        }
    }
}