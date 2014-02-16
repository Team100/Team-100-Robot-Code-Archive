//ready, except for LED light strip
package org.usfirst.frc100.Ballrus.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Preferences;
import org.usfirst.frc100.Ballrus.RobotMap;

/**
 * Controls the shooter pull-back and trigger.
 */
public class Shooter extends Subsystem {

    private final SpeedController motor = RobotMap.shooterMotor; // positive = pull back
    private final DigitalInput forwardLimit = RobotMap.shooterForwardLimit; // true = shooter pushed forward completely
    private final DigitalInput backLimit = RobotMap.shooterBackLimit; // true = shooter pulled back completely
    private final AnalogChannel potentiometer = RobotMap.shooterPotentiometer; // positive = pull back
    private final Solenoid release = RobotMap.shooterRelease; // true = released
    //private final Relay readyIndicator = RobotMap.shooterReadyIndicator; // what type is this?
    
    private double positionError = 0; // positive = too close, negative = too far
    private boolean inPosition = true;
    
    // No default command
    public void initDefaultCommand() {
    }
    
    // Pulls the shooter back to a given amount of inches
    public void setPosition(double position){
        positionError = position-getPosition();
        inPosition = false;
        if (positionError>org.usfirst.frc100.Ballrus.Preferences.shooterDistanceBuffer&&!backLimit.get()){ // too close
            motor.set(Preferences.shooterPullBackSpeed);
        } else if (positionError<-org.usfirst.frc100.Ballrus.Preferences.shooterDistanceBuffer&&!forwardLimit.get()){ // too far
            motor.set(-Preferences.shooterPullForwardSpeed);
        } else { // correct distance
            motor.set(0);
            inPosition = true;
        }
    }
    
    // Reattaches the two parts of the shooter after a shot using the hall effect
    public boolean reload(){
        if(getPosition()<=0 || forwardLimit.get()){
            motor.set(0);
            return true;
        }
        else{
            motor.set(-Preferences.shooterPullForwardSpeed);
            return false;
        }
    }
    
    // Returns the pullback distance
    public double getPosition(){
        return potentiometer.getValue()/Preferences.shooterPotToInchRatio-Preferences.shooterPotZeroPosition;
    }
    
    // Returns whether the shooter is ready to shoot
    public boolean inPosition(){
        return inPosition;
    }
    
    // Sets the position of the quick release
    public void setTrigger(boolean forward) {
        release.set(forward);
    }
    
    // Stops the shooter motor
    public void stop(){
        motor.set(0);
    }
    
    // Directly controls motor speed
    public void manualControl(double speed){
        if(speed>0&&!backLimit.get()){
            motor.set(speed);
        }
        else if(speed<0&&!forwardLimit.get()){
            motor.set(speed);
        }
        else{
            motor.set(0);
        }
    }

    // Puts values on dashboard
    public void updateDashboard() {
        SmartDashboard.putNumber("ShooterPosition", getPosition());
        if(Preferences.shooterTuningMode){
            SmartDashboard.putNumber("ShooterRawPotValue", potentiometer.getValue());
            SmartDashboard.putBoolean("ShooterForwardHallEffect", forwardLimit.get());
            SmartDashboard.putBoolean("ShooterBackHallEffect", backLimit.get());
            SmartDashboard.putNumber("ShooterError", positionError);
            SmartDashboard.putNumber("ShooterOutput", motor.get());
            SmartDashboard.getBoolean("ShooterBackLimit", backLimit.get());
        }
    }
}