//ready, except for LED light strip
package org.usfirst.frc100.Ballrus.subsystems;

import org.usfirst.frc100.Ballrus.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.Ballrus.Preferences;

/**
 * Controls the shooter pull-back and trigger.
 */
public class Shooter extends Subsystem {

    SpeedController motor = RobotMap.shooterMotor; // positive = pull back
    DigitalInput hallEffectForward = RobotMap.shooterHallEffectForward; // true = shooter pushed forward completely
    DigitalInput hallEffectBack = RobotMap.shooterHallEffectBack; // true = shooter pulled back completely
    AnalogChannel potentiometer = RobotMap.shooterPotentiometer; // positive = pull back
    DoubleSolenoid release = RobotMap.shooterRelease; // forward = released
    Encoder encoder = RobotMap.shooterEncoder; // increase = pull back
    Relay readyIndicator = RobotMap.shooterReadyIndicator; // what type is this?
    
    double positionError = 0; // positive = too close, negative = too far
    boolean inPosition = true;
    
    // No default command
    public void initDefaultCommand() {
    }
    
    // Adjusts the position of the shooter
    // WARNING: If encoder is used, DO NOT call this command before first shot
    public void setPosition(double position){
//        if(hallEffectForward.get()){
//            encoder.reset();
//        }
        positionError = position-getPosition();
        inPosition = false;
        if (positionError>org.usfirst.frc100.Ballrus.Preferences.shooterDistanceBuffer&&!hallEffectBack.get()){ // too close
            motor.set(Preferences.shooterPullBackSpeed);
        } else if (positionError<-org.usfirst.frc100.Ballrus.Preferences.shooterDistanceBuffer&&!hallEffectForward.get()){ // too far
            motor.set(-Preferences.shooterPullForwardSpeed);
        } else { // correct distance
            motor.set(0);
            inPosition = true;
        }
        if(Preferences.shooterTuningMode){
            SmartDashboard.putNumber("ShooterSensorValue", potentiometer.getValue());
//            SmartDashboard.putNumber("ShooterSensorValue", encoder.get());
            SmartDashboard.putNumber("ShooterPosition", getPosition());
            SmartDashboard.putNumber("ShooterError", positionError);
            SmartDashboard.putNumber("ShooterOutput", motor.get());
            SmartDashboard.getBoolean("ShooterForwardLimit", hallEffectForward.get());
            SmartDashboard.getBoolean("ShooterBackLimit", hallEffectBack.get());
        }
    }
    
    // Reattaches the two parts of the shooter after a shot using the hall effect
    public boolean reload(){
        if(hallEffectForward.get()){
            motor.set(0);
            return true;
        }
        else{
            motor.set(-Preferences.shooterPullForwardSpeed);
            return false;
        }
    }
    
    // Returns the pullback distance in inches
    public double getPosition(){
        return potentiometer.getValue()/Preferences.shooterPotToInchRatio+Preferences.shooterPotOffsetInches;
//        return encoder.get()/Preferences.shooterEncoderToInchRatio;
    }
    
    // Returns whether the shooter is ready to shoot
    public boolean inPosition(){
        return inPosition;
    }
    
    // Sets the position of the quick release
    public void setTrigger(boolean forward){
        if(forward){
            release.set(DoubleSolenoid.Value.kForward);
        }
        else{
            release.set(DoubleSolenoid.Value.kReverse);
        }
    }
    
    // Stops the shooter motor
    public void stop(){
        motor.set(0);
    }
    
    // Directly controls motor speed
    public void manualControl(double speed){
        if(speed>0&&!hallEffectBack.get()){
            motor.set(speed);
        }
        else if(speed<0&&!hallEffectForward.get()){
            motor.set(speed);
        }
        else{
            motor.set(0);
        }
    }
}