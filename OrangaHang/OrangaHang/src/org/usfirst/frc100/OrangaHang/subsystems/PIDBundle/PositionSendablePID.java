/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.subsystems.PIDBundle;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import org.usfirst.frc100.OrangaHang.commands.CommandBase;

/**
 *
 * @author Isis
 */
public class PositionSendablePID implements Sendable{
    private Preferences prefs = Preferences.getInstance();
    
    private final PIDSource m_source;
    private final PIDOutput m_output;
    private final TimedThread m_thread;
    private final PositionPIDBase m_base;
    private final String m_name;
    private static NetworkTable myTable;

    private String dashboardName(String key) {
        return m_name + key;
    }//end dashboardName

    public String getName() {
        return m_name;
    }
    
    public double getP() {
        return m_base.getP();
    }
    
    public PositionSendablePID(String name, PIDSource source, PIDOutput output, double distRatio) {
        m_base = new PositionPIDBase(distRatio, name);
        m_name = name;
        myTable = NetworkTable.getTable("SmartDashboard/" + m_name);
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
                //The following do not go in the widget table b/c not displayed by widget
                if(CommandBase.isDebugMode()){
                    SmartDashboard.putNumber(dashboardName("Input"), input);
                    SmartDashboard.putNumber(dashboardName("Output"), result);
                    SmartDashboard.putBoolean(dashboardName("Enabled"), m_base.isEnabled());
                }
                timer.reset();
                if (m_base.isEnabled()) {
                    m_output.pidWrite(result);
                }
            }
        };
        m_thread = new TimedThread(callable, dashboardName("Period"));
        m_thread.start();
        
        SmartDashboard.putData(m_name, this);
    }//end SendablePID

    private void PIDInit() {
        
    }//end PIDInit

    public void writePreferences() {
        prefs.putDouble(m_name + "P", m_base.getP());
        prefs.putDouble(m_name + "I", m_base.getI());
        prefs.putDouble(m_name + "D", m_base.getD());
        prefs.putDouble(m_name + "MinOutput",m_base.getMinOutput());
        prefs.putDouble(m_name + "MaxOutput",m_base.getMaxOutput());
        prefs.putDouble(m_name + "MaxVelocity", m_base.getMaxVelocity());
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
        m_base.setMaxVeloc(getValueFromTable("MaxVelocity"));
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

    public void initTable(ITable itable) {
        //Nothing
    }

    public ITable getTable() {
        return myTable;
    }

    public String getSmartDashboardType() {
        return "Pidget";
    }
      
}//end SendablePID

