package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Creates a PID loop, has methods to choose setpoints and get the output. 
 * Automatically generates new smart dashboard values and preferences.
 */
public class PID {
    
    private String name = ""; // The loop's unique identifier
    private double kP = 0.0, kI = 0.0, kD = 0.0; // PID constants
    private double setPoint = 0.0; // Target for the PID loop
    private double offset = 0.0; // Offset from input value to the zero value
    private double error = 0.0; // Distance from target value
    private double lastError = 0.0, totalError = 0.0; // Stores previous error
    private double output = 0.0; // Loop output value
    private final Timer timer = new Timer(); // Keeps track of loop frequency
    private boolean enabled = true;
    
    // Instantiates a PID loop, requires a unique name for preferences
    public PID(String name){        
        if(Preferences.containsKey(name+"_kP")){
            kP = Preferences.getDouble(name + "_kP");
            SmartDashboard.putNumber(name+"_kP", kP);
        } else {
            Preferences.setPreference(name+"_kP", 0.0);
            SmartDashboard.putNumber(name+"_kP", 0.0);
        }
        if(Preferences.containsKey(name+"_kI")){
            kP = Preferences.getDouble(name + "_kI");
            SmartDashboard.putNumber(name+"_kI", kI);
        } else {
            Preferences.setPreference(name+"_kI", 0.0);
            SmartDashboard.putNumber(name+"_kI", 0.0);
        }
        if(Preferences.containsKey(name+"_kD")){
            kP = Preferences.getDouble(name + "_kD");
            SmartDashboard.putNumber(name+"_kD", kD);
        } else {
            Preferences.setPreference(name+"_kD", 0.0);
            SmartDashboard.putNumber(name+"_kD", 0.0);
        }
        if(!Preferences.containsKey(name+"SensorRatio")){
            Preferences.setPreference(name+"SensorRatio", 0.0);
        }
        if(!Preferences.containsKey(name+"InitialOffset")){
            Preferences.setPreference(name+"InitialOffset", 0.0);
        }
        
        this.name = name;
        this.offset = Preferences.getDouble(name+ "InitialOffset");
        timer.reset();
        timer.start();
        
        SmartDashboard.putNumber(name+"Output", output);
        SmartDashboard.putNumber(name+"Error", error);
        SmartDashboard.putNumber(name+"SetPoint", setPoint);
    }
    
    // Updates the PID loop using new input data
    public void update(double newValue){
        newValue *= Preferences.getDouble(name + "SensorRatio");
        newValue -= offset;
        lastError = error;
        error = setPoint - newValue;
        totalError += error;
        output = kP*error + kI*totalError + kD*(error-lastError)/timer.get();
        timer.reset();
        timer.start();
        
        kP = SmartDashboard.getNumber(name+"_kP");
        kI = SmartDashboard.getNumber(name+"_kI");
        kD = SmartDashboard.getNumber(name+"_kD");
        Preferences.setPreference(name+"_kP",kP);
        Preferences.setPreference(name+"_kI",kI);
        Preferences.setPreference(name+"_kD",kD);
        
        SmartDashboard.putNumber(name+"Error", error);
        SmartDashboard.putNumber(name+"Output", output);
    }
    
    // Returns the current output value
    public double getOutput(){
        if(enabled){
            return output;
        } else {
            return 0.0;
        }
    }
    
    // Sets the target value of the PID loop
    public void setSetPoint(double newSetPoint){
        setPoint = newSetPoint;
        SmartDashboard.putNumber(name+"SetPoint", setPoint);
    }
    
    // Returns the current target for the PID loop
    public double getSetPoint(){
        return setPoint;
    }
    
    // Sets the current state of the loop to be considered zero, resets setPoint
    public void resetLoop(){
        offset += setPoint-error;
        error = lastError = totalError = 0.0;
        timer.reset();
        timer.start();
        setPoint = 0.0;
        
        SmartDashboard.putNumber(name+"Error", error);
        SmartDashboard.putNumber(name+"SetPoint", setPoint);
    }
    
    // Allows PID loop to output
    public void enable(){
        enabled = true;
    }
    
    // Sets PID output to zero
    public void disable(){
        enabled = false;
    }
}