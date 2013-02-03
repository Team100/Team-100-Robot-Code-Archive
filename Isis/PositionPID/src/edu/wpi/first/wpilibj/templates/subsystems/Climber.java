/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.Climb;
import edu.wpi.first.wpilibj.templates.subsystems.PIDBundle.SendablePID;

/**
 *
 * @author Isis
 */
public class Climber extends Subsystem {
    //Robot parts
    private final Encoder encoderClimberA = new Encoder (5,4);
    private final Encoder encoderClimberB = new Encoder (3,2);
    private final Jaguar motorRight = new Jaguar(9);
    private final Jaguar motorLeft = new Jaguar(10);
    
    //Constants
    private final double kClimberDistRatioA = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));
    private final double kClimberDistRatioB = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet    
    
    public Climber(){
        motorRight.setExpiration(1.0);
        motorLeft.setExpiration(1.0);
        motorRight.setSafetyEnabled(true);
        motorLeft.setSafetyEnabled(true);
        encoderClimberA.setReverseDirection(true);
        encoderClimberB.setReverseDirection(false);
        encoderClimberA.reset();
        encoderClimberB.reset();
        encoderClimberA.start();
        encoderClimberB.start();
    }//end constructor
    
    //shooterFront
    PIDSource sourceClimberA = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderClimberA_raw", encoderClimberA.getRaw());
            return encoderClimberA.getRaw();
        }
    }; //end anonym class PIDSource
    PIDOutput outputClimberA = new PIDOutput(){
        public void pidWrite(double output){
            motorLeft.set(output);
        }
    }; //end anonym class PIDOutput
    private SendablePID pidClimberA = new SendablePID("ClimberA",sourceClimberA, outputClimberA, kClimberDistRatioA);    
    
    PIDSource sourceClimberB = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderClimberB_raw", encoderClimberB.getRaw());
            return encoderClimberB.getRaw();
        }
    }; //end anonym class PIDSource
    PIDOutput outputClimberB = new PIDOutput(){
        public void pidWrite(double output){
            motorRight.set(output);
        }
    }; //end anonym class PIDOutput
    private SendablePID pidClimberB = new SendablePID("ClimberB",sourceClimberB, outputClimberB, kClimberDistRatioB);    
    
    public void setSetpoint(double setpoint){
        pidClimberA.setSetpoint(setpoint);
        pidClimberB.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Climb());
    }//end initDefaultCommand
    
    public void disable(){
        pidClimberA.disable();
        pidClimberB.disable();
        encoderClimberA.setReverseDirection(true);
        encoderClimberB.setReverseDirection(false);
        encoderClimberA.reset();
        encoderClimberB.reset();
    }//end disable
    
    public void enable(){
        encoderClimberA.setReverseDirection(true);
        encoderClimberB.setReverseDirection(false);
        encoderClimberA.reset();
        encoderClimberB.reset();
        encoderClimberA.start();
        encoderClimberB.start();
        pidClimberA.enable();
        pidClimberB.enable();
    }//end enable
    
    public void savePrefs(){
        pidClimberA.savePrefs();
        pidClimberB.savePrefs();
    }//end savePrefs
    
}// end Climber
