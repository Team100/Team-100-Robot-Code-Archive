//unfinished
package org.usfirst.frc100.Ballrus;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Runs calculations for PID.
 */
public class PID {

    String subsystem;
    double kP = 0, kI = 0, kD = 0;
    double error, p, i, d;
    double setpoint = 0, lastError = 0, totalError = 0;
    Timer period = new Timer();

    public PID(String subsystem) {
        this.subsystem = subsystem;
    }

    public void updateConstants() {
        if (subsystem.equals("driveDistance")){
            if(Preferences.driveTrainTuningMode){
                kP = SmartDashboard.getNumber("DriveDistance_kP", 0);
                kI = SmartDashboard.getNumber("DriveDistance_kI", 0);
                kD = SmartDashboard.getNumber("DriveDistance_kD", 0);
            } else{
                kP = Preferences.driveDistance_kP;
                kI = Preferences.driveDistance_kI;
                kD = Preferences.driveDistance_kD;
            }
        }
        if (subsystem.equals("driveAngle")){
            if(Preferences.driveTrainTuningMode){
                kP = SmartDashboard.getNumber("DriveAngle_kP", 0);
                kI = SmartDashboard.getNumber("DriveAngle_kI", 0);
                kD = SmartDashboard.getNumber("DriveAngle_kD", 0);
            } else{
                kP = Preferences.driveAngle_kP;
                kI = Preferences.driveAngle_kI;
                kD = Preferences.driveAngle_kD;
            }
        }
        if (subsystem.equals("driveDistanceLowGear")){
            if(Preferences.driveTrainTuningMode){
                kP = SmartDashboard.getNumber("DriveDistanceLowGear_kP", 0);
                kI = SmartDashboard.getNumber("DriveDistanceLowGear_kI", 0);
                kD = SmartDashboard.getNumber("DriveDistanceLowGear_kD", 0);
            } else{
                kP = Preferences.driveDistanceLowGear_kP;
                kI = Preferences.driveDistanceLowGear_kI;
                kD = Preferences.driveDistanceLowGear_kD;
            }
        }
        if (subsystem.equals("driveAngleLowGear")){
            if(Preferences.driveTrainTuningMode){
                kP = SmartDashboard.getNumber("DriveAngleLowGear_kP", 0);
                kI = SmartDashboard.getNumber("DriveAngleLowGear_kI", 0);
                kD = SmartDashboard.getNumber("DriveAngleLowGear_kD", 0);
            } else{
                kP = Preferences.driveAngleLowGear_kP;
                kI = Preferences.driveAngleLowGear_kI;
                kD = Preferences.driveAngleLowGear_kD;
            }
        }
        if (subsystem.equals("tilter")){
            if(Preferences.tilterTuningMode){
                kP = SmartDashboard.getNumber("Tilter_kP", 0);
                kI = SmartDashboard.getNumber("Tilter_kI", 0);
                kD = SmartDashboard.getNumber("Tilter_kD", 0);
            } else{
                kP = Preferences.tilter_kP;
                kI = Preferences.tilter_kI;
                kD = Preferences.tilter_kD;
            }
        }
    }
    
    public double getValue(double currentValue){
        updateConstants();
        error = setpoint - currentValue;
        totalError+=error;
        p = kP*error;
        i = kI*totalError;
        d = kD*(error-lastError)/period.get();
        period.reset();
        period.start();
        lastError = error;
        return p+i+d;
    }
    
    public void setSetpoint(double target){
        setpoint = target;
        lastError = 0;
        totalError = 0;
        period.reset();
        period.start();
    }
}
