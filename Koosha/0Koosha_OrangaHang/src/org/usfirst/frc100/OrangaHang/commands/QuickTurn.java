/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc100.OrangaHang.commands;

import org.usfirst.frc100.OrangaHang.subsystems.PIDBundle.PositionPIDBase;

/**
 *
 * @author Team100
 */
public class QuickTurn extends CommandBase {
    
    private boolean highGear;
    private boolean lowGear;
    private PositionPIDBase turnControl;
    private double setPoint;
    
    public QuickTurn(double setPoint)
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(driveTrain);
        highGear = driveTrain.isHighGear();
        lowGear = !driveTrain.isHighGear();
        turnControl = new PositionPIDBase(1.0, "Turn Controller");
        
        if(driveTrain.isHighGear())
        {
            turnControl.setKP(0.0025);
            turnControl.setKI(0.000);
            turnControl.setKD(0.000);
        }
        else
        {
            turnControl.setKP(0.005);
            turnControl.setKI(0.000);
            turnControl.setKD(0.000);
        }
        
        this.setPoint = setPoint;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        driveTrain.resetGyro();
        turnControl.setSetpoint(setPoint);
        turnControl.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        turnControl.setInput(driveTrain.getGyro());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return Math.abs(setPoint - driveTrain.getGyro()) < 0.8;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        turnControl.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        turnControl.disable();
    }
}
