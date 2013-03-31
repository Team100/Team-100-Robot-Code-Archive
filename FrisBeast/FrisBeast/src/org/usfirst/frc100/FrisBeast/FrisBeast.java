/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc100.FrisBeast;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.FrisBeast.commands.Autonomous;
import org.usfirst.frc100.FrisBeast.commands.CommandBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class FrisBeast extends IterativeRobot {
    // Get modules once, because it's expensive
    DigitalModule digitalModule = DigitalModule.getInstance(1);
    AnalogModule analogModule = AnalogModule.getInstance(1);
    DriverStationLCD driverStation = DriverStationLCD.getInstance();
    // Loop period periodTimer
    Timer periodTimer = new Timer();
    Timer testIOTimer = new Timer();
    //Shooting delays
    private final double kDefaultInitialDelay = 1.0;
    private final double kDefaultNormalDelay = 0.5;
    private final double kDefaultTimeout = 7.0;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize all devices and subsystems
        RobotMap.init();
        CommandBase.init();
        //Add autonomous prefs
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("AutonInitialDelay")) {
            p.putDouble("AutonInitialDelay", kDefaultInitialDelay);
        }
        if (!p.containsKey("AutonTimeout")) {
            p.putDouble("AutonTimeout", kDefaultTimeout);
        }
    }//end robotInit

    public void disabledInit(){
	CommandBase.disableAll();
    }//end disabledInit
    
    public void autonomousInit() {
        initializeAll();
        //Autonomous
        Preferences p = Preferences.getInstance();
        final double kInitialDelay = p.getDouble("AutonInitialDelay", kDefaultInitialDelay);
        final double kTimeout = p.getDouble("AutonTimeout", kDefaultTimeout);
        Autonomous auton = new Autonomous(kInitialDelay, kTimeout);
        auton.start();
        //Timing
        periodTimer.reset();
        periodTimer.start();
    }//end autonomousInit

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Period", periodTimer.get());  
        printDataToDriverStation();
        periodTimer.reset();
    }//end autonomousPeriodic

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        Scheduler.getInstance().removeAll();

        // Redo full init sequence, in case we didn't run autonomous
        initializeAll();

        // Teleop relies on button-originated commands and default commands
        
        periodTimer.reset();
        periodTimer.start();
        testIOTimer.reset();
        testIOTimer.start();
    }//end teleopInit

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        testIO();
        SmartDashboard.putNumber("Period", periodTimer.get());
        printDataToDriverStation();
        periodTimer.reset();
    }//end teleopPeriodic
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        // Scheduler is not invoked
    }//end testPeriodic
    
    public void testInit(){
        Scheduler.getInstance().removeAll();        
        CommandBase.disableAll();
        CommandBase.unSafeAll();
        CommandBase.pneumatics.enable();//starts the compressor
    }//end testInit

    //Load PID Info has been integrated into UpdateWidgets
    
    public void testIO(){
        // Don't do this every iteration because it's too expensive
        if(testIOTimer.get() < 0.5) {
            return;
        }
        testIOTimer.reset();
        
        NetworkTable table = NetworkTable.getTable("Status");
        table.putNumber("dioData", digitalModule.getAllDIO());
        
        for(int i = 1; i <= 10; i++) {
            table.putNumber("pwm" + i, digitalModule.getPWM(i));
        }
        
        for(int i = 1; i <= 8; i++) {
            table.putNumber("analog" + i, ((int)(analogModule.getVoltage(i) * 1000) / 1000.0));
        }
    }//end testIO

    private void initializeAll() {        
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("RobotSafetyEnabled")) {
            p.putBoolean("RobotSafetyEnabled", false);
        }
        if (p.getBoolean("RobotSafetyEnabled", false)) {
            CommandBase.safeAll();
        } else {
            CommandBase.unSafeAll();
        }
        CommandBase.pneumatics.enable();
	CommandBase.shifter.shiftHighGear();
    }//end initializeAll
    
    private void printDataToDriverStation(){
// pneumatic subsystems don't have default commands, so they can't be displayed here
//        driverStation.println(DriverStationLCD.Line.kUser1, 1, "Hanger: "+CommandBase.hanger.CurrentCommand().toString()+"        ");
        driverStation.println(DriverStationLCD.Line.kUser2, 1, "Shooter: "+CommandBase.shooter.getCurrentCommand().toString()+"        ");
        driverStation.println(DriverStationLCD.Line.kUser3, 1, "DriveTrain: "+CommandBase.driveTrain.getCurrentCommand().toString()+"        ");
//        driverStation.println(DriverStationLCD.Line.kUser4, 1, "Feeder: "+CommandBase.feeder.getCurrentCommand().toString()+"        ");
//        driverStation.println(DriverStationLCD.Line.kUser5, 1, "Tilter: "+CommandBase.tilter.getCurrentCommand().toString()+"        ");
//shifter, pneumatics
        driverStation.println(DriverStationLCD.Line.kUser6, 1, "Period: "+periodTimer.get()+"    ");
        driverStation.updateLCD();
    }//end printDataToDriverStation
    
}//end FrisBeast
