/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author student
 */
public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Jaguar rightA;
    Jaguar leftA;
    RobotDrive drive;
    
    public DriveTrain(){
        rightA = new Jaguar(RobotMap.PWM2_rightDrive);
        leftA = new Jaguar(RobotMap.PWM3_leftDrive);
        drive = new RobotDrive(leftA, rightA);
    }
    public void arcadeDrive(Joystick left, Joystick right){
        drive.arcadeDrive(-right.getX(), -left.getY());
        /*Yes, both of these are negative for a reason.
         *If you've worked with Kraken before, you'll know that the motors face in opposite directions,
         * so, the negatives force it into going the way regular arcade drive works.
         */
                
        
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
