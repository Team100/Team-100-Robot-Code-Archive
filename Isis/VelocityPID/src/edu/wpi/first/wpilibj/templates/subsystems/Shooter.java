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
    PIDBase m_PIDInstance;
    SendablePID sendable;
    private double output = 0.0;
    
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    public void sendPIDInput(){
        sendable = new SendablePID();
        double input = encoderRight.getRaw();
        sendable.activatePID(input);
    }// end runPID()
    
    public void sendPIDOutput(){
        output = sendable.returnOutput();
        jaguarLeft.set(output);
        jaguarRight.set(output);
    }
    
}// end Shooter
