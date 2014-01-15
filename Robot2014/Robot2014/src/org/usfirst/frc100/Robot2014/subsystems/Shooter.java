package org.usfirst.frc100.Robot2014.subsystems;

import org.usfirst.frc100.Robot2014.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.Robot2014.Preferences;

/**
 * Controls the shooter pull-back and trigger.
 */
public class Shooter extends Subsystem {

    SpeedController motor = RobotMap.shooterMotor; // positive = pull back
    DigitalInput hallEffectForward = RobotMap.shooterHallEffectForward; // true = pressed
    DigitalInput hallEffectBack = RobotMap.shooterHallEffectBack; // true = pressed
    AnalogChannel potentiometer = RobotMap.shooterPotentiometer; // positive = pull back
    DoubleSolenoid release = RobotMap.shooterRelease; // forward = released
    Encoder encoder = RobotMap.shooterEncoder; // positive = pull back
    Relay readyIndicator = RobotMap.shooterReadyIndicator; // what type is this?
    
    double positionError = 0; // positive = too close, negative = too far
    boolean inPosition = true;
    
    public void initDefaultCommand() {
    }
    
    // Adjusts the position of the shooter
    // WARNING: If encoder is used, DO NOT call this command before first shot
    public void setPosition(double position){
        if(hallEffectForward.get()){
            encoder.reset();
        }
        positionError = position-getPosition();
        inPosition = false;
        if (positionError>org.usfirst.frc100.Robot2014.Preferences.shooterDistanceBuffer&&!hallEffectBack.get()){ // too close
            motor.set(Preferences.shooterPullBackSpeed);
        } else if (positionError<-org.usfirst.frc100.Robot2014.Preferences.shooterDistanceBuffer&&!hallEffectForward.get()){ // too far
            motor.set(-Preferences.shooterPullForwardSpeed);
        } else { // correct distance
            motor.set(0);
            inPosition = true;
        }
    }
    
    // Returns the pullback distance in inches
    public double getPosition(){
        return potentiometer.getValue()/Preferences.shooterPotToInchRatio+Preferences.shooterPotOffsetInches;
        // return encoder.get()/Preferences.shooterEncoderToInchRatio;
    }
    
    public void setTrigger(boolean forward){
        if(forward){
            release.set(DoubleSolenoid.Value.kForward);
        }
        else{
            release.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    // Returns whether the shooter is ready to shoot
    public boolean inPosition(){
        return inPosition;
    }
    
    // Stops the shooter motor
    public void stop(){
        motor.set(0);
    }
}