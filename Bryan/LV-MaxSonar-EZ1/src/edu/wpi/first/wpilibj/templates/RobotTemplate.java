/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    AnalogChannel sonar;
    Timer slow;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        sonar = new AnalogChannel(5);
        slow= new Timer();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit() {
        slow.start();
    }
    
    public void teleopPeriodic() {
        if (slow.get()>1.0){
        SmartDashboard.putNumber("sonar_voltage", sonar.getVoltage());
        SmartDashboard.putNumber("Theoretic_Distance", (sonar.getVoltage())/0.0095703125); 
        //voltage per inch =.0095703125
        SmartDashboard.putNumber("Experimental_Distance", (sonar.getVoltage())/.00969487179487179);
        //slope = 9.69487179487179
        slow.reset();
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
