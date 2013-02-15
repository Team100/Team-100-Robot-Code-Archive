/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.OrangaHang.RobotMap;


/**
 *
 * @author Team100
 */
public class DriveTrain extends Subsystem
{
    private final SpeedController leftMotor = RobotMap.driveLeftMotor;
    private final SpeedController rightMotor = RobotMap.driveRightMotor;
    
    private final Gyro gyro = RobotMap.driveGyro;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void resetGyro()
    {
        gyro.reset();
    }
    
    public double getGyro()
    {
        return gyro.getAngle();
    }
    
    public int double2unit(double d)
    {
        if(d == 0)
        {
            return 0;
        }
        
        double returnVal = d/Math.abs(d);
        return (int) returnVal;
    }
}
