/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc100.OrangaHang;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.commands.CommandBase;
import org.usfirst.frc100.OrangaHang.commands.Drive;
import org.usfirst.frc100.OrangaHang.commands.ManualClimb;
import org.usfirst.frc100.OrangaHang.commands.ManualTilt;
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
    // Autonomous command
    Reproduce reproduce;
    
    // Teleop commands
    UpdateWidgets updateWidgets;
    
    // Get modules once, because it's expensive
    DigitalModule myModule = DigitalModule.getInstance(1);
    AnalogModule myAnalogModule = AnalogModule.getInstance(1);
    
    // Loop period timer
    Timer timer = new Timer();
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        //autonomousCommand = new ExampleCommand();
        // Initialize all subsystems
        CommandBase.init();
        RobotMap.init();
       
    }//end robotInit

    public void disabledInit(){
	CommandBase.disableAll();
    }//end disabledInit
    
    public void autonomousInit() {
        initializeAll();

        // schedule the autonomous command (example)
        reproduce = new Reproduce();
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
        timer.reset();
    }//end autonomousPeriodic

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (reproduce != null){
            reproduce.cancel();
        }
        // Redo full init sequence, in case we didn't run autonomous
        initializeAll();

        // Teleop relies on button-originated commands and default commands
        
        // FIXME: do we need UpdateWidgets?
        updateWidgets = new UpdateWidgets();
        updateWidgets.start();

        timer.reset();
        timer.start();
    }//end teleopInit

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        testIO();
        SmartDashboard.putNumber("Period", timer.get());
        timer.reset();
    }//end teleopPeriodic
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }//end testPeriodic
    
    public void testInit(){
        CommandBase.disableAll();
        CommandBase.unSafeAll();
    }//end testInit

    //Load PID Info has been integrated into UpdateWidgets
    
    public void testIO(){
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
        CommandBase.safeAll();
        CommandBase.pneumatics.startCompressor();
	CommandBase.shifter.shiftHighGear();
        CommandBase.climber.homingSequence();

        //CommandBase.tower.stowArms();//do BEFORE the match
        //CommandBase.tower.tiltToStart();//do BEFORE the match
    }//end initializeAll
}//end OrangaHang
