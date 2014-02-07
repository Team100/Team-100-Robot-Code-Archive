/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
    private boolean isBackward;
    private boolean pressed;

    public RobotTemplate() {
        joystick = new Joystick(1);
        joystick2 = new Joystick(2);
        jag1 = new Jaguar(2);
        jag2 = new Jaguar(3);
        servo = new Servo(1);
        drive = new RobotDrive(jag1, jag2);
        pot = new AnalogChannel(1);
        toggleA = new DigitalInput(7);
        toggleB = new DigitalInput(2);
        button1 = new JoystickButton (joystick, 1);
        isBackward = false;
        pressed = false;
    }//end constructor
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {


      if (button1.get())
        {
          pressed = !pressed;

                 isBackward = !isBackward;
        }
      else
        {

             isBackward = false;
        }

        if (isBackward == false && pressed == false)
        {
          if (-90 <= joystick.getDirectionDegrees() && joystick.getDirectionDegrees() <= 90)
              {
                drive.arcadeDrive(joystick);
                SmartDashboard.putNumber("joystick direction is:", joystick.getDirectionDegrees());
                SmartDashboard.putNumber("joystick throttle is:", joystick.getThrottle());
              }
          else
              {
                drive.stopMotor();
              }
         }

        if ( pressed == true)
        {
          if (-90>=joystick.getDirectionDegrees() || joystick.getDirectionDegrees()>=90)
              {
                drive.arcadeDrive(joystick);
                SmartDashboard.putNumber("joystick direction is:", joystick.getDirectionDegrees());
                SmartDashboard.putNumber("joystick throttle is:", joystick.getThrottle());
              }
          else
              {
                drive.stopMotor();
              }
         }







             /*   if (toggleA.get())
        {


               drive.arcadeDrive(joystick);
               SmartDashboard.putDouble("joystick direction is:", joystick.getDirectionDegrees());
              SmartDashboard.putDouble("joystick throttle is:", joystick.getThrottle());

        }*/

            /* if (!toggleA.get())
        {
             {
               drive.stopMotor();
               if (-90>=joystick.getDirectionDegrees() || joystick.getDirectionDegrees()>=90)
               {
                   drive.arcadeDrive(joystick);
               }
             }
        }*/





    }//end teleopPeriodic()
}//end RobotTemplate






    
    

