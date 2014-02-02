package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.OI;

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
        
        driveTrain.drive(-OI.driverLeft.getY(), -OI.driverRight.getX());
        SmartDashboard.putNumber("Joy1", -OI.driverLeft.getY());
        SmartDashboard.putNumber("Joy2", -OI.driverRight.getX());
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
