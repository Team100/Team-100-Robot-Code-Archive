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
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
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
    
    public static class LineReader extends LineReading
    {
        private final AnalogChannel lineReader;
        private final AnalogTrigger trigger;
        private final Counter count;
        private boolean prevTrigger;
        private boolean currTrigger;
        private double currVel;
        private double enterVel;
        private boolean whiteZone;

        public LineReader(int k_channel)
        {
            lineReader = new AnalogChannel(k_channel);
            trigger = new AnalogTrigger(lineReader);
            count = new Counter(trigger);
            prevTrigger = true;
            currTrigger = true;
            currVel = 1.0;
            enterVel = 1.0;
            whiteZone = true;
        }
    }
    private final LineReader left = new LineReader(3);
    private final LineReader right = new LineReader(7);

    private final Encoder encodeL = new Encoder(4, 5);
    private final Encoder encodeR = new Encoder(8, 9);
    private final Gyro gyro = new Gyro(1);
    private int borderState = 0;
    private boolean isReady; //????

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        left.trigger.setLimitsRaw(50, 75);
        right.trigger.setLimitsRaw(50, 75);
        left.count.setUpSourceEdge(false, true);
        right.count.setUpSourceEdge(false, true);
    }
    
    public void disabledInit()
    {
        encodeL.stop();
        encodeR.stop();
        left.count.stop();
        right.count.stop();
    }
    
    public void autonomousInit()
    {
        encodeL.reset();
        encodeL.start();
        encodeR.reset();
        encodeR.start();
        gyro.reset();
        left.count.reset();
        left.count.start();
        right.count.reset();
        right.count.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        {// check if trigger is over carpet
            if(1 == left.count.get())
            {
                left.count.reset();
                left.currTrigger = true;
            }
            else
            {
                left.currTrigger = false;
            }
            if(1 == right.count.get())
            {
                right.count.reset();
                right.currTrigger = true;
            }
            else
            {
                right.currTrigger = false;
            }
        }

        {// record direction of movement to later tell if robot is leaving its zone
            if(left.prevTrigger && !left.currTrigger)
            {
                left.enterVel = left.currVel;
            }
            if(right.prevTrigger && !right.currTrigger)
            {
                right.enterVel = right.currVel;
            }
        }

        {// if the robot crossed the line w/o u-turning change zone boolean
            if(!left.prevTrigger && left.currTrigger &&
                    (Math.abs(left.enterVel)/left.enterVel == Math.abs(left.currVel)/left.currVel))
            {
                left.whiteZone = !left.whiteZone;
            }
            if(!right.prevTrigger && right.currTrigger &&
                    (Math.abs(right.enterVel)/right.enterVel == Math.abs(right.currVel)/right.currVel))
            {
                right.whiteZone = !right.whiteZone;
            }
        }

        SmartDashboard.putBoolean("Trigger1", left.trigger.getTriggerState());
        SmartDashboard.putBoolean("Trigger2", right.trigger.getTriggerState());
        SmartDashboard.putNumber("Reader1 Value", left.lineReader.getValue());
        SmartDashboard.putNumber("Reader2 Value", right.lineReader.getValue());
        SmartDashboard.putNumber("Border State", borderState);

        {// asign state number based on state chart
            if(left.currTrigger && left.whiteZone && right.currTrigger && right.whiteZone)
                borderState = 0;
            if(left.currTrigger && left.whiteZone && !right.currTrigger)
                borderState = 1;
            if(left.currTrigger && left.whiteZone && right.currTrigger && !right.whiteZone)
                borderState = 2;
            if(!left.currTrigger && right.currTrigger && right.whiteZone)
                borderState = 3;
            if(left.currTrigger && !left.whiteZone && right.currTrigger && right.whiteZone)
                borderState = 4;
            if(!left.currTrigger && right.currTrigger && !right.whiteZone)
                borderState = 5;
            if(left.currTrigger && !left.whiteZone && !right.currTrigger)
                borderState = 6;
            if(left.currTrigger && !left.whiteZone && right.currTrigger && !right.whiteZone)
                borderState = 7;
            if(!left.currTrigger && !right.currTrigger)
                borderState = 8;
        }

        // record previous state so changes will be noticed
        left.prevTrigger = left.currTrigger;
        right.prevTrigger = right.currTrigger;
    }
    
    public void teleopInit()
    {
        left.count.start();
        right.count.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        SmartDashboard.putBoolean("Trigger1", left.trigger.getTriggerState());
        SmartDashboard.putBoolean("Trigger2", right.trigger.getTriggerState());
        SmartDashboard.putNumber("Reader1 Value", left.lineReader.getValue());
        SmartDashboard.putNumber("Reader2 Value", right.lineReader.getValue());
        SmartDashboard.putNumber("On line", left.count.get());
        SmartDashboard.putNumber("On line", right.count.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void turnInPlace(double angle, LineReader handedness)
    {
        
    }
    
    public double counts2feet(int count)
    {
        return count / 4.875;
    }
    
}
