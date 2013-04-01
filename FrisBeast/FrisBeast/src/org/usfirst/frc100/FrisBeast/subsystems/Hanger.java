package org.usfirst.frc100.FrisBeast.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.FrisBeast.RobotMap;

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
        //FIXME: double sol positions
        if(hangerPistons.get().equals(DoubleSolenoid.Value.kReverse)){
            hangerPistons.set(DoubleSolenoid.Value.kForward);
        } else {
            hangerPistons.set(DoubleSolenoid.Value.kReverse);
        }
    }//end toggleHanger
    
    public void disable() {
    }//end disable

    public void enable() {
    }//end enable
    
}//end Hanger
