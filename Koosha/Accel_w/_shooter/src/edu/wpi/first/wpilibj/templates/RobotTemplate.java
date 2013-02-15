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
public class RobotTemplate extends IterativeRobot
{
    private ADXL345_I2C accel;
    private ADXL345_I2C.AllAxes axes;
    private Jaguar motor;
    private Victor loudMotor;
    private Timer clock;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        accel = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k16G);
        axes = new ADXL345_I2C.AllAxes();
        motor = new Jaguar(2);
        loudMotor = new Victor(1);
        clock = new Timer();
    }
    
    public void disableInit()
    {
        clock.stop();
        clock.reset();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    public void teleopInit()
    {
        clock.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        axes=accel.getAccelerations();
        SmartDashboard.putNumber("Test", clock.get());
        SmartDashboard.putNumber("X-Axis", accel.getAcceleration(ADXL345_I2C.Axes.kX));
        SmartDashboard.putNumber("Y-Axis", accel.getAcceleration(ADXL345_I2C.Axes.kY));
        SmartDashboard.putNumber("Z-Axis", accel.getAcceleration(ADXL345_I2C.Axes.kZ));
        SmartDashboard.putNumber("All Axes:XAxis", axes.XAxis);
        SmartDashboard.putNumber("All Axes:YAxis", axes.YAxis);
        SmartDashboard.putNumber("All Axes:ZAxis", axes.ZAxis);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
