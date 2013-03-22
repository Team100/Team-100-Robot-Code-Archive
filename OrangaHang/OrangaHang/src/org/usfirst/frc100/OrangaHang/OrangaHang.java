/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc100.OrangaHang;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.commands.CommandBase;
import org.usfirst.frc100.OrangaHang.commands.HomeClimber;
import org.usfirst.frc100.OrangaHang.commands.Reproduce;
import org.usfirst.frc100.OrangaHang.commands.UpdateWidgets;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class OrangaHang extends IterativeRobot {
    // Get modules once, because it's expensive
    DigitalModule myModule = DigitalModule.getInstance(1);
    AnalogModule myAnalogModule = AnalogModule.getInstance(1);
    DriverStationLCD driverStation = DriverStationLCD.getInstance();
    // Loop period timer
    Timer timer = new Timer();
    Timer timer2 = new Timer();
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize all devices and subsystems
        RobotMap.init();
        CommandBase.init();

        // SD throws table key undefined exception "SmartDashboard/Scheduler/count"
        //SmartDashboard.putData(Scheduler.getInstance());
    }//end robotInit

    public void disabledInit(){
	CommandBase.disableAll();
        
        
        
    }//end disabledInit
    
    public void autonomousInit() {
        initializeAll();

        // schedule the autonomous command (example)
        Reproduce reproduce = new Reproduce();
        reproduce.start();
        
        timer.reset();
        timer.start();
    }//end autonomousInit

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Period", timer.get());
        
        printDataToDriverStation();
        timer.reset();
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
        
        UpdateWidgets updateWidgets = new UpdateWidgets();
        updateWidgets.start();
        
        HomeClimber homeClimber = new HomeClimber();
        //homeClimber.start();

        timer.reset();
        timer.start();
        timer2.reset();
        timer2.start();
    }//end teleopInit

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        testIO();
        SmartDashboard.putNumber("Period", timer.get());
        printDataToDriverStation();
        timer.reset();
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
        if(timer2.get() < 0.5) {
            return;
        }
        timer2.reset();
        
        NetworkTable table = NetworkTable.getTable("Status");
        table.putNumber("dioData", myModule.getAllDIO());
        
        for(int i = 1; i <= 10; i++) {
            table.putNumber("pwm" + i, myModule.getPWM(i));
        }
        
        for(int i = 1; i <= 8; i++) {
            table.putNumber("analog" + i, ((int)(myAnalogModule.getVoltage(i) * 1000) / 1000.0));
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

        //CommandBase.tower.stowArms();//do BEFORE the match
        //CommandBase.tower.tiltToStart();//do BEFORE the match
    }//end initializeAll
    
    private void printDataToDriverStation(){
        driverStation.println(DriverStationLCD.Line.kUser1, 1, "Climber: "+((Subsystem)CommandBase.subsystems.elementAt(0)).getCurrentCommand().toString()+"        ");
        driverStation.println(DriverStationLCD.Line.kUser2, 1, "Shooter: "+((Subsystem)CommandBase.subsystems.elementAt(1)).getCurrentCommand().toString()+"        ");
        driverStation.println(DriverStationLCD.Line.kUser3, 1, "DriveTrain: "+((Subsystem)CommandBase.subsystems.elementAt(2)).getCurrentCommand().toString()+"        ");
        if ("FrisbeeTransportOff".equals(((Subsystem)CommandBase.subsystems.elementAt(3)).getCurrentCommand().toString())){
            driverStation.println(DriverStationLCD.Line.kUser4, 1, "Intake: "+"Off"+"          ");
        }
        else{
            driverStation.println(DriverStationLCD.Line.kUser4, 1, "Intake: "+((Subsystem)CommandBase.subsystems.elementAt(3)).getCurrentCommand().toString()+"        ");
        }
        driverStation.println(DriverStationLCD.Line.kUser5, 1, "Tower: "+((Subsystem)CommandBase.subsystems.elementAt(5)).getCurrentCommand().toString()+"        ");
        driverStation.println(DriverStationLCD.Line.kUser6, 1, "Period: "+timer.get()+"    ");
        driverStation.updateLCD();
    }
}//end OrangaHang
