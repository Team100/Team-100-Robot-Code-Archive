/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.TestKicker;

/**
 *
 * @author Daniel
 */
public class Kicker extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Jaguar roller;
    Jaguar kicker;
    Counter photogate;
    DigitalInput primer;
    
    public Kicker() {
        roller = new Jaguar(RobotMap.PWM7_roller);
        kicker = new Jaguar(RobotMap.PWM6_kicker);
        photogate = new Counter(RobotMap.DIO5_KickerPhotogate);
        primer = new DigitalInput(RobotMap.DIO10_KickerReady);
        photogate.reset();
        photogate.start();
    }
    
    public void runRoller() {
        roller.set(1.0);
    }
    
    public void resetCounter() {
        photogate.reset();
    }
    public void testSensors() {
        System.out.println("Counter: " + photogate.get());
        System.out.println("Button: " + primer.get());
    }
    public void kick() {
        if(photogate.get() % 2 != 1) {
            kicker.set(0.4);
            System.out.println("Waiting to be tripped");
        } else {
            kicker.set(0.0);
            System.out.println("Tripped");
        }
    }
    
    public int getCounts() {
        return photogate.get();
    }
    
    public boolean getPressed() {
        return primer.get();
    }
    
    public void set() {
        if(primer.get()) {
            kicker.set(-0.4);
        }
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new TestKicker());
    }
}
