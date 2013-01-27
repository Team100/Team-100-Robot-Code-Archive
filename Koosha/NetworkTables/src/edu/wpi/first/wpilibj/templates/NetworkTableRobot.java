/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTableProvider;
import edu.wpi.first.wpilibj.networktables2.NetworkTableNode;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class NetworkTableRobot extends IterativeRobot {
    
    //public NetworkTableNode node1;
    //public NetworkTableProvider provider1;
    //public NetworkTable table;
    Timer timer;
    private double countQ = 0.0;
    private double countA = 0.0;
    private double countS = 0.0;
    private double countZ = 0.0;
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        //node1 = new NetworkTableNode();
        //provider1 = new NetworkTableProvider(node1);
        //table = new NetworkTable("C:\Users\Student\Documents\GoogleCodeRepo\Koosha", provider1);
        timer = new Timer();
    }

    public void disabledInit()
    {
        timer.stop();
        timer.reset();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopInit()
    {
        timer.start();
    }
    
    public void teleopPeriodic()
    {
        countQ+=1.0;
        countA+=1.0;
        countS+=1.0;
        countZ+=1.0;
        SmartDashboard.putNumber("LevelA", countQ);
        SmartDashboard.putNumber("LevelA/Level1", countA);
        SmartDashboard.putNumber("LevelA/Level2", countS);
        SmartDashboard.putNumber("LevelA/Level1/Levela", countZ);
        SmartDashboard.putNumber("Amount of time without accident:", timer.get());
        SmartDashboard.putNumber("Amount of time without accident:/minutes", timer.get()/60.0);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
