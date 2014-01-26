/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DriverStation;
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
    private final Jaguar jaguar1_;
    private final Jaguar jaguar2_;
    private final Encoder encoder_;
    private final CustomPIDController pidControl_;
    private final EncoderPIDSource pidSource_;
    private final MotorPIDOutput pidOutput_;
    private DriverStation ds_;
    private double velocity;
    private double maxVeloc_;
    //private final Joystick joystick;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public RobotTemplate(){
        jaguar1_ = new Jaguar(3);
        jaguar2_ = new Jaguar(2);
        encoder_ = new Encoder(7,6);
        pidSource_ = new EncoderPIDSource(encoder_);
        pidOutput_ = new MotorPIDOutput(jaguar1_,jaguar2_);
        maxVeloc_ = 0.0;
        pidControl_ = new CustomPIDController(pidSource_,pidOutput_, 250*4*(27.0/13.0)*(0.5*3.14159)/2); 
        //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet
        ds_ = DriverStation.getInstance();
        }
    
    public void robotInit() {
        encoder_.setDistancePerPulse(1.0);
        SmartDashboard.putNumber("Target position", 0.0);
        //note: PID constants calibrated for drivetrain
        SmartDashboard.putNumber("P", -30.0);
        SmartDashboard.putNumber("I", 0.0);
        SmartDashboard.putNumber("D", -3.0);
        SmartDashboard.putNumber("F", 0.0);
        SmartDashboard.putNumber("maxVeloc", 0.5);
        encoder_.reset();
        encoder_.start();
        pidControl_.reset();
        pidControl_.setSetpoint(0.0,0.0);
        pidControl_.setPID(0.0, 0.0, 0.0,-0.00019347);
        pidControl_.setInputRange(-1000.0, 1000.0);
        pidControl_.setOutputRange(-12.0, 12.0);
        pidControl_.enable();  
    }
    /**
     * This function is called periodically during autonomous
     */
    public void disabledPeriodic(){
        SmartDashboard.putNumber("Encoder raw", encoder_.getRaw());
    }
    public void autonomousPeriodic() {

    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Encoder raw", encoder_.getRaw());
        SmartDashboard.putNumber("Jaguar1 value", jaguar1_.get());
        SmartDashboard.putNumber("Jaguar2 value", jaguar2_.get());
        SmartDashboard.putNumber("m_filteredPosition", pidControl_.m_filteredPosition);
//        double p = ds_.getAnalogIn(1);
//        double i = ds_.getAnalogIn(2);
//        double d = ds_.getAnalogIn(3);
//        double f = ds_.getAnalogIn(4);
        double position = SmartDashboard.getNumber("Target position", 0.0);
        double p = SmartDashboard.getNumber("P", 0.0);
        double i = SmartDashboard.getNumber("I", 0.0);
        double d = SmartDashboard.getNumber("D", 0.0);
        double f = SmartDashboard.getNumber("F", 0.0);
        double m = SmartDashboard.getNumber("maxVeloc", 0.0);
        pidControl_.setMaxVeloc(m);
        if (pidControl_.getSetpoint() != position) {
            pidControl_.setPositionSetpoint(position);
        }
        if (pidControl_.getP() != p || pidControl_.getI() != i || pidControl_.getD() != d || pidControl_.getF() != f){
             pidControl_.setPID(p,i,d,f);
        }
        SmartDashboard.putNumber("Error", pidControl_.getError());
        SmartDashboard.putNumber("Total Error", pidControl_.getTotalError());
        SmartDashboard.putNumber("PID output", pidControl_.get());
        System.out.println(pidControl_.getP() + ", " + pidControl_.getI() + ", " + pidControl_.getD() + ", " + m);
    }//end teleopPeriodic()
    
}