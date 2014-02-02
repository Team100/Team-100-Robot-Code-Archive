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
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import  edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    Jaguar Jag;
    Jaguar Jag2;
    Joystick control;
    NamedSendable what;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    Jag = new Jaguar (3);
    Jag2 = new Jaguar (2);
    control = new Joystick(1);
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
        //LiveWindow.setEnabled(true);
        //LiveWindow.addActuator("JaguarSub", "Jaguar", Jag);
        //LiveWindow.addActuator("JaguarSub", "Jaguar", Jag2);
        //Jag.set((control.getY())*-1);
        SmartDashboard.putBoolean("ClimberTopSwitch", true);
        SmartDashboard.putBoolean("ClimberBotSwitch", true);
        SmartDashboard.putNumber("LeftEncoder", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("RightEncoder", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("ShooterEncoder", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("ClimberEncoder", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("ShooterEncoder2", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("ClimberPWM", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("ClimberTilter", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("LeftPWM", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("RightPWM", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("ShooterPWM", ROBOT_TASK_PRIORITY);
        //SmartDashboard.putNumber("Gyro", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("Potiometer", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("LeftShifterForward", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("LeftShifterReverse", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("RightShifterForward", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("RightShifterReverse", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("AccelerometerX", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("AccelerometerY", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("AccelerometerZ", ROBOT_TASK_PRIORITY);
        SmartDashboard.putBoolean("FeedGateInput", true);
        SmartDashboard.putBoolean("CompressorSwitch",true);
        SmartDashboard.putNumber("FeedPWM", ROBOT_TASK_PRIORITY);
        SmartDashboard.putNumber("IntakePWM", ROBOT_TASK_PRIORITY);
        //SmartDashboard.putBoolean("Boolean", Jag.getSpeed()==1);
        //SmartDashboard.putData(what);
        //SmartDashboard.putData("putData", Jag);
        //SmartDashboard.putNumber("putNumber", Jag.getSpeed());
        //SmartDashboard.putString("type", Jag.getSmartDashboardType());
        //SmartDashboard.putString("Description", Jag.getDescription());
        //SmartDashboard.putNumber("get", SmartDashboard.getNumber("putNumber", 1.0));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
