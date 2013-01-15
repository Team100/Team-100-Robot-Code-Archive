/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private final Jaguar ljag1 = new Jaguar(2);
    private final Jaguar rjag1 = new Jaguar(3);
    //private final Jaguar ljag2 = new Jaguar(7);
    //private final Jaguar rjag2 = new Jaguar(9);
    //private final RobotDrive drive = new RobotDrive(ljag1, ljag2, rjag1, rjag2);
    private final RobotDrive drive = new RobotDrive(ljag1, rjag1);
    Joystick leftJoystick = new Joystick(1);
    Joystick rightJoystick = new Joystick(2);
    SendableChooser driveChooser;
    
    public void robotInit() {
        driveChooser = new SendableChooser();
        driveChooser.addDefault("Tank Drive", "tank");
        driveChooser.addObject("One Joystick Arcade Drive", "arcade1");
        driveChooser.addObject("Two Joystick Arcade Drive", "arcade2");
        driveChooser.addObject("None", "none");
        SmartDashboard.putData("Drive Mode", driveChooser);
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
        if (driveChooser.getSelected().equals("tank")){
            drive.tankDrive(leftJoystick.getY()*-1, rightJoystick.getY()*-1); 
        }
        if (driveChooser.getSelected().equals("arcade1")){
            drive.arcadeDrive(leftJoystick.getY()*-1, leftJoystick.getX()*-1);
        }
        if (driveChooser.getSelected().equals("arcade2")){
            drive.arcadeDrive(leftJoystick.getY()*-1, rightJoystick.getX()*-1);
        }
        SmartDashboard.putNumber("Y", leftJoystick.getY());
        SmartDashboard.putNumber("X", leftJoystick.getX());
        SmartDashboard.putNumber("Throttle", leftJoystick.getThrottle());
        SmartDashboard.putNumber("Twist", leftJoystick.getTwist());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
