/*Shooter subsystem*/
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
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
    private final double kDefaultDumpSpeed = 0.5;
    private final double kDefaultShootSpeed = 0.2;
    private final double kDefaultReverseSpeed = -0.1;
    private final double kDefaultDumpSetpoint = 10.0;
    private final double kDefaultShootSetpoint = 50.0;
    private final boolean kDefaultPIDEnable = true;
    
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
        if (!p.containsKey("ShooterPIDEnable")) {
            p.putBoolean("ShooterPIDEnable", kDefaultPIDEnable);
        }
    }//end constructor
    
    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //set speed for dumping
    public void dumpFrisbees(){
        Preferences p = Preferences.getInstance();
        final boolean kPIDEnable = p.getBoolean("ShooterPIDEnable", true);
        if (kPIDEnable){
            final double kDumpSetpoint = p.getDouble("ShooterDumpSetpoint", 0.0);
            pidFront.setSetpoint(kDumpSetpoint);
            pidBack.setSetpoint(kDumpSetpoint/2.0);
            if (!isEnabled()){
                enable();
            }
        } else { 
            final double kDumpSpeed = p.getDouble("ShooterDumpSpeed", 0.0);
            motorFront.set(kDumpSpeed);
            motorBack.set(kDumpSpeed/2.0);
        }
    }//end dumpFrisbees
    
    //set speed for shooting
    public void shootFrisbees(){
        Preferences p = Preferences.getInstance();
        final boolean kPIDEnable = p.getBoolean("ShooterPIDEnable", true);
        if (kPIDEnable){
            final double kShootSetpoint = p.getDouble("ShooterShootSetpoint", 0.0);
            pidFront.setSetpoint(kShootSetpoint);
            pidBack.setSetpoint(kShootSetpoint/2.0);
            if (!isEnabled()){
                enable();
            }
        } else { 
            final double kShootSpeed = p.getDouble("ShooterShootSpeed", 0.0);
            motorFront.set(kShootSpeed);
            motorBack.set(kShootSpeed/2.0);
        }
    }//end shootFrisbees
    
    //runs shooter in reverse direction for intake
    public void intakeFrisbees(){
        Preferences p = Preferences.getInstance();
        final double kReverseSpeed = p.getDouble("ShooterReverseSpeed", 0.0);
        motorFront.set(kReverseSpeed);
        motorBack.set(kReverseSpeed);
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
    private VelocitySendablePID pidFront = new VelocitySendablePID("FrontShooter",sourceFront, periodFront, outputFront, kFrontDistRatio);
    
        
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
    private VelocitySendablePID pidBack = new VelocitySendablePID("BackShooter",sourceBack, periodBack, outputBack, kBackDistRatio);
    
    
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
    
    public void disable(){
        setSetpoint(0.0);
        pidFront.disable();
        pidBack.disable();
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
