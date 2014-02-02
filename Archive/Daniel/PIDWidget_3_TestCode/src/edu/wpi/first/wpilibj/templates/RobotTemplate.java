/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    VelocitySendablePID mySendablePID;
    VelocitySendablePID myOtherSendablePID;
    PositionSendablePID myPositionSendablePID;
    
    Encoder enc = new Encoder(1, 2);
    Victor motor = new Victor(1);
    
    PIDSource source;
    PIDOutput output;
    PIDSource period;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        source = new PIDSource() {
            public double pidGet() {
                return enc.get();
            }
        };
        period = new PIDSource() {
            public double pidGet() {
                return enc.getRate();
            }
        };
        output = new PIDOutput() {
            public void pidWrite(double d) {
                motor.set(d);
            }
        };
        mySendablePID = new VelocitySendablePID("Test", source, period, output, 1.0);
        myOtherSendablePID = new VelocitySendablePID("Test2", source, period, output, 1.0);
        myPositionSendablePID = new PositionSendablePID("Test3", source, output, 1.0);
        
        
//        LiveWindow.addActuator("PID Thing", "PID Controller", mySendablePID);
        SmartDashboard.putData("Test", mySendablePID);
        SmartDashboard.putData("Test2", myOtherSendablePID);
        SmartDashboard.putData("Test3", myPositionSendablePID);
        
        
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
        System.out.println("What is my name? " + mySendablePID.getName());
        System.out.println("P: " + mySendablePID.getP());
        System.out.println("What is my name? " + myOtherSendablePID.getName());
        System.out.println("P: " + myOtherSendablePID.getP());
        System.out.println("What is my name? " + myPositionSendablePID.getName());
        System.out.println("P: " + myPositionSendablePID.getP());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        System.out.println("P:" + mySendablePID.getP());
    }
    
}
