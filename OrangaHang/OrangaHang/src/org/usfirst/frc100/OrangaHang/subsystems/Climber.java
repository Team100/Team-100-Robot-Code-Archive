/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.Climb;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.PositionSendablePID;

/**
 *
 * @author Isis
 */
public class Climber extends Subsystem {
    //Robot parts
    private final Encoder encoder = RobotMap.climberEncoder;
    private final Victor motorTop = RobotMap.climberTopMotor;
    private final Victor motorBottom = RobotMap.climberBottomMotor;
    //Constants
    private final double kClimberDistRatio = 1440 / ((18.0/30.0)*(7.5/12.0*3.14159));
    //encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet    
    
    public Climber(){
        motorTop.setExpiration(1.0);
        motorBottom.setExpiration(1.0);
        motorTop.setSafetyEnabled(true);
        motorBottom.setSafetyEnabled(true);
        encoder.setReverseDirection(true);
        encoder.reset();
        encoder.start();
    }//end constructor
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Climb());
    }//end initDefaultCommand
    
    //add in state machine/manual control here!
    
    //PID control
    PIDSource sourceClimber = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("encoderClimber_raw", encoder.getRaw());
            return encoder.getRaw();
        }
    }; //end anonym class PIDSource
    PIDOutput outputClimber = new PIDOutput(){
        public void pidWrite(double output){
            motorTop.set(output);
            motorBottom.set(output);
        }
    }; //end anonym class PIDOutput
    private PositionSendablePID pidClimber = new PositionSendablePID("Climber",sourceClimber, outputClimber, kClimberDistRatio);      
    
    public void setSetpoint(double setpoint){
        pidClimber.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void disable(){
        pidClimber.disable();
        encoder.setReverseDirection(true);
        encoder.reset();
    }//end disable
    
    public void enable(){
        encoder.setReverseDirection(true);
        encoder.reset();
        encoder.start();
        pidClimber.enable();
    }//end enable
    
}// end Climber
