/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.ManualTilt;

/**
 *
 * @author Team100
 */
public class Tower extends Subsystem implements SubsystemControl{
    //Robot parts
    private final AnalogChannel towerMagEncoder = RobotMap.towerMagEncoder;
    private final Victor towerMotor = RobotMap.towerMotor;
    //Constants
    //TODO: calibrate all values
    private final double kDefaultClimbPosition = 1.60;//FIXME needs to be voltage
    private final double kDefaultShootPosition = 2.05;
    private final double kDefaultIntakePosition = 1.25;//3.901
    private final double kDefaultStartPosition = 0.4;
    private final double kDefaultTolerance = 0.05;
    private final double kDefaultTiltSpeed = 0.2;
    private String prevPosition = " ";
    
    public void initDefaultCommand() {
        setDefaultCommand(new ManualTilt());
    }//end initDefaultCommand
    
    public Tower() {
        Preferences p = Preferences.getInstance();
        if (!p.containsKey("TowerClimbPos")) {
            p.putDouble("TowerClimbPos", kDefaultClimbPosition);
        }
        if (!p.containsKey("TowerShootPos")) {
            p.putDouble("TowerShootPos", kDefaultShootPosition);
        }
        if (!p.containsKey("TowerIntakePos")) {
            p.putDouble("TowerIntakePos", kDefaultIntakePosition);
        }
        if (!p.containsKey("TowerStartPos")) {
            p.putDouble("TowerStartPos", kDefaultStartPosition);
        }
        if (!p.containsKey("TowerTolerance")) {
            p.putDouble("TowerTolerance", kDefaultTolerance);
        }
        if (!p.containsKey("TowerTiltSpeed")) {
            p.putDouble("TowerTiltSpeed", kDefaultTiltSpeed);
        }
    }//end constructor
    
    public void manualTilt(double speed){
        towerMotor.set(-speed);
        SmartDashboard.putNumber("MagEncoderVoltage", towerMagEncoder.getVoltage());
    }//end manualTilt

    public boolean tiltToPosition(String key) {
        Preferences p = Preferences.getInstance();
        final double kPosition = p.getDouble(key, 0.0);
        final double kTolerance = p.getDouble("TowerTolerance", 0.0);
        final double kTiltSpeed = p.getDouble("TowerTiltSpeed", 0.0);
        double voltage = towerMagEncoder.getVoltage();
        SmartDashboard.putNumber("MagEncoderVoltage", voltage);//only in debug mode
        if (voltage < kPosition + kTolerance && voltage > kPosition - kTolerance){
            towerMotor.set(0.0);
            System.out.println("Case 1");
            return true;
        } else if (voltage > kPosition + kTolerance) {
            towerMotor.set(-kTiltSpeed);
            System.out.println("Case 2");
        } else if(voltage < kPosition - kTolerance){
            towerMotor.set(kTiltSpeed);
            System.out.println("Case 3");
        }
        return false;
    }//end tiltToPosition
    
    //SubsystemControl interface ops - DO NOT REMOVE
    public void disable() {
        towerMotor.set(0.0);
    }//end disable

    public void enable() {
        
    }//end enable
    
    public void writePreferences() {
        //nothing special to do
    }//end writePreferences
    
}//end Tower
