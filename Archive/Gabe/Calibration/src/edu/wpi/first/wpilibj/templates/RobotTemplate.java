/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {

    Joystick joystick1;
    Victor victor1;
    Victor victor2;

    RobotTemplate() {
        joystick1 = new Joystick(1);
        victor1 = new Victor(2);
        victor2 = new Victor(3);
    }

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() { 
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        while (isOperatorControl()) {
            SmartDashboard.putNumber("Joystick Y axis", joystick1.getY());
            SmartDashboard.putNumber("Joystick Throttle axis", joystick1.getThrottle());
            victor1.set(joystick1.getY()*-1);
            victor2.set(joystick1.getThrottle()*-1);
        }
    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    }
}
