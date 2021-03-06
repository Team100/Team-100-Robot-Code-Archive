//ready
package org.usfirst.frc100.Ballrus.subsystems;

import org.usfirst.frc100.Ballrus.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.commands.StopIntake;

/**
 * Controls the rollers on the intake, as well as the actuators to move the
 * intake rollers.
 */
public class Intake extends Subsystem {

    private final SpeedController topMotor = RobotMap.intakeTopMotor; // positive = in
    private final SpeedController sideMotor = RobotMap.intakeSideMotor; // positive = in
    private final Solenoid topPiston = RobotMap.intakeTopPiston; // true = raised
    private final Solenoid bottomPiston = RobotMap.intakeSparePiston; // true = deployed
    private final DigitalInput ballDetector = RobotMap.intakeBallDetector; // true = no ball

    // No default command
    public void initDefaultCommand() {
        this.setDefaultCommand(new StopIntake());
    }
    
    // Adds intake preferences to dashboard during tuning mode
    public Intake(){
        if(Preferences.intakeTuningMode){
            SmartDashboard.putNumber("IntakeInSpeed", Preferences.intakeInSpeed);
            SmartDashboard.putNumber("IntakeOutSpeed", Preferences.intakeOutSpeed);
            SmartDashboard.putNumber("IntakeSideRollerSpeed", Preferences.intakeSideRollerSpeed);
        }
    }
    
    // Runs rollers inwards unless ball is detected inside
    public void runIn(){
        if(ballDetector.get()){
            if(Preferences.intakeTuningMode){
                topMotor.set(SmartDashboard.getNumber("IntakeInSpeed"));
                sideMotor.set(SmartDashboard.getNumber("IntakeSideRollerSpeed"));
            } else {
                topMotor.set(Preferences.intakeInSpeed);
                sideMotor.set(Preferences.intakeSideRollerSpeed);
            }
        } else {
            topMotor.set(0);
            sideMotor.set(0);
        }
    }
    
    // Runs rollers outwards
    public void runOut() {
        topPiston.set(false);
        if (Preferences.intakeTuningMode) {
            topMotor.set(-SmartDashboard.getNumber("IntakeOutSpeed"));
            sideMotor.set(0);
        } else {
            topMotor.set(-Preferences.intakeOutSpeed);
            sideMotor.set(0);
        }
    }
    
    // Stops rollers
    public void stop(){
        topMotor.set(0.0);
        sideMotor.set(0.0);
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
    
    // Returns whether the upper roller is deployed. true if up, false if not up
    public boolean getTopPiston() {
        return topPiston.get();
    }
    
    // Returns whether the lower roller is deployed
    public boolean getBottomPistonState(){
        return bottomPiston.get();
    }

    // Puts values on the smart dashboard
    public void updateDashboard() {
        SmartDashboard.putBoolean("IntakeBallDetector", ballDetector.get());
    }
}