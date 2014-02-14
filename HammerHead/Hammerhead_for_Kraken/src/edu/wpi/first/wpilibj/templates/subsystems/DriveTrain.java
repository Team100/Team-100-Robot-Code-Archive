/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.commands.*;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Daniel
 */
public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Jaguar rightA;
    Jaguar rightB;
    Jaguar leftA;
    Jaguar leftB;
   
    public DriveTrain() {
        rightA = new Jaguar(RobotMap.PWM2_rightDriveA);
        leftA = new Jaguar(RobotMap.PWM4_leftDriveA);
        
    }
    
    public void tankDrive(double left, double right) {
        leftA.set(-left);
        rightA.set(right);
//        System.out.println("LeftA: " + leftB.get());
//        System.out.println("RightA: " + rightB.get());
    }
    public void test() {
        leftA.set(1.0);
        System.out.println("LeftA: " + leftA.get());
        rightA.set(1.0);
        System.out.println("RightA: " + rightA.get());
    
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new TankDrive());
    }
}
