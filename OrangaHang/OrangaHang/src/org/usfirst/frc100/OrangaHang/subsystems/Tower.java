/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.commands.TestTilter;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.PositionSendablePID;

/**
 *
 * @author Team100
 */
public class Tower extends Subsystem
{
    //Robot parts
    private final AnalogChannel potentiometer = RobotMap.towerPotent;
    private final Victor motor = RobotMap.towerMotor;
    //Constants
    private double kClimbPosition = 60.0;
    private double kShootPosition = 50.0;
    private double kIntakePosition = 60.0;
    private double kStartPosition = 95.0;
    private double kTowerAngleRatio = 0.277;
    private double kZeroAngle = 228;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new TestTilter());
    }//end initDefaultCommand
    
    public void manualControl(double s)
    {
        if(potentiometer.getValue()>=68.0 && s>=0)
        {
            motor.set(s);
        }
        else if(potentiometer.getValue()<=0.0 && s<0)
        {
            motor.set(s);
        }
    }
    
    //calculates and returns the angle of the tower
    public double getAngle(){
        return kZeroAngle-potentiometer.getValue()*kTowerAngleRatio;
    }//end getAngle()
    
    public void testTilter(double speed){
        double pot=getAngle();
        double max=Preferences.getInstance().getDouble("TowerUpperLimit", 0);
        double min=Preferences.getInstance().getDouble("TowerLowerLimit", 0);
        if (pot<max&&speed>0||pot>min&&speed<0){
            motor.set(speed);
        }
        if (pot<min&&speed<0){
            motor.set(0);        
        }
        if (pot>max&&speed>0){
            motor.set(0);
        }
        if(pot<max&&pot>min){
            motor.set(speed);        
        }
    }

    //PID control
    PIDSource sourceTower = new PIDSource() {
        public double pidGet() {
            SmartDashboard.putNumber("potVoltage_raw", potentiometer.getValue());
            return potentiometer.getValue();
        }
    }; //end anonym class PIDSource
    PIDOutput outputTower = new PIDOutput() {
        public void pidWrite(double output) {
            motor.set(output);
        }
    }; //end anonym class PIDOutput
    private PositionSendablePID pidTower = new PositionSendablePID("Tower", sourceTower, outputTower, kTowerAngleRatio);

    public void setSetpoint(double setpoint) {
        pidTower.setSetpoint(setpoint);
    }//end setSetpoint

    public void tiltToClimb() {
        enable();
        pidTower.setSetpoint(kClimbPosition);
    }//end tiltToClimb

    public void tiltToShoot() {
        enable();
        pidTower.setSetpoint(kShootPosition);
    }//end tiltToClimb

    public void tiltToIntake() {
        enable();
       pidTower.setSetpoint(kIntakePosition);
    }//end tiltToIntake
    
    public void tiltToStart(){
        enable();
       pidTower.setSetpoint(kStartPosition);
    }//end tiltToStart

    public void disable() {
        pidTower.disable();
    }//end disable

    public void enable() {
        pidTower.enable();
    }//end enable    
}//end Tower
