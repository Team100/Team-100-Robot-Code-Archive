package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.OrangaHang.RobotMap;

/**
 *
 */
public class Intake extends Subsystem {
    DigitalInput intakeTopSwitch=RobotMap.intakeTopSwitch;
    DigitalInput intakeBottomSwitch=RobotMap.intakeBottomSwitch;
    Victor intakeMotor=RobotMap.intakeMotor; 
    
    //make these preferences!
    double intakeSpeed=-.5;//negative
    double shootingSpeed=.5;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //call to load frisbees, does NOT run shooter wheels
    public void takeFrisbees(){
        if(!intakeBottomSwitch.get()){
            intakeMotor.set(intakeSpeed);
        }
    }//end takeFrisbees
    
    //slowly moves frisbees into shooter, does NOT run shooter wheels
    public void shootFrisbees(){
        if(!intakeTopSwitch.get()){
            intakeMotor.set(shootingSpeed);
        }
    }//end shootFrisbees
    
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
    }//end manualControl
}//end Intake