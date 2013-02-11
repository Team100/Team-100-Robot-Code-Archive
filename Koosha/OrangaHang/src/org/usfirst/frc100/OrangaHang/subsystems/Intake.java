/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.OrangaHang.RobotMap;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Team100
 */
public class Intake extends Subsystem
{
    private static DigitalInput topSwitch;
    private static DigitalInput bottomSwitch;
    private static SpeedController motor;
            
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Intake()
    {
        topSwitch = RobotMap.intakeTopSwitch;
        bottomSwitch = RobotMap.intakeBottomSwitch;
        motor = RobotMap.intakeMotor;
    }
    
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean getTop()
    {
        return topSwitch.get();
    }
    
    public boolean getBottom()
    {
        return bottomSwitch.get();
    }
    
    public void setMotor(double s)
    {
        motor.set(s);
    }
}
