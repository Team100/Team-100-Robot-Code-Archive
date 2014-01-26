// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc100.GeeWrathwithLineReader.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc100.GeeWrathwithLineReader.Robot;
import org.usfirst.frc100.GeeWrathwithLineReader.subsystems.DriveTrain;
import com.sun.squawk.util.MathUtils;

/**
 *
 */
public class  Align extends Command
{
    private final DriveTrain driveTrain = Robot.driveTrain;
    
    private final double width = 24.5;
    private boolean lTriggered;
    private boolean rTriggered;
    private boolean leftInPos;
    private boolean rightInPos;
    private double displacement;
    private boolean doneTurn;
    private boolean doneAlign;

    public Align()
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

        requires(driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        leftInPos = false;
        rightInPos = false;
        displacement = 0.0;
        doneTurn = false;
        doneAlign = false;

        driveTrain.startEncoder();
        driveTrain.startCounter();
        driveTrain.setBearing(driveTrain.getAngle());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        lTriggered = driveTrain.getLeftTrigger();
        rTriggered = driveTrain.getRightTrigger();
        
        if(lTriggered && rTriggered)
        {
            if(!doneTurn && (!leftInPos || !rightInPos))
                driveTrain.autoDriveStraight(216.0, 0.6);
            else
                driveTrain.autoDriveStraight(-216.0, 0.6);
        }
        else if(!lTriggered && rTriggered)
        {
            if(!leftInPos)
            {
               driveTrain.resetEncoder();
               driveTrain.autoDriveStraight(216.0, 0.6);
            }
            if(rightInPos)
            {
                displacement = -driveTrain.getDistance();
            }
            leftInPos = true;
        }
        else if(lTriggered && !rTriggered)
        {
            if(!rightInPos)
            {
                driveTrain.resetEncoder();
                driveTrain.autoDriveStraight(216.0, 0.6);
            }
            if(leftInPos)
            {
                displacement = driveTrain.getDistance();
            }
            rightInPos = true;
        }
        else if(!lTriggered && !rTriggered)
        {
            if(doneTurn)
            {
                driveTrain.tankDrive(0.0, 0.0);
                doneAlign = true;
            }
            else
            {
                driveTrain.autoDriveStraight(216.0, 0.6);
            }
        }
        
        if(leftInPos && rightInPos)
        {
            doneTurn = driveTrain.autoTurnByAngle((180.0 / Math.PI) * MathUtils.atan(displacement / width));
        }
        
        if(Robot.oi.getDualshock().getRawButton(5))
            doneAlign = true;

        System.out.print("Turning:" + doneTurn + " ");
        driveTrain.updateDashboard();
        SmartDashboard.putBoolean("Left Black Line", lTriggered);
        SmartDashboard.putBoolean("Right Black Line", rTriggered);
        SmartDashboard.putNumber("Encoder Distance", driveTrain.getDistance());
        SmartDashboard.putBoolean("Is Aligning", true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return doneAlign;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        SmartDashboard.putBoolean("Is Aligning", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        end();
    }
}
