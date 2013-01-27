/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class SonorRobot extends IterativeRobot {
    
    AnalogChannel sonor;
    Timer timer;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        sonor = new AnalogChannel(5);
        timer = new Timer();
    }
    
    public void DisabledInit()
    {
        timer.stop();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    public void teleopInit()
    {
        timer.reset();
        timer.start();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        SmartDashboard.putNumber("Countdown to Genesis", (-1)*timer.get());
        SmartDashboard.putNumber("Sonor Value", sonor.getValue());
        SmartDashboard.putNumber("Sonor Voltage", sonor.getVoltage());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
