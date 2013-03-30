/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.FrisBeast.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.FrisBeast.RobotMap;

/**
 *
 * @author Team100
 */
public class Feeder extends Subsystem implements SubsystemControl {
    private final DoubleSolenoid feederPistons = RobotMap.feederPistons;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    public void pushForward(){
        feederPistons.set(DoubleSolenoid.Value.kForward);
    }//end pushForward
    
    public void pullBack(){
        feederPistons.set(DoubleSolenoid.Value.kReverse);
    }//end pullBack 
    
    public void disable() {
    }//end disable

    public void enable() {
    }//end enable

    public void writePreferences() {
    }//end writePreferences
    
}//end Feeder
