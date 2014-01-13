//ready
package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import org.usfirst.frc100.Robot2014.commands.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the rollers on the intake, as well as the actuators to move the
 * intake rollers.
 */
public class Intake extends Subsystem {

    SpeedController topMotor = RobotMap.intakeTopMotor;
    SpeedController bottomMotor = RobotMap.intakeBottomMotor;
    DoubleSolenoid topPiston = RobotMap.intakeTopPiston;
    DoubleSolenoid bottomPiston = RobotMap.intakeBottomPiston;
    DigitalInput ballDetector = RobotMap.intakeBallDetector;

    // No default command
    public void initDefaultCommand() {
    }
    
    // Runs rollers inwards unless ball is detected inside
    public void runIn(){
        if(!ballDetector.get()){
            topMotor.set(1);
            bottomMotor.set(1);
        }
        else{
            topMotor.set(0);
            bottomMotor.set(0);
        }
    }
    
    // Runs rollers outwards
    public void runOut(){
        topMotor.set(-1);
        bottomMotor.set(-1);
    }
    
    // Stops rollers
    public void stop(){
        topMotor.set(0);
        bottomMotor.set(0);
    }
    
    public void setTopPiston(boolean forward){
        if(forward){
            topPiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            topPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    public void setBottomPiston(boolean forward){
        if(forward){
            bottomPiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            bottomPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }
}