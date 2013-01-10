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
    private double m_rightOffset = 0.0;
    private static double m_filtWeight = 0.0;
    
    public void SendablePID(double input){
        m_input = input;
    }//end SendablePID
    
    public void PIDInit(){
//        encoderLeft.setReverseDirection(true);
//        encoderLeft.setDistancePerPulse(1.0);
//        encoderRight.setDistancePerPulse(1.0);
        resetInit();
        SmartDashboard.putNumber("Velocity_Setpoint", 0.0);
        SmartDashboard.putNumber("kTolerance", 0.01);
        SmartDashboard.putNumber("kDesPeriod", 0.02);
        SmartDashboard.putNumber("kMaxDuration", 10.0);
        SmartDashboard.putNumber("kMaxOutput", 0.5);
        SmartDashboard.putNumber("kMinOutput", 0.05);
        SmartDashboard.putNumber("filtWeight", 0.0);
        SmartDashboard.putNumber("rightOffset", 1.104);
        SmartDashboard.putNumber("kP", 0.03);
        SmartDashboard.putNumber("kI",0.0);
        SmartDashboard.putNumber("kD",0.001);
    }//end PIDInit
    
    public void resetInit(){
        
    }//end resetInit
    
    public void getValues(){
        m_kP = SmartDashboard.getNumber("kP", 0.03);
        m_kI = SmartDashboard.getNumber("kI", 0.0);
        m_kD = SmartDashboard.getNumber("kD", 0.001);
        m_rightOffset = SmartDashboard.getNumber("rightOffset", 1.104);
        m_filtWeight = SmartDashboard.getNumber("filtWeight", 0.0);
    }
    
    public void activatePID(){
        PIDBase base = new PIDBase(m_input, m_kP, m_kI, m_kD);
    }//end activatePID
    
    public double getRightOffset(){
        return m_rightOffset;
    }//getRightOffset
    
    public static double getFiltWeight(){
        return m_filtWeight;
    }//getRightOffset
    
}//end SendablePID
