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
public class TankDrive extends CommandBase{
    
    public TankDrive(){
        requires(driveTrain);
    }

    protected void initialize() {
    }

    protected void execute() {
        driveTrain.drive(oi.getJoy1_y1(), oi.getJoy1_y2());
        SmartDashboard.putNumber("LeftEncoder", oi.getLeftEncoder());
        SmartDashboard.putNumber("RightEncoder", oi.getRightEncoder());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
