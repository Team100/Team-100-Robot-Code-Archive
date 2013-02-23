/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

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
        return m_name + key;// + m_name;
    }//end dashboardName
    
    private String rawDashboardName(String key) {
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
//        table = NetworkTable.getTable("PIDSystems/" + name);
        myTable = NetworkTable.getTable(m_name);
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
                myTable.putNumber(dashboardName("Output"), result);
                myTable.putBoolean(dashboardName("Enabled"), m_base.isEnabled());
//                SmartDashboard.putNumber(rawDashboardName("PositionInput"), input);
//                SmartDashboard.putNumber(rawDashboardName("PositionResult"), result);
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
        
//        table.putNumber(dashboardName("kP"), 0.0);
//        
//        table.putNumber(dashboardName("kI"), 0.0);
//        table.putNumber(dashboardName("kD"), 0.0);
//        table.putNumber(dashboardName("kMaxOutput"), 0.0);
//        table.putNumber(dashboardName("kMinOutput"), 0.0);
//        table.putNumber(dashboardName("kMaxVeloc"), 0.0);
    }//end PIDInit

    public void writePreferences() {
        prefs.putString(m_name + "p", "" + m_base.getP());
        prefs.putString(m_name + "i", "" + m_base.getI());
        prefs.putString(m_name + "d", "" + m_base.getD());
        prefs.putString(m_name + "maxOut", "" + m_base.getMaxOut());
        prefs.putString(m_name + "minOut", "" + m_base.getMinOut());
        prefs.putString(m_name + "maxVelocity", "" + m_base.getMaxVelocity());
        prefs.save();
    }
    
    public void getValues() {
       myTable = NetworkTable.getTable("SmartDashboard/" + m_name);
        try {
            m_base.setKP(Double.parseDouble(myTable.getString("p")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setKP(myTable.getNumber("p"));
        } catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) { //Catches if the table key isnt defined yet
            myTable.putString("p", prefs.getString(m_name + "p", "0.0")); //TODO: Load from Preferences instead of static variable
        }
        try {
            m_base.setKI(Double.parseDouble(myTable.getString("i")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setKI(myTable.getNumber("i"));
        }  catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("i", prefs.getString(m_name + "i", "0.0"));
        }
        try {
            m_base.setKD(Double.parseDouble(myTable.getString("d")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setKD(myTable.getNumber("d"));
        }  catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("d", prefs.getString(m_name + "d", "0.0"));
        }
        try {
            m_base.setMaxOutput(Double.parseDouble(myTable.getString("maxOut")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setMaxOutput(myTable.getNumber("maxOut"));
        }  catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("maxOut", prefs.getString(m_name + "maxOut", "0.0"));
        }
        try {
            m_base.setMinOutput(Double.parseDouble(myTable.getString("minOut")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setMinOutput(myTable.getNumber("minOut"));
        }  catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("minOut", prefs.getString(m_name + "minOut", "0.0"));
        }
        try { //Extra read for Max Velocity specific to position PID
            m_base.setMaxVeloc(Double.parseDouble(myTable.getString("maxVelocity")));
        } catch (java.lang.ClassCastException ex) {
            m_base.setMaxVeloc(myTable.getNumber("maxVelocity"));
        } catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            myTable.putString("maxVelocity", prefs.getString(m_name + "maxVelocity", "0.0"));
        }
        myTable = NetworkTable.getTable(m_name);
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

