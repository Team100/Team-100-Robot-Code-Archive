/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.OrangaHang.RobotMap;

/**
 *
 * @author Student
 */
public class Shifter extends Subsystem implements SubsystemControl {
    private final DoubleSolenoid shifter = RobotMap.shifterGear;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void shiftHighGear() {
        shifter.set(DoubleSolenoid.Value.kReverse);
    }//end shiftHighGear
    
    public void shiftLowGear() {
        shifter.set(DoubleSolenoid.Value.kForward);
    }//end shiftLowGear
    
    public void disable() {
    }

    public void enable() {
    }

    public void writePreferences() {
    }
}
