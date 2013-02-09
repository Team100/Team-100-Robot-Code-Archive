/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Sam
 */
public class Memorize extends CommandBase{
    
    public Memorize(){
        requires(autoMemory);
    }

    protected void initialize() {
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
        autoMemory.stopCollection();
        SmartDashboard.putBoolean("Collecting", false);
    }

    protected void interrupted() {
        end();
    }
    
}
