/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    private Jaguar ljag1 = new Jaguar(10);
    private Jaguar rjag1 = new Jaguar(9);
    private RobotDrive drive = new RobotDrive(ljag1, rjag1);
    private Joystick leftJoystick = new Joystick(1);
    private Joystick rightJoystick = new Joystick(2);
    private Victor BridgePusher = new Victor(1);
    private AnalogChannel BridgePot = new AnalogChannel(5);
    private int setpoint;
    private int error;
    private double output;
            
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        SmartDashboard.putNumber("Setpoint", 0);
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
        drive.tankDrive(leftJoystick.getY(), rightJoystick.getY());
        error = (int)SmartDashboard.getNumber("Setpoint", 0) - BridgePot.getValue();
        output = error * 0.01;
        SmartDashboard.putNumber("Output Value", output);
       // BridgePusher.set(output);
        SmartDashboard.putNumber("Potentiometer Value", BridgePot.getVoltage());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
