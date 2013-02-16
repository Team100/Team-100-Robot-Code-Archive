/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc100.OrangaHang;


import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc100.OrangaHang.commands.CommandBase;
import org.usfirst.frc100.OrangaHang.commands.Drive;
import org.usfirst.frc100.OrangaHang.commands.ManualClimb;
import org.usfirst.frc100.OrangaHang.commands.UpdateWidgets;
//import org.usfirst.frc100.Robot2013.commands.ExampleCommand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class OrangaHang extends IterativeRobot {

    Command autonomousCommand;
    ManualClimb manualClimb;
    Drive drive;
    UpdateWidgets updateWidgets;
    
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
        // schedule the autonomous command (example)
        if (autonomousCommand != null){
            autonomousCommand.start();
        }

	CommandBase.driveTrain.shiftHighGear();
        CommandBase.climber.homingSequence();
    }//end autonomousInit

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }//end autonomousPeriodic

    public void teleopInit() {
        loadPreferences();
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null){
            autonomousCommand.cancel();
        }
        manualClimb = new ManualClimb();
        drive = new Drive();
        updateWidgets = new UpdateWidgets();
        manualClimb.start();
        drive.start();
        updateWidgets.start();

    }//end teleopInit

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        testIO();
    }//end teleopPeriodic
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }//end testPeriodic
    
    public void testInit(){
        CommandBase.disableAll();
    }

    public void loadPreferences() {
        Preferences p = Preferences.getInstance();
        NetworkTable table;
        //Load PID Data
        table = NetworkTable.getTable("PIDSystems");
        //Load data for the FRONT SHOOTER  PID
        table.putNumber("_FrontShooterkP", p.getDouble("_FrontShooterkP", -1.0));
        table.putNumber("_FrontShooterkI", p.getDouble("_FrontShooterkI", -1.0));
        table.putNumber("_FrontShooterkD", p.getDouble("_FrontShooterkD", -1.0));
        table.putNumber("_FrontShooterkMinOutput", p.getDouble("_FrontShooterkMinOutput", -1.0));
        table.putNumber("_FrontShooterkMaxOutput", p.getDouble("_FrontShooterkMaxOutput", -1.0));
        //Load data for the BACK SHOOTER  PID
        table.putNumber("_BackShooterkP", p.getDouble("_BackShooterkP", -1.0));
        table.putNumber("_BackShooterkI", p.getDouble("_BackShooterkI", -1.0));
        table.putNumber("_BackShooterkD", p.getDouble("_BackShooterkD", -1.0));
        table.putNumber("_BackShooterkMinOutput", p.getDouble("_BackShooterkMinOutput", -1.0));
        table.putNumber("_BackShooterkMaxOutput", p.getDouble("_BackShooterkMaxOutput", -1.0));
    }
    
    public void testIO(){
        NetworkTable table = NetworkTable.getTable("Status");
        table.putNumber("dioData", DigitalModule.getInstance(1).getAllDIO());
        
        for(int i = 1; i <= 10; i++) {
            table.putNumber("pwm" + i, DigitalModule.getInstance(1).getPWM(i));
        }
        
        for(int i = 1; i <= 8; i++) {
            table.putNumber("analog" + i, ((int)(AnalogModule.getInstance(1).getVoltage(i) * 1000) / 1000.0));
        }
    }
}//end OrangaHang
