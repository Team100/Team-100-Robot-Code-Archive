/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.AnalogTrigger;
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
public class LineReadingSimple extends IterativeRobot
{
    final AnalogChannel left = new AnalogChannel(6);
    final AnalogChannel right = new AnalogChannel(7);
    final AnalogTrigger LTrigger = new AnalogTrigger(left);
    final AnalogTrigger RTrigger = new AnalogTrigger(right);
    final Jaguar leftA = new Jaguar(6);
    final Jaguar leftB = new Jaguar(7);
    final Jaguar rightA = new Jaguar(8);
    final Jaguar rightB = new Jaguar(9);
    final RobotDrive drive = new RobotDrive(leftA, leftB, rightA, rightB);
    final Joystick dualshock = new Joystick(1);
    double leftVal = 0.0;
    double rightVal = 0.0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        LTrigger.setLimitsRaw(50, 75);
        RTrigger.setLimitsRaw(50, 75);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        leftVal = -dualshock.getY();
        rightVal = -dualshock.getThrottle();
        if(!LTrigger.getTriggerState())
            leftVal = 0.0;
        if(!RTrigger.getTriggerState())
            rightVal = 0.0;
        
        drive.tankDrive(leftVal, rightVal);

        SmartDashboard.putBoolean("Left", LTrigger.getTriggerState());
        SmartDashboard.putBoolean("Right", RTrigger.getTriggerState());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
