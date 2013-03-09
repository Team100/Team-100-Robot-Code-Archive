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
public class MetalSensingRobot extends IterativeRobot
{
    private DigitalInput metalSensor1;
    private DigitalInput metalSensor2;
    private Timer timer;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        metalSensor1 = new DigitalInput(8);
        metalSensor2 = new DigitalInput(10);
        timer = new Timer();
    }
    
    public void disabledInit()
    {
        timer.stop();
        timer.reset();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    public void teleopInit()
    {
        timer.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        SmartDashboard.putBoolean("Metal Sensor1", metalSensor1.get());
        SmartDashboard.putBoolean("Metal Sensor2", metalSensor2.get());
        SmartDashboard.putNumber("Timer Value", timer.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
