/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems.PIDBundle;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Isis
 */
public class PositionSendablePID {
    
    private final PIDSource m_source;
    private final PIDOutput m_output;
    private final TimedThread m_thread;
    private final PositionPIDBase m_base;
    private final String m_name;
    private final NetworkTable table;

    private String dashboardName(String key) {
        return key;// + "_" + m_name;
    }//end dashboardName
    
    public PositionSendablePID(String name, PIDSource source, PIDOutput output, double distRatio) {
        m_base = new PositionPIDBase(distRatio, name);
        m_name = name;
        table = NetworkTable.getTable("PIDSystems").getTable(name);
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
                table.putNumber(dashboardName("Output"), result);
                timer.reset();
                if (m_base.isEnabled()) {
                    m_output.pidWrite(result);
                }
            }
        };
        m_thread = new TimedThread(callable);
        m_thread.start();
    }//end SendablePID

    private void PIDInit() {
        table.putNumber(dashboardName("kP"), 0.0);
        
        table.putNumber(dashboardName("kI"), 0.0);
        table.putNumber(dashboardName("kD"), 0.0);
        table.putNumber(dashboardName("kMaxOutput"), 0.0);
        table.putNumber(dashboardName("kMinOutput"), 0.0);
        table.putNumber(dashboardName("kMaxVeloc"), 0.0);
    }//end PIDInit

    public void getValues() {
        m_base.setKP(table.getNumber(dashboardName("kP"), 0.0));
        m_base.setKI(table.getNumber(dashboardName("kI"), 0.0));
        m_base.setKD(table.getNumber(dashboardName("kD"), 0.0));
        m_base.setMaxOutput(table.getNumber(dashboardName("kMaxOutput"), 0.0));
        m_base.setMinOutput(table.getNumber(dashboardName("kMinOutput"), 0.0));
        m_base.setMaxVeloc(table.getNumber(dashboardName("kMaxVeloc"), 0.0));
    }//end getValues
    
    public void setSetpoint(double setpoint) {
        m_base.setSetpoint(setpoint);
    }//end setSetpoint
    
    public void enable(){
        m_base.enable();
    }//end enable
    
    public void disable(){
        m_base.disable();
        m_output.pidWrite(0.0);
    }//end disable
      
}//end SendablePID

