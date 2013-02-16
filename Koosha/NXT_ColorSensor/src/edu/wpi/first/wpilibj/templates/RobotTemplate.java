/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    HiTechnicColorSensor colorSensor; // used to sense frisbee color; best if used on bottom of frisbee
    int colorVal;
    int frisbeeType;
    String frisbeeOwner;
    int alliance;
    Jaguar motor1;
    Victor vic1;
    Timer timer;
    Joystick joy1;
    DigitalInput input;
    NetworkTable table;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        colorSensor = new HiTechnicColorSensor(1);
        frisbeeType=3; //0->red, 1->blue, 2->white, 3->default
        frisbeeOwner="Unknown";
        motor1 = new Jaguar(1);
        vic1 = new Victor(3);
        timer = new Timer();
        input = new DigitalInput(9);
        table = NetworkTable.getTable("Status");
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        alliance=DriverStation.getInstance().getAlliance().value;
        colorVal = colorSensor.getColor();
        
        if(((colorVal+1)%11)>=9) //will return true for 8, and 9
        {
            frisbeeType=0;
        }
        else if((Math.abs((colorVal-2)%8.5))<=1) //will return true for 1, 2, 3, and 11
        {
            frisbeeType=1;
        }
        else if(colorVal==17)
        {
            frisbeeType=2;
        }
        else if(colorVal==0)
        {
            frisbeeType=3;
        }
        else
        {
            frisbeeType=3;
        }
        
        if(frisbeeType==alliance)
        {
            frisbeeOwner="Ours";
        }
        else if(frisbeeType==(alliance^1))
        {
            frisbeeOwner="Theirs";
        }
        else if(frisbeeType==2)
        {
            frisbeeOwner="Neutral";
        }
        else
        {
            frisbeeOwner="Unknown";
        }
        
//        vic1.set(joy.getY());
//        jag2.set(joy.getThrottle());
        
        for(int i = 1; i <= 14; i++)
        {
            short mask = (short) (0x01 << i);
            table.putNumber("dio" + i, DigitalModule.getInstance(1).getAllDIO() & mask);
        }
        
        for(int i = 1; i <= 10; i++)
        {
            table.putNumber("pwm" + i, DigitalModule.getInstance(1).getPWM(i));
        }
        
        for(int i = 1; i <= 8; i++)
        {
            table.putNumber("analog" + i, ((int)(AnalogModule.getInstance(1).getVoltage(i) * 1000) / 1000.0));
        }
        
        motor1.set(1.0);
        SmartDashboard.putNumber("LOOK HERE!", timer.get());
        SmartDashboard.putNumber("Color Sensor Value", colorVal);
        SmartDashboard.putString("Alliance Color", DriverStation.getInstance().getAlliance().name);
        SmartDashboard.putNumber("Alliance Value", alliance);
        SmartDashboard.putNumber("Frisbee Number", frisbeeType);
        SmartDashboard.putString("Frisbee Owner", frisbeeOwner);
        SmartDashboard.putBoolean("Digital Input", input.get());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
