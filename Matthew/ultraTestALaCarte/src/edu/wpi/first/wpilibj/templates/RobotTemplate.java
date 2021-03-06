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
    
    private AnalogChannel ultra1;
    private AnalogChannel IRrange;
    
    public void robotInit() {
         ultra1 = new AnalogChannel(5);
         IRrange = new AnalogChannel(4);
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
        
        //double range = ultra1.getVoltage()/5*512/2.54; //if MB1023 ultrasonic sensor
        double range = (ultra1.getVoltage()*1024/12.7); //if MB1220 ultrasonic sensor 
        double RAWrange = ultra1.getVoltage();
        System.out.println("RAW-Voltage: " + RAWrange);
        SmartDashboard.putNumber("RAW-Voltage: ", RAWrange);
        System.out.println("Inches: " + range);
        SmartDashboard.putNumber("Inches: ", range);
        
        double tempIR = ((IRrange.getVoltage()*0.0073)-0.0082); //if sharp IR long dist.
        double IRval = (1/tempIR)/2.54; //if sharp IR long dist.
        double RAWrangeIR = IRrange.getVoltage();
        System.out.println("RAW-Voltage IR: " + RAWrangeIR);
        SmartDashboard.putNumber("RAW-Voltage IR: ", RAWrangeIR);
        System.out.println("Inches IR: " + IRval);
        SmartDashboard.putNumber("Inches IR: ", IRval);
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
