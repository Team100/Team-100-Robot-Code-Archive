package org.usfirst.frc100.FrisBeast.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.FrisBeast.RobotMap;

/**
 *
 * @author Team100
 */
public class Feeder extends Subsystem implements SubsystemControl {
    private final DoubleSolenoid feederPistons = RobotMap.feederPistons;
    private final double kDefaultShootDelay = 0.5;
    
    public Feeder(){
        Preferences p = Preferences.getInstance();
        if(!p.containsKey("FeederShootDelay")) {
            p.putDouble("FeederShootDelay", kDefaultShootDelay);
        }
    }//end constructor
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    public double getShootDelay(){
        Preferences p = Preferences.getInstance();
        final double kShootDelay = p.getDouble("FeederShootDelay", kDefaultShootDelay);
        return kShootDelay;
    }//end getShootDelay
    
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

}//end Feeder
