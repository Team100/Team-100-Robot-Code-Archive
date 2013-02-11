/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.Shoot;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.VelocitySendablePID;

/**
 *
 * @author Isis
 */
public class Shooter extends Subsystem {
    //Robot parts
    private final DigitalInput opticalFront = RobotMap.shooterFrontOptical;
    private final DigitalInput opticalBack = RobotMap.shooterBackOptical;
    private final Victor motorFront = RobotMap.shooterFrontMotor;
    private final Victor motorBack = RobotMap.shooterBackMotor;
    private Counter counterFront = new Counter(opticalFront);
    private Counter counterBack = new Counter(opticalBack);
    //Constants
    private final double kBackDistRatio = 1000 / ((18.0/30.0)*(7.5/12.0*3.14159));
    private final double kFrontDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));
    //encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet    
    
    public Shooter(){
        counterFront.reset();
        counterBack.reset();
        counterFront.start();
        counterBack.start();
    }//end constructor
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Shoot());
    }//end initDefaultCommand
    
    //add manual control option here!
    
    //PID Control
    
    //shooterFront
    PIDSource sourceFront = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("counterFront_raw", counterFront.get());
            return counterFront.get();
        }
    }; //end anonym class PIDSource
    PIDOutput outputFront = new PIDOutput(){
        public void pidWrite(double output){
            motorFront.set(output);
        }
    }; //end anonym class PIDOutput
    private VelocitySendablePID pidFront = new VelocitySendablePID("front",sourceFront, outputFront, kFrontDistRatio);
    
    //shooterBack
     PIDSource sourceBack = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("counterBack_raw", counterBack.get());
            return counterBack.get();
        }
    }; //end anonym class PIDSource
    PIDOutput outputBack = new PIDOutput(){
        public void pidWrite(double output){
             motorBack.set(output);
        }
    }; //end anonym class PIDOutput
    private VelocitySendablePID pidBack = new VelocitySendablePID("back",sourceBack, outputBack, kBackDistRatio);
    
    public void setSetpoint(double setpoint){
        pidFront.setSetpoint(setpoint);
        pidBack.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void disable(){
        pidFront.disable();
        pidBack.disable();
        counterFront.reset();
        counterBack.reset();
    }//end disable
    
    public void enable(){
        counterFront.reset();
        counterBack.reset();
        counterFront.start();
        counterBack.start();
        pidFront.enable();
        pidBack.enable();
    }//end enable
    
}// end Shooter
