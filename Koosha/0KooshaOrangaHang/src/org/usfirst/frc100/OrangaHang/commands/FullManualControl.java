/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import org.usfirst.frc100.OrangaHang.OI;

/**
 *
 * @author Team100
 */
public class FullManualControl extends CommandBase {
    
    public FullManualControl()
    {
        // Use requires() here to declare subsystem dependencies
        requires(climber);
        requires(shooter);
        requires(driveTrain);
        requires(intake);
        requires(tower);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        double speed = OI.driverLeft.getY();
        
        if(OI.tiltClimbButton.get())
        {
            driveTrain.setLeftMotor(speed);
        }
        
        if(OI.tiltShootButton.get())
        {
            driveTrain.setRightMotor(speed);
        }
        
        if(OI.tiltIntakeButton.get())
        {
            climber.manualControl(speed);
        }
        
        if(OI.primeShootButton.get())
        {
            shooter.setFrontMotor(speed);
        }
        
        if(OI.shootButton.get())
        {
            shooter.setBackMotor(speed);
        }
        
        if(OI.primeDumpButton.get())
        {
            //intake.manualControl(speed);
        }
        
        if(OI.intakeButton.get())
        {
            tower.manualControl(speed);
        }
        
        if(OI.manipulator.getRawAxis(6)<0.0)
        {
            driveTrain.shiftHighGear();
        }
        
        if(OI.manipulator.getRawAxis(6)>0.0)
        {
            driveTrain.shiftLowGear();
        }
        
        if(OI.manipulator.getRawAxis(5)<0.0 && !tower.isStowed())
        {
            tower.deployArms();
        }
        
        if(OI.manipulator.getRawAxis(5)>0.0 && tower.isStowed())
        {
            tower.deployArms();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        driveTrain.setLeftMotor(0.0);
        driveTrain.setRightMotor(0.0);
        climber.disable();
        shooter.setFrontMotor(0.0);
        shooter.setBackMotor(0.0);
        //intake.setMotor(0.0);
        tower.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        driveTrain.setLeftMotor(0.0);
        driveTrain.setRightMotor(0.0);
        climber.disable();
        shooter.setFrontMotor(0.0);
        shooter.setBackMotor(0.0);
        tower.disable();
    }
}
