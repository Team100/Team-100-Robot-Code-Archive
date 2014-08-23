/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    Jaguar leftMotor = new Jaguar(1);
    Jaguar rightMotor = new Jaguar(2);
    RobotDrive drive = new RobotDrive(leftMotor, rightMotor);
    Encoder leftEncoder = new Encoder(1,2);
    Encoder rightEncoder = new Encoder(3,4);
    Gyro gyro = new Gyro(1);
    PID drivePID = new PID("drive");
    PID driveAnglePID = new PID("driveAngle");
    Joystick joystick = new Joystick(1);
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        SmartDashboard.putNumber("TestDistance", 0.0);
        SmartDashboard.putNumber("TestAngle", 0.0);
        drivePID.enable();
        driveAnglePID.enable();
    }

    public void autonomousInit(){
        drivePID.setSetPoint(SmartDashboard.getNumber("TestDistance"));
        driveAnglePID.setSetPoint(SmartDashboard.getNumber("TestAngle"));
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        drivePID.update(leftEncoder.get()/2+rightEncoder.get()/2);
        driveAnglePID.update(gyro.getAngle());
        drive.arcadeDrive(drivePID.getOutput(),driveAnglePID.getOutput());
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        drive.tankDrive(joystick.getY(), joystick.getThrottle());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        
    }
    
}
