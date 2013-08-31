/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Student
 */
public class Autonomous1 extends CommandBase {
    
    private Timer timer;
    private double delay;
    
    public Autonomous1()
    {
        // Use requires() here to declare subsystem dependencies
        requires(driveTrain);
        
        timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        timer.reset();
        
        timer.start();
        delay = SmartDashboard.getNumber("Wait Time");
        if(delay>15.0)
        {
            delay = 15.0;
        }
        SmartDashboard.putString("Test", "First; True");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        if(timer.get()>delay)
        {
            driveTrain.tankDrive(-1.0, 0.0);
        }
        
        SmartDashboard.putNumber("Timer", timer.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        driveTrain.tankDrive(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        driveTrain.tankDrive(0.0, 0.0);
    }
}
