/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.FrisBeast.subsystems.PIDBundle;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import org.usfirst.frc100.FrisBeast.commands.CommandBase;

/**
 *
 * @author Isis
 */
public class VelocitySendablePID implements Sendable {
    private Preferences prefs = Preferences.getInstance();
    
    private final PIDSource m_source;
    private final PIDSource m_period;
    private final PIDOutput m_output;
    private final TimedThread m_thread;
    private final VelocityPIDBase m_base;
    private final String m_name;
    private static NetworkTable myTable;

    private String dashboardName(String key) {
        return m_name + key;
    }//end dashboardName
    
    public VelocitySendablePID(String name, PIDSource source, PIDSource period, PIDOutput output, double distRatio) {
        m_base = new VelocityPIDBase(distRatio, name);
        m_name = name;
        myTable = NetworkTable.getTable("SmartDashboard/" + m_name);
        PIDInit();
        m_source = source;
        m_period = period;
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
                double result = m_base.calculate(timer.get(), m_period.pidGet());
                //The following do not go in the widget table b/c not displayed by widget
                if(CommandBase.isDebugMode()){
                    SmartDashboard.putNumber(dashboardName("Input"), input);
                    SmartDashboard.putNumber(dashboardName("Output"), result);
                    SmartDashboard.putNumber(dashboardName("CounterPeriod"), m_period.pidGet());
                    SmartDashboard.putNumber(dashboardName("TotalError"), m_base.getTotalError());
                    SmartDashboard.putBoolean(dashboardName("Enabled"), m_base.isEnabled());
                }
                timer.reset();
                if (m_base.isEnabled()){
                    m_output.pidWrite(result);
                }
            }
        };
        m_thread = new TimedThread(callable, dashboardName("Period"));
        m_thread.start();
        
        SmartDashboard.putData(m_name, this);
    }//end VelocitySendablePID

    private void PIDInit() {
        
    }//end PIDInit

    public String getSmartDashboardType(){
        return "Pidget";
    }
    
    public String getName() {
        return m_name;
    }
    
    public void writePreferences() {
        prefs.putDouble(m_name + "P", m_base.getP());
        prefs.putDouble(m_name + "I", m_base.getI());
        prefs.putDouble(m_name + "D", m_base.getD());
        prefs.putDouble(m_name + "MinOutput",m_base.getMinOutput());
        prefs.putDouble(m_name + "MaxOutput",m_base.getMaxOutput());
        prefs.save();
    }
    
    private double getValueFromTable(String key) {
        double value;
        try {
            value = Double.parseDouble(myTable.getString(key));
        } catch (java.lang.ClassCastException ex) {
            value = myTable.getNumber(key);
        } catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            //Catches if the table key isn't defined yet
            //Initialize from preferences
            value = prefs.getDouble(dashboardName(key), 0.0);
            myTable.putString(key, Double.toString(value));
        }
        return value;
    }
    
    public void getValues() {
        m_base.setKP(getValueFromTable("P"));
        m_base.setKI(getValueFromTable("I"));
        m_base.setKD(getValueFromTable("D"));
        m_base.setMinOutput(getValueFromTable("MinOutput"));
        m_base.setMaxOutput(getValueFromTable("MaxOutput"));
    }//end getValues
    
    public void setSetpoint(double setpoint) {
        m_base.setSetpoint(setpoint);
    }//end setSetpoint
    
    public boolean isEnabled(){
        return m_base.isEnabled();
    }//end isEnabled
    
    public void enable(){
        m_base.enable();
    }//end enable
    
    public void disable(){
        m_base.disable();
        m_output.pidWrite(0.0);
    }//end disable
    
    public double getP() {
        return m_base.getP();
    }
    
    public double getI() {
        return m_base.getI();
    }
    
    public double getD() {
        return m_base.getD();
    }

    public void startLiveWindowMode() {
        disable();
    }

    public void stopLiveWindowMode() {
    }

    public void initTable(ITable itable) {
        
    }

    public ITable getTable() {
        return myTable;
    }

    
      
}//end VelocitySendablePID

