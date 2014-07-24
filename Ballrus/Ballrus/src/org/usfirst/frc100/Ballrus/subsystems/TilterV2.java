//ready
package org.usfirst.frc100.Ballrus.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.PID;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.RobotMap;

/**
 * Controls the angle of the arm.
 */
public class TilterV2 extends Subsystem {

    private final SpeedController motor = RobotMap.tilterMotor; // positive = tilt up
    private final AnalogChannel potentiometer = RobotMap.tilterPotentiometer; // increase = up
    private final DigitalInput topLimit = RobotMap.tilterTopLimit; // false = too far
    private final DigitalInput bottomLimit = RobotMap.tilterBottomLimit; // true = too far
    private PID tilterPID = new PID("tilter");
    
    double angleError = 0.0; // positive = too low, negative = too high
    double output = 0.0;
    boolean inPosition = true;
    
    // No default command
    public void initDefaultCommand() {
    }
    
    // Initializes kP value on dashboard if in tuning mode
    public TilterV2(){
        if(Preferences.tilterTuningMode){
            SmartDashboard.putNumber("Tilter_kP", Preferences.tilter_kP);
            SmartDashboard.putNumber("Tilter_kI", Preferences.tilter_kI);
            SmartDashboard.putNumber("Tilter_kD", Preferences.tilter_kD);
            SmartDashboard.putNumber("TilterTestAngle", Preferences.tilterShootHighAngle);
        }
    }
    
    // Refreshes the values from the PID, returns true when in position
    public void updatePID(){
        output = tilterPID.getValue(getPotDegrees());
        if((Preferences.practiceBot^topLimit.get()&&output>0)||(bottomLimit.get()&&output<0)){
            motor.set(0);
        }
        else{
            motor.set(output);
        }
        inPosition = output<Preferences.tilterAngleBuffer;
    }
    
    // Sets the target distance
    public void setDistanceSetPoint(double setpoint){
        tilterPID.setSetpoint(setpoint);
    }
    
    // Returns the current angle above the floor in degrees
    public double getPotDegrees(){
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
        SmartDashboard.putNumber("TilterAngle", getPotDegrees());
        if(Preferences.tilterTuningMode){
            SmartDashboard.putNumber("TilterSensorValue", potentiometer.getValue());
            SmartDashboard.putNumber("TilterError", angleError);
            SmartDashboard.putNumber("TilterOutput", motor.get());
            SmartDashboard.putBoolean("TilterTopLimit", Preferences.practiceBot^topLimit.get());
            SmartDashboard.putBoolean("TilterBottomLimit", bottomLimit.get());
        }
    }
}