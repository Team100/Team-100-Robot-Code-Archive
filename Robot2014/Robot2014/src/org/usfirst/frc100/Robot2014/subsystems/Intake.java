//ready
package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the rollers on the intake, as well as the actuators to move the
 * intake rollers.
 */
public class Intake extends Subsystem {

    SpeedController topMotor = RobotMap.intakeTopMotor; // positive = in
    SpeedController bottomMotor = RobotMap.intakeBottomMotor; // positive = in
    DoubleSolenoid topPiston = RobotMap.intakeTopPiston; // forward = raised
    DoubleSolenoid bottomPiston = RobotMap.intakeBottomPiston; // forward = deployed
    DigitalInput ballDetector = RobotMap.intakeBallDetector; // true = ball

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
    
    // Deploys or retracts the top piston
    public void setTopPiston(boolean forward){
        if(forward){
            topPiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            topPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    // Deploys or retracts the bottom piston
    public void setBottomPiston(boolean forward){
        if(forward){
            bottomPiston.set(DoubleSolenoid.Value.kForward);
        }
        else{
            bottomPiston.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    // Returns whether the lower roller is deployed
    public boolean getBottomPistonState(){
        return bottomPiston.get()==DoubleSolenoid.Value.kForward;
    }
}