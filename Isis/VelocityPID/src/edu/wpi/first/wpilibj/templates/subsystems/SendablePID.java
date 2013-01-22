/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Isis
 */
public class SendablePID {
    
    private final PIDSource m_source;
    private final PIDOutput m_output;
    private final TimedThread m_thread;
    private final PIDBase m_base;

    public SendablePID(PIDSource source, PIDOutput output, double distRatio) {
        m_base = new PIDBase(distRatio);
        PIDInit();
        m_source = source;
        m_output = output;
        Callable callable = new Callable() {
            Timer timer = new Timer();
            public void call() {
                if (timer.get() == 0.0){
                    timer.start();
                    return;
                }
                double input = m_source.pidGet();
                getValues();
                double result = m_base.calculate(timer.get());
                timer.reset();
                m_output.pidWrite(result);
            }
        };
        m_thread = new TimedThread(callable);
        m_thread.run();
    }//end SendablePID

    private void PIDInit() {
        SmartDashboard.putNumber("kP", 0.0);
        SmartDashboard.putNumber("kI", 0.0);
        SmartDashboard.putNumber("kD", 0.0);
        SmartDashboard.putNumber("kMaxOutput", 0.0);
        SmartDashboard.putNumber("kMinOutput", 0.0);
    }//end PIDInit

    public void getValues() {
        m_base.setKP(SmartDashboard.getNumber("kP", 0.0));
        m_base.setKI(SmartDashboard.getNumber("kI", 0.0));
        m_base.setKD(SmartDashboard.getNumber("kD", 0.0));
        m_base.setMaxOutput(SmartDashboard.getNumber("kMaxOutput", 0.0));
        m_base.setMinOutput(SmartDashboard.getNumber("kMinOutput", 0.0));
        System.out.println(SmartDashboard.getNumber("kP", 0.0) + " " + SmartDashboard.getNumber("kI", 0.0) + " " + SmartDashboard.getNumber("kD", 0.0));
        System.out.println(SmartDashboard.getNumber("kMaxOutput", 0.0) + " " + SmartDashboard.getNumber("kMinOutput", 0.0));
    }//end getValues
    
    public void setSetpoint(double setpoint) {
        m_base.setSetpoint(setpoint);
    }//end setSetpoint
      
}//end SendablePID

