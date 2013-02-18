
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 */
public class Intake extends Subsystem {
    DigitalInput intakeTopSwitch=RobotMap.intakeTopSwitch;
    DigitalInput intakeBottomSwitch=RobotMap.intakeBottomSwitch;
    Victor intakeMotor=RobotMap.intakeMotor; 
    
    //make these preferences!
    double intakeSpeed=-1;
    double shootingSpeed=.5;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    //call before loading frisbees
    public void takeFrisbees(){
        if(!intakeBottomSwitch.get()){
            intakeMotor.set(intakeSpeed);
        }
    }
    
    //slowly moves frisbees into shooter
    public void shootFrisbees(){
        if(!intakeTopSwitch.get()){
            intakeMotor.set(shootingSpeed);
        }
    }
    
    //sets motor to a given value, has safeties
    public void manualControl(double d){
        if (intakeTopSwitch.get()&&d>0||intakeBottomSwitch.get()&&d<0){
            intakeMotor.set(0);
        }
        if (intakeTopSwitch.get()&&d<0){
            intakeMotor.set(d);
        }
        if (intakeBottomSwitch.get()&&d>0){
            intakeMotor.set(d);
        }
        if(!intakeTopSwitch.get()&&!intakeBottomSwitch.get()){
            intakeMotor.set(d);
        }
    }
}