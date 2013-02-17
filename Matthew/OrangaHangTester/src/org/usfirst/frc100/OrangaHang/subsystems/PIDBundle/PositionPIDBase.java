/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems.PIDBundle;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Isis
 */
public class PositionPIDBase {
    private boolean enabled = false;
    private String name;
    //Previous value variables
    private double input = 0.0;
    private double prevDist = 0.0; 
    private double totalDistError = 0.0; 
    private double output = 0.0;
    private double filtDist = 0.0;
    //Tuneables
    private double kMaxVeloc = 1.0;
    private double kMaxOutput = 0.5;
    private double kMinOutput = 0.05;
    private double kP = 0.0;
    private double kI = 0.0;
    private double kD = 0.0;
    //Constants
    private double setpoint = 0.0;
    private double kDistRatio;
    private double kDeadband = 0.05;

    private void resetValues(){
        input = 0.0;
        prevDist = 0.0;
        totalDistError = 0.0;
        output = 0.0;
        filtDist = 0.0;
    }//end resetValues

    public PositionPIDBase(double distRatio, String key){
        kDistRatio = distRatio;
        name = key;
    }//end constructor

    private String dashboardName(String key) {
        return key + "_" + name;
    }//end dashboardName
    
    public double calculate(double period){
        if (!enabled) {
            output = 0.0;
            return output;
        }

        double goalDist = getSetpoint();

        //calculate instantaneous velocity
        double currDist = input / kDistRatio;
        SmartDashboard.putNumber(dashboardName("currDist"), currDist);
        double deltaDist = currDist - prevDist;
        double instVeloc = deltaDist / period;
        SmartDashboard.putNumber(dashboardName("instVeloc"), instVeloc);
        
        //rectangular motion profile
        if (goalDist - filtDist > kMaxVeloc * period) {
            filtDist += kMaxVeloc * period;
        } else if (goalDist - filtDist < -kMaxVeloc * period) {
            filtDist -= kMaxVeloc * period;
        } else {
            filtDist = goalDist;
        }
        double distError = filtDist - currDist;
        totalDistError += distError / period;
        SmartDashboard.putNumber(dashboardName("distError"), distError);
        
        //capping our integral
        //don't increase totalDistError if kI is unset!
        if (kI != 0.0){
            double errorMax = kMaxOutput/kI;
            if((errorMax < totalDistError) || (kI * totalDistError > kMaxOutput)){
                totalDistError = errorMax;
            }
            if(-errorMax > totalDistError){
                totalDistError = -errorMax;
            }
        } else if (kI == 0.0){
            totalDistError = 0.0;
        }        
        
        //compute the output
        output = kP * distError + kI * totalDistError - kD*(instVeloc);

        //limit to one direction of motion, eliminate deadband
        if (output > 0.0) {
            output = output + kDeadband;

        } else if (output < 0.0) {
            output = output - kDeadband;
        }
        if (output > kMaxOutput) {
            output = kMaxOutput;
        }
        if (output < kMinOutput) {
            output = kMinOutput;
        }
            
        //setup for next loop
        prevDist = currDist;

        return output;
    }// end calculate

    
    //obtain, set setpoint
    public synchronized void setSetpoint(double sp){
        setpoint = sp;
    }//end setVelocSetpt
    private synchronized double getSetpoint (){
        return setpoint;
    }//end getSetpoint

    public boolean isEnabled() {
        return enabled;
    }
    
    //check enable/disable of robot
    public void disable(){
        enabled = false;
    }//end disable
    public void enable(){
        if (!enabled){
            resetValues();
        }
        enabled = true;
    }//end enable

    
    //set tuneables
    public void setInput(double put){
        input = put;
    }//end setInput
    public void setKP(double p){
        kP = p;
    }//end setKP
    public void setKI(double i){
        kI = i;
    }//end setKI
    public void setKD(double d){
        kD = d;
    }//end setKD
    public void setMaxOutput(double max){
        kMaxOutput = max;
    }//end setMaxOutput
    public void setMinOutput(double min){
        kMinOutput = min;
    }//end setMaxOutput
    public void setMaxVeloc(double vel){
        kMaxVeloc = vel;
    }//end setMaxVeloc

}// end PositionPIDBase
