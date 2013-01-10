/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Student
 */
public class PIDBase implements Callable {
    private final Timer timer = new Timer(); //in s (ignore WPIlib docs)
    //Previous value variables
    private double prevInstVeloc = 0.0; //previous instantaneous velocity
    private double prevDeriv = 0.0; //previous difference between above and currInstVeloc
    private double prevDist = 0.0; //previous encoder raw count divided by gear ratio
    private double prevTime = 0.0; //in s; previous sample of time elapsed
    private double prevError = 0.0; //previous difference btwn currInstVeloc and kVelocSetpt
    private double prevWeightedInstVeloc = 0.0;
    private double prevDeltaDist = 0.0;
    private double goalDist = 0.0; //accumulated desired distances (products of kVelocSetpt & loopPeriod)
    private double filtDist = 0.0; //accumulated deltas between currDist & prevDist
    private double output = 0.0;
    //Quality metrics
    private double numOscills = 0.0; //number of oscillations in velocity
    private double maxVeloc = 0.0; // max recorded inst veloc
    private double totalError = 0.0; //sum of epsilons between currInstVeloc and kVelocSetpt
    private double converge = 0.0; //first time achieve within tolerance
    //Tuneables
    private double kTolerance = 0.01; //in velocity, ft/s
    private double kDesPeriod = 0.02; //desired loop period
    private double kVelocSetpt = 0.0; //desired or goal velocity
    private double kMaxDuration = 10.0; 
    private double kMaxOutput = 0.5;
    private double kMinOutput = 0.05;
    private double kP = 0.04; 
    private double kI = 0.0; 
    private double kD = 0.001; 
    //Constants
    private final double kGearRatio = 250 * 4 * (27.0 / 13.0) * (0.5 * 3.14159) / 2;
    
    public PIDBase(double input){
        
    }
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet
    
    public void call(){
        //check if enough time has elapsed
        double currTime = timer.get();
        double loopPeriod = currTime - prevTime;
        if (loopPeriod < kDesPeriod) {
            return;
        } else if (currTime > kMaxDuration){
            timer.stop();
            encoderLeft.stop();
            encoderRight.stop();
            jaguarLeft.set(0.0);
            jaguarRight.set(0.0);
            return;
        }
        
        //check for updated values
        kVelocSetpt = SmartDashboard.getDouble("Velocity_Setpoint", 0.0);
        kTolerance = SmartDashboard.getDouble("kTolerance", 0.01);
        double filtWeight = SmartDashboard.getDouble("filtWeight", 0.0);
        kMaxOutput = SmartDashboard.getDouble("kMaxOutput", 0.5);
        kMinOutput = SmartDashboard.getDouble("kMinOutput", 0.05);
        kP = SmartDashboard.getDouble("kP", 0.04);
        kI = SmartDashboard.getDouble("kI", 0.0);
        kD = SmartDashboard.getDouble("kD", 0.001);
        
        //calculate instantaneous velocity
        double currDist = encoderLeft.getRaw() / kGearRatio;
        double currDeltaDist = currDist - prevDist;
        currDeltaDist = (currDeltaDist + filtWeight*prevDeltaDist)/(1.0 + filtWeight);
        filtDist += currDeltaDist;
        double currInstVeloc = currDeltaDist / loopPeriod;
        double error = kVelocSetpt - currInstVeloc;
        totalError += Math.abs(error*loopPeriod);
        if (currInstVeloc > kVelocSetpt - kTolerance && converge == 0.0) {
            converge = timer.get();
        } 
        
        
        //goal distance is our integral
        //don't increase goal distance if kI is unset!
        double totalDistError;
        if (kI == 0.0) {
            totalDistError = 0.0;
        } else {
            goalDist += kVelocSetpt * loopPeriod;
            totalDistError = goalDist - filtDist;
            if (kI * totalDistError > kMaxOutput) {
                totalDistError = kMaxOutput / kI;
            }
        }
       
        //compute the output
        double currWeightedInstVeloc = currInstVeloc/loopPeriod;
        output += kP * error + kI * totalDistError - kD * (currWeightedInstVeloc - prevWeightedInstVeloc);
        
        //limit to one directiion of motion, eliminate deadband
        if (output > kMaxOutput) {
            output = kMaxOutput;
        } else if (output < kMinOutput) {
            output = kMinOutput;
        }
        
        //Gather data for number of oscillations in output and max currInstVeloc
        double currDeriv = currInstVeloc - prevInstVeloc;
        if ((prevDeriv > 0.0 && currDeriv < 0.0) || (prevDeriv < 0.0 && currDeriv > 0.0)) {
            numOscills++;
        }
        if (currInstVeloc > maxVeloc) {
            maxVeloc = currInstVeloc;
        }
        
        //setup for next loop
        prevDist = filtDist;
        prevError = error;
        prevTime = currTime;
        prevDeriv = currDeriv;
        prevInstVeloc = currInstVeloc;
        prevWeightedInstVeloc = currWeightedInstVeloc;
        prevDeltaDist = currDeltaDist;
        
        returnOutput(output);
        
    }// end calculate
    
    public double returnOutput(double output){
        return output;
    }
    
}// end PIDAlgorithm
