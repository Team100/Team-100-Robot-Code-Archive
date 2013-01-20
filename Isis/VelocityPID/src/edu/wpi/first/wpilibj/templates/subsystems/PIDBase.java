/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

/**
 *
 * @author Student
 */
public class PIDBase implements Calculate {
    GetValues getValues;
    //Previous value variables
    private double input = 0.0;
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
    //Tuneables
    private double kTolerance = 0.01; //in velocity, ft/s
    private double kDesPeriod = 0.02; //desired loop period
    private double kVelocSetpt = 0.0; //desired or goal velocity
    private double kMaxDuration = 10.0; 
    private double kFiltWeight = 0.0;
    private double kMaxOutput = 0.5;
    private double kMinOutput = 0.05;
    private double kP = 0.04; 
    private double kI = 0.0; 
    private double kD = 0.001; 
    //Constants
    private final double kGearRatio = 250 * 4 * (27.0 / 13.0) * (0.5 * 3.14159) / 2;
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet    
    
    public void calculate(){
       //check for updated values
       setValues();
        
        //calculate instantaneous velocity
        double currDist = input / kGearRatio;
        double currDeltaDist = currDist - prevDist;
        currDeltaDist = (currDeltaDist + kFiltWeight*prevDeltaDist)/(1.0 + kFiltWeight);
        filtDist += currDeltaDist;
        double currInstVeloc = currDeltaDist / loopPeriod;
        double error = kVelocSetpt - currInstVeloc;
        totalError += Math.abs(error*loopPeriod);
        
        
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
        prevDeriv = currDeriv;
        prevInstVeloc = currInstVeloc;
        prevWeightedInstVeloc = currWeightedInstVeloc;
        prevDeltaDist = currDeltaDist;        
    }// end calculate
    
    public double writePIDOutput(){
        return output;
    }// end writePIDOutput
    
    private void setValues(){
        setKP();
        setKI();
        setKD();
        setFiltWeight();
        setVelocSetpt();
        setTolerance();
        setDesPeriod();
    }//end setValues
    
    private void setKP(){
        kP = getValues.getKP();
    }//end setKP
    
    private void setKI(){
        kI = getValues.getKI();
    }//end setKI
    
    private void setKD(){
        kD = getValues.getKD();
    }//end setKD
    
    private void setFiltWeight(){
        kFiltWeight = getValues.getFiltWeight();
    }//setFiltWeight
    
    private void setVelocSetpt(){
        kVelocSetpt = getValues.getVelocSetpt();
    }//end setVelocSetpt
    
    private void setTolerance(){
        kTolerance = getValues.getTolerance();
    }//end setTolerance
    
    private void setDesPeriod(){
        kDesPeriod = getValues.getDesPeriod();
    }//end setTolerance
    
}// end PIDBase
