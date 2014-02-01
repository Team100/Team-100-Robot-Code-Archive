/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.samples;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class DashBoardExample extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    boolean driving = false;
    private final Joystick joystick;
    private final Relay relay;
    
    public DashBoardExample(){
        joystick = new Joystick(1);
        relay = new Relay(8);
    }
    
    public void robotInit() {
        Watchdog.getInstance().setEnabled(false);
        relay.setDirection(Relay.Direction.kForward);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }
    
    Jaguar jag = new Jaguar(1);
    double speed = -1.0;

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        updateDashboard();
        SmartDashboard.putBoolean("Button X", joystick.getButton(Joystick.ButtonType.kTrigger));
        SmartDashboard.putBoolean("Reversed(Button A/2)", joystick.getRawButton(2));
        
        //Simple Control of a motor using throught the spike(uses relay)
        //Button 1 to make it driver, Button 2 to change direction
        if(joystick.getRawButton(2)){
            relay.setDirection(Relay.Direction.kReverse);
        }else{
            relay.setDirection(Relay.Direction.kForward);
        }
        
        if(joystick.getButton(Joystick.ButtonType.kTrigger)){
            relay.set(Relay.Value.kOn);
        }else{relay.set(Relay.Value.kOff);
        }
        
        
   
        
    }

    void updateDashboard() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        lowDashData.addCluster();
        {
            lowDashData.addCluster();
            {     //analog modules
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                        //Displays the Analog Values on the SmartDashboard
                        SmartDashboard.putDouble("Analog Voltage " + i, (float) AnalogModule.getInstance(1).getAverageVoltage(i));
                        
                    }
                }
                lowDashData.finalizeCluster();
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) { 
                        //lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                        //AnalogModule 2 does not exist: Putting in 0 as a value
                        lowDashData.addFloat((float) 0);
                    }
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            lowDashData.addCluster();
            { //digital modules
                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 1;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        //int module = 2;
                        // Digital module 2 does not exist: using 1 for dummy data
                        int module = 1;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

            }
            lowDashData.finalizeCluster();

            //lowDashData.addByte(Solenoid.getAllFromDefaultModule());
            //more dummy data sicence the solenoid does not exist
            lowDashData.addByte((byte)0);
            
        }
        lowDashData.finalizeCluster();
        lowDashData.commit();

    }
}
