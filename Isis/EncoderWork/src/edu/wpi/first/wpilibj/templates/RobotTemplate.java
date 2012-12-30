/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    private final Jaguar jaguar_;
    private final Encoder encoder_;
    private final CustomPIDController pidControl_;
    private final EncoderPIDSource pidSource_;
    private final MotorPIDOutput pidOutput_;
    //private final Joystick joystick;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public RobotTemplate(){
        jaguar_ = new Jaguar(1);
        encoder_ = new Encoder(3,4);
        pidSource_ = new EncoderPIDSource(encoder_);
        pidOutput_ = new MotorPIDOutput(jaguar_);
        pidControl_ = new CustomPIDController(1.0,1.0,1.0,1.0,pidSource_,pidOutput_);
        //joystick = new Joystick(1);
    }
    
    public void robotInit() {
        encoder_.setDistancePerPulse(1.0);
        encoder_.start();
        SmartDashboard.putDouble("Target rate: ", 100.0);
        SmartDashboard.putDouble("P: ", 0.0);
        SmartDashboard.putDouble("I: ", 0.0);
        SmartDashboard.putDouble("D: ", 0.0);
        SmartDashboard.putDouble("F: ", 0.0);
        //SmartDashboard.putDouble("Jaguar value: ", 0.0);
        pidControl_.setSetpoint(100.0);
        pidControl_.setPID(-0.01, 0.0, 0.0, 0.002769);
        pidControl_.setInputRange(0.0, 300.0);
        pidControl_.setOutputRange(0.0, 1.0);
        pidControl_.enable();
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //jaguar_.set(joystick.getY());
        SmartDashboard.putDouble("Encoder rate: ", encoder_.getRate());
        SmartDashboard.putDouble("Encoder raw: ", encoder_.getRaw());
        SmartDashboard.putDouble("Jaguar value: ", jaguar_.get());
        //double jagSpeed = SmartDashboard.getDouble("Jaguar value: ", 0.0);
        //jaguar_.set(jagSpeed);
        double target = SmartDashboard.getDouble("Target rate: ", 0.0);
        double p = SmartDashboard.getDouble("P: ", 0.0);
        double i = SmartDashboard.getDouble("I: ", 0.0);
        double d = SmartDashboard.getDouble("D: ", 0.0);
        double f = SmartDashboard.getDouble("F: ", 0.0);
        if (pidControl_.getSetpoint() != target) {
            pidControl_.setSetpoint(target);
            pidControl_.reset();
            pidControl_.enable();
        }
        if (pidControl_.getP() != p || pidControl_.getI() != i || pidControl_.getD() != d || pidControl_.getF() != f){
             pidControl_.setPID(p,i,d,f);
        }
        SmartDashboard.putDouble("Error: ", pidControl_.getError());
        SmartDashboard.putDouble("Total Error: ", pidControl_.getTotalError());
        SmartDashboard.putDouble("PID output: ", pidControl_.get());
        System.out.println(pidControl_.getP() + ", " + pidControl_.getI() + ", " + pidControl_.getD());
    }//end teleopPeriodic()
    
}
