/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.GeeWrathwithLineReader.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc100.GeeWrathwithLineReader.Robot;

/**
 *
 * @author Student
 */
public class Turn extends Command {
    private double angle;
    private boolean isFin;
    
    public Turn(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.driveTrain);
        
        this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        isFin = false;
        Robot.driveTrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        isFin = Robot.driveTrain.autoTurnByAngle(angle);
        Robot.driveTrain.updateDashboard();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFin;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
