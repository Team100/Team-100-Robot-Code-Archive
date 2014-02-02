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
    
    Joystick myXbox = new Joystick(1);
    double leftX, leftY, rightX, rightY, triggers;
    boolean buttonA, buttonB, buttonX, buttonY, leftBumper, rightBumper, buttonBack, buttonStart, leftStick, rightStick;
    
    Jaguar jag1, jag2;
    
    public void robotInit() {
        jag1 = new Jaguar(1);
        jag2 = new Jaguar(2);
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
        leftX = myXbox.getRawAxis(1);
        leftY = myXbox.getRawAxis(2);
        triggers = myXbox.getRawAxis(3);
        rightX = myXbox.getRawAxis(4);
        rightY = myXbox.getRawAxis(5);
        buttonA = myXbox.getRawButton(1);
        buttonB = myXbox.getRawButton(2);
        buttonX = myXbox.getRawButton(3);
        buttonY = myXbox.getRawButton(4);
        leftBumper = myXbox.getRawButton(5);
        rightBumper = myXbox.getRawButton(6);
        buttonBack = myXbox.getRawButton(7);
        buttonStart = myXbox.getRawButton(8);
        leftStick = myXbox.getRawButton(9);
        rightStick = myXbox.getRawButton(10);
        
        SmartDashboard.putNumber("leftX", leftX);
        SmartDashboard.putNumber("leftY", leftY);
        SmartDashboard.putNumber("rightX", rightX);
        SmartDashboard.putNumber("rightY", rightY);
        SmartDashboard.putNumber("triggers", triggers);
        SmartDashboard.putBoolean("A", buttonA);
        SmartDashboard.putBoolean("B", buttonB);
        SmartDashboard.putBoolean("X", buttonX);
        SmartDashboard.putBoolean("Y", buttonY);
        SmartDashboard.putBoolean("leftBumper", leftBumper);
        SmartDashboard.putBoolean("rightBumper", rightBumper);
        SmartDashboard.putBoolean("Back", buttonBack);
        SmartDashboard.putBoolean("Start", buttonStart);
        SmartDashboard.putBoolean("Left Stick Clicked", leftStick);
        SmartDashboard.putBoolean("Right Stick Clicked", rightStick);
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
