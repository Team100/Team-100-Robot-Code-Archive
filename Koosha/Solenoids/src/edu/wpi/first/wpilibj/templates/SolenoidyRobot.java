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
public class SolenoidyRobot extends IterativeRobot
{
    Compressor compressor;
    Solenoid soly1;
    Solenoid soly2;
    Timer timer;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        compressor = new Compressor(1,1);
        soly1 = new Solenoid(1);
        soly2 = new Solenoid(2);
        timer = new Timer();
    }
    
    public void disabledInit()
    {
        compressor.stop();
    }
    
    public void autonomousInit()
    {
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        
    }

    public void teleopInit()
    {
        compressor.start();
        timer.start();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        if(timer.get()%6<3.0)
        {
            soly2.set(false);
            soly1.set(true);
        }
        else
        {
            soly1.set(false);
            soly2.set(true);
        }
        
        SmartDashboard.putBoolean("Pressure", compressor.getPressureSwitchValue());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
