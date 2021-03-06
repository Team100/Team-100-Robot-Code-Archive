//ready
package org.usfirst.frc100.Ballrus.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.RobotMap;

/**
 * Controls the angle of the arm.
 */
public class Tilter extends Subsystem {

    private final SpeedController motor = RobotMap.tilterMotor; // positive = tilt up
    private final AnalogChannel potentiometer = RobotMap.tilterPotentiometer; // increase = up
    private final DigitalInput topLimit = RobotMap.tilterTopLimit; // false = too far
    private final DigitalInput bottomLimit = RobotMap.tilterBottomLimit; // true = too far
    Timer period = new Timer();
    
    double angleError = 0, lastError = 0, totalError = 0; // positive = too low, negative = too high
    boolean inPosition;
    double lastSetpoint = 0;
    
    // No default command
    public void initDefaultCommand() {
    }
    
    // Initializes kP value on dashboard if in tuning mode
    public Tilter(){
        if(Preferences.tilterTuningMode){
            SmartDashboard.putNumber("Tilter_kP", Preferences.tilter_kP);
            SmartDashboard.putNumber("Tilter_kI", Preferences.tilter_kI);
            SmartDashboard.putNumber("Tilter_kD", Preferences.tilter_kD);
            SmartDashboard.putNumber("TilterTestAngle", Preferences.tilterShootHighAngle);
        }
        angleError = 0.0;
        inPosition = true;
    }
    
    // Adjusts the motor value to reach the correct position (angle in degrees above floor)
    public void setPosition(double angle){
        angleError = angle-getAngle();
        if((Preferences.practiceBot^topLimit.get()&&angleError>0)||(bottomLimit.get()&&angleError<0)){
            motor.set(0);
            
            return;
        }
        if(angle!=lastSetpoint){
            lastSetpoint = angle;
            period.reset();
            period.start();
            lastError = angleError;
            inPosition = false;
            totalError = 0;
            return;
        }
        if (Math.abs(angleError)>Preferences.tilterAngleBuffer){ // incorrect angle
            if(Preferences.tilterTuningMode){
                motor.set(-(angleError*SmartDashboard.getNumber("Tilter_kP", 0)+SmartDashboard.getNumber("Tilter_kI")*totalError+SmartDashboard.getNumber("Tilter_kD", 0)*(angleError-lastError)/period.get()));
            }
            else{
                motor.set(-(angleError*Preferences.tilter_kP+Preferences.tilter_kI*totalError+Preferences.tilter_kD*(angleError-lastError)/period.get()));
            }
            period.reset();
            period.start();
            totalError+=angleError;
            lastError = angleError;
        } else{ // correct angle
            motor.set(0);
            inPosition = true;
        }
    }
    
    // Returns the current angle above the floor in degrees
    public double getAngle(){
        return (((double)potentiometer.getValue()-Preferences.tilterPot180DegreePosition)/Preferences.tilterPotToDegreeRatio+180);
    }
    
    // Returns whether the tilter has reached the correct position
    public boolean inPosition(){
        return inPosition;
    }
    
    // Stops the tilter motor
    public void stop(){
        motor.set(0);
    }
    
    // Returns the value of the forward limit switch
    public boolean getTopLimit(){
        return topLimit.get();
    }
    
    // Returns the value of the back limit switch
    public boolean getBottomLimit(){
        return bottomLimit.get();
    }
    
    // Returns the value of the potentiometer
    public double getSensorValue(){
        return potentiometer.getValue();
    }
    
    // Directly controls motor speed
    public void manualControl(double speed){
        if((Preferences.practiceBot^topLimit.get()&&speed>0)||(bottomLimit.get()&&speed<0)){
            motor.set(0);
        }
        else{
            motor.set(-speed);
        }
    }

    public void updateDashboard() {
        SmartDashboard.putNumber("TilterAngle", getAngle());
        if(Preferences.tilterTuningMode){
            SmartDashboard.putNumber("TilterSensorValue", potentiometer.getValue());
            SmartDashboard.putNumber("TilterError", angleError);
            SmartDashboard.putNumber("TilterOutput", motor.get());
            SmartDashboard.putBoolean("TilterTopLimit", Preferences.practiceBot^topLimit.get());
            SmartDashboard.putBoolean("TilterBottomLimit", bottomLimit.get());
        }
    }
}