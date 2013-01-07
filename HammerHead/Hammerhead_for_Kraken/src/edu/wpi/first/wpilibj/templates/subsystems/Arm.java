/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.RetractArm;
import edu.wpi.first.wpilibj.templates.commands.StopArm;

/**
 *
 * @author Daniel
 */
public class Arm extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Jaguar arm;
    Jaguar winch;
    AnalogChannel pot;
    public Arm() {
//        arm = new Jaguar(RobotMap.PWM10_arm);
//        winch = new Jaguar(RobotMap.PWM9_winch);
        pot = new AnalogChannel(RobotMap.AI2_DeploymentPot);
    }
    public void runArmUp() {
        arm.set(0.6);
        System.out.println("Running Arm Up");
    }
    public void runArmDown() {
        arm.set(-0.3);
        System.out.println("Running Arm Down");
    }
    public void spoolIn() {
        winch.set(0.2);
    }
    public void spoolOut() {
        winch.set(-0.2);
    }
    public void stopArm() {
        arm.set(0.0);
        System.out.println("Stopping Arm");
    }
    public double getPot() {
        return pot.getValue();
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new RetractArm());
    }
}
