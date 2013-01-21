/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.buttons.*;

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
    private final Joystick joystick = new Joystick(1);
    private final JoystickButton button1 = new JoystickButton(joystick, 1);
    private final JoystickButton button2 = new JoystickButton(joystick, 2);
    private final JoystickButton button3 = new JoystickButton(joystick, 3);
    private final JoystickButton button4 = new JoystickButton(joystick, 4);
    private final JoystickButton button5 = new JoystickButton(joystick, 5);
    private final JoystickButton button6 = new JoystickButton(joystick, 6);
    private final JoystickButton button7 = new JoystickButton(joystick, 7);
    private final JoystickButton button8 = new JoystickButton(joystick, 8);
    private final Timer timer = new Timer();
    private double prevDist = 0.0;
    private double prevDist2 = 0.0;
    private double prevTime = 0.0;
    private double prevTime2 = 0.0;
    private final double kGearRatio = 1;
    

    {
        
    }
    
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet
    
    public void robotInit() {
        SmartDashboard.putNumber("Victor_output", 0.0);
        encoderLeft.setReverseDirection(true);
        encoderRight.setReverseDirection(true);
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
        double currTime2 = timer.get();
        double loopPeriod2 = currTime2 - prevTime2;
        double currDist2 = encoderLeft.getRaw() / kGearRatio;
        double deltaDist2 = currDist2 - prevDist2;
        double currInstVeloc2 = deltaDist2 / loopPeriod2;
        SmartDashboard.putNumber("currInstVeloc", currInstVeloc);
        SmartDashboard.putNumber("currInstVeloc2", currInstVeloc2);
        System.out.println("output: " + output);
        victorLeft.set(output);
        victorRight.set(output);
        prevDist = currDist;
        prevDist2 = currDist2;
        prevTime = currTime;
        prevTime2 = currTime2; 
        SmartDashboard.putNumber("Battery voltage:",  DriverStation.getInstance().getBatteryVoltage());
        
    }//end teleopPeriodic()
    
}
