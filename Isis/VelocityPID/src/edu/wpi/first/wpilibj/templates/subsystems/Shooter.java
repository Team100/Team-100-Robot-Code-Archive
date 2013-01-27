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
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final Encoder encoderLeft = new Encoder(7,6);
    private final Encoder encoderRight = new Encoder (2,1);
    private final Victor motorFront = new Victor(3);
    private final Victor motorBack = new Victor(2);
    
    private final double kDistRatio = 1000 / ((18.0/30.0)*(7.5/12.0*3.14159));
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet    
    
    public Shooter(){
        encoderLeft.setReverseDirection(true);
        encoderRight.setReverseDirection(true);
        encoderLeft.reset();
        encoderRight.reset();
        encoderLeft.start();
        encoderRight.start();
    }
    
    PIDSource source = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderLeft_raw", encoderLeft.getRaw());
            SmartDashboard.putNumber("encoderRight_raw", encoderRight.getRaw());
            return encoderLeft.getRaw();
        }
    }; //end anonym class PIDSource
    
    PIDOutput output = new PIDOutput(){
        public void pidWrite(double output){
            motorFront.set(output);
            motorBack.set(output);
        }
    }; //end anonym class PIDOutput
    private SendablePID sendable = new SendablePID(source, output, kDistRatio);
    
    public void setSetpoint(double setpoint){
        sendable.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Shoot());
    }//end initDefaultCommand
    
    public void disable(){
        sendable.disable();
        encoderLeft.setReverseDirection(true);
        encoderRight.setReverseDirection(true);
        encoderLeft.reset();
        encoderRight.reset();
    }//end disable
    
    public void enable(){
        encoderLeft.setReverseDirection(true);
        encoderRight.setReverseDirection(true);
        encoderLeft.reset();
        encoderRight.reset();
        encoderLeft.start();
        encoderRight.start();
        sendable.enable();
    }//end enable
    
}// end Shooter
