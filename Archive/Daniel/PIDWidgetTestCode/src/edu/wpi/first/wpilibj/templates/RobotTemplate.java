/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    NetworkTable table = NetworkTable.getTable("PIDExtension");
    private double vel = 18.0;
    Timer timer = new Timer();
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        timer.reset();
        timer.start();
        table.putNumber("p", 0.01);
        table.putNumber("i", 0.10);
        table.putNumber("d", 1.00);
        table.putNumber("setpoint", 20.0);
        table.putNumber("minOutput", 0.2);
        table.putNumber("maxOutput", 1.0);
        table.putNumber("velocity", 18.0);
        table.putNumber("time", 0.0);
        table.putBoolean("boolean", false);
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
        
//        System.out.println(table.getNumber("p"));
//        System.out.println(table.getNumber("i"));
//        System.out.println(table.getNumber("d"));
//        System.out.println(table.getNumber("setpoint"));
//        System.out.println(table.getNumber("minOutput"));
//        System.out.println(table.getNumber("maxOutput"));
//        System.out.println(table.getNumber("velocity"));
//        System.out.println(table.getNumber("time"));
        
        table.putNumber("time", timer.get());
        vel = vel * 1.0001;
        table.putNumber("velocity", vel);
       
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
