/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.FrisBeast.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.FrisBeast.RobotMap;

/**
 *
 * @author Team100
 */
public class Feeder extends Subsystem {
    private final DoubleSolenoid feederPistons = RobotMap.feederPistons;
    Timer timer = new Timer();
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    public void moveFrisbeesToShoot(){
        setReverse();
        
    }//end moveFrisbeesToShoot
    
    public void setForward(){
        feederPistons.set(DoubleSolenoid.Value.kForward);
    }//end setForward
    
    public void setReverse(){
        feederPistons.set(DoubleSolenoid.Value.kReverse);
    }//end setReverse 
    
    public void disable() {
    }//end disable

    public void enable() {
    }//end enable

    public void writePreferences() {
    }//end writePreferences
    
}//end Feeder
