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
public class SendablePID implements Callable, GetValues {

    private double m_kP = 0.0;
    private double m_kI = 0.0;
    private double m_kD = 0.0;
    private double m_velocSetpt = 0.0;
    private double m_filtWeight = 0.0;
    private double m_desPeriod = 0.02;
    private double m_tolerance = 0.01;
    private double m_maxOutput = 1.0;
    private double m_minOutput = 0.05;
    private int m_period_ms = 1;
    private double input = 0.0;
    private double output = 0.0;
    TimedThread thread;
    PIDInput source;
    PIDOutput write;
    Callable callable;
    GetValues obtain;
    Calculate calculate;
    

    public void PIDInit() {
//        encoderLeft.setReverseDirection(true);
//        encoderLeft.setDistancePerPulse(1.0);
//        encoderRight.setDistancePerPulse(1.0);
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

    public void call(){
        thread = new TimedThread(callable);
        thread.run();
        input = source.sendPIDInput();
        getValues();
        calculate.calculate();
        output = write.writePIDOutput();
    }//end call
    
    
    //fill variables for GetValues
    public void getValues() {
        m_kP = SmartDashboard.getNumber("kP", 0.03);
        m_kI = SmartDashboard.getNumber("kI", 0.0);
        m_kD = SmartDashboard.getNumber("kD", 0.001);
        m_velocSetpt = SmartDashboard.getNumber("Velocity_Setpoint", 0.0);
        m_filtWeight = SmartDashboard.getNumber("filtWeight", 0.0);
        m_desPeriod = SmartDashboard.getNumber("kDesPeriod", 0.02);
        m_tolerance = SmartDashboard.getNumber("kTolerance", 0.01);
        m_maxOutput = SmartDashboard.getNumber("kMaxOutput", 1.0);
        m_minOutput = SmartDashboard.getNumber("kminOutput", 0.05);
    }

    //GetValues interface functions
    public double getKP(){
        return m_kP;
    }//end getKP
    
    public double getKI(){
        return m_kI;
    }//end getKI
    
    public double getKD(){
        return m_kD;
    }//end getKD
    
    public double getVelocSetpt(){
         return m_velocSetpt;
    }//end getVelocSetpt
    
    public double getDesPeriod() {
        return m_desPeriod;
    }//end getMaxDuration

    public double getFiltWeight() {
        return m_filtWeight;
    }//end getFiltWeight
    
    public double getTolerance(){
        return m_tolerance;
    }//end getTolerance
    
    public double getMaxOutput(){
        return m_maxOutput;
    }//end getMaxOutput
    
    public double getMinOutput(){
        return m_minOutput;
    }//end getMinOutput
    
}//end SendablePID

