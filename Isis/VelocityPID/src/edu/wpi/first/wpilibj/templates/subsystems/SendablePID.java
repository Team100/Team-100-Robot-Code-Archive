/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Isis
 */
public class SendablePID {

    PIDSource source;
    PIDOutput output;
    TimedThread thread;
    PIDBase base;

    public SendablePID() {
        PIDInit();
        Callable callable = new Callable() {
            public void call() {
                double input = source.pidGet();
                setInput(input);
                getValues();
                double result = base.calculate();
                output.pidWrite(result);
            }
        };
        thread = new TimedThread(callable);
        thread.run();
    }//end SendablePID

    private void PIDInit() {
        SmartDashboard.putNumber("kP", 0.0);
        SmartDashboard.putNumber("kI", 0.0);
        SmartDashboard.putNumber("kD", 0.0);
        SmartDashboard.putNumber("filtWeight", 0.0);
        SmartDashboard.putNumber("kTolerance", 0.0);
        SmartDashboard.putNumber("Period", 0.02);
        SmartDashboard.putNumber("kMaxOutput", 0.0);
        SmartDashboard.putNumber("kMinOutput", 0.0);
    }//end PIDInit

    private void setInput(double input){
        base.setInput(input);
    }//end setInput
    
    public void setDistRatio(double distRatio) {
        base.setDistRatio(distRatio);
    }//end setDistRatio

    public void setSetpoint(double setpoint) {
        base.setSetpoint(setpoint);
    }//end setSetpoint

    public void getValues() {
        base.setKP(SmartDashboard.getNumber("kP", 0.0));
        base.setKI(SmartDashboard.getNumber("kI", 0.0));
        base.setKD(SmartDashboard.getNumber("kD", 0.0));
        base.setFiltWeight(SmartDashboard.getNumber("filtWeight", 0.0));
        base.setPeriod(checkPeriod(SmartDashboard.getNumber("Period", 0.02)));
        base.setTolerance(SmartDashboard.getNumber("kTolerance", 0.0));
        base.setMaxOutput(SmartDashboard.getNumber("kMaxOutput", 0.0));
        base.setMinOutput(SmartDashboard.getNumber("kminOutput", 0.0));
    }//end getValues
    
    private double checkPeriod(double period){
        if (period == 0.0){
            return 0.02;
        }
        else{
            return period;
        }
    }//end checkPeriod
    
}//end SendablePID

