/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.Climb;
import edu.wpi.first.wpilibj.templates.subsystems.PIDBundle.SendablePID;

/**
 *
 * @author Isis
 */
public class Climber extends Subsystem {
    //Robot parts
    private final Encoder encoderClimber = new Encoder (2,1);
    private final Victor motorClimberA = new Victor(3);
    private final Victor motorClimberB = new Victor(2);
    //Constants
    private final double kClimberDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet    
    
    public Climber(){
        encoderClimber.setReverseDirection(true);
        encoderClimber.reset();
        encoderClimber.start();
    }//end constructor
    
    //shooterFront
    PIDSource sourceClimber = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderClimber_raw", encoderClimber.getRaw());
            return encoderClimber.getRaw();
        }
    }; //end anonym class PIDSource
    PIDOutput outputClimber = new PIDOutput(){
        public void pidWrite(double output){
            motorClimberA.set(output);
            motorClimberB.set(output);
        }
    }; //end anonym class PIDOutput
    private SendablePID pidClimber = new SendablePID("Climber",sourceClimber, outputClimber, kClimberDistRatio);    
    
    public void setSetpoint(double setpoint){
        pidClimber.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Climb());
    }//end initDefaultCommand
    
    public void disable(){
        pidClimber.disable();
        encoderClimber.setReverseDirection(true);
        encoderClimber.reset();
    }//end disable
    
    public void enable(){
        encoderClimber.setReverseDirection(true);
        encoderClimber.reset();
        encoderClimber.start();
        pidClimber.enable();
    }//end enable
    
}// end Climber
