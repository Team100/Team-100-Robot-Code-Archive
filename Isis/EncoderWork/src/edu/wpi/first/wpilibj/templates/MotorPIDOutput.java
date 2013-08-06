package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Isis
 */
public class MotorPIDOutput implements PIDOutput {
    
    private Jaguar jaguar_;
    
    public MotorPIDOutput(Jaguar jaguar){
        jaguar_ = jaguar;
    }
    
    
    public void pidWrite(double output) {
        jaguar_.set(output);
    }
    
}
