/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Isis
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final Encoder encoderLeft = new Encoder(7,6);
    private final Encoder encoderRight = new Encoder (2,1);
    private final Jaguar jaguarLeft = new Jaguar(3);
    private final Jaguar jaguarRight = new Jaguar(2);
    private SendablePID sendable = new SendablePID();
    private final double kDistRatio = 0.0;
    
    public Shooter(){
        sendable.setDistRatio(kDistRatio);
    }//end Shooter
    
    PIDSource source = new PIDSource(){
        public double pidGet(){
            return (encoderRight.getRaw()+ encoderLeft.getRaw())/2;
        }
    }; //end anonym class PIDSource
    
    PIDOutput output = new PIDOutput(){
        public void pidWrite(double output){
            jaguarLeft.set(output);
            jaguarRight.set(output);
        }
    }; //end anonym class PIDOutput
    
    public void setSetpoint(double setpoint){
        sendable.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
}// end Shooter
