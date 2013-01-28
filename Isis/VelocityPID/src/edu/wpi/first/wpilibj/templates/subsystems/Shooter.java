/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.Shoot;

/**
 *
 * @author Isis
 */
public class Shooter extends Subsystem {
    //Robot parts
    private final Encoder encoderFront = new Encoder (2,1);
    private final Victor motorFront = new Victor(3);
    private final Encoder encoderBack = new Encoder(7,6);
    private final Victor motorBack = new Victor(2);
    //Constants
    private final double kBackDistRatio = 1000 / ((18.0/30.0)*(7.5/12.0*3.14159));
    private final double kFrontDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet    
    
    public Shooter(){
        encoderFront.setReverseDirection(true);
        encoderBack.setReverseDirection(true);
        encoderFront.reset();
        encoderBack.reset();
        encoderFront.start();
        encoderBack.start();
    }//end constructor
    
    //shooterFront
    PIDSource sourceFront = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderFront_raw", encoderFront.getRaw());
            return encoderFront.getRaw();
        }
    }; //end anonym class PIDSource
    PIDOutput outputFront = new PIDOutput(){
        public void pidWrite(double output){
            motorFront.set(output);
        }
    }; //end anonym class PIDOutput
    private SendablePID pidFront = new SendablePID("front",sourceFront, outputFront, kFrontDistRatio);
    
    
    //shooterBack
     PIDSource sourceBack = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderBack_raw", encoderBack.getRaw());
            return encoderBack.getRaw();
        }
    }; //end anonym class PIDSource
    PIDOutput outputBack = new PIDOutput(){
        public void pidWrite(double output){
             motorBack.set(output);
        }
    }; //end anonym class PIDOutput
    private SendablePID pidBack = new SendablePID("back",sourceBack, outputBack, kBackDistRatio);
    
    
    public void setSetpoint(double setpoint){
        pidFront.setSetpoint(setpoint);
        pidBack.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Shoot());
    }//end initDefaultCommand
    
    public void disable(){
        pidFront.disable();
        pidBack.disable();
        encoderFront.setReverseDirection(true);
        encoderBack.setReverseDirection(true);
        encoderFront.reset();
        encoderBack.reset();
    }//end disable
    
    public void enable(){
        encoderFront.setReverseDirection(true);
        encoderBack.setReverseDirection(true);
        encoderFront.reset();
        encoderBack.reset();
        encoderFront.start();
        encoderBack.start();
        pidFront.enable();
        pidBack.enable();
    }//end enable
    
}// end Shooter
