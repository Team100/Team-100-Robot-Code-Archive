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
    private final Jaguar jag1;
    private final Jaguar jag2;
    private final Servo servo;
    //private final RobotDrive drive;
    private final AnalogChannel pot;
    private final DigitalInput toggleA;
    private final DigitalInput toggleB;
    private final Counter counter;
    private int count;

    public RobotTemplate() {
        joystick = new Joystick(1);
        joystick2 = new Joystick(2);
        jag1 = new Jaguar(1);
        jag2 = new Jaguar(2);
        servo = new Servo(3);
       // drive = new RobotDrive(jag1, jag2);
        pot = new AnalogChannel(1);
        toggleA = new DigitalInput(1);
        toggleB = new DigitalInput(2);
        button1 = new JoystickButton (joystick, 1);
        counter = new Counter(toggleA);
        count = 0;
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

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit() {
        counter.start();
        counter.reset();
        count = counter.get();
        System.out.println(count);
        System.out.println("teleopinit was called");
    }
    public void teleopPeriodic() {

 int newcount = counter.get();

        if(count != newcount)
        {
            System.out.println(newcount);
            count = newcount;
        }

    SmartDashboard.putInt("Count is", newcount);

    /*Please note that there is a major amount of "bounce" when the limit switch is pressed.
      The switch will count around 15 for every press (though it probably depends on the switch.
      This can be reduced (if not eliminated) by adding a capacitor. */






   }//end teleopPeriodic()
}//end RobotTemplate

