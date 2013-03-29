/*Shooter subsystem*/
package org.usfirst.frc100.FrisBeast.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.FrisBeast.RobotMap;
import org.usfirst.frc100.FrisBeast.commands.ShooterOff;
import org.usfirst.frc100.FrisBeast.subsystems.PIDBundle.VelocitySendablePID;

/**
 *
 * @author Isis
 */
public class Shooter extends Subsystem implements SubsystemControl{
    //Robot parts
    private final Victor motorFront = RobotMap.shooterFrontMotor;
    private final Victor motorBack = RobotMap.shooterBackMotor;
    private final Counter counterFront = RobotMap.shooterCounterFront;
    private final Counter counterBack = RobotMap.shooterCounterBack;
    //Constants
    //Use raw counter values in order to simplify calibration
    private final double kBackDistRatio = 1.0; //3.0/12.0*Math.PI/4;
    private final double kFrontDistRatio = 1.0; //3.0/12.0*Math.PI/4;
    //Preferences defaults
    //TODO: calibrate all constants
    private final double kDefaultLowSpeed = 0.3;
    private final double kDefaultHighSpeed = 1.0;
    private final double kDefaultLowSpeedSetpoint = 50.0;
    private final double kDefaultHighSpeedSetpoint = 100.0;
    private final boolean kDefaultPIDEnableHighSpeed = false;
    private final boolean kDefaultPIDEnableLowSpeed = false;
    private final boolean kDefaultSliderEnableLowSpeed = true;
    
    //sets counters
    public Shooter(){
        counterFront.setMaxPeriod(1.0);
        counterBack.setMaxPeriod(1.0);
        counterFront.reset();
        counterBack.reset();
        counterFront.start();
        counterBack.start();
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("ShooterLowSpeed")) {
            p.putDouble("ShooterLowSpeed", kDefaultLowSpeed);
        }
        if (!p.containsKey("ShooterHighSpeed")) {
            p.putDouble("ShooterHighSpeed", kDefaultHighSpeed);
        }
        if (!p.containsKey("ShooterLowSpeedSetpoint")) {
            p.putDouble("ShooterLowSpeedSetpoint", kDefaultLowSpeedSetpoint);
        }
        if (!p.containsKey("ShooterHighSpeedSetpoint")) {
            p.putDouble("ShooterHighSpeedSetpoint", kDefaultHighSpeedSetpoint);
        }
        if (!p.containsKey("ShooterPIDEnableHighSpeed")) {
            p.putBoolean("ShooterPIDEnableHighSpeed", kDefaultPIDEnableHighSpeed);
        }
        if (!p.containsKey("ShooterPIDEnableLowSpeed")) {
            p.putBoolean("ShooterPIDEnableLowSpeed", kDefaultPIDEnableLowSpeed);
        }
        if (!p.containsKey("ShooterSliderEnableLowSpeed")) {
            p.putBoolean("ShooterSliderEnableLowSpeed", kDefaultSliderEnableLowSpeed);
        }
    }//end constructor
    
    //empty
    public void initDefaultCommand() {
        setDefaultCommand(new ShooterOff());
    }//end initDefaultCommand
    
    //Sets lower speed for shooting by one of three ways: PID, set speed, 
    //or analog slider on driver station
    public void primeLowSpeed(){
        Preferences p = Preferences.getInstance();
        final boolean kPIDEnableLowSpeed = p.getBoolean("ShooterPIDEnableLowSpeed", kDefaultPIDEnableLowSpeed);
        final boolean kSliderEnableLowSpeed = p.getBoolean("ShooterSliderEnableLowSpeed", kDefaultSliderEnableLowSpeed);
        if (kPIDEnableLowSpeed){
            final double kLowSpeedSetpoint = p.getDouble("ShooterLowSpeedSetpoint", 0.0);
            pidFront.setSetpoint(kLowSpeedSetpoint);
            pidBack.setSetpoint(kLowSpeedSetpoint);
            if (!isEnabled()){
                enable();
            }
        } else if (!kSliderEnableLowSpeed){ 
            disablePID();
            final double kLowSpeed = p.getDouble("ShooterLowSpeed", kDefaultLowSpeed);
            motorFront.set(kLowSpeed);
            motorBack.set(kLowSpeed);
        } else if (kSliderEnableLowSpeed){
            disablePID();
            DriverStation ds = DriverStation.getInstance();
            final double kLowSpeed = ds.getAnalogIn(1);
            motorFront.set(kLowSpeed/5.0);
            motorBack.set(kLowSpeed/5.0);
        }
    }//end primeLowSpeed
    
    //Sets high speed for shooting in one of two ways: PID or set speed.
    public void primeHighSpeed(){
        Preferences p = Preferences.getInstance();
        final boolean kPIDEnableHighSpeed = p.getBoolean("ShooterPIDEnableHighSpeed", kDefaultPIDEnableHighSpeed);
        if (kPIDEnableHighSpeed){
            final double kHighSpeedSetpoint = p.getDouble("ShooterHighSpeedSetpoint", 0.0);
            pidFront.setSetpoint(kHighSpeedSetpoint);
            pidBack.setSetpoint(kHighSpeedSetpoint/2.0);
            if (!isEnabled()){
                enable();
            }
        } else { 
            disablePID();
            final double kHighSpeed = p.getDouble("ShooterHighSpeed", 0.0);
            motorFront.set(kHighSpeed);
            motorBack.set(kHighSpeed/2.0);
        }
    }//end primeHighSpeed

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //PID Control
    
    //shooterFront
    PIDSource sourceFront = new PIDSource(){
        public double pidGet(){
            return counterFront.get();
        }
    }; //end anonym class PIDSource
    PIDSource periodFront = new PIDSource(){
        public double pidGet(){
            return counterFront.getPeriod();
        }
    };//end anonym class PIDSource periodFront
    PIDOutput outputFront = new PIDOutput(){
        public void pidWrite(double output){
            motorFront.set(output);
        }
    }; //end anonym class PIDOutput
    private VelocitySendablePID pidFront = new VelocitySendablePID("ShooterFront",sourceFront, periodFront, outputFront, kFrontDistRatio);
    
        
    //shooterBack
    PIDSource sourceBack = new PIDSource(){
        public double pidGet(){
            return counterBack.get();
        }
    }; //end anonym class PIDSource
    PIDSource periodBack = new PIDSource(){
        public double pidGet(){
            return counterBack.getPeriod();
        }
    };//end anonym class PIDSource periodFront
    PIDOutput outputBack = new PIDOutput(){
        public void pidWrite(double output){
             motorBack.set(output);
        }
    }; //end anonym class PIDOutput
    private VelocitySendablePID pidBack = new VelocitySendablePID("ShooterBack",sourceBack, periodBack, outputBack, kBackDistRatio);
    
    
    public void setSetpoint(double setpoint){
        pidFront.setSetpoint(setpoint);
        pidBack.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void writePreferences() {
        pidFront.writePreferences();
        pidBack.writePreferences();
    }//end writePreferences
    
    private boolean isEnabled(){
        return (pidFront.isEnabled() && pidBack.isEnabled());
    }//end isEnabled

    private void disablePID() {
        setSetpoint(0.0);
        pidFront.disable();
        pidBack.disable();
    }
    
    public void disable(){
        disablePID();
        counterFront.reset();
        counterBack.reset();
        motorFront.set(0.0);
        motorBack.set(0.0);
    }//end disable
    
    public void enable(){
        counterFront.reset();
        counterBack.reset();
        counterFront.start();
        counterBack.start();
        pidFront.enable();
        pidBack.enable();
    }//end enable
    
}// end Shooter
