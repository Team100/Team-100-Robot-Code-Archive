/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    //Objects from robot
    private final Jaguar jaguarLeft = new Jaguar(3);
    private final Jaguar jaguarRight = new Jaguar(2);
    private final Encoder encoderLeft = new Encoder(7, 6);
    private final Encoder encoderRight = new Encoder(2, 1);
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
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        encoderLeft.setReverseDirection(true);
        encoderLeft.setDistancePerPulse(1.0);
        encoderRight.setDistancePerPulse(1.0);
        resetInit();
        SmartDashboard.putNumber("Velocity_Setpoint", 0.0);
        SmartDashboard.putNumber("kTolerance", 0.01);
        SmartDashboard.putNumber("kDesPeriod", 0.02);
        SmartDashboard.putNumber("kMaxDuration", 10.0);
        SmartDashboard.putNumber("kMaxOutput", 0.5);
        SmartDashboard.putNumber("kMinOutput", 0.05);
        SmartDashboard.putNumber("filtWeight", 0.0);
        SmartDashboard.putNumber("rightOffset", 0.0);
        SmartDashboard.putNumber("kP", 0.04);
        SmartDashboard.putNumber("kI",0.0);
        SmartDashboard.putNumber("kD",0.001);
    }//end robotInit()

    public void autonomousInit() {
        resetInit();
    }//end autonomousInit()

    private void resetInit() {
        //Robot object resets
        jaguarLeft.set(0.0);
        jaguarRight.set(0.0);
        encoderLeft.reset();
        encoderRight.reset();
        encoderLeft.start();
        encoderRight.start();
        timer.reset();
        timer.start();
        //Previous variable resets
        prevInstVeloc = 0.0;
        prevDeriv = 0.0;
        prevDist = 0.0; 
        prevTime = 0.0; 
        prevError = 0.0;
        prevWeightedInstVeloc = 0.0;
        prevDeltaDist = 0.0;
        goalDist = 0.0;
        filtDist = 0.0;
        output = 0.0;
        //Quality metric resets
        numOscills = 0.0; 
        maxVeloc = 0.0; 
        totalError = 0.0;
        converge = 0.0;
    }// end resetInit()

    /**
     * This function is called as often as possible during autonomous
     */
    public void autonomousPeriodic() {
        //check if enough time has elapsed
        kDesPeriod = SmartDashboard.getNumber("kDesPeriod", kDesPeriod);
        kMaxDuration = SmartDashboard.getNumber("kMaxDuration", kMaxDuration);
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
        kVelocSetpt = SmartDashboard.getNumber("Velocity_Setpoint", 0.0);
        kTolerance = SmartDashboard.getNumber("kTolerance", 0.01);
        double filtWeight = SmartDashboard.getNumber("filtWeight", 0.0);
        kMaxOutput = SmartDashboard.getNumber("kMaxOutput", 0.5);
        kMinOutput = SmartDashboard.getNumber("kMinOutput", 0.05);
        kP = SmartDashboard.getNumber("kP", 0.04);
        kI = SmartDashboard.getNumber("kI", 0.0);
        kD = SmartDashboard.getNumber("kD", 0.001);
        
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
            SmartDashboard.putNumber("Convergence Time", converge);
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
             
        //Move!
        if (output > kMinOutput){
            //1.104
            double rightOffset = SmartDashboard.getNumber("rightOffset", 0.0);
            jaguarRight.set(rightOffset*output);
        } else {
            jaguarRight.set(output);
        }
        jaguarLeft.set(output);
        
        //print to SmartDashboard
        SmartDashboard.putNumber("Total_error", totalError);
        SmartDashboard.putNumber("encoderLeft_Raw", encoderLeft.getRaw());
        SmartDashboard.putNumber("encoderRight_Raw", encoderRight.getRaw());
        SmartDashboard.putNumber("Distance", filtDist);
//        SmartDashboard.putNumber("Actual_VelocSetpt", kVelocSetpt);
        SmartDashboard.putNumber("InstVeloc", currInstVeloc);
        SmartDashboard.putNumber("loopPeriod", loopPeriod);
        SmartDashboard.putNumber("goalDist", goalDist);
        SmartDashboard.putNumber("totalDistError", totalDistError);
        SmartDashboard.putNumber("numOscills", numOscills);
        SmartDashboard.putNumber("Error", error);
        SmartDashboard.putNumber("Output", output);
        SmartDashboard.putNumber("Max Error", maxVeloc - kVelocSetpt);
    
        //print to console
        System.out.println(kVelocSetpt + ", " + kTolerance + ", " + kDesPeriod);
        System.out.println(kP + ", " + kI + ", " + kD);
        
        //setup for next loop
        prevDist = filtDist;
        prevError = error;
        prevTime = currTime;
        prevDeriv = currDeriv;
        prevInstVeloc = currInstVeloc;
        prevWeightedInstVeloc = currWeightedInstVeloc;
        prevDeltaDist = currDeltaDist;
        
    }//end autonomousPeriodic()

}//end robotTemplate()
