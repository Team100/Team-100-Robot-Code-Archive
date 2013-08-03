/*Shooter subsystem*/
package org.usfirst.frc100.Mk3.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.Mk3.RobotMap;
import org.usfirst.frc100.Mk3.commands.ShooterOff;

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
    //Preferences defaults
    //TODO: calibrate all constants
    private final double kDefaultLowSpeed = 0.3;
    private final double kDefaultHighSpeed = 1.0;
    private final double kDefaultReverseSpeed = -1.0;
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
        if (!p.containsKey("ShooterReverseSpeed")) {
            p.putDouble("ShooterReverseSpeed", kDefaultReverseSpeed);
        }
        if (!p.containsKey("ShooterSliderEnableLowSpeed")) {
            p.putBoolean("ShooterSliderEnableLowSpeed", kDefaultSliderEnableLowSpeed);
        }
    }//end constructor
    
    public void initDefaultCommand() {
        setDefaultCommand(new ShooterOff());
    }//end initDefaultCommand
    
    //Sets lower speed for shooting by one of two ways: set speed from 
    //preferences or analog slider on driver station.
    public void primeLowSpeed(){
        Preferences p = Preferences.getInstance();
        final boolean kSliderEnableLowSpeed = p.getBoolean("ShooterSliderEnableLowSpeed", kDefaultSliderEnableLowSpeed);
        if (!kSliderEnableLowSpeed){ 
            final double kLowSpeed = p.getDouble("ShooterLowSpeed", kDefaultLowSpeed);
            motorFront.set(kLowSpeed);
            motorBack.set(kLowSpeed);
        } else {
            DriverStation ds = DriverStation.getInstance();
            final double kLowSpeed = ds.getAnalogIn(1);
            motorFront.set(kLowSpeed/5.0);
            motorBack.set(kLowSpeed/5.0);
        }
    }//end primeLowSpeed
    
    //Sets high speed to set speed in preferences.
    public void primeHighSpeed(){
        Preferences p = Preferences.getInstance();
        final double kHighSpeed = p.getDouble("ShooterHighSpeed", 0.0);
        motorFront.set(kHighSpeed);
        motorBack.set(kHighSpeed);
    }//end primeHighSpeed
    
    public void runBackwards(){
        Preferences p = Preferences.getInstance();
        final double kReverseSpeed = p.getDouble("ShooterReverseSpeed", kDefaultReverseSpeed);
        motorFront.set(kReverseSpeed);
        motorBack.set(kReverseSpeed);
    }//end runBackwards
    
    public void disable(){
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
    }//end enable
    
}// end Shooter
