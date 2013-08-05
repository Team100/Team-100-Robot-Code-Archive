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
    
    private Jaguar jaguar1_;
    private Jaguar jaguar2_;
    private final double k_deadband = 0.05;
    
    public MotorPIDOutput(Jaguar jaguar1, Jaguar jaguar2){
        jaguar1_ = jaguar1;
        jaguar2_ = jaguar2;
    }
    
    public double removeDeadband(double output) {
        if (output > 0.0){
            return output + k_deadband;
        }
        else if(output < 0.0){
            return output - k_deadband;
        }
        else {
            return 0.0;
        }
    }
    
    public void pidWrite(double output) {
        double new_output = removeDeadband(output / 12.0);
        jaguar1_.set(new_output);
        jaguar2_.set(new_output);
    }
    
}
