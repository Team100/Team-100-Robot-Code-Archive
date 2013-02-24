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
import org.usfirst.frc100.OrangaHang.OI;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.ManualTilt;

/**
 *
 * @author Team100
 */
public class Tower extends Subsystem implements SubsystemControl{
    Preferences p = Preferences.getInstance();
    //Robot parts
    private final AnalogChannel potentiometer = RobotMap.towerPotent;
    private final Victor motor = RobotMap.towerMotor;
    //Constants
    private double kClimbPosition = p.getDouble("kTowerClimbPos", 275.0);
    private double kShootPosition = p.getDouble("kTowerShootPos", 100.0);
    private double kIntakePosition = p.getDouble("kTowerIntakePos", 275.0);
    private double kStartPosition = p.getDouble("kTowerStartPos", 700.0);
    private double kMaxPos = p.getDouble("kTowerMaxPos", 700);
    private double kMinPos = p.getDouble("kTowerMinPos", 50);
    private double kTolerance = 50;//FIXME
    private double kTiltSpeed = 0.7;//FIXME
    
    public void initDefaultCommand() {
        setDefaultCommand(new ManualTilt());
    }//end initDefaultCommand
    
    public Tower() {
        SmartDashboard.putNumber("kTowerClimbPos", kClimbPosition);
        SmartDashboard.putNumber("kTowerShootPos", kShootPosition);
        SmartDashboard.putNumber("kTowerIntakePos", kIntakePosition);
        SmartDashboard.putNumber("kTowerStartPos", kStartPosition);
        SmartDashboard.putNumber("kTowerMaxPos", kMaxPos);
        SmartDashboard.putNumber("kTowerMinPos", kMinPos);
    }//end constructor
    
    public void testTilter(double speed){
        getDashboardValues();
        motor.set(-speed);
        SmartDashboard.putNumber("Tower_manual",speed);
        SmartDashboard.putNumber("Joystick",OI.manipulator.getThrottle());
        //if (pot<max&&speed>0||pot>min&&speed<0){
        //   motor.set(speed);
        //}
        //if (pot<min&&speed<0){
        //    motor.set(0);        
        //}
        //if (pot>max&&speed>0){
        //    motor.set(0);
        //}
        //if(pot<max&&pot>min){
        //    motor.set(speed);        
        //}
    }//end testTilter

    public void tiltToClimb() {
        if (potentiometer.getValue() < kClimbPosition + kTolerance && potentiometer.getValue()> kClimbPosition - kTolerance ){
            motor.set(0.0);
        } else if (potentiometer.getValue() > kClimbPosition + kTolerance) {
            motor.set(kTiltSpeed);
        } else if(potentiometer.getValue() < kClimbPosition - kTolerance){
            motor.set(-kTiltSpeed);
        }
    }//end tiltToClimb

    public void tiltToShoot() {
        if (potentiometer.getValue() < kShootPosition + kTolerance && potentiometer.getValue()> kShootPosition - kTolerance ){
            motor.set(0.0);
        } else if (potentiometer.getValue() > kShootPosition + kTolerance) {
            motor.set(kTiltSpeed);
        } else if (potentiometer.getValue() < kShootPosition - kTolerance){
            motor.set(-kTiltSpeed);
        }
    }//end tiltToShoot

    public void tiltToIntake() {
        if (potentiometer.getValue() < kIntakePosition + kTolerance && potentiometer.getValue()> kIntakePosition - kTolerance ){
            motor.set(0.0);
        } else if (potentiometer.getValue() > kIntakePosition + kTolerance) {
            motor.set(kTiltSpeed);
        } else if (potentiometer.getValue() < kIntakePosition - kTolerance){
            motor.set(-kTiltSpeed);
        }
    }//end tiltToIntake
    
    public void tiltToStart(){
        if (potentiometer.getValue() < kStartPosition + kTolerance && potentiometer.getValue()> kStartPosition - kTolerance ){
            motor.set(0.0);
        } else if (potentiometer.getValue() > kStartPosition + kTolerance) {
            motor.set(kTiltSpeed);
        } else if (potentiometer.getValue() < kStartPosition - kTolerance) {
            motor.set(-kTiltSpeed);
        }
    }//end tiltToStart
    
    //SubsystemControl interface ops - DO NOT REMOVE
    public void disable() {
        motor.set(0.0);
    }//end disable

    public void enable() {
        
    }//end enable
    
    public void writePreferences() { //Might want to write some other stuff specific to the tower
        
    }//end writePreferences
    
    public void getDashboardValues(){
        kClimbPosition=SmartDashboard.getNumber("kTowerClimbPos", kClimbPosition);
        kShootPosition=SmartDashboard.getNumber("kTowerShootPos", kShootPosition);
        kIntakePosition=SmartDashboard.getNumber("kTowerIntakePos", kIntakePosition);
        kStartPosition=SmartDashboard.getNumber("kTowerStartPos", kStartPosition);
        kMaxPos=SmartDashboard.getNumber("kTowerMaxPos", kMaxPos);
        kMinPos=SmartDashboard.getNumber("kTowerMinPos", kMinPos);
    }//end getDashboardValues
    
}//end Tower
