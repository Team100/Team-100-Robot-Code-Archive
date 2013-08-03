package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.Mk3.RobotMap;

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
    
    public void tiltUp(){
      tiltPistons.set(DoubleSolenoid.Value.kForward);
    }//end tiltUp
    
    public void tiltDown(){
       tiltPistons.set(DoubleSolenoid.Value.kReverse);
    }//end tiltDown
    
    public void disable() {
    }//end disable

    public void enable() {
    }//end enable

}//end Tilter
