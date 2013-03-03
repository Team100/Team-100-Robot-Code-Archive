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
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 *
 * @author Isis
 */
public class VelocitySendablePID implements Sendable {
    private boolean myFirst = true;
    private Preferences prefs = Preferences.getInstance();
    
    private final PIDSource m_source;
    private final PIDSource m_period;
    private final PIDOutput m_output;
    private final TimedThread m_thread;
    private final VelocityPIDBase m_base;
    private final String m_name;
    private static NetworkTable myTable;

    private String dashboardName(String key) {
        return key;// + "_" + m_name;
    }//end dashboardName
    
    private String rawDashboardName(String key) {
        return m_name + "_" + key;
    }//end dashboardName
    
    public VelocitySendablePID(String name, PIDSource source, PIDSource period, PIDOutput output, double distRatio) {
        m_base = new VelocityPIDBase(distRatio, name);
        m_name = name;
        myTable = NetworkTable.getTable(m_name);
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
                myTable.putNumber(dashboardName("Output"), result);
                myTable.putBoolean(dashboardName("Enabled"), m_base.isEnabled());
//                SmartDashboard.putNumber(rawDashboardName("VelocityInput"), input);
//                SmartDashboard.putNumber(rawDashboardName("VelocityResult"), result);
                timer.reset();
                if (m_base.isEnabled()){
                    m_output.pidWrite(result);
                }
            }
        };
        m_thread = new TimedThread(callable);
        m_thread.setPeriod(50);//TODO: add to preferences and widget
        m_thread.start();
        
        SmartDashboard.putData(m_name, this);
    }//end VelocitySendablePID

    private void PIDInit() {
        initTable(myTable);
    }//end PIDInit

    public String getSmartDashboardType(){
        return "Pidget";
    }
    
    public String getName() {
        return m_name;
    }
    
    public void writePreferences() {
        prefs.putString(m_name + "P", "" + m_base.getP());
        prefs.putString(m_name + "I", "" + m_base.getI());
        prefs.putString(m_name + "D", "" + m_base.getD());
        prefs.putString(m_name + "MaxOutput", "" + m_base.getMaxOutput());
        prefs.putString(m_name + "MinOutput", "" + m_base.getMinOutput());
        prefs.save();
    }
    
    public void getValues() {
        myTable = NetworkTable.getTable("SmartDashboard/" + m_name);
        try {
            m_base.setKP(Double.parseDouble(myTable.getString("P")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setKP(myTable.getNumber("P"));
        } catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) { //Catches if the table key isnt defined yet
            myTable.putString("P", prefs.getString(m_name + "P", "0.0")); //TODO: Load from Preferences instead of static variable
        }
        try {
            m_base.setKI(Double.parseDouble(myTable.getString("I")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setKI(myTable.getNumber("I"));
        }  catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("I", prefs.getString(m_name + "I", "0.0"));
        }
        try {
            m_base.setKD(Double.parseDouble(myTable.getString("D")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setKD(myTable.getNumber("D"));
        }  catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("D", prefs.getString(m_name + "D", "0.0"));
        }
        try {
            m_base.setMaxOutput(Double.parseDouble(myTable.getString("MinOutput")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setMaxOutput(myTable.getNumber("MinOutput"));
        }  catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("MinOutput", prefs.getString(m_name + "MinOutput", "0.0"));
        }
        try {
            m_base.setMinOutput(Double.parseDouble(myTable.getString("MinOutput")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setMinOutput(myTable.getNumber("MinOutput"));
        }  catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("MinOutput", prefs.getString(m_name + "MinOutput", "0.0"));
        }
        myTable = NetworkTable.getTable(m_name);
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

    public void updateTable() {
        
    }
    
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

