/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Isis
 */
public class PIDBase {
    //Previous value variables
    private double input = 0.0; 
    private double prevDist = 0.0; //previous encoder raw count divided by gear ratio
    private double prevWeightedInstVeloc = 0.0;
    private double goalDist = 0.0; //accumulated desired distances (products of kVelocSetpt & loopPeriod)
    private double output = 0.0;
    //Tuneables 
    private double kMaxOutput = 0.5;
    private double kMinOutput = 0.05;
    private double kP = 0.0; 
    private double kI = 0.0; 
    private double kD = 0.0; 
    //Constants
    private double setpoint = 0.0;
    private double kDistRatio;
    
    public PIDBase(double distRatio){
        kDistRatio = distRatio;
    }
    
    public double calculate(double period){
        double goalVeloc = getSetpoint();
        SmartDashboard.putNumber("kDistRatio", kDistRatio);
        
        //calculate instantaneous velocity
        double currDist = input / kDistRatio;
        SmartDashboard.putNumber("currDist", currDist);
        double deltaDist = currDist - prevDist;
        double instVeloc = deltaDist / period;
        SmartDashboard.putNumber("instVeloc", instVeloc);
        double error = goalVeloc - instVeloc;
        
        //goalVeloc distance is our integral
        //don't increase goalVeloc distance if kI is unset!
        double totalDistError;
        if (kI == 0.0) {
            totalDistError = 0.0;
        } else {
            goalDist += goalVeloc * period;
            totalDistError = goalDist - currDist;
            if (kI * totalDistError > kMaxOutput) {
                totalDistError = kMaxOutput / kI;
            }
        }
       
        //compute the output
        double currWeightedInstVeloc = instVeloc/period;
        output += kP * error + kI * totalDistError - kD * (currWeightedInstVeloc - prevWeightedInstVeloc);
        
        //limit to one directiion of motion, eliminate deadband
        if (output > kMaxOutput) {
            output = kMaxOutput;
        } else if (output < kMinOutput) {
            output = kMinOutput;
        }
        
        //setup for next loop
        prevWeightedInstVeloc = currWeightedInstVeloc;
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
   
    //set tuneables
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
    
}// end PIDBase
