/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.visa.VisaException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Serial2Arduino extends IterativeRobot {
    
    SerialPort serial;
    String in;
    byte[][] out = {{73}, {84}};
    Victor motr = new Victor(4);
    Timer clock = new Timer();
    AnalogChannel IRrange = new AnalogChannel(4);
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        try {
            serial = new SerialPort(9600);
        }
        catch (VisaException ex) {
            ex.printStackTrace();
        }
        clock.start();
    }
    
    public void disabledInit() {
    }
    
    public void disabledPeriodic() {
        if(serial != null) {
            try {
                if(serial.getBytesReceived() != 0) {
                    System.out.print("BytesIn:" + serial.getBytesReceived() + " ");
                    System.out.println("DisabledIn:" + serial.readString());
                }
            }
            catch (VisaException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    public void teleopInit() {
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if(serial != null) {
            try {
                if(serial.getBytesReceived() != 0) {
                    System.out.print("BytesReceived:" + serial.getBytesReceived() + " ");
                    System.out.print("InByte:" + serial.readString() + " ");
                    if(clock.get()%2.0 < 1.0) {
                        System.out.print("Data:" + out[0][0] + " ");
                        System.out.print("#ofBytes:" + serial.write(out[0], 1) + " ");
                    }
                    else {
                        System.out.print("Data:" + out[1][0] + " ");
                        System.out.print("#ofBytes:" + serial.write(out[1], 1) + " ");
                    }
                    System.out.println("Clock:" + clock.get());
                }
            }
            catch (VisaException ex) {
                ex.printStackTrace();
            }
        }

        SmartDashboard.putNumber("IRrange Inches", (1/((IRrange.getVoltage()*0.0073)-0.0082))*2.54);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
}
