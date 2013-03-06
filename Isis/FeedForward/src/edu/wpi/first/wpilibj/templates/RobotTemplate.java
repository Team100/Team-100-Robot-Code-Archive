/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    private final Victor victorLeft = new Victor(3);
    private final Victor victorRight = new Victor(2);
    private final Encoder encoderLeft = new Encoder(7, 6);
    private final Encoder encoderRight = new Encoder(2, 1);
    private final Timer timer = new Timer();
    private double prevDist = 0.0;
    private double prevTime = 0.0;
    private final double kGearRatio = 250 * 4* (30.0/18.0) * 2*(4.0/12.0)*3.14159;
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet
    
    public void robotInit() {
        SmartDashboard.putNumber("Victor_output", 0.0);
        encoderLeft.setReverseDirection(true);
        encoderLeft.setDistancePerPulse(1.0);
        encoderRight.setDistancePerPulse(1.0);
        resetInit();
    }//end robotInit()

    private void resetInit() {
        encoderLeft.reset();
        encoderRight.reset();
        encoderLeft.start();
        encoderRight.start();
        timer.reset();
        timer.start();
    }//end resetInit()
    
    public void teleopInit(){
        resetInit();
    }
    
    public void teleopPeriodic() {
        double output = SmartDashboard.getNumber("Victor_output", 0.0);
        double currTime = timer.get();
        double loopPeriod = currTime - prevTime;        
        double currDist = encoderRight.getRaw() / kGearRatio;
        double deltaDist = currDist - prevDist;
        double currInstVeloc = deltaDist / loopPeriod;
        SmartDashboard.putNumber("encoderLeftraw", encoderLeft.getRaw());
        SmartDashboard.putNumber("encoderRightraw", encoderRight.getRaw());
        SmartDashboard.putNumber("currInstVeloc", currInstVeloc);
        victorLeft.set(output);
        victorRight.set(output);
        prevDist = currDist;
        prevTime = currTime;
    }//end teleopPeriodic()
    
}