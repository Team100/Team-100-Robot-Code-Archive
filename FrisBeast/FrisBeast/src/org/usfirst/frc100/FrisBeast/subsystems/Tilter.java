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
public class Tilter extends Subsystem implements SubsystemControl {
    private final DoubleSolenoid tiltPistons = RobotMap.tiltPistons;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //changes shooter angle depending on whether we're shooting from in 
    //front or behind the pyramid
    public void toggleTilt(){
        //FIXME: double sol positions
        if(tiltPistons.get().equals(DoubleSolenoid.Value.kReverse)){
            tiltPistons.set(DoubleSolenoid.Value.kForward);
        } else {
            tiltPistons.set(DoubleSolenoid.Value.kReverse);
        }
    }//end toggleTilt
    
    public void disable() {
    }//end disable

    public void enable() {
    }//end enable

}//end Tilter
