/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

    //private final Joystick joystick;
    //private final Joystick joystick2;
    //private final JoystickButton button1;
    //private final Victor vic1;
    //private final Jaguar jag2;
    //private final Servo servo;
    //private final RobotDrive drive;
    //private final AnalogChannel pot;
    //private final AnalogTrigger lightsensor;
    //private final DigitalInput toggleA;
    //private final DigitalInput toggleB;
    private final AnalogChannel IRrangeA;
    
    public RobotTemplate() {
        IRrangeA = new AnalogChannel(2);
    }
    
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
        
       
        SmartDashboard.putNumber("Average Voltage Is", IRrangeA.getAverageVoltage());
        SmartDashboard.putNumber("Voltage Is", IRrangeA.getVoltage());
        
        
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
