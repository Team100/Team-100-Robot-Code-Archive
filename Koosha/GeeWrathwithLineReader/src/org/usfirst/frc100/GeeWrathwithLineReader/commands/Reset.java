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
public class Reset extends Command {
    
    public Reset() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.driveTrain.resetEncoder();
        Robot.driveTrain.resetGyro();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
