/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 * @author Student
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final Encoder encoderLeft = new Encoder(7,6);
    private final Encoder encoderRight = new Encoder (2,1);
    private final Jaguar jaguarLeft = new Jaguar(3);
    private final Jaguar jaguarRight = new Jaguar(2);
    private final Timer timer = new Timer(); //in s (ignore WPIlib docs)
    PIDOutput write;
    private double output = 0.0;
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double sendPIDInput(){
        double input = encoderRight.getRaw();
        return input;
    }// end runPID()
    
    public void setPIDOutput(){
        output = write.writePIDOutput();
        jaguarLeft.set(output);
        jaguarRight.set(output);
    }//end setPIDOutput
    
}// end Shooter
