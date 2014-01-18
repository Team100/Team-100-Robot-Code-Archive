/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
    final AnalogTrigger lTrigger = new AnalogTrigger(left);
    final AnalogTrigger rTrigger = new AnalogTrigger(right);
    final Counter lCount = new Counter(lTrigger);
    final Counter rCount = new Counter(rTrigger);
    boolean lTriggered = false;
    boolean rTriggered = false;
    final Jaguar leftA = new Jaguar(6);
    final Jaguar leftB = new Jaguar(7);
    final Jaguar rightA = new Jaguar(8);
    final Jaguar rightB = new Jaguar(9);
    final RobotDrive drive = new RobotDrive(leftA, leftB, rightA, rightB);
    final Joystick dualshock = new Joystick(1);
    final JoystickButton alignPress = new JoystickButton(dualshock, 4);
    double leftVal = 0.0;
    double rightVal = 0.0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        lTrigger.setLimitsRaw(888, 900 );
        rTrigger.setLimitsRaw(888, 900);
        
    }
    
    public void disabledPeriodic()
    {
        SmartDashboard.putNumber("Left Value", left.getValue());
        SmartDashboard.putNumber("right Value", right.getValue());
        SmartDashboard.putBoolean("Left Black Line", lTrigger.getTriggerState());
        SmartDashboard.putBoolean("Right Black Line", rTrigger.getTriggerState());   
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
        if(1 == lCount.get())
        {
            lCount.reset();
            lTriggered = true;
        }
        else
        {
            lTriggered = false;
        }
        
        if(1 == rCount.get())
        {
            rCount.reset();
            rTriggered = true;
        }
        else
        {
            rTriggered = false;
        }

        leftVal = -0.75*dualshock.getY();
        rightVal = -0.75*dualshock.getThrottle();
//        if(!LTrigger.getTriggerState())
//            leftVal = 0.0;
//        if(!RTrigger.getTriggerState())
//            rightVal = 0.0;
        
        drive.tankDrive(leftVal, rightVal);
        
        if(alignPress.get())
            align();

        SmartDashboard.putNumber("Left Value", left.getValue());
        SmartDashboard.putNumber("right Value", right.getValue());
        SmartDashboard.putBoolean("Left Black Line", lTrigger.getTriggerState());
        SmartDashboard.putBoolean("Right Black Line", rTrigger.getTriggerState());
        SmartDashboard.putBoolean("Is Aligning", false);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void align()
    {
        SmartDashboard.putBoolean("Is Aligning", true);
        while(true)
        {
            if(lTriggered && rTriggered)
                drive.tankDrive(0.8, 0.8);
            else if(!lTriggered && rTriggered)
                drive.tankDrive(0.0, 0.3);
            else if(lTriggered && !rTriggered)
                drive.tankDrive(0.3, 0.0);
            else
            {
                drive.stopMotor();
                return;
            }

            SmartDashboard.putNumber("Left Value", left.getValue());
            SmartDashboard.putNumber("right Value", right.getValue());
            SmartDashboard.putBoolean("Left Black Line", lTrigger.getTriggerState());
            SmartDashboard.putBoolean("Right Black Line", rTrigger.getTriggerState());
            SmartDashboard.putBoolean("Is Aligning", false);
        }
    }
}
