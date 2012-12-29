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

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private final Jaguar jaguarLeft = new Jaguar(3);
    private final Jaguar jaguarRight = new Jaguar(2);
    private final Encoder encoderLeft = new Encoder(7, 6);
    private final Encoder encoderRight = new Encoder(2, 1);
    private final Timer timer = new Timer();
    private double currInstVeloc = 0.0; //instantaneous velocity
    private double velocSetpt = 0.0; //desired or goal velocity
    private double output = 0.0; //PWM signal output to Jaguars
    private double prevInstVeloc = 0.0;
    private double prevDeriv = 0.0;
    private double currDeriv = 0.0;
    private double numOscills = 0.0; //number of oscillations in velocity
    private double maxVeloc = 0.0; // max recorded inst veloc
    private double distance = 0.0; //average between raw counts of both encoders
    private double prevCount = 0.0; //previous encoder count
    private double currCount = 0.0; //current encoder count
    private double totalError = 0.0; //sum of epsilons between currInstVeloc and velocSetpt
    private double loopPeriod = 0.0; //in ms
    private double prevTime = 0.0; //in ms
    private double currTime = 0.0;//in ms
    private double kTolerance = 0.05; //in velocity, ft/s
    private double kIncrement = 0.05; //in PWM signal to Jaguars
    private double kGearRatio = 250 * 4 * (27.0 / 13.0) * (0.5 * 3.14159) / 2;
    //encoder ticks*(quadrature)*gearRatio*circumference*conversion to feet
    private double kDesPeriod = 1.0; //desired loop period

    public void robotInit() {
        resetInit();
        SmartDashboard.putDouble("Velocity_Setpoint", 0.0);
        SmartDashboard.putDouble("kTolerance", 0.05);
        SmartDashboard.putDouble("kIncrement", 0.05);
        SmartDashboard.putDouble("kDesPeriod", 1.0);
        encoderLeft.setReverseDirection(true);
        encoderLeft.setDistancePerPulse(1.0);
        encoderRight.setDistancePerPulse(1.0);
        encoderLeft.reset();
        encoderRight.reset();
        encoderLeft.start();
        encoderRight.start();
        timer.reset();
        timer.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousInit() {
        resetInit();
    }//end autonomousInit()

    private void resetInit() {
        currInstVeloc = 0.0;
        velocSetpt = 0.0;
        output = 0.0;
        distance = 0.0;
        prevCount = 0.0;
        currCount = 0.0;
        totalError = 0.0;
        loopPeriod = 0.0;
        prevTime = 0.0;
        currTime = 0.0;
    }// end resetInit()

    public void autonomousContinuous() {
        //check if enough time has elapsed
        kDesPeriod = SmartDashboard.getDouble("kDesPeriod", kDesPeriod);
        currTime = timer.get();
        loopPeriod = currTime - prevTime;
        if (loopPeriod < kDesPeriod) {
            return;
        }

        //check for updated values
        velocSetpt = SmartDashboard.getDouble("Velocity_Setpoint", 0.5);
        kTolerance = SmartDashboard.getDouble("kTolerance", 0.05);
        kIncrement = SmartDashboard.getDouble("kIncrement", 0.05);

        //calculate instantaneous velocity
        distance = encoderRight.getRaw() / kGearRatio;
        currCount = distance;
        double deltaCount = currCount - prevCount;
        currInstVeloc = deltaCount / loopPeriod;
        totalError += Math.abs(velocSetpt - currInstVeloc);

        //print to SmartDashboard
        SmartDashboard.putDouble("Total_error", totalError);
        SmartDashboard.putDouble("Current_Time", currTime);
        SmartDashboard.putDouble("encoderLeft_Raw", encoderLeft.getRaw());
        SmartDashboard.putDouble("encoderRight_Raw", encoderRight.getRaw());
        SmartDashboard.putDouble("Distance", distance);
        SmartDashboard.putDouble("Actual_VelocSetpt", velocSetpt);
        SmartDashboard.putDouble("InstVeloc", currInstVeloc);

        //print to console
        System.out.println("Total error: " + totalError + ", " + "Loop period: " + loopPeriod);
        System.out.println(velocSetpt + ", " + kTolerance + ", " + kIncrement);

        //if velocity is within tolerance of velocity, stay same. Else, increase or decrease speed to reach velocity setpoint
        if (currInstVeloc < kTolerance + velocSetpt && currInstVeloc > velocSetpt - kTolerance) {
            output = output;
        } else if (currInstVeloc < velocSetpt) {
            output += kIncrement;
        } else if (currInstVeloc > velocSetpt) {
            output -= kIncrement;
        }

        //keep in single direction of motion, eliminate deadband
        if (output > 1.0) {
            output = 1.0;
        } else if (output < 0.05) {
            output = 0.05;
        }

        //Move!
        jaguarLeft.set(output);
        jaguarRight.set(output);
        SmartDashboard.putDouble("Output", output);

        //Gather data for number of oscillations in output and max currInstVeloc
        currDeriv = currInstVeloc - prevInstVeloc;
        if ((prevDeriv > 0.0 && currDeriv < 0.0) || (prevDeriv < 0.0 && currDeriv > 0.0)) {
            numOscills++;
        }
        if (currInstVeloc > prevInstVeloc && currInstVeloc > maxVeloc) {
            maxVeloc = currInstVeloc;
        }
        SmartDashboard.putDouble("numOscills", numOscills);
        SmartDashboard.putDouble("Diff_btwn_actual_&_goal_veloc", maxVeloc - velocSetpt);

        //setup for next loop
        prevDeriv = currDeriv;
        prevInstVeloc = currInstVeloc;
        prevTime = currTime;
        prevCount = currCount;
        
    }//end autonomousPeriodic()
}//end RobotTemplate