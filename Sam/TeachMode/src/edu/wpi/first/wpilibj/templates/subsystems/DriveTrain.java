/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.commands.TankDrive;

/**
 *
 * @author Sam
 */
public class DriveTrain extends Subsystem{
    
    Jaguar leftjag;
    Jaguar rightjag;
    public static boolean reproducing = false;
    public static double reproleft = 0;
    public static double reproright = 0;
    
    public DriveTrain(){
        leftjag = new Jaguar(1);
        rightjag = new Jaguar(2);
    }
    
    public void drive(double left, double right){
        if(!reproducing){
            leftjag.set(left);
            rightjag.set(right);
        }else{
            leftjag.set(reproleft);
            rightjag.set(reproright);
        }
        
    }
    
    public void test(){
        leftjag.set(-0.3);
        rightjag.set(-0.3);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }
    
}
