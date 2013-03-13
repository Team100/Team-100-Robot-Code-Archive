/*Shooter subsystem*/
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.ShooterOff;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.VelocitySendablePID;

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
    private final double kDefaultDumpSpeed = 0.3;
    private final double kDefaultShootSpeed = 1.0;
    private final double kDefaultReverseSpeed = -0.3;
    private final double kDefaultDumpSetpoint = 50.0;
    private final double kDefaultShootSetpoint = 100.0;
    private final boolean kDefaultPIDEnableShoot = false;
    private final boolean kDefaultPIDEnableDump = false;
    private final boolean kDefaultSliderEnableDump = true;
    
    //sets counters
    public Shooter(){
        counterFront.setMaxPeriod(1.0);
        counterBack.setMaxPeriod(1.0);
        counterFront.reset();
        counterBack.reset();
        counterFront.start();
        counterBack.start();
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("ShooterDumpSpeed")) {
            p.putDouble("ShooterDumpSpeed", kDefaultDumpSpeed);
        }
        if (!p.containsKey("ShooterShootSpeed")) {
            p.putDouble("ShooterShootSpeed", kDefaultShootSpeed);
        }
        if (!p.containsKey("ShooterReverseSpeed")) {
            p.putDouble("ShooterReverseSpeed", kDefaultReverseSpeed);
        }
        if (!p.containsKey("ShooterDumpSetpoint")) {
            p.putDouble("ShooterDumpSetpoint", kDefaultDumpSetpoint);
        }
        if (!p.containsKey("ShooterShootSetpoint")) {
            p.putDouble("ShooterShootSetpoint", kDefaultShootSetpoint);
        }
        if (!p.containsKey("ShooterPIDEnableShoot")) {
            p.putBoolean("ShooterPIDEnableShoot", kDefaultPIDEnableShoot);
        }
        if (!p.containsKey("ShooterPIDEnableDump")) {
            p.putBoolean("ShooterPIDEnableDump", kDefaultPIDEnableDump);
        }
        if (!p.containsKey("ShooterSliderEnableDump")) {
            p.putBoolean("ShooterSliderEnableDump", kDefaultSliderEnableDump);
        }
    }//end constructor
    
    //empty
    public void initDefaultCommand() {
        setDefaultCommand(new ShooterOff());
    }//end initDefaultCommand
    
    //set speed for dumping
    public void dumpFrisbees(){
        Preferences p = Preferences.getInstance();
        final boolean kPIDEnableDump = p.getBoolean("ShooterPIDEnableDump", false);
        final boolean kSliderEnableDump = p.getBoolean("ShooterSliderEnableDump", true);
        if (kPIDEnableDump){
            final double kDumpSetpoint = p.getDouble("ShooterDumpSetpoint", 0.0);
            pidFront.setSetpoint(kDumpSetpoint);
            pidBack.setSetpoint(kDumpSetpoint);
            if (!isEnabled()){
                enable();
            }
        } else if (!kSliderEnableDump){ 
            disablePID();
            final double kDumpSpeed = p.getDouble("ShooterDumpSpeed", 0.0);
            motorFront.set(kDumpSpeed);
            motorBack.set(kDumpSpeed);
        } else if (kSliderEnableDump){
            disablePID();
            DriverStation ds = DriverStation.getInstance();
            final double kDumpSpeed = ds.getAnalogIn(1);
            motorFront.set(kDumpSpeed/5.0);
            motorBack.set(kDumpSpeed/5.0);
        }
    }//end dumpFrisbees
    
    //set speed for shooting
    public void shootFrisbees(){
        Preferences p = Preferences.getInstance();
        final boolean kPIDEnableShoot = p.getBoolean("ShooterPIDEnableShoot", true);
        if (kPIDEnableShoot){
            final double kShootSetpoint = p.getDouble("ShooterShootSetpoint", 0.0);
            pidFront.setSetpoint(kShootSetpoint);
            pidBack.setSetpoint(kShootSetpoint/2.0);
            if (!isEnabled()){
                enable();
            }
        } else { 
            disablePID();
            final double kShootSpeed = p.getDouble("ShooterShootSpeed", 0.0);
            motorFront.set(kShootSpeed);
            motorBack.set(kShootSpeed/2.0);
        }
    }//end shootFrisbees
    
    //runs shooter in reverse direction for intake
    public void intakeFrisbees(){
        disablePID();
        Preferences p = Preferences.getInstance();
        final double kReverseSpeed = p.getDouble("ShooterReverseSpeed", 0.0);
        motorFront.set(kReverseSpeed);
        motorBack.set(kReverseSpeed*2/3);
    }//end intakeFrisbees

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
