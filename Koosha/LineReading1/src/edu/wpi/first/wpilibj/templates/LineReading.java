/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.AnalogTriggerOutput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class LineReading extends IterativeRobot {
    private final AnalogChannel lineReader1 = new AnalogChannel(3);
    private final AnalogChannel lineReader2 = new AnalogChannel(7);
    private final AnalogTrigger trigger1 = new AnalogTrigger(lineReader1);
    private final AnalogTrigger trigger2 = new AnalogTrigger(lineReader2);
    private boolean prevTriggerL;
    private boolean prevTriggerR;
    private boolean currTriggerL;
    private boolean currTriggerR;
    private double currVelL = 1.0;
    private double currVelR = 1.0;
    private double enterVelL = 1.0;
    private double enterVelR = 1.0;
    private boolean whiteZoneL;
    private boolean whiteZoneR;
    private int borderState = 0;
    private Counter isTrue = new Counter(trigger1);
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        trigger1.setLimitsRaw(50, 75);
        trigger2.setLimitsRaw(50, 75);
    }
    
    public void disabledInit()
    {
        isTrue.stop();
        isTrue.reset();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        currTriggerL = trigger1.getTriggerState();
        currTriggerR = trigger2.getTriggerState();
        if(prevTriggerL && !currTriggerL)
        {
            enterVelL = currVelL;
        }
        if(prevTriggerR && !currTriggerR)
        {
            enterVelR = currVelR;
        }

        SmartDashboard.putBoolean("Trigger1", trigger1.getTriggerState());
        SmartDashboard.putBoolean("Trigger2", trigger2.getTriggerState());
        SmartDashboard.putNumber("Reader1 Value", lineReader1.getValue());
        SmartDashboard.putNumber("Reader2 Value", lineReader2.getValue());
        
        if(!prevTriggerL && currTriggerL && (Math.abs(enterVelL)/enterVelL == Math.abs(currVelL)/currVelL))
        {
            whiteZoneL = !whiteZoneL;
        }
        if(!prevTriggerR && currTriggerR && (Math.abs(enterVelR)/enterVelR == Math.abs(currVelR)/currVelR))
        {
            whiteZoneL = !whiteZoneL;
        }

        if(currTriggerL && whiteZoneL && currTriggerR && whiteZoneR)
        {
            borderState = 0;
        }
        if(currTriggerL && whiteZoneL && !currTriggerR)
        {
            borderState = 1;
        }
        if(currTriggerL && whiteZoneL && currTriggerR && !whiteZoneR)
        {
           borderState = 2;
        }
        if(!currTriggerL && currTriggerR && whiteZoneR)
        {
             borderState = 3;
        }
        
        prevTriggerL = currTriggerL;
        prevTriggerL = currTriggerR;
    }
    
    public void teleopInit()
    {
        isTrue.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        SmartDashboard.putBoolean("Trigger1", trigger1.getTriggerState());
        SmartDashboard.putBoolean("Trigger2", trigger2.getTriggerState());
        SmartDashboard.putNumber("Reader1 Value", lineReader1.getValue());
        SmartDashboard.putNumber("Reader2 Value", lineReader2.getValue());
        SmartDashboard.putNumber("On line", isTrue.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
