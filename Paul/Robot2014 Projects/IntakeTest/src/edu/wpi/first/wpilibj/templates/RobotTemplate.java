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
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
    Jaguar lDrive = new Jaguar(1);
    Jaguar rDrive = new Jaguar(2);
    Jaguar intakeTop = new Jaguar(3);
    Jaguar intakeBottom = new Jaguar(5);
    Joystick left = new Joystick(1);
    Joystick right = new Joystick(2);
    JoystickButton intake = new JoystickButton(left, 1);
    JoystickButton expel = new JoystickButton(right, 1);
    
    public void robotInit() {

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
        lDrive.set(left.getY());
        rDrive.set(-right.getY());
        if(intake.get()){
            intakeTop.set(1);
            intakeBottom.set(1);
        }
        else if(expel.get()){
            intakeTop.set(-1);
            intakeBottom.set(-1);
        } else{
            intakeTop.set(0);
            intakeBottom.set(0);
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
