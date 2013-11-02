package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.Mk3.RobotMap;

/**
 * Changes gears.
 */
public class Shifter extends Subsystem implements SubsystemControl {
    private final DoubleSolenoid shifter = RobotMap.shifterGear;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    public void shiftHighGear() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }//end shiftHighGear
    
    public void shiftLowGear() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }//end shiftLowGear
    
    public void disable() {
    }//end disable

    public void enable() {
    }//end enable
    
}//end Shifter
