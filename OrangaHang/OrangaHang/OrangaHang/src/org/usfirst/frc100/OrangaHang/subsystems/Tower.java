/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStation;
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
    private final double kDefaultClimbPosition = 4.1;//FIXME needs to be voltage
    private final double kDefaultShootPosition = 4.276;
    private final double kDefaultIntakePosition = 3.901;
    private final double kDefaultStartPosition =  2.525;
    private final double kDefaultTolerance = 0.05;
    private final double kDefaultTiltDownSpeed = 0.2;
    private final double kDefaultTiltUpSpeed = 1.0;
    
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
        if (!p.containsKey("TowerTiltDownSpeed")) {
            p.putDouble("TowerTiltDownSpeed", kDefaultTiltDownSpeed);
        }
        if (!p.containsKey("TowerTiltUpSpeed")) {
            p.putDouble("TowerTiltUpSpeed", kDefaultTiltUpSpeed);
        }
    }//end constructor
    
    public void manualTilt(double speed){
        if (speed>0.0){
        DriverStation ds = DriverStation.getInstance();
        final double kTowerReduce = ds.getAnalogIn(2);
        towerMotor.set(speed/kTowerReduce);
        } else if (speed<0.0){
            towerMotor.set(speed);
        } else {
            towerMotor.set(0.0);
        }
        SmartDashboard.putNumber("MagEncoderVoltage", towerMagEncoder.getVoltage());
    }//end manualTilt

    public boolean tiltToPosition(String key) {
        Preferences p = Preferences.getInstance();
        final double kPosition = p.getDouble(key, 0.0);
        final double kTolerance = p.getDouble("TowerTolerance", 0.0);
        final double kTiltUpSpeed = p.getDouble("TowerTiltUpSpeed", 0.0);
        final double kTiltDownSpeed = p.getDouble("TowerTiltDownSpeed", 0.0);
        double voltage = towerMagEncoder.getVoltage();
        SmartDashboard.putNumber("MagEncoderVoltage", voltage);//only in debug mode 
        if (voltage < kPosition + kTolerance && voltage > kPosition - kTolerance){
            towerMotor.set(0.0);
            System.out.println("Case 1");
            return true;
        } else if (voltage > kPosition + kTolerance) {
            towerMotor.set(kTiltUpSpeed);
            System.out.println("Case 2");
        } else if(voltage < kPosition - kTolerance){
            towerMotor.set(-Math.abs(kTiltDownSpeed));
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
