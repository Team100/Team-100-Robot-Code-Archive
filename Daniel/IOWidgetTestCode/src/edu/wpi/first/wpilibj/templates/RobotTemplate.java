/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    private NetworkTable table = NetworkTable.getTable("Status");
    Victor vic1 = new Victor(1);
    Jaguar jag2 = new Jaguar(2);
    Joystick joy = new Joystick(1);
    
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
        
        vic1.set(joy.getY());
        jag2.set(joy.getThrottle());
        
        for(int i = 1; i < 15; i++) {
            short mask = (short) (0x01 << i);
            table.putNumber("dio" + i, DigitalModule.getInstance(1).getAllDIO() & mask);
        }
        
        for(int i = 1; i <= 10; i++) {
            table.putNumber("pwm" + i, DigitalModule.getInstance(1).getPWM(i));
        }
        for(int i = 1; i <= 8; i++) {
            table.putNumber("analog" + i, ((int)(AnalogModule.getInstance(1).getVoltage(i) * 1000) / 1000.0));
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
