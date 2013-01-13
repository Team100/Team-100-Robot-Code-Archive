/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.subsystems.ThreadCreator;

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
    PIDBase m_PIDInstance;
    ThreadCreator m_thread;
    double input = 0.0;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
    public void sendPIDInput(){
        double input = encoderRight.getRaw();
        m_PIDInstance = new PIDBase(input);
        ThreadCreator(input, m_PIDInstance);
        
    }// end runPID()
}
