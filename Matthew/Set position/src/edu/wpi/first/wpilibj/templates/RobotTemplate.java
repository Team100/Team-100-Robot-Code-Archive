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
    private final RobotDrive drive;
    private final AnalogChannel pot;
    private final DigitalInput toggleA;
    private final DigitalInput toggleB;


    public RobotTemplate() {
        joystick = new Joystick(1);
        joystick2 = new Joystick(2);
        jag1 = new Jaguar(2);
        jag2 = new Jaguar(3);
        servo = new Servo(8);
        drive = new RobotDrive(jag1, jag2);
        pot = new AnalogChannel(3);
        toggleA = new DigitalInput(1);
        toggleB = new DigitalInput(2);
        button1 = new JoystickButton (joystick, 1);
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
    public void teleopPeriodic() {
        
        
         SmartDashboard.putNumber("potentiometer voltage is", pot.getVoltage());
         SmartDashboard.putNumber("Joystick getY is", joystick.getY());
         SmartDashboard.putNumber("servo speed is", servo.get()); 
         //servo.set controls the SPEED of the vex motor module: .5 = stopped, 1= full speed forward, 0 = full speed back
        
        
          if (pot.getVoltage() < 4.5 && pot.getVoltage() > .5) {
            servo.set((joystick.getY() + 1) / 2);
        } else {
            if (pot.getVoltage() > 4.5) {

                if (joystick.getY() > 0) {
                    servo.set((joystick.getY() + 1) / 2);
                } else {
                    servo.set(0.5);
                }
            } else if (pot.getVoltage() < .5) {

                if (joystick.getY() < 0) {
                    servo.set((joystick.getY() + 1) / 2);
                } else {
                    servo.set(0.5);
                }
            }

        }
        
        
        if(pot.getVoltage() < 2.9 || pot.getVoltage() > 3.1) {
            if(pot.getVoltage() < 3.1) {
                //while (pot.getVoltage() < 3.0) {
                    servo.set(0.25);
                }
            //}
            else if(pot.getVoltage() > 2.9) {
                //while (pot.getVoltage() >3.0) {
                    servo.set(0.75);
                }
           // }
        }
        else {
            servo.set(0.5);
        
        
        }
                
    }
    //servo.set(max(joystick.getY()+1)/2)), 1.0);
//servo.set(pot.getValue()/977.0);
}//end teleopPeriodic()
//end RobotTemplate