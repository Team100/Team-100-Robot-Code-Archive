
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 */
public class FrisbeeHandler extends Subsystem
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
 
    private Victor beltMotor;
    
    public void FrisbeeHandler()
    {
        beltMotor = new Victor(RobotMap.kBeltMotor);
    }
    
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public boolean getFrisbeeHook()
    {
        return CommandBase.arduino.getInput(RobotMap.kFrisbeeHook);
    }
}

