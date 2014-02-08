package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;
import org.usfirst.frc100.Mk3.RobotMap;

/**
 * Controls angle of intake and runs intake rollers.
 */
public class Intake extends Subsystem implements SubsystemControl {
    
    private final double kDefaultIntakeDelay1 = .5;
    private final double kDefaultIntakeDelay2 = .5;
    private final double kDefaultIntakeDelay3 = .5;
    private final DoubleSolenoid tiltPistons = RobotMap.intakeTiltPistons;
    private final DoubleSolenoid discGripper1 = RobotMap.intakeDiscGripper1;
    private final DoubleSolenoid discGripper2 = RobotMap.intakeDiscGripper2;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Intake() {
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("IntakeDelay1")) {
            p.putDouble("IntakeDelay1", kDefaultIntakeDelay1);
        }
        if (!p.containsKey("IntakeDelay2")) {
            p.putDouble("IntakeDelay2", kDefaultIntakeDelay2);
        }
        if (!p.containsKey("IntakeDelay3")) {
            p.putDouble("IntakeDelay3", kDefaultIntakeDelay3);
        }
    }
    
    public void toggleGripper(int i){
        if(i==1){
            if(discGripper1.get()==DoubleSolenoid.Value.kForward){
                discGripper1.set(DoubleSolenoid.Value.kReverse);
            }
            else{
                discGripper1.set(DoubleSolenoid.Value.kForward);
            }
        }
        else{
            if(discGripper2.get()==DoubleSolenoid.Value.kForward){
                discGripper2.set(DoubleSolenoid.Value.kReverse);
            }
            else{
                discGripper2.set(DoubleSolenoid.Value.kForward);
            }
        }
    }
    
    public void setGripper(int gripper, boolean position){
        if(gripper==1){
            if(!position){
                discGripper1.set(DoubleSolenoid.Value.kForward);
            }
            else{
                discGripper1.set(DoubleSolenoid.Value.kReverse);
            }
        }
        else{
            if(!position){
                discGripper2.set(DoubleSolenoid.Value.kForward);
            }
            else{
                discGripper2.set(DoubleSolenoid.Value.kReverse);
            }
        }
    }
    
    public void raiseIntake(){
        tiltPistons.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void lowerIntake(){
        tiltPistons.set(DoubleSolenoid.Value.kForward);
    }
    
    public void disable() {
    }
    
    public void enable() {
        setGripper(1, true);
        setGripper(2, true);
    }
}