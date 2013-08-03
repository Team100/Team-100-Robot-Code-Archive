package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.Mk3.RobotMap;

/**
 *
 * @author Team100
 */
public class Feeder extends Subsystem implements SubsystemControl {
    private final DoubleSolenoid feederPistons = RobotMap.feederPistons;
    private final double kDefaultShootDelay = 0.3;
    
    public Feeder(){
        Preferences p = Preferences.getInstance();
        if(!p.containsKey("FeederShootBackDuration")) {
            p.putDouble("FeederShootBackDuration", kDefaultShootDelay);
        }
        if(!p.containsKey("FeederShootForwardDuration")) {
            p.putDouble("FeederShootForwardDuration", kDefaultShootDelay);
        }
    }//end constructor
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    public double getShootBackDuration(){
        Preferences p = Preferences.getInstance();
        final double kShootDelay = p.getDouble("FeederShootBackDuration", kDefaultShootDelay);
        return kShootDelay;
    }//end getShootDelay
    
    public double getShootForwardDuration(){
        Preferences p = Preferences.getInstance();
        final double kShootDelay = p.getDouble("FeederShootForwardDuration", kDefaultShootDelay);
        return kShootDelay;
    }//end getShootDelay
    
    public void pushForward(){
        feederPistons.set(DoubleSolenoid.Value.kReverse);
    }//end pushForward
    
    public void pullBack(){
        feederPistons.set(DoubleSolenoid.Value.kForward);
    }//end pullBack 
    
    public void disable() {
    }//end disable

    public void enable() {
    }//end enable

}//end Feeder
