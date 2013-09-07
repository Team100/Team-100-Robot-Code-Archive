package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.Mk3.RobotMap;

/**
 *
 * @author Team100
 */
public class Hanger extends Subsystem implements SubsystemControl{
   private final DoubleSolenoid hangerPistons = RobotMap.hangerPistons;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //extends or retracts hooks on hanger for, well, hanging
    public void toggleHanger(){
        //forward is down, reverse is up
        if(hangerPistons.get().equals(DoubleSolenoid.Value.kReverse)){
            hangerPistons.set(DoubleSolenoid.Value.kForward);
        } else {
            hangerPistons.set(DoubleSolenoid.Value.kReverse);
        }
    }//end toggleHanger
    
    public void retractHanger(){
        //forward is down, reverse is up
        hangerPistons.set(DoubleSolenoid.Value.kForward);
    }//end retractHanger
    
    public void disable() {
    }//end disable

    public void enable() {
    }//end enable
    
}//end Hanger
