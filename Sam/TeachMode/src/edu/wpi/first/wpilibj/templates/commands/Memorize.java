/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.IOException;

/**
 *
 * @author Sam
 */
public class Memorize extends CommandBase{
    
    public Memorize(){
        requires(autoMemory);
    }

    protected void initialize() {
        System.out.println("Beginning Collection");
        autoMemory.beginCollection();
        SmartDashboard.putBoolean("Collecting", true);
    }

    protected void execute() {
        //autoMemory.collect(oi.getJoy1_y1(), oi.getJoy1_y2());
        autoMemory.collectString(oi.getJoy1_y1(), oi.getJoy1_y2());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        try {
            autoMemory.stopCollection();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        SmartDashboard.putBoolean("Collecting", false);
    }

    protected void interrupted() {
        end();
    }
    
}
