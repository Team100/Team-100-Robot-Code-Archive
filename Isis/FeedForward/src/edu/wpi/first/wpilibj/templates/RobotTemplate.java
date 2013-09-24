/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;
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
    // Get modules once, because it's expensive
    DigitalModule myModule = DigitalModule.getInstance(1);
    AnalogModule myAnalogModule = AnalogModule.getInstance(1);
    private final Jaguar jagLeft = new Jaguar(1);
    private final Jaguar jagRight = new Jaguar(2);
    private final Encoder encoderLeft = new Encoder(3,4);
    private final Encoder encoderRight = new Encoder(5,6);
    private final Timer timer = new Timer();
    private double prevDist = 0.0;
    private double prevTime = 0.0;
    private final double kGearRatio = 250 * 4* (30.0/18.0) * 2*(4.0/12.0)*3.14159;
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet
    
    public void robotInit() {
        SmartDashboard.putNumber("Victor_output", 0.0);
        encoderLeft.setReverseDirection(true);
        encoderLeft.setDistancePerPulse(1.0);
        encoderRight.setDistancePerPulse(1.0);
        resetInit();
    }//end robotInit()

    private void resetInit() {
        encoderLeft.reset();
        encoderRight.reset();
        encoderLeft.start();
        encoderRight.start();
        timer.reset();
        timer.start();
    }//end resetInit()
    
    public void teleopInit(){
        resetInit();
    }
    
    public void teleopPeriodic() {
        double output = SmartDashboard.getNumber("Victor_output", 0.0);
        double currTime = timer.get();
        double loopPeriod = currTime - prevTime;        
        double currDist = encoderRight.getRaw() / kGearRatio;
        double deltaDist = currDist - prevDist;
        double currInstVeloc = deltaDist / loopPeriod;
        SmartDashboard.putNumber("encoderLeftraw", encoderLeft.getRaw());
        SmartDashboard.putNumber("encoderRightraw", encoderRight.getRaw());
        SmartDashboard.putNumber("currInstVeloc", currInstVeloc);
        jagLeft.set(output);
        jagRight.set(output);
        testIO();
        prevDist = currDist;
        prevTime = currTime;
    }//end teleopPeriodic()
    
    public void testIO(){
        // Don't do this every iteration because it's too expensive
        if(timer.get() < 0.5) {
            return;
        }
        timer.reset();
        
        NetworkTable table = NetworkTable.getTable("Status");
        table.putNumber("dioData", myModule.getAllDIO());
        
        for(int i = 1; i <= 10; i++) {
            table.putNumber("pwm" + i, myModule.getPWM(i));
        }
        
        for(int i = 1; i <= 8; i++) {
            table.putNumber("analog" + i, ((int)(myAnalogModule.getVoltage(i) * 1000) / 1000.0));
        }
    }//end testIO
    
}