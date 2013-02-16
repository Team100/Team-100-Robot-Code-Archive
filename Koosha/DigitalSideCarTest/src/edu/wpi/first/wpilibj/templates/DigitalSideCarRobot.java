/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class DigitalSideCarRobot extends IterativeRobot
{
    private DigitalInput[] inputs;
    private PWM[] pwms;
    private Relay[] relays;
    private I2C i2c;
    private final int kAddress = 0x02;
    private Timer timer;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        int i;
        
        for(i=1; i<=14; i++)
        {
            inputs[i-1] = new DigitalInput(i);
        }
        
        for(i=1; i<=10; i++)
        {
            pwms[i-1] = new PWM(i);
        }
        
        for(i=1; i<=8; i++)
        {
            relays[i-1] = new Relay(i);
        }
        
        i2c = new I2C(DigitalModule.getInstance(1), kAddress);
    }
    
    public void autonomousInit()
    {
        timer.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        int i;
        
        boolean[] digitalValues = null;
        for(i=0; i<14; i++)
        {
            digitalValues[i] = inputs[i].get();
        }
        
        for(i=0; i<10; i++)
        {
            pwms[i].setRaw(Math.abs(((timer.get()+3)%4)-2)-1);
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
