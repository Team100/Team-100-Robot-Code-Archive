/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends SimpleRobot {
    public Jaguar bigMotor;
    public Victor smallMotor;
    public Timer timer;
    
    public RobotTemplate()
    {
        bigMotor = new Jaguar(2);
        smallMotor = new Victor(1);
        timer = new Timer();
    }
    
    public double absDouble(double a)
    {
        if(a<0)
        {
            return -a;
        }
        return a;
    }
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous()
    {
        double sec=0.0;
        double speed=0.0;
        timer.start();
        while(true)
        {
            sec=timer.get();
            speed=(absDouble(((sec+3)%4)-2)-1); // 'speed' will accelerate at 1 unit/s^2 than decelerate at -1 unit/s^2
            SmartDashboard.putNumber("Timer value", sec);
            bigMotor.set(speed);
            smallMotor.set(speed);
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
