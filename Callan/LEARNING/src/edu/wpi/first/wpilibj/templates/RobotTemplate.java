/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


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
    //this code is for g-wrath
    private Jaguar ljag1;
    private Jaguar rjag1;
    private Jaguar ljag2;
    private Jaguar rjag2;
    private RobotDrive drive;
    private Joystick Joystick = new Joystick(1);

    public void robotInit() {
        ljag1 = new Jaguar(6);
        ljag2 = new Jaguar(7);
        rjag1 = new Jaguar(8);
        rjag2 = new Jaguar(9);
        drive = new RobotDrive(ljag1, ljag2, rjag1, rjag2);
    }

    public void autonomousPeriodic() {
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        drive.tankDrive(0.75*Joystick.getY(), 0.75*Joystick.getThrottle());
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

    }

}
