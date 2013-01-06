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
        SmartDashboard.putDouble("Velocity_Setpoint", 0.0);
        SmartDashboard.putDouble("kTolerance", 0.01);
        SmartDashboard.putDouble("kDesPeriod", 0.02);
        SmartDashboard.putDouble("kMaxDuration", 10.0);
        SmartDashboard.putDouble("kMaxOutput", 0.5);
        SmartDashboard.putDouble("kMinOutput", 0.05);
        SmartDashboard.putDouble("filtWeight", 0.0);
        SmartDashboard.putDouble("kP", 0.04);
        SmartDashboard.putDouble("kI",0.0);
        SmartDashboard.putDouble("kD",0.001);
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
    public void autonomousContinuous() {
        //check if enough time has elapsed
        kDesPeriod = SmartDashboard.getDouble("kDesPeriod", kDesPeriod);
        kMaxDuration = SmartDashboard.getDouble("kMaxDuration", kMaxDuration);
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
        double currDist = encoderRight.getRaw() / kGearRatio;
        double currDeltaDist = currDist - prevDist;
        currDeltaDist = (currDeltaDist + filtWeight*prevDeltaDist)/(1.0 + filtWeight);
        filtDist += currDeltaDist;
        double currInstVeloc = currDeltaDist / loopPeriod;
        double error = kVelocSetpt - currInstVeloc;
        totalError += Math.abs(error*loopPeriod);
        if (currInstVeloc > kVelocSetpt - kTolerance && converge == 0.0) {
            converge = timer.get();
            SmartDashboard.putDouble("Convergence Time", converge);
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
        jaguarLeft.set(output);
        jaguarRight.set(output);
        
        //print to SmartDashboard
        SmartDashboard.putDouble("Total_error", totalError);
//       SmartDashboard.putDouble("encoderLeft_Raw", encoderLeft.getRaw());
//        SmartDashboard.putDouble("encoderRight_Raw", encoderRight.getRaw());
        SmartDashboard.putDouble("Distance", filtDist);
//        SmartDashboard.putDouble("Actual_VelocSetpt", kVelocSetpt);
        SmartDashboard.putDouble("InstVeloc", currInstVeloc);
        SmartDashboard.putDouble("loopPeriod", loopPeriod);
        SmartDashboard.putDouble("goalDist", goalDist);
        SmartDashboard.putDouble("totalDistError", totalDistError);
        SmartDashboard.putDouble("numOscills", numOscills);
        SmartDashboard.putDouble("Error", error);
        SmartDashboard.putDouble("Output", output);
        SmartDashboard.putDouble("Max Error", maxVeloc - kVelocSetpt);
    
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
        
    }//end autonomousContinuous()

}//end robotTemplate()
