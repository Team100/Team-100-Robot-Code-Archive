/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/




/*-------------------------------INSTRUCTIONS---------------------------------*/
/* Do not use any strings for KEYS that have spaces in them                   */
/* To view and edit in real time go to View>Add>Robot Preferences             */
/* To add more listings from SmartDashboard, enable editing mode              */
/* and then disable editing mode. Then double click on the fields you want    */
/* NOTE: KEYS cannot be edited after saving                                   */
/*----------------------------------------------------------------------------*/


package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    Preferences preferences;
    
    public void robotInit() {
        preferences.getInstance().putString("Test", "Hello World");
        preferences.getInstance().putBoolean("Bool", true);
        preferences.getInstance().putInt("Int", 512);
        preferences.getInstance().save();
//       preferences.getInstance().
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
        System.out.println("Keys: " + preferences.getInstance().getKeys());
//        System.out.println(preferences.getInstance().getString("Test", "If this is showing up then nothing was found at that key"));
        
    }
    
}
