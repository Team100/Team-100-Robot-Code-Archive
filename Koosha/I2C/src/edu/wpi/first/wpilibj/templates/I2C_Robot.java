/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class I2C_Robot extends IterativeRobot
{
    I2C i2cObj;
    Timer timer;
    byte[] data;
    boolean abort;
    Joystick logitech;
    JoystickButton button8;
    boolean pre;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        i2cObj = new I2C(DigitalModule.getInstance(1), 0x3A);
        timer = new Timer();
        data = new byte[2];
        abort = false;
        logitech = new Joystick(1);
        button8 = new JoystickButton(logitech, 8);
        pre = false;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        if(button8.get() && !pre)
        {
            pre=true;
            timer.reset();
            timer.start();
            abort = i2cObj.read(0x42, data.length, data);
            timer.stop();
            SmartDashboard.putNumber("Data", data[0]);
            SmartDashboard.putNumber("Time to read", timer.get());
            SmartDashboard.putBoolean("Abort=", abort);
        }
        else if(!button8.get())
        {
            pre=false;
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
