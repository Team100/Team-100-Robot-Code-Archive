/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.Vector;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.Connector;


/**
 *
 * @author Sam
 */
public class AutoMemory extends Subsystem{
    
    Vector LeftMemory;
    Vector RightMemory;
    
    
    public AutoMemory(){
    }

    public void beginCollection(){
        LeftMemory = new Vector();
        RightMemory = new Vector();
    }
    
    public void collect(double left, double right){
        LeftMemory.addElement(Double.valueOf(left));
        RightMemory.addElement(Double.valueOf(right));
    }
    
    public void stopCollection(){
    }
    
    public Vector RequestLeft(){
        return LeftMemory;
    }
    
    public Vector RequestRight(){
        return RightMemory;
    }
    
    public void ReadMemory(int index){
        //read from a file and set the vectors equal to the info
    }
    
    public void Reproduce(double leftTarget, double rightTarget){
        DriveTrain.reproleft = leftTarget;
        DriveTrain.reproright = rightTarget;
        SmartDashboard.putNumber("Left Input", leftTarget);
        SmartDashboard.putNumber("Right Input", rightTarget);
    }
    
    protected void initDefaultCommand() {
    }
    
    private void write(String FILE_NAME){
        FileConnection file = null;
        try {
                file = (FileConnection) Connector.open(FILE_NAME, Connector.WRITE);

                file.create();

                OutputStream output = file.openOutputStream();
                
                //output.write(bytes);
        } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (file != null) {
                    try {
                        file.close();
                    } catch (IOException ex) {
                    }
                }
        }
    
    }
    
    
    
    
    
    
    
    
    
}
