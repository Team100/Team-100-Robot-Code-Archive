/*Shooter subsystem*/
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.VelocitySendablePID;

/**
 *
 * @author Isis
 */
public class Shooter extends Subsystem {
    //Robot parts
    private final DigitalInput hallFront = RobotMap.shooterFrontHallEffect;
    private final DigitalInput hallBack = RobotMap.shooterBackHallEffect;
    private final Victor motorFront = RobotMap.shooterFrontMotor;
    private final Victor motorBack = RobotMap.shooterBackMotor;
    private Counter counterFront = new Counter(hallFront);
    private Counter counterBack = new Counter(hallBack);
    //Constants
    private final double kBackDistRatio = 3.0/12.0*Math.PI/4;
    private final double kFrontDistRatio = 3.0/12.0*Math.PI/4;
    //encoder ticks*(quadrature)/gearRatio*circumference*conversion to feet  
    private double dumpSpeed = 0.5;//change this eventually
    private double shootSpeed = .2;
    private double reverseSpeed = -.1;
    
    //sets counters
    public Shooter(){
        counterFront.setMaxPeriod(1.0);
        counterBack.setMaxPeriod(1.0);
        counterFront.reset();
        counterBack.reset();
        counterFront.start();
        counterBack.start();
        SmartDashboard.putNumber("ShooterDumpSpeed", dumpSpeed);
        SmartDashboard.putNumber("ShooterShootSpeed", shootSpeed);
        SmartDashboard.putNumber("ShooterReverseSpeed", reverseSpeed);
    }//end constructor
    
    //empty
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand
    
    //set speed for dumping
    public void dumpFrisbees(){
        motorFront.set(SmartDashboard.getNumber("ShooterDumpSpeed", dumpSpeed));
        motorBack.set(SmartDashboard.getNumber("ShooterDumpSpeed", dumpSpeed));
    }//end dumpFrisbees
    
    //set speed for shooting
    public void shootFrisbees(){
        motorFront.set(SmartDashboard.getNumber("ShooterShootSpeed", shootSpeed));
        motorBack.set(SmartDashboard.getNumber("ShooterShootSpeed", shootSpeed));
    }//end shootFrisbees
    
    //runs shooter in reverse direction for intake
    public void runBackwards(){
        motorFront.set(SmartDashboard.getNumber("ShooterReverseSpeed", reverseSpeed));
        motorBack.set(SmartDashboard.getNumber("ShooterReverseSpeed", reverseSpeed));
    }//end runBackwards
    
    public void setFrontMotor(double s)
    {
        motorFront.set(s);
    }
    
    public void setBackMotor(double s)
    {
        motorBack.set(s);
    }
    
    //PID Control
    
    //shooterFront
    PIDSource sourceFront = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("counterFront_raw", counterFront.get());
            return counterFront.get();
        }
    }; //end anonym class PIDSource
    PIDSource periodFront = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("periodFront", counterFront.getPeriod());
            return counterFront.getPeriod();
        }
    };//end anonym class PIDSource periodFront
    PIDOutput outputFront = new PIDOutput(){
        public void pidWrite(double output){
            motorFront.set(output);
        }
    }; //end anonym class PIDOutput
    private VelocitySendablePID pidFront = new VelocitySendablePID("front",sourceFront, periodFront, outputFront, kFrontDistRatio);
    
    //shooterBack
    PIDSource sourceBack = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("counterBack_raw", counterBack.get());
            return counterBack.get();
        }
    }; //end anonym class PIDSource
    PIDSource periodBack = new PIDSource(){
        public double pidGet(){
            SmartDashboard.putNumber("periodBack", counterBack.getPeriod());
            return counterBack.getPeriod();
        }
    };//end anonym class PIDSource periodFront
    PIDOutput outputBack = new PIDOutput(){
        public void pidWrite(double output){
             motorBack.set(output);
        }
    }; //end anonym class PIDOutput
    private VelocitySendablePID pidBack = new VelocitySendablePID("back",sourceBack, periodBack, outputBack, kBackDistRatio);
    
    public void setSetpoint(double setpoint){
        pidFront.setSetpoint(setpoint);
        pidBack.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void disable(){
        setSetpoint(0.0);
        pidFront.disable();
        pidBack.disable();
        counterFront.reset();
        counterBack.reset();
        motorFront.set(0);
        motorBack.set(0);
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
