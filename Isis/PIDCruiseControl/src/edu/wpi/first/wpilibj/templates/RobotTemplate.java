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
        SmartDashboard.putDouble("Target position", 0.0);
        //note: PID constants calibrated for drivetrain
        SmartDashboard.putDouble("P", -30.0);
        SmartDashboard.putDouble("I", 0.0);
        SmartDashboard.putDouble("D", -3.0);
        SmartDashboard.putDouble("F", 0.0);
        SmartDashboard.putDouble("maxVeloc", 0.5);
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
        SmartDashboard.putDouble("Encoder raw", encoder_.getRaw());
    }
    public void autonomousPeriodic() {

    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        SmartDashboard.putDouble("Encoder raw", encoder_.getRaw());
        SmartDashboard.putDouble("Jaguar1 value", jaguar1_.get());
        SmartDashboard.putDouble("Jaguar2 value", jaguar2_.get());
        SmartDashboard.putDouble("m_filteredPosition", pidControl_.m_filteredPosition);
//        double p = ds_.getAnalogIn(1);
//        double i = ds_.getAnalogIn(2);
//        double d = ds_.getAnalogIn(3);
//        double f = ds_.getAnalogIn(4);
        double position = SmartDashboard.getDouble("Target position", 0.0);
        double p = SmartDashboard.getDouble("P", 0.0);
        double i = SmartDashboard.getDouble("I", 0.0);
        double d = SmartDashboard.getDouble("D", 0.0);
        double f = SmartDashboard.getDouble("F", 0.0);
        double m = SmartDashboard.getDouble("maxVeloc", 0.0);
        pidControl_.setMaxVeloc(m);
        if (pidControl_.getSetpoint() != position) {
            pidControl_.setPositionSetpoint(position);
        }
        if (pidControl_.getP() != p || pidControl_.getI() != i || pidControl_.getD() != d || pidControl_.getF() != f){
             pidControl_.setPID(p,i,d,f);
        }
        SmartDashboard.putDouble("Error", pidControl_.getError());
        SmartDashboard.putDouble("Total Error", pidControl_.getTotalError());
        SmartDashboard.putDouble("PID output", pidControl_.get());
        System.out.println(pidControl_.getP() + ", " + pidControl_.getI() + ", " + pidControl_.getD() + ", " + m);
    }//end teleopPeriodic()
    
}