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

    private final SpeedController motor = RobotMap.shooterMotor; // positive = pull back
    private final DigitalInput forwardHallEffect = RobotMap.shooterForwardHallEffect; // true = shooter pushed forward completely
    private final DigitalInput backHallEffect = RobotMap.shooterBackHallEffect; // true = shooter pulled back completely
    private final AnalogChannel potentiometer = RobotMap.shooterPotentiometer; // positive = pull back
    private final Solenoid release = RobotMap.shooterRelease; // true = released
    //private final Encoder encoder = RobotMap.shooterEncoder; // increase = pull back
    //private final Relay readyIndicator = RobotMap.shooterReadyIndicator; // what type is this?
    
    private double positionError = 0; // positive = too close, negative = too far
    private boolean inPosition = true;
    
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
        if (positionError>org.usfirst.frc100.Ballrus.Preferences.shooterDistanceBuffer&&!backHallEffect.get()){ // too close
            motor.set(Preferences.shooterPullBackSpeed);
        } else if (positionError<-org.usfirst.frc100.Ballrus.Preferences.shooterDistanceBuffer&&!forwardHallEffect.get()){ // too far
            motor.set(-Preferences.shooterPullForwardSpeed);
        } else { // correct distance
            motor.set(0);
            inPosition = true;
        }
        SmartDashboard.putNumber("ShooterPosition", getPosition());
        if(Preferences.shooterTuningMode){
            SmartDashboard.putNumber("ShooterSensorValue", potentiometer.getValue());
//            SmartDashboard.putNumber("ShooterSensorValue", encoder.get());
            SmartDashboard.putNumber("ShooterError", positionError);
            SmartDashboard.putNumber("ShooterOutput", motor.get());
            SmartDashboard.getBoolean("ShooterForwardLimit", forwardHallEffect.get());
            SmartDashboard.getBoolean("ShooterBackLimit", backHallEffect.get());
        }
    }
    
    // Reattaches the two parts of the shooter after a shot using the hall effect
    public boolean reload(){
        if(forwardHallEffect.get()){
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
            release.set(true);
        }
        else{
            release.set(false);
        }
    }
    
    // Stops the shooter motor
    public void stop(){
        motor.set(0);
    }
    
    // Directly controls motor speed
    public void manualControl(double speed){
        if(speed>0&&!backHallEffect.get()){
            motor.set(speed);
        }
        else if(speed<0&&!forwardHallEffect.get()){
            motor.set(speed);
        }
        else{
            motor.set(0);
        }
        SmartDashboard.putNumber("ShooterPosition", getPosition());
        if(Preferences.shooterTuningMode){
            SmartDashboard.putNumber("ShooterSensorValue", potentiometer.getValue());
//            SmartDashboard.putNumber("ShooterSensorValue", encoder.get());
            SmartDashboard.putNumber("ShooterError", positionError);
            SmartDashboard.putNumber("ShooterOutput", motor.get());
            SmartDashboard.getBoolean("ShooterForwardLimit", forwardHallEffect.get());
            SmartDashboard.getBoolean("ShooterBackLimit", backHallEffect.get());
        }
    }
}