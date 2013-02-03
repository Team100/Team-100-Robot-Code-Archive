/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;
import java.util.Vector;

/**
 *
 * @author Sam
 */
public class AutoMemory extends Subsystem{
    
    Vector LeftMemory;
    Vector RightMemory;
    
    public AutoMemory(){
    }

    public void beginCollection(){
        LeftMemory = new Vector();
        RightMemory = new Vector();
    }
    
    public void collect(double left, double right){
        LeftMemory.addElement(Double.valueOf(left));
        RightMemory.addElement(Double.valueOf(right));
    }
    
    public void stopCollection(){
        //Write to a file
    }
    
    public Vector RequestLeft(){
        return LeftMemory;
    }
    
    public Vector RequestRight(){
        return RightMemory;
    }
    
    public void ReadMemory(int index){
        //read from a file and set the vectors equal to the info
    }
    
    public void Reproduce(double leftTarget, double rightTarget){
        //DriveTrain.reproleft = 0.2;
        //DriveTrain.reproright = 0.2; 
        pidLeft.setSetpoint(leftTarget);
        pidRight.setSetpoint(rightTarget);
    }
    
    protected void initDefaultCommand() {
    }
    
    //PID
    private final double Ratio = 1000 / ((18.0/30.0)*(6/12.0*3.14159));
    
    PIDSource sourceLeft = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderLeft_raw", OI.left);
            return OI.left;
        }
    }; //end anonym class PIDSource
    PIDOutput outputFront = new PIDOutput(){
        public void pidWrite(double output){
            DriveTrain.reproleft = (output);
        }
    }; //end anonym class PIDOutput
    private SendablePID pidLeft = new SendablePID("front",sourceLeft, outputFront, Ratio);
    
    
    //shooterBack
     PIDSource sourceRight = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderRight_raw", OI.right);
            return OI.right;
        }
    }; //end anonym class PIDSource
    PIDOutput outputBack = new PIDOutput(){
        public void pidWrite(double output){
             DriveTrain.reproright = output;
        }
    }; //end anonym class PIDOutput
    private SendablePID pidRight = new SendablePID("back",sourceRight, outputBack, Ratio);
    
}
