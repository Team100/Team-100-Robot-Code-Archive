//ready
package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import org.usfirst.frc100.Robot2014.Preferences;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls the angle of the arm.
 */
public class Tilter extends Subsystem {

    SpeedController motor = RobotMap.tilterMotor; // positive = up
    AnalogChannel potentiometer = RobotMap.tilterPotentiometer; // positive = up

    double angleError = 0; // positive = too low, negative = too high
    boolean inPosition = true;
    
    public void initDefaultCommand() {
    }
    
    // Initializes kP value on dashboard if in tuning mode
    public Tilter(){
        if(Preferences.tilterTuningMode){
            SmartDashboard.putNumber("Tilter_kP", Preferences.tilter_kP);
        }
    }
    
    // Adjusts the motor value to reach the correct position (angle in degrees above floor)
    public void setPosition(double angle){
        angleError = angle-getAngle();
        inPosition = false;
        if (Math.abs(angleError)>Preferences.tilterAngleBuffer){ // incorrect angle
            if(Preferences.tilterTuningMode){
                motor.set(angleError*SmartDashboard.getNumber("Tilter_kP", 0));
            }
            else{
                motor.set(angleError*Preferences.tilter_kP);
            }
        } else{ // correct angle
            motor.set(0);
            inPosition = true;
        }
        if(Preferences.tilterTuningMode){
            SmartDashboard.putNumber("TilterAngle", getAngle());
            SmartDashboard.putNumber("TilterError", angleError);
            if (Math.abs(angleError)>Preferences.tilterAngleBuffer){
                SmartDashboard.putNumber("TilterOutput", angleError*SmartDashboard.getNumber("kP", 0));
            }
            else{
                SmartDashboard.putNumber("TilterOutput", 0);
            }
        }
    }
    
    // Returns the current angle above the floor in degrees
    public double getAngle(){
        return potentiometer.getValue()/Preferences.tilterPotToDegreeRatio+Preferences.tilterPotOffsetDegrees;
    }
    
    // Returns whether the tilter has reached the correct position
    public boolean inPosition(){
        return inPosition;
    }
    
    // Stops the tilter motor
    public void stop(){
        motor.set(0);
    }
}