/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class NetworkTableRobot extends IterativeRobot {
    
    public static NetworkTable tableau;
    public static NetworkTable table;
    Timer timer;
    private double countQ = 0.0;
    private double countA = 0.0;
    private double countS = 0.0;
    private double countZ = 0.0;
    private double genericDouble;
    private int seed;
    
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        tableau = NetworkTable.getTable("tableauInteligent");
        table = NetworkTable.getTable("SmartDashboard");
        timer = new Timer();
    }

    public void disabledInit()
    {
        timer.stop();
        timer.reset();
    }
    
    public void autonomousInit()
    {
        genericDouble = 9.8765432109876543210;
        seed = 168;
        timer.start();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        if(timer.get()>=1.0)
        {
            seed = (int) (MathUtils.pow(seed-seed%100,2) + MathUtils.pow(seed%100-seed%10,2) + MathUtils.pow(seed%10,2));
            timer.reset();
        }
        SmartDashboard.putNumber("Generic Double", genericDouble);
        SmartDashboard.putNumber("Generic Double part 2",(genericDouble*1000)%1.0);
        SmartDashboard.putNumber("???", seed);
        SmartDashboard.putNumber("pow", MathUtils.pow(3, 2));
        System.out.println(genericDouble);
        table.putNumber("GenericDouble3.0",genericDouble);
        tableau.putNumber("Generic Double2.0", genericDouble);
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
