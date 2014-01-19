package org.usfirst.frc100.Robot2014;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Robot2014.commands.*;
import org.usfirst.frc100.Robot2014.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    Command autonomousCommand;
    public static OI oi;
    public static DriveTrain driveTrain;
    public static Shooter shooter;
    public static Intake intake;
    public static Tilter tilter;
    public static Compressor compressor;

    // This function is run when the robot is first started up and should be
    // used for any initialization code.
    public void robotInit() {
        RobotMap.init();
        driveTrain = new DriveTrain();
        shooter = new Shooter();
        intake = new Intake();
        tilter = new Tilter();
        compressor = new Compressor();
        oi = new OI();
        
        SmartDashboard.putData(driveTrain);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(intake);
        SmartDashboard.putData(tilter);
        SmartDashboard.putData(compressor);
        
        autonomousCommand = new Autonomous();
    }

    // This function is called at the beginning of autonomous
    public void autonomousInit() {
        autonomousCommand.start();
    }

    // This function is called periodically during autonomous
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    // This function is called at the beginning of operator control
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        new Drive().start();
        new TiltToIntake().start();
    }

    // This function is called periodically during operator control
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        System.out.println(driveTrain.getCurrentCommand());
        System.out.println(shooter.getCurrentCommand());
        System.out.println(intake.getCurrentCommand());
        System.out.println(tilter.getCurrentCommand());
        System.out.println(compressor.getCurrentCommand());
    }

    // This function called periodically during test mode
    public void testPeriodic() {
        LiveWindow.run();
    }
}