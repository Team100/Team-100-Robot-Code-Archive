/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.*;
import org.usfirst.frc100.OrangaHang.RobotMap;

/**
 *
 * @author Team100
 */
public class Tower extends Subsystem
{
    private static AnalogChannel anglePot;
    private static SpeedController motor;
    private static DoubleSolenoid pistons;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Tower()
    {
        anglePot = RobotMap.towerPotent;
        motor = RobotMap.towerMotor;
        pistons = RobotMap.towerArmPistons;
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getAngle()
    {
        return anglePot.getVoltage();
    }
    
    public void setMotor(double s)
    {
        motor.set(s);
    }
    
    public void setPistons(boolean p)
    {
        pistons.set(p ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }
}
