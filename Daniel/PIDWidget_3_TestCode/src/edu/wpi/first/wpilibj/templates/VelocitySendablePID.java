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
        m_thread.start();
    }//end VelocitySendablePID

    private void PIDInit() {
        initTable(myTable);
//        myTable.putNumber(dashboardName("hohoho"), 1337);
//        myTable.putNumber(dashboardName("kI"), 0.0);
//        myTable.putNumber(dashboardName("kD"), 0.0);
//        myTable.putNumber(dashboardName("kMaxOutput"), 0.0);
//        myTable.putNumber(dashboardName("kMinOutput"), 0.0);
    }//end PIDInit

    public String getSmartDashboardType(){
        return "Pidget";
    }
    
    public String getName() {
        return m_name;
    }
    
    public void writePrefrences() {
        prefs.putString(m_name + "p", "" + m_base.getP());
        prefs.putString(m_name + "i", "" + m_base.getI());
        prefs.putString(m_name + "d", "" + m_base.getD());
        prefs.putString(m_name + "maxOut", "" + m_base.getMaxOutput());
        prefs.putString(m_name + "minOut", "" + m_base.getMinOutput());
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

//      DEPRECATED -------------------------------------------
//    private ITableListener listener = new ITableListener() {
//        public void valueChanged(ITable table, String key, Object value, boolean isNew) {
//            if (key.equals("p") || key.equals("i") || key.equals("d")) {
//                if (m_base.getP() != Double.parseDouble(table.getString("p")) || m_base.getI() != Double.parseDouble(table.getString("i")) || 
//                        m_base.getD() != Double.parseDouble(table.getString("d"))) {
//                    m_base.setPID(Double.parseDouble(table.getString("p")), Double.parseDouble(table.getString("i")), Double.parseDouble(table.getString("d")));
//                }
//            } else if (key.equals("setpoint")) {
//                if (m_base.getSetpoint() != ((Double) value).doubleValue()) {
//                    setSetpoint(((Double) value).doubleValue());
//                }
//            } else if (key.equals("enabled")) {
//                if (m_base.getEnabled() != ((Boolean) value).booleanValue()) {
//                    if (((Boolean) value).booleanValue()) {
//                        enable();
//                    } else {
//                        disable();
//                    }
//                }
//            }
//        }
//    };
    

    public void initTable(ITable itable) {
//        DEPRECATED --------------------------- Keep this method to implement Sendable
//        if(myTable!=null && myFirst) {
//            myTable.removeTableListener(listener);
//        }
//        if(myTable!=null && myFirst){
//            myTable.putNumber("p", m_base.getP());
//            myTable.putNumber("i", m_base.getI());
//            myTable.putNumber("d", m_base.getD());
//            myTable.putNumber("maxOut", m_base.getMaxOutput());
//            myTable.putNumber("minOut", m_base.getMinOutput());
//            myTable.putNumber("setpoint", m_base.getSetpoint());
//            myTable.putBoolean("enabled", m_base.getEnabled());
//            myTable.addTableListener(listener, false);
//            myFirst = false;
//        }
    }

    public ITable getTable() {
        return myTable;
    }
      
}//end VelocitySendablePID

