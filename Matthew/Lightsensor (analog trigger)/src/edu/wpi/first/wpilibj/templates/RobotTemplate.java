/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    private final Joystick joystick;
    private final Joystick joystick2;
    private final JoystickButton button1;
    private final Victor vic1;
    private final Jaguar jag2;
    //private final Servo servo;
    //private final RobotDrive drive;
    //private final AnalogChannel pot;
    private final AnalogTrigger lightsensor;
    private final DigitalInput toggleA;
    private final DigitalInput toggleB;
    private final AnalogChannel lightsensor1;
    private final Counter counter;
    private boolean firsttime;
    private int count;

    public RobotTemplate() {
        joystick = new Joystick(1);
        joystick2 = new Joystick(2);
        vic1 = new Victor (1);
        jag2 = new Jaguar(2);
        //servo = new Servo(1);
        //drive = new RobotDrive(vic1, jag2);
        //pot = new AnalogChannel(1);
        toggleA = new DigitalInput(1);
        toggleB = new DigitalInput(2);
        button1 = new JoystickButton (joystick, 1);
        lightsensor = new AnalogTrigger (1);
        lightsensor1 = new AnalogChannel (1);
        counter = new Counter (lightsensor);
        firsttime = true;
        count = 0;
        lightsensor.setLimitsVoltage(2.9, 3.9);
        /* One sensor's approx. voltage for white binder paper: 0.2 Aprrox. for dark marker line on the paper: 3.7.
          Another sensor's approx. voltage for white binder paper: 2.3 Approx for dark marker line on the paper: 4.55
         However, the voltage levels change depending on the sensor and the darkness of the marker */
    }//end constructor

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        //don't put anything in here
    }//end robotInit()

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        //don't put anything in here either
    }//end autonomousPeriodic()

    public void disabledInit()    {
        counter.reset();
        counter.start();
        counter.stop();
        System.out.println("disabledinit was called");
        //this section might not be necessary
    }

    public void teleopInit()   {
        counter.start();
        counter.reset();
        count = counter.get();
        System.out.println(count);
        firsttime = true;
        System.out.println("teleopinit was called");
    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        if(firsttime)
        {
            counter.reset();
            firsttime = false;
        }

        int newcount = counter.get();

        if(count != newcount)
        {
            System.out.println(newcount);
            count = newcount;
        }

    SmartDashboard.putDouble("Analog Channel Voltage is", lightsensor1.getVoltage());
    SmartDashboard.putInt("Count is", newcount);


    SmartDashboard.putBoolean("Analog Trigger state is" , lightsensor.getTriggerState());
    /* "false" means that it is light, "true" means that it is dark. */

//    if(lightsensor.getTriggerState() == false)
//        drive.arcadeDrive(joystick);
//    else
//        drive.stopMotor();

    if(button1.get())
        counter.reset();


   }//end teleopPeriodic()
}//end RobotTemplate