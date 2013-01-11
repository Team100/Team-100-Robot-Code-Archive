/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Student
 */
public class SendablePID {

    private double m_input = 0.0;
    private double m_kP = 0.0;
    private double m_kI = 0.0;
    private double m_kD = 0.0;
    private double m_velocSetpt = 0.0;
    private double m_filtWeight = 0.0;
    private double m_desPeriod = 0.02;
    private double m_tolerance = 0.01;
    private double m_maxOutput = 1.0;
    private double m_minOutput = 0.05;
    private double output = 0.0;
    TimedThread thread;

    public void PIDInit() {
//        encoderLeft.setReverseDirection(true);
//        encoderLeft.setDistancePerPulse(1.0);
//        encoderRight.setDistancePerPulse(1.0);
        resetInit();
        SmartDashboard.putNumber("Velocity_Setpoint", 0.0);
        SmartDashboard.putNumber("kTolerance", 0.01);
        SmartDashboard.putNumber("kDesPeriod", 0.02);
        SmartDashboard.putNumber("kMaxOutput", 0.5);
        SmartDashboard.putNumber("kMinOutput", 0.05);
        SmartDashboard.putNumber("filtWeight", 0.0);
        SmartDashboard.putNumber("rightOffset", 1.104);
        SmartDashboard.putNumber("kP", 0.03);
        SmartDashboard.putNumber("kI", 0.0);
        SmartDashboard.putNumber("kD", 0.001);
    }//end PIDInit

    public void resetInit() {
    }//end resetInit

    public void getValues() {
        m_kP = SmartDashboard.getNumber("kP", 0.03);
        m_kI = SmartDashboard.getNumber("kI", 0.0);
        m_kD = SmartDashboard.getNumber("kD", 0.001);
        m_velocSetpt = SmartDashboard.getNumber("Velocity_Setpoint", 0.0);
        m_filtWeight = SmartDashboard.getNumber("filtWeight", 0.0);
        m_tolerance = SmartDashboard.getNumber("kTolerance", 0.01);
        m_maxOutput = SmartDashboard.getNumber("kMaxOutput", 1.0);
        m_minOutput = SmartDashboard.getNumber("kminOutput", 0.05);
    }

    public void activatePID(double input) {
        PIDBase base = new PIDBase(m_input, m_kP, m_kI, m_kD);
        thread.run();
    }//end activatePID

    public double returnOutput() {
        output = base.getOutput();
        return output;
    }//end returnOutput

    public double getVelocSetpt(){
         m_velocSetpt = SmartDashboard.getNumber("Velocity_Setpoint", 0.0);
         return m_velocSetpt;
    }//end getVelocSetpt
    
    public double getDesPeriod() {
        m_desPeriod = SmartDashboard.getNumber("kDesPeriod", 0.02);
        return m_desPeriod;
    }//end getMaxDuration

    public double getFiltWeight() {
        m_filtWeight = SmartDashboard.getNumber("filtWeight", 0.0);
        return m_filtWeight;
    }//getRightOffset
}//end SendablePID

