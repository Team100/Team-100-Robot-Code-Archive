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
public class ArduinoRobot extends IterativeRobot {
    
    Multiplexer arduino;
    boolean success = false;
    ADXL345_I2C accel;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        arduino = new Multiplexer();
        accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        success = arduino.readArduino();
        SmartDashboard.putBoolean("Arduino is on=", success);
        SmartDashboard.putNumber("Accel Values", accel.getAccelerations().XAxis);
        if(success)
        {
            SmartDashboard.putBoolean("Imaginary Input", arduino.getInput(15));
            SmartDashboard.putBoolean("LED Input", arduino.getInput(13));
            SmartDashboard.putBoolean("Input 12", arduino.getInput(12));
            SmartDashboard.putBoolean("Input 5", arduino.getInput(5));
        }
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
