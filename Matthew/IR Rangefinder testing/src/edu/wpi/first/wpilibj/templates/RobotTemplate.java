/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
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
    //private final Joystick joystick2;
    private final JoystickButton button1;
    private final JoystickButton button2;
    private final Victor vic2;
    //private final Jaguar jag2;
    private final Servo servo;
    //private final RobotDrive drive;
    //private final AnalogChannel pot;
    //private final AnalogTrigger lightsensor;
    //private final DigitalInput toggleA;
    //private final DigitalInput toggleB;
   // private final AnalogChannel IRrangeA;
    //private final AnalogChannel gyro;
    
    public RobotTemplate() {
       // IRrangeA = new AnalogChannel(2);
        //gyro = new AnalogChannel(1);
        vic2 = new Victor(2);
        joystick = new Joystick(1);
        button1 = new JoystickButton( joystick, 1);
        button2 = new JoystickButton( joystick, 2);
        servo = new Servo(7);
    }
    
    public void robotInit() {
        servo.set(0.5);
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
        
       //vic2.set(joystick.getY());
       //SmartDashboard.putNumber( "Voltage", gyro.getVoltage()); 
       
       if(button1.get()==true)
       {
           servo.set(0.7);
       }
       if(button2.get() == true)
       {
           servo.set(0.3);
       }
       servo.set((joystick.getY() + 1)/2 ); 
        
        
        
        
        
        
        
        /*SmartDashboard.putNumber("Average Voltage Is", IRrangeA.getAverageVoltage());
        SmartDashboard.putNumber("Voltage Is", IRrangeA.getVoltage());*/
        
        
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
