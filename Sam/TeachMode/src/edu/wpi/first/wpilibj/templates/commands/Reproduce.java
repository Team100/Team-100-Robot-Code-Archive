/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.subsystems.DriveTrain;
import java.util.Vector;


/**
 *
 * @author Sam
 */
public class Reproduce extends CommandBase{
    
    int index;
    int position;
    Vector memory;
    Vector leftVector;
    Vector rightVector;
    
    
    public Reproduce(){
        requires(autoMemory);
    }

    protected void initialize() {

        String file;
        file = "file:///autonomous/" + SmartDashboard.getString("Select Autonomous Procedure") + ".sam";
        System.out.println("Reproduce Init :" + file);
        
        autoMemory.read(file);//READ TEST FILE
        leftVector = autoMemory.RequestLeft();
        rightVector = autoMemory.RequestRight();
        position = 0;
        DriveTrain.reproducing = true;
        SmartDashboard.putBoolean("Reproducing", true);
        this.setInterruptible(true);
    }

    protected void execute() {
        if(position > leftVector.size()-2){
            this.cancel();
        }
        Double left = (Double) leftVector.elementAt(position);
        Double right = (Double) rightVector.elementAt(position);
        autoMemory.Reproduce(left.doubleValue(), right.doubleValue());
        position++;
             
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        autoMemory.Reproduce(0,0);
        DriveTrain.reproleft = 0;
        DriveTrain.reproright = 0;
        DriveTrain.reproducing = false;
        position = 0;
        SmartDashboard.putBoolean("Reproducing", false);
    }

    protected void interrupted() {
        end();
    }
    
}
