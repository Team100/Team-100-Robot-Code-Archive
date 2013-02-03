/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.templates.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class FrisbeeHandlingRobot extends IterativeRobot {

    private Command autonomous;
    private Command autonomous1;
    
    SendableChooser autonomousChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        // instantiate the command used for the autonomous period

        // Initialize all subsystems
        CommandBase.init();
        
        autonomousChooser = new SendableChooser();
        autonomousChooser.addDefault("Default Autonomous", "autonomous");
        autonomousChooser.addObject("Autononomous1", "autonomous1");
        autonomousChooser.addObject("Nothing", "none");
        SmartDashboard.putData("Autonomous Mode", autonomousChooser);
        autonomous = new Autonomous();
        autonomous1 = new Autonomous1();
        SmartDashboard.putNumber("Wait Time", 0.0);
    }

    public void disabledInit()
    {
        autonomous.cancel();
        autonomous1.cancel();        
    }
    
    public void disabledPeriodic()
    {
        
    }
    
    public void autonomousInit()
    {
        if(autonomousChooser.getSelected().equals("autonomous"))
        {
            autonomous1.cancel();
            autonomous.start();
        }
        else if(autonomousChooser.getSelected().equals("autonomous1"))
        {
            autonomous.cancel();
            autonomous1.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {        
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        
        autonomous.cancel();
        autonomous1.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
