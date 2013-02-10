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
    private final DigitalInput lightsensor;
//    private final AnalogChannel lightsensor1;
    private final Counter counter;
    private boolean firsttime;
    private int index;
    //public final Counter counter2;
    private final Jaguar Jag;
    private final Joystick Joy;
    private final Timer time;
    private double prevtime;
    private int prevcount;
    private int[] arrayCount;  //array[2] == 3rd element
    private double[] arrayTime;
    private final int kNumSamples = 50;

    public RobotTemplate() {
        time = new Timer();
        lightsensor = new DigitalInput (12);
        Jag = new Jaguar (8);
//        lightsensor1 = new AnalogChannel (1);
        counter = new Counter (lightsensor);
        firsttime = true;
        index = 0;
        //counter2 = new Counter (lightsensor);
        Joy = new Joystick(1);
        prevtime = 0.0;
        prevcount = 0;
        arrayCount = new int[kNumSamples];
        arrayTime = new double [kNumSamples];
        for (int i=0; i<kNumSamples; i++){
            arrayCount[i] = 0;
            arrayTime[i] = 0.0;    
        }
        /* One sensor's approx. voltage for white binder paper: 0.2 Aprrox. for dark marker line on the paper: 3.7.
          Another sensor's approx. voltage for white binder paper: 2.3 Approx for dark marker line on the paper: 4.55
         However, the voltage levels change depending on the sensor and the darkness of the marker */
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

    public void disabledInit()    {
//        counter.reset();
//        counter.start();
//        counter.stop();
//        System.out.println("disabledinit was called");
        //this section might not be necessary
    }

    public void teleopInit()   {
        counter.start();
        counter.reset();
        //counter2.start();
        //counter2.reset();
        firsttime = true;
        System.out.println("teleopinit was called");
        time.start();
        
    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

//        if(firsttime)
//        {
//            counter.reset();
//            firsttime = false;
//        }
//
//        int newcount = counter.get();
//
//        if(count != newcount)
//        {
//            System.out.println(newcount);
//            count = newcount;
//        }
   
    Jag.set(Joy.getY());
    arrayCount[index] = counter.get();
    arrayTime[index] = time.get();
    int nextIndex = (index+1)%kNumSamples;
    double deltaTime = arrayTime[index]-arrayTime[nextIndex];
    int deltaCount = arrayCount[index] - arrayCount[nextIndex];
    index = nextIndex;
    SmartDashboard.putBoolean("LightSensor", lightsensor.get());
    SmartDashboard.putNumber("deltaCount", deltaCount);
    SmartDashboard.putNumber("counter_rate", (deltaCount/(deltaTime*2))*60);
    SmartDashboard.putNumber("Joy_value", Joy.getY());
    SmartDashboard.putNumber("deltaTime",deltaTime);
    
    
    
            //SmartDashboard.putInt("Count is", newcount);


    //SmartDashboard.putBoolean("Analog Trigger state is" , lightsensor.getTriggerState());
    /* "false" means that it is light, "true" means that it is dark. */

//    if(lightsensor.getTriggerState() == false)
//        drive.arcadeDrive(joystick);
//    else
//        drive.stopMotor();

    //if(button1.get())
        //counter.reset();


   }//end teleopPeriodic()
}//end RobotTemplate