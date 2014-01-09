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
public class RobotTemplate extends IterativeRobot {
    AnalogChannel lineReader1 = new AnalogChannel(3);
    AnalogChannel lineReader2 = new AnalogChannel(7);
    AnalogTrigger trigger1 = new AnalogTrigger(lineReader1);
    AnalogTrigger trigger2 = new AnalogTrigger(lineReader2);
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    public void teleopInit()
    {
        trigger1.setLimitsRaw(50, 75);
        trigger2.setLimitsRaw(50, 75);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        SmartDashboard.putBoolean("Trigger1", trigger1.getTriggerState());
        SmartDashboard.putBoolean("Trigger2", trigger2.getTriggerState());
        SmartDashboard.putNumber("Reader1 Value", lineReader1.getValue());
        SmartDashboard.putNumber("Reader2 Value", lineReader2.getValue());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
