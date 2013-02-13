/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.OrangaHang.RobotMap;
import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.PositionSendablePID;

/**
 *
 * @author Team100
 */
public class Tower extends Subsystem {
    //Robot parts

    AnalogChannel potentiometer = RobotMap.towerPotent;
    Victor motor = RobotMap.towerMotor;
    //Constants
//    private double kClimbBackLimit = 0.0;
//    private double kClimbFrontLimit = 0.0;
    private final double kClimbPosition = 0.0;
//    private double kShootBackLimit = 0.0;
//    private double kShootFrontLimit = 0.0;
    private final double kShootPosition = 0.0;
//    private double kIntakeBackLimit = 0.0;
//    private double kIntakeFrontLimit = 0.0;
    private final double kIntakePosition = 0.0;
    private final double kTowerAngleRatio = 0.0;
    private boolean isClimbing = false;
    private boolean isShooting = false;
    private boolean isGettingFrisbees = false;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }//end initDefaultCommand

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
        isShooting = false;
        isGettingFrisbees = false;
        isClimbing = true;
        pidTower.setSetpoint(kClimbPosition);
    }//end tiltToClimb

    public void tiltToShoot() {
        isGettingFrisbees = false;
        isShooting = true;
        isClimbing = false;
        pidTower.setSetpoint(kShootPosition);
    }//end tiltToClimb

    public void tiltToIntake() {
       isGettingFrisbees = true;
       isShooting = false;
       isClimbing = false;
       pidTower.setSetpoint(kIntakePosition);
    }//end tiltToIntake

    public void disable() {
        pidTower.disable();
    }//end disable

    public void enable() {
        pidTower.enable();
    }//end enable    
}//end Tower
