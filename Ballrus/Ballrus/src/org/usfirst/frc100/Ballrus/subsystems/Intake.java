//ready
package org.usfirst.frc100.Ballrus.subsystems;

import org.usfirst.frc100.Ballrus.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the rollers on the intake, as well as the actuators to move the
 * intake rollers.
 */
public class Intake extends Subsystem {

    SpeedController topMotor = RobotMap.intakeTopMotor; // positive = in
    SpeedController sideMotor = RobotMap.intakeSideMotor; // positive = in
    Solenoid topPiston = RobotMap.intakeTopPiston; // forward = raised
    Solenoid bottomPiston = RobotMap.intakeSparePiston; // forward = deployed
    DigitalInput ballDetector = RobotMap.intakeBallDetector; // true = ball

    // No default command
    public void initDefaultCommand() {
    }
    
    // Runs rollers inwards unless ball is detected inside
    public void runIn(){
        if(!ballDetector.get()){
            topMotor.set(1);
            sideMotor.set(1);
        }
        else{
            topMotor.set(0);
            sideMotor.set(0);
        }
    }
    
    // Runs rollers outwards
    public void runOut(){
        topMotor.set(-1);
        sideMotor.set(-1);
    }
    
    // Stops rollers
    public void stop(){
        topMotor.set(0);
        sideMotor.set(0);
    }
    
    // Deploys or retracts the top piston
    public void setTopPiston(boolean forward){
        if(forward){
            topPiston.set(true);
        }
        else{
            topPiston.set(false);
        }
    }
    
    // Deploys or retracts the bottom piston
    public void setBottomPiston(boolean forward){
        if(forward){
            bottomPiston.set(true);
        }
        else{
            bottomPiston.set(false);
        }
    }
    
    // Returns whether the lower roller is deployed
    public boolean getBottomPistonState(){
        return bottomPiston.get();
    }
}