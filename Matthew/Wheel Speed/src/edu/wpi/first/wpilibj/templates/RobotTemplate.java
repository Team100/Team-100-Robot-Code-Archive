/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    private final DigitalInput magswitch;
    private final Counter counter;
    private int index;
   // private final Jaguar Jag;
    private final Joystick Joy;
    private final Timer time;
    private int[] arrayCount;  //array[2] == 3rd element
    private double[] arrayTime;
    private final int kNumSamples = 20;

    public RobotTemplate() {
        time = new Timer();
        magswitch = new DigitalInput (1);
       // Jag = new Jaguar (2);
        counter = new Counter (magswitch);
        counter.setMaxPeriod(1.0);
        index = 0;
        Joy = new Joystick(1);
        arrayCount = new int[kNumSamples];
        arrayTime = new double [kNumSamples];
        for (int i=0; i<kNumSamples; i++){
            arrayCount[i] = 0;
            arrayTime[i] = 0.0;    
        }
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

    public void teleopInit()   {
        counter.start();
        counter.reset();;
        System.out.println("teleopinit was called");
        time.start();
        
    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
   // Jag.set(Joy.getY());
    arrayCount[index] = counter.get();
    arrayTime[index] = time.get();
    int nextIndex = (index+1)%kNumSamples;
    double deltaTime = arrayTime[index]-arrayTime[nextIndex];
    int deltaCount = arrayCount[index] - arrayCount[nextIndex];
    index = nextIndex;
    SmartDashboard.putBoolean("MagSwitch", magswitch.get());
    SmartDashboard.putNumber("deltaCount", deltaCount);
    SmartDashboard.putNumber("counter_rate", (deltaCount/(deltaTime*4))*60);
    SmartDashboard.putNumber("Joy_value", Joy.getY());
    SmartDashboard.putNumber("deltaTime",deltaTime);
    SmartDashboard.putNumber("CounterPeriod", counter.getPeriod());

   }//end teleopPeriodic()
}//end RobotTemplate