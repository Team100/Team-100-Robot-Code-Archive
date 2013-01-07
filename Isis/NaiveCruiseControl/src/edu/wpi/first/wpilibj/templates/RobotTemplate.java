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
    //Robot objects
    private final Jaguar jaguarLeft = new Jaguar(3);
    private final Jaguar jaguarRight = new Jaguar(2);
    private final Encoder encoderLeft = new Encoder(7, 6);
    private final Encoder encoderRight = new Encoder(2, 1);
    private final Timer timer = new Timer();
    //Current value variables
    private double output = 0.0; //PWM signal output to Jaguars
    //Previous value variables
    private double prevInstVeloc = 0.0;
    private double prevDeriv = 0.0;
    private double prevDist = 0.0; //previous encoder count
    private double prevTime = 0.0; //in s
    //Quality metrics
    private double numOscills = 0.0; //number of oscillations in velocity
    private double maxVeloc = 0.0; // max recorded inst veloc
    private double totalError = 0.0; //sum of epsilons between currInstVeloc and kVelocSetpt
    private double converge = 0.0; //first time achieve within tolerance
    //Tuneable constants
    private double kVelocSetpt = 0.0; //desired or goal velocity
    private double kTolerance = 0.01; //in velocity, ft/s
    private double kIncrement = 0.005; //in PWM signal to Jaguars
    private double kDesPeriod = 0.02; //desired loop period
    private double kMaxDuration = 10.0;
    //Constants
    private final double kMaxOutput = 1.0;
    private final double kMinOutput = 0.05;
    private final double kGearRatio = 250 * 4 * (27.0 / 13.0) * (0.5 * 3.14159) / 2;
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet

    public void robotInit() {
        encoderLeft.setReverseDirection(true);
        encoderLeft.setDistancePerPulse(1.0);
        encoderRight.setDistancePerPulse(1.0);
        resetInit();
        SmartDashboard.putDouble("Velocity_Setpoint", 0.0);
        SmartDashboard.putDouble("kTolerance", 0.01);
        SmartDashboard.putDouble("kIncrement", 0.005);
        SmartDashboard.putDouble("kDesPeriod", 0.02);
        SmartDashboard.putDouble("kMaxDuration", 10.0);
    }

    public void autonomousInit() {
        resetInit();
    }//end autonomousInit()

    private void resetInit() {
        output = 0.0;
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
        //Quality metric resets
        numOscills = 0.0; 
        maxVeloc = 0.0; 
        totalError = 0.0;
        converge = 0.0;
    }// end resetInit()

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
        kIncrement = SmartDashboard.getDouble("kIncrement", 0.005);

        //calculate instantaneous velocity
        double currDist = encoderRight.getRaw() / kGearRatio;
        double deltaCount = currDist - prevDist;
        double currInstVeloc = deltaCount / loopPeriod;
        totalError += Math.abs((kVelocSetpt - currInstVeloc)*loopPeriod);

        //if velocity is within tolerance of velocity, stay same. Else, increase or decrease speed to reach velocity setpoint
        if (currInstVeloc < kTolerance + kVelocSetpt && currInstVeloc > kVelocSetpt - kTolerance) {
            output = output;
            if (converge == 0.0 && currInstVeloc > kVelocSetpt - kTolerance){
                converge = timer.get();
                SmartDashboard.putDouble("Convergence Time", converge);
            }
        } else if (currInstVeloc < kVelocSetpt) {
            output += kIncrement;
        } else if (currInstVeloc > kVelocSetpt) {
            output -= kIncrement;
        }

        //keep in single direction of motion, eliminate deadband
        if (output > kMaxOutput) {
            output = kMaxOutput;
        } else if (output < kMinOutput) {
            output = kMinOutput;
        }

        //Move!
        if (output > kMinOutput){
            //1.104
            double rightOffset = SmartDashboard.getDouble("rightOffset", 0.0);
            jaguarRight.set(rightOffset*output);
        } else {
            jaguarRight.set(output);
        }
        jaguarLeft.set(output);
        SmartDashboard.putDouble("Output", output);

        //Gather data for number of oscillations in output and max currInstVeloc
        double currDeriv = currInstVeloc - prevInstVeloc;
        if ((prevDeriv > 0.0 && currDeriv < 0.0) || (prevDeriv < 0.0 && currDeriv > 0.0)) {
            numOscills++;
        }
        if (currInstVeloc > maxVeloc) {
            maxVeloc = currInstVeloc;
        }
                
        //print to SmartDashboard
        SmartDashboard.putDouble("Total_error", totalError);
        SmartDashboard.putDouble("Current_Time", currTime);
        SmartDashboard.putDouble("encoderLeft_Raw", encoderLeft.getRaw());
        SmartDashboard.putDouble("encoderRight_Raw", encoderRight.getRaw());
        SmartDashboard.putDouble("Distance", currDist);
        SmartDashboard.putDouble("Actual_VelocSetpt", kVelocSetpt);
        SmartDashboard.putDouble("InstVeloc", currInstVeloc);
        SmartDashboard.putDouble("Max Error", maxVeloc - kVelocSetpt);
        SmartDashboard.putDouble("numOscills", numOscills);

        //print to console
        System.out.println("Total error: " + totalError + ", " + "Loop period: " + loopPeriod);
        System.out.println(kVelocSetpt + ", " + kTolerance + ", " + kIncrement);
        
        //setup for next loop
        prevDeriv = currDeriv;
        prevInstVeloc = currInstVeloc;
        prevTime = currTime;
        prevDist = currDist;
        
    }//end autonomousPeriodic()
}//end RobotTemplate