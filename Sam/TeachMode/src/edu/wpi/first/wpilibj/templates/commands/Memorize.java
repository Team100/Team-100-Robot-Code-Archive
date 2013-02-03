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
        autoMemory.collect(oi.getLeftEncoder(), oi.getRightEncoder());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        autoMemory.stopCollection();
        SmartDashboard.putBoolean("Collecting", false);
    }

    protected void interrupted() {
        autoMemory.stopCollection();
        SmartDashboard.putBoolean("Collecting", false);
    }
    
}
