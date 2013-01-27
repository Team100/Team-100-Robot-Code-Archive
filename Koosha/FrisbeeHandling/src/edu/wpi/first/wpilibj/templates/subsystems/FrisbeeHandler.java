/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Koosha Jahani
 */
public class FrisbeeHandler extends Subsystem
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    private static DigitalInput feedGate;
    private static Jaguar feedMotor;
    private static Jaguar intakeMotor;
    
    public FrisbeeHandler()
    {
        feedGate = new DigitalInput(RobotMap.kFeedGate);
        feedMotor = new Jaguar(RobotMap.kFeedMotor);
        intakeMotor = new Jaguar(RobotMap.kIntakeMotor);        
    }

    public boolean getFeedGate()
    {
        return feedGate.get();
    }
    
    public void setFeedMotor(double speed)
    {
        feedMotor.set(speed);
    }
    
    public double getFeedMotor()
    {
        return feedMotor.get();
    }
    
    public void setIntakeMotor(double speed)
    {
        intakeMotor.set(speed);
    }
    
    public double getIntakeMotor()
    {
        return intakeMotor.get();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
