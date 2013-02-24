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
public class FixedArms extends Subsystem implements SubsystemControl {
    private final DoubleSolenoid armPistons = RobotMap.armPistons;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //switches arms from raised to lowered
    public void toggleArms(){
        //reverse=down
        if(armPistons.get().equals(DoubleSolenoid.Value.kReverse)){
            armPistons.set(DoubleSolenoid.Value.kForward);
        } else {
            armPistons.set(DoubleSolenoid.Value.kReverse);
        }
    }//end deployArms
    
    //sets arms to down position
    public void stowArms(){
        armPistons.set(DoubleSolenoid.Value.kReverse);
    }//end stowArms

    public void disable() {
    }

    public void enable() {
    }

    public void writePreferences() {
    }
}
