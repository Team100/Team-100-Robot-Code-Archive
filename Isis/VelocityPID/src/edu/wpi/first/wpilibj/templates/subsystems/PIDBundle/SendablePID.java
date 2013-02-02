/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems.PIDBundle;

import edu.wpi.first.wpilibj.templates.subsystems.PIDBundle.Callable;
import edu.wpi.first.wpilibj.templates.subsystems.PIDBundle.PIDBase;
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
    private final String m_name;

    private String dashboardName(String key) {
        return key + "_" + m_name;
    }//end dashboardName
    
    public SendablePID(String name, PIDSource source, PIDOutput output, double distRatio) {
        m_base = new PIDBase(distRatio, name);
        m_name = name;
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
                m_base.setInput(input);
                getValues();
                double result = m_base.calculate(timer.get());
                SmartDashboard.putNumber(dashboardName("Output"), result);
                timer.reset();
                m_output.pidWrite(result);
            }
        };
        m_thread = new TimedThread(callable);
        m_thread.start();
    }//end SendablePID

    private void PIDInit() {
        SmartDashboard.putNumber(dashboardName("kP"), 0.0);
        SmartDashboard.putNumber(dashboardName("kI"), 0.0);
        SmartDashboard.putNumber(dashboardName("kD"), 0.0);
        SmartDashboard.putNumber(dashboardName("kMaxOutput"), 0.0);
        SmartDashboard.putNumber(dashboardName("kMinOutput"), 0.0);
    }//end PIDInit

    public void getValues() {
        m_base.setKP(SmartDashboard.getNumber(dashboardName("kP"), 0.0)/100.0);
        m_base.setKI(SmartDashboard.getNumber(dashboardName("kI"), 0.0));
        m_base.setKD(SmartDashboard.getNumber(dashboardName("kD"), 0.0)/100.0);
        m_base.setMaxOutput(SmartDashboard.getNumber(dashboardName("kMaxOutput"), 0.0));
        m_base.setMinOutput(SmartDashboard.getNumber(dashboardName("kMinOutput"), 0.0));
    }//end getValues
    
    public void setSetpoint(double setpoint) {
        m_base.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void enable(){
        m_base.enable();
    }//end enable
    
    public void disable(){
        m_base.disable();
    }//end disable
      
}//end SendablePID

